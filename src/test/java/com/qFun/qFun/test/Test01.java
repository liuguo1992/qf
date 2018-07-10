package com.qFun.qFun.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;


public class Test01 {
	
	public static void main(String[] args) throws ParseException {
		/*final String time = "15:30:00";  
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd " + time); 
		
		Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sdf.format(new Date()));  
		
		System.out.println(startTime);*/
		
		
		Date date = new Date();
		 DateFormat df3 = DateFormat.getTimeInstance();//只显示出时分秒
		 System.out.println(df3.format(date));
		 
		
	}
	
	

}
