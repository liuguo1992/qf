/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.qFun.qFun.modules.sys.dao;

import java.util.List;

import com.qFun.qFun.common.persistence.TreeDao;
import com.qFun.qFun.common.persistence.annotation.MyBatisDao;
import com.qFun.qFun.modules.sys.entity.Office;

/**
 * 机构DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
	
	
	/**
	 * 查询部门负责人
	 * @param id
	 * @return
	 */
	public List<Office> getOfficeListByUserId(String id);
	
}
