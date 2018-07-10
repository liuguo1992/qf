package com.qFun.qFun.modules.apply.service;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qFun.qFun.common.persistence.Page;
import com.qFun.qFun.common.service.CrudService;
import com.qFun.qFun.common.utils.IdGen;
import com.qFun.qFun.modules.apply.dao.BudgetDao;
import com.qFun.qFun.modules.apply.dao.BudgetRecordDao;
import com.qFun.qFun.modules.apply.dao.ChargeDetailDao;
import com.qFun.qFun.modules.apply.entity.Budget;
import com.qFun.qFun.modules.apply.entity.BudgetRecord;
import com.qFun.qFun.modules.apply.entity.ChargeDetail;
import com.qFun.qFun.modules.apply.utils.NumberToCN;
import com.qFun.qFun.modules.sys.dao.OfficeDao;
import com.qFun.qFun.modules.sys.dao.RoleDao;
import com.qFun.qFun.modules.sys.entity.Office;
import com.qFun.qFun.modules.sys.entity.User;
import com.qFun.qFun.modules.sys.utils.UserUtils;

@Service
@Transactional(readOnly = true)
public class BudgetService extends CrudService<BudgetDao,Budget>{
	
	
	@Autowired
	private BudgetDao budgetDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private OfficeDao officeDao;
	
	@Autowired
	private BudgetRecordDao budgetRecordDao;
	
	@Autowired
	private ChargeDetailService chargeDetailService;
	
	@Autowired
	private ChargeDetailDao chargeDetailDao;
	
	@Transactional(readOnly = false)
	public Page<Budget> findPage(Page<Budget> page, Budget budget) {
		return super.findPage(page, budget);
	}
	
	
	
	/*
	 * 获取申请记录和审批记录
	 */
	public Map<String,Object> getBudgetInfo(Budget budget){
		Map<String,Object> map = new HashMap<>();
		//查询申请人的相关信息
		budget = budgetDao.getBudget(budget.getbId());
		if(budget == null ){
			throw new RuntimeException("该申请不存在，请仔细核实！");
		}
		map.put("budget", budget);
		//查询审批人的相关信息
		map.put("budgetRecord", budgetRecordDao.find(new BudgetRecord(null,budget.getbId(),new User(),new Budget())));
		//查询申请明细
		map.put("chargeDetail", chargeDetailDao.getByBudgetId(budget.getbId()));
		return map;
	}
	
	
	@Transactional(readOnly = false)
	public boolean saveBudget(Budget budget,ChargeDetail chargeDetail) throws Exception{
		//添加明细
		chargeDetail.setId(IdGen.uuid());
		chargeDetail.setbId(budget.getbId());
		//金额换成中文汉字
		double money = Double.parseDouble(chargeDetail.getMoney());
		String cAmout = NumberToCN.number2CNMontrayUnit(new BigDecimal(money));
		chargeDetail.setcAmout(cAmout);
		//将金额保留两位小数保存
		Double cny = Double.parseDouble(chargeDetail.getMoney());//转换成Double
		DecimalFormat df = new DecimalFormat("0.00");//格式化
		String _money = df.format(cny);
		chargeDetail.setMoney(_money);
		chargeDetailService.saveChargeDetail(chargeDetail);
		
		User user = UserUtils.getUser();
		//保存新建审批
		budget.setMoney(_money);
		budget.setuId(user.getId());
		
		//获取当前用户所属部门
		Office office = user.getOffice();
		
		//创建一条申请记录
		BudgetRecord record = new BudgetRecord();
		record.setId(IdGen.uuid());
		record.settId(budget.getbId());
		
		//如果当前用户和部门负责人是同一个人 则该用户是部门经理  。type = 2 类型为部门  1 为 公司 3 为部门一下的组 
		if(office.getPrimaryPerson() != null && office.getPrimaryPerson().getId().equals(user.getId()) && office.getType().equals("3")){
			//直接找到上一级部门总监
			User pUser = officeDao.get(office.getParentId()).getPrimaryPerson();
			record.setuId(pUser.getId());
			budget.setStep(8);
		}else{
			//找到对应的部门主管
			System.out.println("office-------------"+office.toString());
			if(office.getPrimaryPerson() == null){
				throw new RuntimeException("该部门还没有负责人，请先设置负责人！");
			}
			record.setuId(office.getPrimaryPerson().getId());
			budget.setStep(4);//设置当前的流程步骤。
		}
		//设置下一审批人
		budget.setpId(record.getuId());
		return budgetDao.insert(budget) > 0 && budgetRecordDao.insert(record) > 0 ? true:false;
		 
		//接入钉钉 通知用户
	} 
	
	
	
	
		
		
		/**
		 * 查询当前用户是否是部门负责人
		 * @param id
		 * @return
		 *//*
	    private boolean  checkIsOfficeManager(String id){
	    	Office result = officeDao.get(id);
				if(result.getPrimaryPerson().getId().equals(id)){
					return true;
			}
	    	return false;
	    }*/
}
