package com.qFun.qFun.modules.attendance.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.qFun.qFun.common.persistence.CrudDao;
import com.qFun.qFun.common.persistence.annotation.MyBatisDao;
import com.qFun.qFun.modules.attendance.entity.AttLog;

@MyBatisDao
public interface AttLogDao extends CrudDao<AttLog>{
	
	
	public void insertBatch(@Param("list")List<AttLog> list);
	
	public List<AttLog> getCurrentDayAttLog(Map<String, Object> params);
	
	public List<AttLog> getPeriodAttLog(Map<String, Object> params);
	
	public void truncateLog();
	
	public List<Integer> getEnrollNumberList(); 

}
