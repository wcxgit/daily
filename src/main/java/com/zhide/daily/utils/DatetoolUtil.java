package com.zhide.daily.utils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期工具类
 * 
 * @author Administrator
 */
public class DatetoolUtil implements Serializable {
	
	  public static ArrayList stringToList(String s, String seperator) {
		    ArrayList result = new ArrayList();
		    s = s.trim();
		    int i = s.indexOf(seperator);
		    while (i >= 0) {
		      result.add(s.substring(0, i));
		      s = s.substring(i + seperator.length()).trim();
		      i = s.indexOf(seperator);
		    }
		    if (!s.equals("")) {
		      result.add(s);
		    }
		    return result;
		  }
	  
	  /**
		 * 获取指定月份的上一月
		 * @param date 指定的时间 格式mm/dd
		 * @return LastMONTH
		 */
	     
	     public static String getlastMONTHMMDD(String date) {
	    	 SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
	    	 
	    	 Calendar cal = Calendar.getInstance();
	         try {
				cal.setTime(sdf.parse(date));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				System.out.println("字符串转日期出现错误");
			}
	         cal.add(Calendar.MONTH, -1);
	         String LastMONTH=sdf.format(cal.getTime());
	         return LastMONTH;
	     }
	  
	  
	  
	  
	 	/**
		 * 获取指定月份的上一月
		 * @param date 指定的时间 格式yyyy/mm 
		 * @return LastMONTH
		 */
	     
	     public static String getLastMONTH(String date) {
	    	 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
	    	 
	    	 Calendar cal = Calendar.getInstance();
	         try {
				cal.setTime(sdf.parse(date));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				System.out.println("字符串转日期出现错误");
			}
	         cal.add(Calendar.MONTH, -1);
	         String LastMONTH=sdf.format(cal.getTime());
	         return LastMONTH;
	     }
	  
	  
	  /**
		 * 获取指定日期月份的第一天
		 * @param date 指定的日期
		 * @param format 
		 * @return
		 */
		public static String getCurrentMonthFirstDay(Date date, String format){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, 0);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			String firstDay = new SimpleDateFormat(format).format(calendar.getTime());
			return firstDay;
		}
		/**
		 * 获取当前月份的第一天
		 * @param format
		 * @return
		 */
		public static String getCurrentMonthFirstDay(String format){
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, 0);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			Date date = calendar.getTime();
			String firstDay = new SimpleDateFormat(format).format(date);
			return firstDay;
		}
		/**
		 * 获取当前月份的最后一天
		 * @param format
		 * @return
		 */
		public static String getCurrentMonthLastDay(String format){
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, 1);
			calendar.set(Calendar.DAY_OF_MONTH, 0);
			Date date = calendar.getTime();
			String lastDay = new SimpleDateFormat(format).format(date);
			return lastDay;
		}
		/**
		 * 获取指定日期月份的最后一天
		 * @param date 指定的日期
		 * @param format
		 * @return
		 */
		public static String getCurrentMonthLastDay(Date date, String format){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, 1);
			calendar.set(Calendar.DAY_OF_MONTH, 0);
			String lastDay = new SimpleDateFormat(format).format(calendar.getTime());
			return lastDay;
		}
		/**
		 * 获取上个月的第一天
		 * @param format
		 * @return
		 */
		public static String getBeforeMonthFirstDay(String format){
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			Date date = calendar.getTime();
			String firstDay = new SimpleDateFormat(format).format(date);
			return firstDay;
		}
		/**
		 * 获取上个月的最后一天
		 * @param format
		 * @return
		 */
		public static String getBeforeMonthLastDay(String format){
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, 0);
			calendar.set(Calendar.DAY_OF_MONTH, 0);
			Date date = calendar.getTime();
			String lastDay = new SimpleDateFormat(format).format(date);
			return lastDay;
		}
		
		/**
		 * 获取前一季度第一天
		 * @param format
		 * @return
		 */
		public static String getPreviousQuarterFirstDay(String format){
			Calendar calendar = Calendar.getInstance();
			int month = calendar.get(Calendar.MONTH) + 1;//获取当前月份并转换成0-12
			double quarter = month/3.00 ;//当前季度1-4
			if(0 < quarter && quarter <= 1){//当前是第一季度，则将日历设置成上一年的第四季度的第一个月
				calendar.add(Calendar.YEAR, -1);
				calendar.set(Calendar.MONTH, 9);
			}else if(1 < quarter && quarter <= 2){//当前是第二季度，则将日历设置成当年的第一季度的第一个月
				calendar.set(Calendar.MONTH, 0);
			}else if(2 < quarter && quarter <= 3){//当前是第三季度，则将日历设置成当年的第二季度的第一个月
				calendar.set(Calendar.MONTH, 3);
			}else if(3 < quarter && quarter <= 4){//当前是第四季度，则将日历设置成当年的第三季度的第一个月
				calendar.set(Calendar.MONTH, 6);
			}
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			Date date = calendar.getTime();
			String firstDay = new SimpleDateFormat(format).format(date);
			return firstDay;
		}
		/**
		 * 获取前一季度最后一天
		 * @param format
		 * @return
		 */
		public static String getPreviousQuarterLastDay(String format){
			Calendar calendar = Calendar.getInstance();
			int month = calendar.get(Calendar.MONTH) + 1;//获取当前月份并转换成0-12
			double quarter = month/3.00 ;//当前季度1-4
			if(0 < quarter && quarter <= 1){//当前是第一季度，则将日历设置成上一年的第四季度的最后一个月
				calendar.add(Calendar.YEAR, 0);
				calendar.set(Calendar.MONTH, 0);
			}else if(1 < quarter && quarter <= 2){//当前是第二季度，则将日历设置成当年的第一季度的最后一个月
				calendar.set(Calendar.MONTH, 3);
			}else if(2 < quarter && quarter <= 3){//当前是第三季度，则将日历设置成当年的第二季度的最后一个月
				calendar.set(Calendar.MONTH, 6);
			}else if(3 < quarter && quarter <= 4){//当前是第四季度，则将日历设置成当年的第三季度的最后一个月
				calendar.set(Calendar.MONTH, 9);
			}
			calendar.set(Calendar.DAY_OF_MONTH, 0);
			Date date = calendar.getTime();
			String lastDay = new SimpleDateFormat(format).format(date);
			return lastDay;
		}
		/**
		 * 按指定长度解析报文
		 * @param format
		 * @return
		 */
		public static List<List<String>> parseMsg(String msg,int len){
			List<List<String>> lists=new ArrayList<List<String>>();
			if(msg.startsWith("Y,")){
				String[] strings=msg.split(",");
				for (int i = 0; i < (strings.length-1)/len; i++) {
					List<String> list=new ArrayList<String>();
					for (int j = 0; j <len; j++) {
//						strings2[i][j]=strings[i*len+j+1];
						list.add(strings[i*len+j+1]);
						
					}
					lists.add(list);
				}
				
			}else{
				return null;
			}
			return lists;
		}
		
		/**
		 * 将当前系统时间按指定格式返回
		 * @param format
		 * @return
		 */
		public static String getCurrentDay(String format){
//			Calendar calendar = Calendar.getInstance();
//			Date date = calendar.getTime();
			Date date = new Date(System.currentTimeMillis());
			String currdate = new SimpleDateFormat(format).format(date);
			return currdate;
		}
}
