package com.qFun.qFun.modules.apply.entity;


import java.util.Date;



import com.qFun.qFun.common.persistence.DataEntity;
import com.qFun.qFun.modules.sys.entity.Office;
import com.qFun.qFun.modules.sys.entity.User;


/**
 * 报销审批实体类
 * @author lg
 *
 */
public class Budget extends DataEntity<Budget>{
	
	private static final long serialVersionUID = 1L;
	private String bId;//费用审批编号
	
	private String uId;//申请人id
	
	private String money;//申请金额
	
	private Integer bStatus ;//流程状态 0 未完成 1 已完成
	
	private String pId; //审批人id
	
	private Integer pStatus;//审批状态 0 未审批 1 已审批
	
	private Integer result ;//审批结果
	
	private String bNote;//申请备注
	
	private String pNote;//审批备注
	
	private Date pDate;//审批时间
	
	private Date startDate;//开始时间
	
	private Date endDate;//结束时间
	
	private Integer isDel;//是否删除
	
	private Integer step;//当前流程步骤
	
	private Integer type;//费用类型
	
	private User user;
	
	private BudgetRecord  budgetRecord;
	
	private ChargeDetail chargeDetail;
	
	private Office office;
	
	
	
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public ChargeDetail getChargeDetail() {
		return chargeDetail;
	}

	public void setChargeDetail(ChargeDetail chargeDetail) {
		this.chargeDetail = chargeDetail;
	}

	public BudgetRecord getBudgetRecord() {
		return budgetRecord;
	}

	public void setBudgetRecord(BudgetRecord budgetRecord) {
		this.budgetRecord = budgetRecord;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Budget() {
		super();
	}
	
	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Date getpDate() {
		return pDate;
	}



	public void setpDate(Date pDate) {
		this.pDate = pDate;
	}



	public String getbId() {
		return bId;
	}

	public void setbId(String bId) {
		this.bId = bId;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public Integer getbStatus() {
		return bStatus;
	}

	public void setbStatus(Integer bStatus) {
		this.bStatus = bStatus;
	}

	public Integer getpStatus() {
		return pStatus;
	}

	public void setpStatus(Integer pStatus) {
		this.pStatus = pStatus;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}


	public String getbNote() {
		return bNote;
	}

	public void setbNote(String bNote) {
		this.bNote = bNote;
	}

	public String getpNote() {
		return pNote;
	}

	public void setpNote(String pNote) {
		this.pNote = pNote;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	
	

}
