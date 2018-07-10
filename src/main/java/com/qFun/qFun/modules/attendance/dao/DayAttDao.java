package com.qFun.qFun.modules.attendance.dao;

import java.util.List;
import java.util.Map;

import com.qFun.qFun.common.persistence.CrudDao;
import com.qFun.qFun.common.persistence.annotation.MyBatisDao;
import com.qFun.qFun.modules.attendance.entity.DayAtt;



@MyBatisDao
public interface DayAttDao extends CrudDao<DayAtt>{

	public List<DayAtt> getParamList(Map<String,Object> map);
}
