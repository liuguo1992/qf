package com.qFun.qFun.modules.apply.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qFun.qFun.common.persistence.Page;
import com.qFun.qFun.common.service.CrudService;
import com.qFun.qFun.modules.apply.dao.ApprovalDao;
import com.qFun.qFun.modules.apply.entity.Approval;

@Service
@Transactional(readOnly = true)
public class ApprovalService extends CrudService<ApprovalDao,Approval>{
	
	
	@Autowired
	private ApprovalDao approvalDao;
	
	

	@Transactional(readOnly = false)
	public Page<Approval> findPage(Page<Approval> page, Approval approval) {
		return super.findPage(page, approval);
	}

}
