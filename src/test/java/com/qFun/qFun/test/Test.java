package com.qFun.qFun.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.qFun.qFun.modules.attendance.utils.ZkemSDK;




public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception {
		
		ZkemSDK sdk = new ZkemSDK();
		boolean  connFlag = sdk.connect("192.168.103.103", 4370);
		if(connFlag)
		{
			
			//List<Map<String, Object>> l = sdk.getUserInfo();
			List<Map<String, Object>> l = null ;
			/*String time = "2018-6-7 09:00:00";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = sdf.parse(time);
			boolean result  = sdk.readLastestLogData(date);
			//sdk.readLastestLogData(new Date());
			System.out.println(result);*/
			
			boolean result  = sdk.readGeneralLogData();
			if(result){
				l = sdk.getGeneralLogData();
			}
			
			for(Map<String, Object> one : l)
			{
				Set<Entry<String, Object>>  s = one.entrySet();
				Iterator<Entry<String, Object>> it = s.iterator();
				while(it.hasNext())
				{
					Entry<String, Object> e = it.next();
					System.out.println(e.getKey() + " : " + e.getValue());
				}
			}
			
			System.out.println(l.size());
			System.out.println(sdk.readLastestLogData(new Date()));
			System.out.println(sdk.getGeneralLogData().size());
			
			String time1 = "2018-6-7 00:00:00";
			String time2 = "2018-6-7 24:00:00";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			boolean _result = sdk.readTimeGLogData(sdf.parse(time1), sdf.parse(time2));
			System.out.println(48788);
			System.out.println(_result);
			  
			sdk.disConnect();
		}else
		{
			System.out.println("连接失败"); 
		}
		/*ActiveXComponent zkem = new ActiveXComponent("zkemkeeper.ZKEM.1");
		
		boolean result = zkem.invoke("Connect_NET", "192.168.11.205", 4370).getBoolean();
		System.out.println(result);
		
		result = zkem.invoke("ReadGeneralLogData",1).getBoolean();
	
		System.out.println(result);
	
		Variant v0 = new Variant(1);
		Variant dwEnrollNumber = new Variant("",true);
		Variant dwVerifyMode = new Variant(0,true);
		Variant dwInOutMode = new Variant(0,true);
		Variant dwYear = new Variant(0,true);
		Variant dwMonth = new Variant(0,true);
		Variant dwDay = new Variant(0,true);
		Variant dwHour = new Variant(0,true);
		Variant dwMinute = new Variant(0,true);
		Variant dwSecond = new Variant(0,true);
		Variant dwWorkCode = new Variant(0,true);
		
		boolean newresult = false;
		do{
			Variant   vResult = Dispatch.call(zkem, "SSR_GetGeneralLogData", v0,dwEnrollNumber,dwVerifyMode,dwInOutMode,dwYear,dwMonth,dwDay,dwHour,
					dwMinute,dwSecond,dwWorkCode);	
			newresult = vResult.getBoolean();
			System.out.println(newresult);
			System.out.println(dwEnrollNumber.getStringRef());
			System.out.println(dwYear.getIntRef() + "-" + dwMonth.getIntRef() + "-" + dwDay.getIntRef() + " " + dwHour.getIntRef() + ":" + dwMinute.getIntRef() + ":" + dwSecond.getIntRef());
			System.out.println();
		}while(newresult == true);
		zkem.invoke("Disconnect");*/
	}

}
