package com.qFun.qFun.modules.attendance.entity;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.qFun.qFun.common.persistence.DataEntity;

public class AttLog extends DataEntity<AttLog>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;//主键
		
	private Integer enrollNumber;//人员编号
	
	private Integer day ;//日
	
	private Integer month ;//月
	
	private Integer year ;//年
	
	private Integer hour ;//时
	
	private String time;//打卡时间
	
	private Integer second ;//秒
	
	private Integer minute;//分
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getEnrollNumber() {
		return enrollNumber;
	}

	public void setEnrollNumber(Integer enrollNumber) {
		this.enrollNumber = enrollNumber;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
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

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	public Date getTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getSecond() {
		return second;
	}

	public void setSecond(Integer second) {
		this.second = second;
	}

	public Integer getMinute() {
		return minute;
	}

	public void setMinute(Integer minute) {
		this.minute = minute;
	}
	
	
	
}
