package com.qFun.qFun.modules.attendance.entity;

import com.qFun.qFun.common.persistence.DataEntity;
import com.qFun.qFun.common.utils.excel.annotation.ExcelField;
import com.qFun.qFun.modules.sys.entity.User;

public class MonthAtt extends DataEntity<MonthAtt>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String uId;//用户id
	
	private String name;//姓名
	
	private String attDays;//实际出勤天数
	
	private String thingLeave ;//事假天数
	
	private String sickLeave;//病假天数
	
	private String paidLeave;//带薪假天数
	
	private String lateTimes;//迟到次数
	
	private String latePay;//迟到罚款
	
	private String lateDays;//迟到天数
	
	private Integer perfectAttAward;//全勤奖
	
	private Integer nightCatAward = 0;//夜猫子奖
	
	private String completionDays;//旷工天数
	
	private Integer month;//月分
	
	private Integer year;//年
	
	private User user;//
	
	private User userEnrollNumber;//

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ExcelField( align=2, sort=3, title = "实际出勤天数")
	public String getAttDays() {
		return attDays;
	}

	public void setAttDays(String attDays) {
		this.attDays = attDays;
	}
	
	@ExcelField(align=2, sort=4, title = "事假天数")
	public String getThingLeave() {
		return thingLeave;
	}

	public void setThingLeave(String thingLeave) {
		this.thingLeave = thingLeave;
	}
	
	@ExcelField( align=2, sort=5, title = "病假天数")
	public String getSickLeave() {
		return sickLeave;
	}

	public void setSickLeave(String sickLeave) {
		this.sickLeave = sickLeave;
	}

	@ExcelField(align=2, sort=6, title = "带薪休假天数")
	public String getPaidLeave() {
		return paidLeave;
	}

	public void setPaidLeave(String paidLeave) {
		this.paidLeave = paidLeave;
	}

	@ExcelField(align=2, sort=7, title = "迟到次数")
	public String getLateTimes() {
		return lateTimes;
	}

	public void setLateTimes(String lateTimes) {
		this.lateTimes = lateTimes;
	}

	@ExcelField(align=2, sort=8, title = "迟到罚款")
	public String getLatePay() {
		return latePay;
	}

	public void setLatePay(String latePay) {
		this.latePay = latePay;
	}

	@ExcelField(align=2, sort=9, title = "迟到天数")
	public String getLateDays() {
		return lateDays;
	}

	public void setLateDays(String lateDays) {
		this.lateDays = lateDays;
	}


	@ExcelField(align=2, sort=10, title = "全勤奖")
	public Integer getPerfectAttAward() {
		return perfectAttAward;
	}

	public void setPerfectAttAward(Integer perfectAttAward) {
		this.perfectAttAward = perfectAttAward;
	}

	@ExcelField(align=2, sort=11, title = "夜猫子奖")
	public Integer getNightCatAward() {
		return nightCatAward;
	}

	public void setNightCatAward(Integer nightCatAward) {
		this.nightCatAward = nightCatAward;
	}

	public String getCompletionDays() {
		return completionDays;
	}

	public void setCompletionDays(String completionDays) {
		this.completionDays = completionDays;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@ExcelField(value="user.name", align=2, sort=2, title = "姓名")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ExcelField(value="user.enrollNumber", align=2, sort=1, title = "编号")
	public User getUserEnrollNumber() {
		return userEnrollNumber;
	}

	public void setUserEnrollNumber(User userEnrollNumber) {
		this.userEnrollNumber = userEnrollNumber;
	}

	
	
}
