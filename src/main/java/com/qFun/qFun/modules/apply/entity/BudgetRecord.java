package com.qFun.qFun.modules.apply.entity;

import java.util.Date;

import com.qFun.qFun.common.persistence.DataEntity;
import com.qFun.qFun.modules.sys.entity.Office;
import com.qFun.qFun.modules.sys.entity.Role;
import com.qFun.qFun.modules.sys.entity.User;


/**
 * 审批记录实体类
 * @author lg
 *
 */
public class BudgetRecord extends DataEntity<BudgetRecord>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id ;//记录编号
	
	private String tId ;//目标id
	
	private String uId;//审批人id/
	
	private Integer result ;//审批结果
	
	private Integer status;//审批状态
	
	private String note ;//备注
	
	private Integer isDel;//是否删除
	
	private Date createDate ;//审批时间
	
	
	private Budget budget;
	
	private User user;
	
	private Role role;
	
	private Office office;
	
	private Approval approval;
	
	private LeaveApproval leaveApproval;
	
	private Loan loan;
	
	
	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	public User getUser() {
		return user;
	}

	public BudgetRecord() {
		super();
	}
	
	public BudgetRecord(String id,String tId,User user,Budget budget) {
		super(id);
		this.tId  = tId;
		this.user = user;
		this.budget = budget;
	}
	
	public String gettId() {
		return tId;
	}

	public void settId(String tId) {
		this.tId = tId;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Budget getBudget() {
		return budget;
	}

	public void setBudget(Budget budget) {
		this.budget = budget;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}


	public Approval getApproval() {
		return approval;
	}

	public void setApproval(Approval approval) {
		this.approval = approval;
	}

	public LeaveApproval getLeaveApproval() {
		return leaveApproval;
	}

	public void setLeaveApproval(LeaveApproval leaveApproval) {
		this.leaveApproval = leaveApproval;
	}
	
	
	
	

}
