package com.qFun.qFun.modules.apply.entity;

import java.util.Date;

import com.qFun.qFun.common.persistence.DataEntity;
import com.qFun.qFun.modules.sys.entity.User;

public class Approval extends DataEntity<Approval>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id ;//主键id
	
	private String uId;//用户id
	
	private String tId;//目标id
	
	private Integer bStatus;//审批流程 0 未完成 1 已完成
	
	private String pId;//下一级审批人
	
	private Integer pStatus;//审批状态 0 未审批 1 已审批
	
	private Integer result ;//结果 0 "" 1 驳回 2 通过
	
	private Date pDate;//审批时间
	
	private Date startDate ;//申请时间
	
	private Date endDate;//

	private Integer step;//流程步骤 
	
	private Integer isDel ;//是否删除 0 未删除 1 已删除
	
	private Integer type;//申请类型
	
	private User user;
	
	private LeaveApproval leaveApproval;
	

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LeaveApproval getLeaveApproval() {
		return leaveApproval;
	}

	public void setLeaveApproval(LeaveApproval leaveApproval) {
		this.leaveApproval = leaveApproval;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String gettId() {
		return tId;
	}

	public void settId(String tId) {
		this.tId = tId;
	}

	public Integer getbStatus() {
		return bStatus;
	}

	public void setbStatus(Integer bStatus) {
		this.bStatus = bStatus;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public Integer getpStatus() {
		return pStatus;
	}

	public void setpStatus(Integer pStatus) {
		this.pStatus = pStatus;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public Date getpDate() {
		return pDate;
	}

	public void setpDate(Date pDate) {
		this.pDate = pDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	
	

}
