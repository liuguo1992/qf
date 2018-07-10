package com.qFun.qFun.modules.attendance.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qFun.qFun.common.persistence.DataEntity;
import com.qFun.qFun.common.utils.excel.annotation.ExcelField;
import com.qFun.qFun.modules.sys.entity.User;


/**
 * 当天的考勤情况
 * @author Administrator
 *
 */
public class DayAtt extends DataEntity<DayAtt>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id ;//主键id
	
	private String uId;//用户id
	
	private Date onWorkTime;//签到
	
	private Date offWorkTime;//签退
	
	private Integer isLate;//是否迟到
	
	private String lateTime;//迟到小时
	
	private String workTime;//上班时间
	
	private Integer isCompletion;//是否旷工
	
	private Integer isLeave;//是否请假
	
	private Integer isOverTime;//是否加班
	
	private Integer year;//年
	
	private Integer month;//月份
	
	private Integer isWork;//是否正常上班
	
	private Integer leaveEarly;//是否早退
	
	private Integer overType;//加班类型
	
	private String onWork = "09:30";//
	
	private String offWork = "18:30";//
	
	private User user;
	
	private User userErollNumber;
	
	

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

	@JsonIgnore
	@ExcelField(title="签到", align=2, sort=6)
	public Date getOnWorkTime() {
		return onWorkTime;
	}

	public void setOnWorkTime(Date onWorkTime) {
		this.onWorkTime = onWorkTime;
	}

	@JsonIgnore
	@NotNull(message="签退不能为空")
	@ExcelField(title="签退", align=2, sort=7)
	public Date getOffWorkTime() {
		return offWorkTime;
	}

	public void setOffWorkTime(Date offWorkTime) {
		this.offWorkTime = offWorkTime;
	}
	
	@JsonIgnore
	@ExcelField(title="是否迟到", align=2, sort=8)
	public Integer getIsLate() {
		return isLate;
	}

	public void setIsLate(Integer isLate) {
		this.isLate = isLate;
	}
	
	@JsonIgnore
	@ExcelField(title="上班时间", align=2, sort=3)
	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}
	
	@JsonIgnore
	@ExcelField(title="是否旷工", align=2, sort=10)
	public Integer getIsCompletion() {
		return isCompletion;
	}

	public void setIsCompletion(Integer isCompletion) {
		this.isCompletion = isCompletion;
	}

	public Integer getIsLeave() {
		return isLeave;
	}

	public void setIsLeave(Integer isLeave) {
		this.isLeave = isLeave;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}
	
	@JsonIgnore
	@ExcelField(title="是否加班", align=2, sort=11)
	public Integer getIsOverTime() {
		return isOverTime;
	}

	public void setIsOverTime(Integer isOverTime) {
		this.isOverTime = isOverTime;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getIsWork() {
		return isWork;
	}

	public void setIsWork(Integer isWork) {
		this.isWork = isWork;
	}
	
	@JsonIgnore
	@ExcelField(title="是否早退", align=2, sort=9)
	public Integer getLeaveEarly() {
		return leaveEarly;
	}

	public void setLeaveEarly(Integer leaveEarly) {
		this.leaveEarly = leaveEarly;
	}

	public Integer getOverType() {
		return overType;
	}

	public void setOverType(Integer overType) {
		this.overType = overType;
	}
	
	@ExcelField(value="user.name", align=2, sort=2, title = "姓名")  
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLateTime() {
		return lateTime;
	}

	public void setLateTime(String lateTime) {
		this.lateTime = lateTime;
	}
	@JsonIgnore
	@ExcelField(title="上班时间", align=2, sort=4)
	public String getOnWork() {
		return onWork;
	}

	public void setOnWork(String onWork) {
		this.onWork = onWork;
	}

	@JsonIgnore
	@ExcelField(title="下班时间", align=2, sort=5)
	public String getOffWork() {
		return offWork;
	}

	public void setOffWork(String offWork) {
		this.offWork = offWork;
	}
	@ExcelField(title="编号", align=2, sort=1 ,value="user.enrollNumber" )
	public User getUserErollNumber() {
		return userErollNumber;
	}

	public void setUserErollNumber(User userErollNumber) {
		this.userErollNumber = userErollNumber;
	}

	
	
	

}
