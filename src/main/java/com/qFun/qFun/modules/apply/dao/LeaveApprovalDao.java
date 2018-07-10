package com.qFun.qFun.modules.apply.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.qFun.qFun.common.persistence.CrudDao;
import com.qFun.qFun.common.persistence.annotation.MyBatisDao;
import com.qFun.qFun.modules.apply.entity.LeaveApproval;


@MyBatisDao
public interface LeaveApprovalDao extends CrudDao<LeaveApproval>{
	
	public LeaveApproval getByuserIdAndStatus(@Param("uId")String uId,@Param("status")int status);
	
	public List<LeaveApproval> getParams(Map<String,Object> map);

}
