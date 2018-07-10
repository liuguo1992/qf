package com.qFun.qFun.modules.apply.entity;

import com.qFun.qFun.common.persistence.DataEntity;

/**
 * 报销明细实体类
 * @author lg
 *
 */
public class ChargeDetail extends DataEntity<ChargeDetail>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;//明细id
	
	private String bId;//报销申请id
	
	private String money;//金额
	
	private String lAmout;//借款金额
	
	private String rAmout;//应退金额
	
	private String rUnit;//收款单位
	
	private String aBank;//开户行
	
	private String bAccout;//银行账号
	
	private Integer pMethod;//付款方式 0 支票  1转账 2 现金
	
	private Integer notes ;//附注 0 预算内支出 1预算外支出
	
	private String summary;//事由摘要
	
	private String cAmout;//大写金额
	
	private Budget budget;
	
	
	public String getcAmout() {
		return cAmout;
	}

	public void setcAmout(String cAmout) {
		this.cAmout = cAmout;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getbId() {
		return bId;
	}

	public void setbId(String bId) {
		this.bId = bId;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
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

	public Integer getNotes() {
		return notes;
	}

	public void setNotes(Integer notes) {
		this.notes = notes;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Budget getBudget() {
		return budget;
	}

	public void setBudget(Budget budget) {
		this.budget = budget;
	}
	
	

}
