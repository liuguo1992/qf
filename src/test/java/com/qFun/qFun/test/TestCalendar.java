package com.qFun.qFun.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestCalendar {

	
	
	public static void main(String[] args) throws ParseException {
		/*String today="2015-11-1";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");*/

		 /*Calendar now = Calendar.getInstance();  
		 //now.setTime(sdf.parse(today)); 
		 //now.add(Calendar.DAY_OF_MONTH, -1);
		 //now.add(Calendar.DAY_OF_MONTH, 1);
		System.out.println("year---"+now.get(Calendar.YEAR));
		System.out.println("month---"+(now.get(Calendar.MONTH)));
		System.out.println("day---"+(now.get(Calendar.DAY_OF_MONTH)));
		System.out.println("day---"+(now.getTime()));*/
		
		//System.out.println(calendarToData(2017,3,16));//打印测试
		
		
		 //获取前月的最后一天.
		/*SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cale = Calendar.getInstance();   
        cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天 
        String lastDay = format.format(cale.getTime());
        System.out.println("-----2------lastDay:"+lastDay);
        
        //获取当前月最后一天
        Calendar ca = Calendar.getInstance();    
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
        //String last = format.format(ca.getTime()); 
        System.out.println("===============last:"+ca.getTime());
        
        Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        String first = format.format(c.getTime());
        System.out.println("===============first:"+first);
        
        Calendar calen = Calendar.getInstance();    
        calen.add(Calendar.MONTH, -1);
        calen.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        String _first_ = format.format(calen.getTime());
        System.out.println("===============first____:"+_first_);
        
        Calendar d = Calendar.getInstance();    
        d.add(Calendar.MONTH, 1);
        d.set(Calendar.DAY_OF_MONTH,1);//次月
        String d_ = format.format(d.getTime());
        System.out.println("===============next:"+d_);
        
        
       
        java.text.SimpleDateFormat format_ = new java.text.SimpleDateFormat("yyyy-MM-dd");    

        java.util.Date beginDate= format_.parse("2007-12-20");    

        java.util.Date endDate= format_.parse("2007-12-25");    

        long day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);    

        System.out.println("相隔的天数="+day);   
        */
		final String TIME_04 = " 04:00:00";
		Calendar now = Calendar.getInstance();  
		Integer month = (now.get(Calendar.MONTH) + 1);
		String _month = month+"" ;
		if(month < 10){
			_month = "0"+month; 
		}
		now.add(Calendar.DAY_OF_MONTH, -1);
		 String sTime = now.get(Calendar.YEAR)+"-"+_month+"-"+now.get(Calendar.DAY_OF_MONTH)+TIME_04;
        now.add(Calendar.DAY_OF_MONTH, 1);
        String eTime = now.get(Calendar.YEAR)+"-"+_month+"-"+now.get(Calendar.DAY_OF_MONTH)+TIME_04;
        
        System.out.println("start---"+sTime+"---end---"+eTime);
	        
	}
	
	public static Date calendarToData(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();//日历类的实例化
        calendar.set(year, month - 1, day);//设置日历时间，月份必须减一
        Date date = calendar.getTime(); // 从一个 Calendar 对象中获取 Date 对象
        return date;
    }
  
}
