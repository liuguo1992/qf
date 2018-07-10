package com.qFun.qFun.modules.apply.service;

import java.util.Date;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qFun.qFun.common.service.CrudService;
import com.qFun.qFun.common.utils.IdGen;
import com.qFun.qFun.modules.apply.dao.ApprovalDao;
import com.qFun.qFun.modules.apply.dao.BudgetRecordDao;
import com.qFun.qFun.modules.apply.dao.LeaveApprovalDao;
import com.qFun.qFun.modules.apply.entity.Approval;
import com.qFun.qFun.modules.apply.entity.BudgetRecord;
import com.qFun.qFun.modules.apply.entity.LeaveApproval;
import com.qFun.qFun.modules.sys.entity.User;
import com.qFun.qFun.modules.sys.utils.UserUtils;


@Service
@Transactional(readOnly = true)
public class LeaveApprovalService extends  CrudService<LeaveApprovalDao,LeaveApproval>{
	
	
	@Autowired
	private LeaveApprovalDao leaveApprovalDao;
	
	@Autowired
	private ApprovalDao approvalDao;
	
	@Autowired
	private BudgetRecordDao budgetRecordDao;
	
	
	
	
	@Transactional(readOnly = false)
	public void saveLeaveApproval(LeaveApproval leaveApproval) throws Exception{
		//获取当前用户信息
		User user = UserUtils.getUser();
		if(user == null){
			throw new RuntimeException("当前用户不存在！");
		}
		//创建一条请假单
		leaveApproval.setId(IdGen.uuid());
		leaveApproval.setuId(user.getId());
		leaveApprovalDao.insert(leaveApproval);
		
		//创建一条申请记录
		Approval approval = new Approval();
		approval.setId(IdGen.uuid());
		approval.setuId(user.getId());
		approval.settId(leaveApproval.getId());
		approval.setType(UserUtils.APPROVAL_TYPE_QJ);//请假
		//设置下一审批人为刘丹
		User ld = UserUtils.getByName(UserUtils.USER_LD);
		if(ld == null){
			throw new RuntimeException("审批人不存在，请核实！");
		}
		approval.setpId(ld.getId());
		approval.setStartDate(new Date());
		approval.setStep(1);
		approvalDao.insert(approval);
		
		//创建一条审批记录
		BudgetRecord budgetRecord = new BudgetRecord();
		budgetRecord.setId(IdGen.uuid());
		budgetRecord.settId(approval.getId());
		budgetRecord.setuId(ld.getId());
		budgetRecordDao.insert(budgetRecord);
		
	}

}
