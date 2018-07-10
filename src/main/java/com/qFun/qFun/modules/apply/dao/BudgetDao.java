package com.qFun.qFun.modules.apply.dao;


import com.qFun.qFun.common.persistence.CrudDao;
import com.qFun.qFun.common.persistence.annotation.MyBatisDao;
import com.qFun.qFun.modules.apply.entity.Budget;

@MyBatisDao
public interface BudgetDao extends CrudDao<Budget>{
	
	//public int updateExpiredWeight(Budget budget);
	
	
	public Budget getBudget(String bId);

}
