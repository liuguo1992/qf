package com.qFun.qFun.modules.apply.dao;

import com.qFun.qFun.common.persistence.CrudDao;
import com.qFun.qFun.common.persistence.annotation.MyBatisDao;
import com.qFun.qFun.modules.apply.entity.ChargeDetail;


@MyBatisDao
public interface ChargeDetailDao extends CrudDao<ChargeDetail>{
	
	public ChargeDetail getByBudgetId(String bId);

}
