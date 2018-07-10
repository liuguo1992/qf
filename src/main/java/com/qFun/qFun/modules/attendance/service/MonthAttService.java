package com.qFun.qFun.modules.attendance.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qFun.qFun.common.service.CrudService;
import com.qFun.qFun.common.utils.IdGen;
import com.qFun.qFun.modules.apply.dao.LeaveApprovalDao;
import com.qFun.qFun.modules.apply.entity.LeaveApproval;
import com.qFun.qFun.modules.attendance.dao.AttLogDao;
import com.qFun.qFun.modules.attendance.dao.DayAttDao;
import com.qFun.qFun.modules.attendance.dao.MonthAttDao;
import com.qFun.qFun.modules.attendance.entity.DayAtt;
import com.qFun.qFun.modules.attendance.entity.MonthAtt;
import com.qFun.qFun.modules.attendance.utils.AttUtils;
import com.qFun.qFun.modules.sys.dao.UserDao;
import com.qFun.qFun.modules.sys.entity.User;

@Service
public class MonthAttService extends CrudService<MonthAttDao,MonthAtt>{
	
	
	private static final Logger logger = Logger.getLogger(MonthAttService.class); 
	
	@Autowired
	private MonthAttDao attMonthAttDao;
	
	
	@Autowired
	private DayAttDao dayAttDao;
	
	@Autowired
	private AttLogDao attLogDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private LeaveApprovalDao leaveApprovalDao;
	
	@Transactional
	public void insertMonthAttData() throws Exception{
		
		Map<String,Object> params = new HashMap<String, Object>();
		//获取当前年月
		Calendar now = Calendar.getInstance();  
		
		params.put("year", now.get(Calendar.YEAR));
        params.put("month", (now.get(Calendar.MONTH)+1));
        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH,1);
        params.put("qTime", cal.getTime());//前一个月的第一天
        
        now.add(Calendar.MONTH, 0);
        now.set(Calendar.DAY_OF_MONTH,1);
        params.put("sTime", now.getTime()); //当前月的第一天
        
        Calendar cle = Calendar.getInstance();
        cle.add(Calendar.MONTH, 1);
        cle.set(Calendar.DAY_OF_MONTH,1);
        params.put("eTime", cle.getTime()); //下个月的第一天
        
		List<Integer> list = attLogDao.getEnrollNumberList();
		
		for(Integer enrollNumber:list){
			User user = userDao.getUserByEnrollNumber(enrollNumber);
			if(user == null ){
				logger.error("当前用户不存在！");
				continue;
			}
			
			params.put("uId", user.getId());
			List<DayAtt> result = dayAttDao.getParamList(params);
			
			Integer lateTimes = 0; //迟到次数
			Integer latePay = 0; //迟到罚款
			Integer lateDays = 0;//迟到天数
			Integer overTimes = 0;//夜猫子加班次数
			Integer perfectAttAward = 200;//全勤
			MonthAtt monthAtt = new MonthAtt(); 
			for(DayAtt dayAtt : result){
				
				 //迟到次数
				 if(dayAtt.getIsLate() != null && dayAtt.getIsLate() == 1){
					 if(dayAtt.getLateTime() != null && Double.valueOf(dayAtt.getLateTime()) >= 1 ){ //迟到一个小时以上算两次迟到。
						 lateTimes += 2;
					 }
					 lateTimes += 1;
					 if(lateTimes <= 3){  //迟到三次不计入迟到次数
						 lateTimes = 0;
					 }
					
				 };
				 
				 //迟到罚款
				 if(lateTimes > 0 && dayAtt.getLateTime() != null && Double.valueOf(dayAtt.getLateTime()) < AttUtils._late_time_30){
					 latePay += AttUtils._late_Pay_50 ;
				 }
				 if(lateTimes > 0 && dayAtt.getLateTime() != null && Double.valueOf(dayAtt.getLateTime()) > AttUtils._late_time_30 && Double.valueOf(dayAtt.getLateTime()) < AttUtils._late_time_60){
					 latePay += AttUtils._late_Pay_100 ;
				 }
				 
				 //迟到天数
				 if(lateTimes > 0 && dayAtt.getIsLate() != null && dayAtt.getIsLate() == 1){
					 lateDays += 1;
					
				 };
				 //全勤奖
				 if(dayAtt.getIsLate() != null && dayAtt.getIsLate() == 1 && lateTimes > 0){
					 perfectAttAward = 0;
				 }
				 //夜猫子奖
				 if(dayAtt.getOverType() != null && dayAtt.getOverType() > 3){
					 overTimes += 1;
				 }
			} 
				
			 //查询当前用户事假天数
			 params.put("type", AttUtils._type_thing_leave);
			 monthAtt.setThingLeave(getLeaveDays(params));
			 
			 //查询病假天数
			 params.put("type", AttUtils._type_sick_leave);
			 monthAtt.setSickLeave(getLeaveDays(params));
			 
			 //查询带薪假天数
			 params.put("type", AttUtils._type_paid_leave);
			 monthAtt.setPaidLeave(getLeaveDays(params));
			 
			 //实际出勤天数
			 Integer days = result.size() - Integer.valueOf(monthAtt.getThingLeave()) - Integer.valueOf(monthAtt.getSickLeave()) - Integer.valueOf(monthAtt.getPaidLeave());
			 monthAtt.setAttDays(days.toString());
			 monthAtt.setLateTimes(lateTimes.toString());
			 monthAtt.setLatePay(latePay.toString());
			 monthAtt.setLateDays(lateDays.toString());
			 monthAtt.setPerfectAttAward(perfectAttAward);
			 monthAtt.setId(IdGen.uuid());
			 monthAtt.setuId(user.getId());
			 monthAtt.setMonth(now.get(Calendar.MONTH)+1);
			 monthAtt.setYear(now.get(Calendar.YEAR));
			 if(overTimes >= 5){
				 monthAtt.setNightCatAward(300);
			 }
			 attMonthAttDao.insert(monthAtt);
		}
		
	}
	
	
	
	/**
	 * 获取假期天数
	 * @param params
	 * @return
	 */
	private String getLeaveDays(Map<String,Object> params){
		//一个之内的请假天数
		if(params == null){
			logger.error("参数不能为空！");
			throw new RuntimeException("参数不能为空！");
		}
		
		List<LeaveApproval> result = leaveApprovalDao.getParams(params);
		if(result.size() == 0){
			return "0";
		}
		
		BigDecimal bd = new BigDecimal("0.0") ;
		for(LeaveApproval approval : result){
			
			BigDecimal bd_ = new BigDecimal(approval.getDays());
			Date startDate = approval.getStartDate();
			Date endDate = approval.getEndDate();
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate); //起始时间
			Calendar cle = Calendar.getInstance();
			cle.setTime(endDate); //结束时间
			
			if((cle.get(Calendar.MONTH)+1) - (cal.get(Calendar.MONTH)+1) > 0 ){ //跨月
				
				cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
				Date date = cal.getTime();
				
				long days = (date.getTime() - startDate.getTime())/(24*60*60*1000); //月底月起始时间相减
				BigDecimal _bd = new BigDecimal(days);
				
				bd = bd.add(_bd);
			}
			
			bd = bd.add(bd_);
		}
		//跨月的请假天数
		/*List<LeaveApproval> result_ = leaveApprovalDao.getParams(params);
		if(result_.size() == 0){
			return bd.toString();		
		}
		for(LeaveApproval approval : result_){
			
		}*/
		return bd.toString();		
	}

}
