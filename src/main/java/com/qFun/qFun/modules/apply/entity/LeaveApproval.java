package com.qFun.qFun.modules.apply.entity;

import java.util.Date;

import com.qFun.qFun.common.persistence.DataEntity;


/**
 * 请假审批实体类
 * @author lg
 *
 */
public class LeaveApproval extends DataEntity<LeaveApproval>{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id ;//主键ID
	
	private String uId ;//userid
	
	private Integer type ;//类型 1 事假 2 病假 3 产假 4 婚假 5 调休假 6 丧假 7 年假
	
	private Date startDate ;//开始时间
	
	private Date endDate ;//结束时间
	
	private String days ;//请假天数
	
	private String note ;//备注

	private String reason ;//事由
	
	private Date createDate;//创建时间

	public String getId() {
		return id;
	}

	public String getuId() {
		return uId;
	}

	public void setId(String id) {
		this.id = id;
	}



	public void setuId(String uId) {
		this.uId = uId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
}
