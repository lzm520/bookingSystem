package com.example.demo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class produceOrderId {
	
	/*根据时间获取订单ID*/
	public static String getOrderIdByTime() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");	
		String newDate=sdf.format(new Date());
		String result="";	
		Random random=new Random();
		for(int i=0;i<3;i++){
			result+=random.nextInt(10);
		}	
		System.out.println(result);
		return newDate+result;			
	}
	
	/*获取当前日期*/
	public static String getToday() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");	
		String newDate=sdf.format(new Date());
		return newDate;
	}
}
