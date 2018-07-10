package com.qFun.qFun.modules.apply.dao;

import java.util.List;

import com.qFun.qFun.common.persistence.CrudDao;
import com.qFun.qFun.common.persistence.annotation.MyBatisDao;
import com.qFun.qFun.modules.apply.entity.Loan;


@MyBatisDao
public interface LoanDao extends CrudDao<Loan>{
	
	
	public List<Loan> getPage(Loan loan);
	
	public Loan getLoanAndUser(String id);
	
	public List<Loan> getBudgetRecord(Loan loan);

}
