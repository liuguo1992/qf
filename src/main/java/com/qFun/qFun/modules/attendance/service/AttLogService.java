package com.qFun.qFun.modules.attendance.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.qFun.qFun.common.service.CrudService;
import com.qFun.qFun.common.utils.IdGen;
import com.qFun.qFun.common.utils.PropertiesFileUtil;
import com.qFun.qFun.modules.apply.dao.LeaveApprovalDao;
import com.qFun.qFun.modules.attendance.dao.AttLogDao;
import com.qFun.qFun.modules.attendance.dao.DayAttDao;
import com.qFun.qFun.modules.attendance.entity.AttLog;
import com.qFun.qFun.modules.attendance.entity.DayAtt;
import com.qFun.qFun.modules.attendance.utils.ZkemSDK;
import com.qFun.qFun.modules.sys.dao.UserDao;
import com.qFun.qFun.modules.sys.entity.User;


@Service
@Transactional(readOnly = true)
public class AttLogService extends CrudService<AttLogDao,AttLog>{

	private Logger logger = Logger.getLogger(AttLogService.class);
	
	private  static final PropertiesFileUtil config = PropertiesFileUtil.getInstance("qFun");
	private static final String KQIP = config.get("KQIP"); 
	private static final String onWork_ = config.get("ONWORK"); //正常上班时间 09:30:00
	private static final String offWork_ = config.get("OFFWORK"); //正常下班时间 18:30:00
	private static final String overTime_22_ = config.get("OVERTIME_22"); //打卡节点1 22:00:00
	private static final String overTime_23_ = config.get("OVERTIME_23"); //节点2 23:00:00
	private static final String overTime_10_ = config.get("OVERTIME_10"); // 10:00:00
	private static final String overTime_24_ = config.get("OVERTIME_24"); // 24:00:00
	private static final String overTime_11_ = config.get("OVERTIME_11"); //11:00:00
	private static final String overTime_03_ = config.get("OVERTIME_03"); //03:00:00
	private static final String overTime_14_ = config.get("OVERTIME_14"); //14:00:00
	private static final String overTime_06_ = config.get("OVERTIME_06"); //06:00:00
	private static final String overTime_04_ = config.get("OVERTIME_04"); //04:00:00
	private static final String TIME_04 = " 04:00:00";
	
	
	
	
	
	
	@Autowired
	private AttLogDao attLogDao;
	
	@Autowired
	private LeaveApprovalDao leaveApprovalDao;
	
	@Autowired
	private DayAttDao attDao;
	
	@Autowired
	private DayAttService dayAttService;
	
	@Autowired
	private UserDao userDao;

	
	
	
	private static final Integer PORT = 4370;
	
	@Transactional(readOnly = false)
	public void saveAttLog() throws Exception{
		ZkemSDK sdk = new ZkemSDK();
		
		boolean connFlag = sdk.connect(KQIP, PORT);
		if(connFlag){
			List<Map<String, Object>> l = null ;
			List<AttLog> list = new ArrayList<AttLog>();
			boolean result  = sdk.readGeneralLogData();
			if(result){
				l = sdk.getGeneralLogData();
			}
			AttLog attLog = null;
			for (Map<String, Object> map : l) {
				attLog = JSON.parseObject(JSON.toJSONString(map), AttLog.class);
				if(attLog == null) continue;
				attLog.setId(IdGen.uuid());
				list.add(attLog);
				
			}
			attLogDao.insertBatch(list);
		}
		List<Integer> result = attLogDao.getEnrollNumberList();
		System.out.println(result);
		for(Integer enrollerNumber:result){
			try {
				getCurrentDayAttLog(enrollerNumber);
			} catch (RuntimeException e) {
				logger.info(e);
				continue;
			}
		}
		
	}
	
	
	
	
	private void getCurrentDayAttLog(Integer enrollNumber) throws ParseException{
		Map<String, Object> params = new HashMap<String, Object>();
		
		Calendar now = Calendar.getInstance();  
       /* params.put("year", now.get(Calendar.YEAR));
        params.put("month", (now.get(Calendar.MONTH) + 1));
        params.put("day", now.get(Calendar.DAY_OF_MONTH));
        params.put("enrollNumber", user.getEnrollNumber());
        */
		Integer month = (now.get(Calendar.MONTH) + 1);
		String _month = month+"" ;
		if(month < 10){
			_month = "0"+month; 
		}
		
		//获取用户当天的所有打卡记录   取前一天4点 至当日凌晨4点的数据
		now.add(Calendar.DAY_OF_MONTH, -1);
        String sTime = now.get(Calendar.YEAR)+"-"+_month+"-"+now.get(Calendar.DAY_OF_MONTH)+TIME_04;
        now.add(Calendar.DAY_OF_MONTH, 1);
        String eTime = now.get(Calendar.YEAR)+"-"+_month+"-"+now.get(Calendar.DAY_OF_MONTH)+TIME_04;
        
        params.put("sTime", sTime);
        params.put("eTime", eTime);
        params.put("enrollNumber", enrollNumber);
        
		List<AttLog> list = attLogDao.getCurrentDayAttLog(params);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		//获取当前用户
		User user = userDao.getUserByEnrollNumber(enrollNumber);
		if(user == null){
			logger.error("当前用户不存在！");
			throw new RuntimeException("当前用户不存在！");
		}
		
		DayAtt dayAtt = new DayAtt();
		dayAtt.setId(IdGen.uuid());
		dayAtt.setuId(user.getId());
		//date的格式转换
		now.add(Calendar.DAY_OF_MONTH, -1);
		String StringDate = sdf.format(now.getTime());
		dayAtt.setWorkTime(StringDate);
		dayAtt.setYear(now.get(Calendar.YEAR));
		dayAtt.setMonth(now.get(Calendar.MONTH) + 1);
		
		if(list.size() == 1){ //打卡一次设置为签到
			 AttLog attLog = list.get(0);   
			 dayAtt.setOnWorkTime(attLog.getTime());
		}
		if(list.size() > 1){ 
			AttLog attLog = list.get(list.size()-1);//签到
			AttLog attLog2 = list.get(0);//签退
			dayAtt.setOnWorkTime(attLog.getTime());
			dayAtt.setOffWorkTime(attLog2.getTime());
		}
		attDao.insert(dayAtt);
		
		params.put("uId", user.getId());
		params.put("workTime", dayAtt.getWorkTime());
		
		//查询列表
		List<DayAtt> result = attDao.getParamList(params);
		if(result.size() == 0){
			throw new RuntimeException("用户当天考勤记录不存在！");
		}
		String onWorkTime_;
		String offWorkTime_;
		String workTime_;
		DateFormat df = DateFormat.getTimeInstance();//只显示出时分秒
		if(result.size() == 1){
			dayAtt = result.get(0); //当天的考勤
			Date onWorkTime = dayAtt.getOnWorkTime();//签到时间
			Date offWorkTime = dayAtt.getOffWorkTime();//签退
			onWorkTime_ = df.format(onWorkTime);
			offWorkTime_ = df.format(offWorkTime);
			if( compTime(onWorkTime_, onWork_) < 0 && compTime(onWorkTime_, overTime_04_) > 0 ){
				if(compTime(onWorkTime_, offWork_) < 0){
					//早退
					dayAtt.setLeaveEarly(1);
				}	
				if(compTime(offWorkTime_, offWork_) > 0 && compTime(offWorkTime_, overTime_04_) < 0){ 
					//正常上班
					dayAtt.setIsWork(1);
				}
				if(compTime(offWorkTime_, overTime_22_) > 0 && compTime(offWorkTime_, overTime_23_) < 0){
					dayAtt.setIsOverTime(1);
					dayAtt.setOverType(1); //22-23点的加班
				}
				if(compTime(offWorkTime_, overTime_23_) > 0 && compTime(offWorkTime_, overTime_24_) < 0){
					dayAtt.setIsOverTime(1);
					dayAtt.setOverType(2); //23-24点的加班
				}
				if(compTime(offWorkTime_, overTime_24_) > 0 && compTime(offWorkTime_, overTime_03_) < 0){
					dayAtt.setIsOverTime(1);
					dayAtt.setOverType(3); //24-03点的加班
				}
				if(compTime(offWorkTime_, overTime_03_) < 0 && compTime(offWorkTime_, overTime_04_) < 0){
					dayAtt.setIsOverTime(1);
					dayAtt.setOverType(4); //03-04点的加班
				}
			}
			if(compTime(onWorkTime_, onWork_) > 0){
				double lateTime = compTime(onWorkTime_, onWork_)/3600;
				
				dayAtt.setLateTime(lateTime+"");
				
				if(Double.valueOf(lateTime) > 1){
					dayAtt.setIsCompletion(1); //旷工
				}else{
					dayAtt.setIsLate(1);
				}
			}
			attDao.update(dayAtt);
			
		}
		if(result.size() > 1){
			dayAtt = result.get(0);//当天的最新考勤
			DayAtt dayAtt2 = result.get(1); //前一天的考勤
			
			Date workTime = dayAtt2.getOffWorkTime();
			workTime_ = df.format(workTime);
			Date onWorkTime = dayAtt.getOnWorkTime();//签到时间
			Date offWorkTime = dayAtt.getOffWorkTime();//签退
			onWorkTime_ =  df.format(onWorkTime);
			offWorkTime_ = df.format(offWorkTime);
			if(compTime(onWorkTime_, overTime_06_) < 0 && compTime(onWorkTime_, overTime_04_) > 0 ){ //04-06 的打卡
				//前一天的加班卡
				dayAtt2.setIsOverTime(1);
				attDao.update(dayAtt2);
			}
			
			if(compTime(onWorkTime_, overTime_06_) > 0 && compTime(onWorkTime_, onWork_) < 0){ //正常上班卡  //06-9.30 
				if(compTime(offWorkTime_, offWork_) < 0){
					//早退
					dayAtt.setLeaveEarly(1);
				}	
				if(compTime(offWorkTime_, offWork_) > 0 && compTime(offWorkTime_, overTime_22_) < 0){ 
					//正常上班
					dayAtt.setIsWork(1);
				}
				if(compTime(offWorkTime_, overTime_22_) > 0 && compTime(offWorkTime_, overTime_23_) < 0){
					dayAtt.setIsOverTime(1);
					dayAtt.setOverType(1); //22-23点的加班
				}
				if(compTime(offWorkTime_, overTime_23_) > 0 && compTime(offWorkTime_, overTime_24_) < 0){
					dayAtt.setIsOverTime(1);
					dayAtt.setOverType(2); //23-24点的加班
				}
				if(compTime(offWorkTime_, overTime_24_) > 0 && compTime(offWorkTime_, overTime_03_) < 0){
					dayAtt.setIsOverTime(1);
					dayAtt.setOverType(3); //24-03点的加班
				}
				if(compTime(offWorkTime_, overTime_03_) > 0 && compTime(offWorkTime_, overTime_04_) < 0){
					dayAtt.setIsOverTime(1);
					dayAtt.setOverType(4); //03-04点的加班
				}
				attDao.update(dayAtt);
			}
			if(compTime(onWorkTime_, onWork_) > 0 && compTime(onWorkTime_, overTime_10_) < 0){ //迟到或者前一天的加班之后的上班卡
				//前一天加班之后的上班卡 
				if(compTime(workTime_, overTime_22_) > 0 ){
					dayAtt2.setIsOverTime(1);
					attDao.update(dayAtt2);
					if(compTime(offWorkTime_, offWork_) < 0){
						//早退
						dayAtt.setLeaveEarly(1);
						attDao.update(dayAtt);
					} 
					if(compTime(offWorkTime_, offWork_) > 0 && compTime(offWorkTime_, overTime_22_) < 0){
						//正常上下班
						dayAtt.setIsWork(1);
						attDao.update(dayAtt);
					} 
					if(compTime(offWorkTime_, overTime_22_) > 0 && compTime(offWorkTime_, overTime_23_) < 0){
						dayAtt.setIsOverTime(1);
						dayAtt.setOverType(1); //22-23点的加班
						attDao.update(dayAtt2);
					}
					if(compTime(offWorkTime_, overTime_23_) > 0 && compTime(offWorkTime_, overTime_24_) < 0){
						dayAtt.setIsOverTime(1);
						dayAtt.setOverType(2); //23-24点的加班
						attDao.update(dayAtt);
					}
					if(compTime(offWorkTime_, overTime_24_) > 0 && compTime(offWorkTime_, overTime_03_) < 0){
						dayAtt.setIsOverTime(1);
						dayAtt.setOverType(3); //24-03点的加班
						attDao.update(dayAtt);
					}
					if(compTime(offWorkTime_, overTime_03_) > 0 && compTime(offWorkTime_, overTime_04_) < 0){
						dayAtt.setIsOverTime(1);
						dayAtt.setOverType(4); //03-04点的加班
						attDao.update(dayAtt);
					}
				}else{ //迟到
					double lateTime = compTime(onWorkTime_, onWork_)/3600;
					dayAtt.setLateTime(lateTime+"");
					if(lateTime > 1){
						dayAtt.setIsCompletion(1); //旷工
					}else{
						dayAtt.setIsLate(1);
					}
					attDao.update(dayAtt);
				}
				
			}
			if(compTime(onWorkTime_, overTime_10_) > 0 && compTime(onWorkTime_, overTime_11_) < 0){
				//前一天加班之后的上班卡 
				if(compTime(workTime_, overTime_23_) > 0 ){
					dayAtt2.setIsOverTime(1);
					attDao.update(dayAtt2);
					if(compTime(offWorkTime_, offWork_) > 0){
						//早退
						dayAtt.setLeaveEarly(1);
						attDao.update(dayAtt);
					}
					if(compTime(offWorkTime_, offWork_) > 0 && compTime(offWorkTime_, overTime_22_) < 0){
						//正常上下班
						dayAtt.setIsWork(1);
						attDao.update(dayAtt);
					}
					if(compTime(offWorkTime_, overTime_22_) > 0 && compTime(offWorkTime_, overTime_23_) < 0){
						dayAtt.setIsOverTime(1);
						dayAtt.setOverType(1); //22-23点的加班
						attDao.update(dayAtt2);
					}
					if(compTime(offWorkTime_, overTime_23_) > 0 && compTime(offWorkTime_, overTime_24_) < 0){
						dayAtt.setIsOverTime(1);
						dayAtt.setOverType(2); //23-24点的加班
						attDao.update(dayAtt);
					}
					if(compTime(offWorkTime_, overTime_24_) > 0 && compTime(offWorkTime_, overTime_03_) < 0 ){
						dayAtt.setIsOverTime(1);
						dayAtt.setOverType(3); //24-03点的加班
						attDao.update(dayAtt);
					}
					if(compTime(offWorkTime_, overTime_03_) > 0&&  compTime(offWorkTime_, overTime_04_) < 0){
						dayAtt.setIsOverTime(1);
						dayAtt.setOverType(4); //03-04点的加班
						attDao.update(dayAtt);
					}
				}else{ //迟到
					double lateTime = compTime(onWorkTime_, onWork_)/3600;
					dayAtt.setLateTime(lateTime+"");
					if(Double.valueOf(lateTime) > 1){
						dayAtt.setIsCompletion(1); //旷工
					}else{
						dayAtt.setIsLate(1);
					}
					attDao.update(dayAtt);
				}
			}
			
			if(compTime(onWorkTime_, overTime_11_) > 0 && compTime(onWorkTime_, overTime_14_) < 0){
				//前一天加班之后的上班卡 
				if(compTime(workTime_, overTime_24_) > 0 ){
					dayAtt2.setIsOverTime(1);
					attDao.update(dayAtt2);
					if(compTime(offWorkTime_, offWork_) < 0){
						//早退
						dayAtt.setLeaveEarly(1);
						attDao.update(dayAtt);
					}  
					if(compTime(offWorkTime_, offWork_) > 0 && compTime(offWorkTime_, overTime_22_) < 0){
						//正常上下班
						dayAtt.setIsWork(1);
						attDao.update(dayAtt);
					} 
					if(compTime(offWorkTime_, overTime_22_) > 0 && compTime(offWorkTime_, overTime_23_) < 0){  
						dayAtt.setIsOverTime(1);
						dayAtt.setOverType(1); //22-23点的加班
						attDao.update(dayAtt2);
					}
					if(compTime(offWorkTime_, overTime_23_) > 0 && compTime(offWorkTime_, overTime_24_) < 0){
						dayAtt.setIsOverTime(1);
						dayAtt.setOverType(2); //23-24点的加班
						attDao.update(dayAtt);
					}
					if(compTime(offWorkTime_, overTime_24_) > 0 && compTime(offWorkTime_, overTime_03_) < 0){
						dayAtt.setIsOverTime(1);
						dayAtt.setOverType(3); //24-03点的加班
						attDao.update(dayAtt);
					}
					if(compTime(offWorkTime_, overTime_03_) > 0 && compTime(offWorkTime_, overTime_04_) < 0){
						dayAtt.setIsOverTime(1);
						dayAtt.setOverType(4); //03-04点的加班
						attDao.update(dayAtt);
					}
				}else{ //迟到
					double lateTime = compTime(onWorkTime_, onWork_)/3600;
					dayAtt.setLateTime(lateTime+"");
					if(Double.valueOf(lateTime) > 1){
						dayAtt.setIsCompletion(1); //旷工
					}else{
						dayAtt.setIsLate(1);
					}
					attDao.update(dayAtt);
				}
			}
			if(compTime(onWorkTime_, overTime_14_) > 0 ){
				//前一天加班之后的上班卡 
				if(compTime(workTime_, overTime_03_) > 0 ){
					dayAtt2.setIsOverTime(1);
					attDao.update(dayAtt2);
					if(compTime(offWorkTime_, offWork_) < 0){ 
						//早退
						dayAtt.setLeaveEarly(1);
						attDao.update(dayAtt);
					}
					if(compTime(offWorkTime_, offWork_) > 0 && compTime(offWorkTime_, overTime_22_) < 0){
						//正常上下班
						dayAtt.setIsWork(1);
						attDao.update(dayAtt);
					}
					if(compTime(offWorkTime_, overTime_22_) > 0 && compTime(offWorkTime_, overTime_23_) < 0){
						dayAtt.setIsOverTime(1);
						dayAtt.setOverType(1); //22-23点的加班
						attDao.update(dayAtt2);
					}
					if(compTime(offWorkTime_, overTime_23_) > 0 && compTime(offWorkTime_, overTime_24_) < 0){
						dayAtt.setIsOverTime(1);
						dayAtt.setOverType(2); //23-24点的加班
						attDao.update(dayAtt);
					}
					if(compTime(offWorkTime_, overTime_24_) >0 && compTime(offWorkTime_, overTime_03_) < 0){
						dayAtt.setIsOverTime(1);
						dayAtt.setOverType(3); //24-03点的加班
						attDao.update(dayAtt);
					}
					if(compTime(offWorkTime_, overTime_03_) > 0 && compTime(offWorkTime_, overTime_04_) < 0 ){
						dayAtt.setIsOverTime(1);
						dayAtt.setOverType(4); //03-04点的加班
						attDao.update(dayAtt);
					}
				}else{ //迟到
					double lateTime = compTime(onWorkTime_, onWork_)/3600;
					dayAtt.setLateTime(lateTime+"");
					if(Double.valueOf(lateTime) > 1){
						dayAtt.setIsCompletion(1); //旷工
					}else{
						dayAtt.setIsLate(1);
					}
					attDao.update(dayAtt);
				}
			}
		}
	}
	
	
	/** 
	 * 获取迟到的小时
	 * @param onWorkTime
	 * @param onWork
	 * @return
	 */
	/*private String getLateTime(Date onWorkTime,Date onWork){
		long hours = (onWorkTime.getTime() - onWork.getTime())/(1000*3600);
		return String.valueOf(hours);
	}*/
	
	
	
	
	 
	private  int compTime(String s1,String s2){
		try {
			if (s1.indexOf(":")<0||s2.indexOf(":")<0) {
				System.out.println("格式不正确");
			}else{
				String[]array1 = s1.split(":");
				int total1 = Integer.valueOf(array1[0])*3600+Integer.valueOf(array1[1])*60+Integer.valueOf(array1[2]);
				String[]array2 = s2.split(":");
				int total2 = Integer.valueOf(array2[0])*3600+Integer.valueOf(array2[1])*60+Integer.valueOf(array2[2]);
				return total1-total2;
			}
		} catch (NumberFormatException e) {
			return 0;   
		}  
		return 0;
 
	}
}
