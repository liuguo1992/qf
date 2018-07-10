package com.qFun.qFun.modules.apply.entity;

import java.util.Date;

import com.qFun.qFun.common.persistence.DataEntity;
import com.qFun.qFun.modules.sys.entity.Office;
import com.qFun.qFun.modules.sys.entity.Role;
import com.qFun.qFun.modules.sys.entity.User;

public class Loan extends DataEntity<Loan>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String id;// 主键ID
	
	private String money;//金额
	
	private String reason;//申请事由
	
	private Integer type;//申请类型
	
	private Date createDate ;//申请时间
	
	private String uId;//用户id
	
	private Integer isRepay;//是否还款
	
	private Date loanDate ;//借款日期
	
	private Date repayDate;//还款日期
	
	private String note;//备注
	
	private String lAmout;//借款金额
	
	private String rAmout;//应退金额
	
	private String rUnit;//收款单位
	
	private String aBank;//开户行
	
	private String bAccout;//银行账号
	
	private Integer pMethod;//付款方式 0 支票  1转账 2 现金
	
	private String cAmout;//大写金额
	
	private User user;
	
	private BudgetRecord budgetRecord;
	
	
	private Approval approval;
	
	private Office office;
	
	private Role role;
	

	public Approval getApproval() {
		return approval;
	}

	public void setApproval(Approval approval) {
		this.approval = approval;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public Integer getIsRepay() {
		return isRepay;
	}

	public void setIsRepay(Integer isRepay) {
		this.isRepay = isRepay;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BudgetRecord getBudgetRecord() {
		return budgetRecord;
	}

	public void setBudgetRecord(BudgetRecord budgetRecord) {
		this.budgetRecord = budgetRecord;
	}

	public Date getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}

	public Date getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getlAmout() {
		return lAmout;
	}

	public void setlAmout(String lAmout) {
		this.lAmout = lAmout;
	}

	public String getrAmout() {
		return rAmout;
	}

	public void setrAmout(String rAmout) {
		this.rAmout = rAmout;
	}

	public String getrUnit() {
		return rUnit;
	}

	public void setrUnit(String rUnit) {
		this.rUnit = rUnit;
	}

	public String getaBank() {
		return aBank;
	}

	public void setaBank(String aBank) {
		this.aBank = aBank;
	}

	public String getbAccout() {
		return bAccout;
	}

	public void setbAccout(String bAccout) {
		this.bAccout = bAccout;
	}

	public Integer getpMethod() {
		return pMethod;
	}

	public void setpMethod(Integer pMethod) {
		this.pMethod = pMethod;
	}

	public String getcAmout() {
		return cAmout;
	}

	public void setcAmout(String cAmout) {
		this.cAmout = cAmout;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	 
	 

}
