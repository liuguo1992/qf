package com.qFun.qFun.modules.apply.dao;

import java.util.List;


import java.util.Map;


import com.qFun.qFun.common.persistence.CrudDao;
import com.qFun.qFun.common.persistence.annotation.MyBatisDao;
import com.qFun.qFun.modules.apply.entity.BudgetRecord;

@MyBatisDao
public interface BudgetRecordDao extends CrudDao<BudgetRecord>{
	
	
	/**
	 * 修改审批记录
	 * @return
	 */
	int updateBudgetRecord(BudgetRecord budgetRecord);
	

	
	public List<BudgetRecord> getPage(BudgetRecord budgetRecord);
	
	public List<BudgetRecord> find(BudgetRecord budgetRecord);
	
	
	public BudgetRecord getLeaveById(String id);
	
	public BudgetRecord getById(String id);
	
	public BudgetRecord getParams( Map<String,Object> param);

}
