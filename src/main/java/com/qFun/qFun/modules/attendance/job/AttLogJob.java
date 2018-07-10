package com.qFun.qFun.modules.attendance.job;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.qFun.qFun.modules.attendance.dao.AttLogDao;
import com.qFun.qFun.modules.attendance.service.AttLogService;
import com.qFun.qFun.modules.attendance.service.MonthAttService;


public class AttLogJob {
	
	@Autowired
	private AttLogService attLogService;
	
	@Autowired
	private AttLogDao attLogDao;
	
	@Autowired
	private MonthAttService monthAttService; 
	
	private Logger logger = Logger.getLogger(AttLogJob.class);
	
	public void excute(){
		logger.info("执行每日考勤excute任务开始:");
		long start = System.currentTimeMillis();
		try {
			
			attLogDao.truncateLog();
			attLogService.saveAttLog();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		logger.info("执行每日考勤excute任务完毕，执行时间:" + (end - start) + "毫秒");
	}
	
	public void doMonthAttLog(){
		logger.info("执行doMonthAttLog任务开始:");
		long start = System.currentTimeMillis();
		try {
			monthAttService.insertMonthAttData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		logger.info("执行doMonthAttLog任务完毕，执行时间:" + (end - start) + "毫秒");
	}

}
