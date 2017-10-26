package com.web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtility {
	private static String TIME_PATTERN="HH:mm:ss";//定义标准时间格式  
	private static String DATE_PATTERN_1="yyyy/MM/dd";//定义标准日期格式1  
	private static String DATE_PATTERN_2="yyyy-MM-dd";//定义标准日期格式2  
	private static String DATE_PATTERN_3="yyyy/MM/dd HH:mm:ss";//定义标准日期格式3，带有时间  
	private static String DATE_PATTERN_4="yyyy/MM/dd HH:mm:ss E";//定义标准日期格式4，带有时间和星期  
	private static String DATE_PATTERN_5="yyyy年MM月dd日 HH:mm:ss E";//定义标准日期格式5，带有时间和星期  
	/** 
	 * 定义时间类型常量 
	 */  
	private final static int SECOND=1;  
	private final static int MINUTE=2;  
	private final static int HOUR=3;  
	private final static int DAY=4;  
	 /**
	  * 得到当前的系统日期格式为:yyyyMMddHHmmss
	  * @return
	  */
    public static String getSDyyyyMMddHHmmss(){
    	Date date=new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	    String da=sdf.format(date);
		return da;    	
    }
    /**
     * 得到当前的系统日期格式为:yyyyMMdd
     * @return
     */
    public static String getSDyyyyMMdd(){
    	Date date=new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	    String da=sdf.format(date);
		return da;    	
    } 
    /**
     * 得到当前的系统日期格式为:yyyy-MM-dd
     * @param date
     * @return
     */
    public static String getSDyformatmd(Date date) {		  
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	   String str = format.format(date);
	   return str;
	}
    /**
     * 返回Date类型:yyyy-MM-dd
     * @param str
     * @return
     */
    public static Date getDateformat(String str) {		  
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	Date date = null;
	   	try {
	   		date = format.parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}
	   	return date;
	} 
    /**
     * 得到当前的系统日期格式为:yyyy.MM.dd
     * @param date
     * @return
     */
    public static String getSDyformatmd2(Date date) {		  
	   SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
	   String str = format.format(date);
	   return str;
	}
    /**
     * 得到当前的系统日期格式为:yyyy年MM月dd日
     * @param date
     * @return
     */
    public static String getSDyformatmd3(Date date) {		  
	   SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
	   String str = format.format(date);
	   return str;
	}
    /**
     * 得到当前的系统日期格式为:yyyy年MM月dd日HH时mm分ss秒
     * @param date
     * @return
     */
    public static String getSDyformatmd4(Date date) {		  
	   SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
	   String str = format.format(date);
	   return str;
	}
    public static String getStrDateDetail(Date date){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String da=sdf.format(date);
		return da; 
    }
    public static Date getDateDetail(String dateStr){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date date = null;
    	try {
    		date = format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return date;
    }
    public static Date getDateYmd(String dateStr){
    	SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    	Date date = null;
    	try {
    		date = format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return date;
    }
    /**
     * 得到明天
     * @return
     */
    public static Date getTomorrow(){
    	Date date = new Date();
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
    }
    /**
     * 比较日期
     * @param date1
     * @param date2
     * @return
     */
    public static int compareDate(Date date1,Date date2){
    	Calendar c1 = Calendar.getInstance();
    	Calendar c2 = Calendar.getInstance();
    	c1.setTime(date1);
    	c2.setTime(date2);
    	int result=c1.compareTo(c2);
    	if(result==0)//c1相等c2
    		return 0;
    	else if(result<0)//c1小于c2
    		return 1;
    	else//c1大于c2
    		return 2;
    }
    
    public static void main(String args[]){
    	//System.out.println(getSDyyyyMMddHHmmss());
    	Date date = new Date();
    	System.out.println(getNextTimeStr(1, 4, date));
    }
    /** 
     * 将一个表示时间段的数转换为毫秒数 
     * @param num 时间差数值,支持小数 
     * @param type 时间类型：1->秒,2->分钟,3->小时,4->天 
     * @return long类型时间差毫秒数，当为-1时表示参数有错 
     */  
    public static long formatToTimeMillis(double num, int type) {  
        if (num <= 0)  
            return 0;  
        switch (type) {  
        case SECOND:  
            return (long) (num * 1000);  
        case MINUTE:  
            return (long) (num * 60 * 1000);  
        case HOUR:  
            return (long) (num * 60 * 60 * 1000);  
        case DAY:  
            return (long) (num * 24 * 60 * 60  * 1000);  
        default:  
            return -1;  
        }  
    }
    /** 
     * 把一个日期，按照某种格式 格式化输出 
     * @param date 日期对象 
     * @param pattern 格式模型 
     * @return 返回字符串类型 
     */  
    public static String formatDate(Date date, String pattern){  
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);  
        return sdf.format(date);  
    }  
    /** 
     * 将字符串类型的时间转换为Date类型 
     * @param str 时间字符串 
     * @param pattern 格式 
     * @return 返回Date类型 
     */  
    public Date formatString(String str,String pattern){  
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);  
        Date time=null;  
        //需要捕获ParseException异常，如不要捕获，可以直接抛出异常，或者抛出到上层  
        try{  
            time = sdf.parse(str);  
        }catch (Exception e) {
            e.printStackTrace();
        }
        return time;  
    } 
    /** 
     * 得到一个日期函数的格式化时间 
     * @param date 日期对象 
     * @return 
     */  
    public static String getTimeByDate(Date date){  
         return formatDate(date, DATE_PATTERN_3);  
    }
    /** 
     * 获取某一指定时间的前一段时间 
     * @param num 时间差数值 
     * @param type 时间差类型：1->秒,2->分钟,3->小时,4->天 
     * @param date 参考时间 
     * @return 返回格式化时间字符串 
     */  
    public static String getPreTimeStr(double num,int type, Date date){  
        long nowLong=date.getTime();//将参考日期转换为毫秒时间  
        Date time = new Date(nowLong-formatToTimeMillis(num, type));//减去时间差毫秒数  
        return getTimeByDate(time);  
    }  
    /** 
     * 获取某一指定时间的前一段时间 
     * @param num 时间差数值 
     * @param type 时间差类型：1->秒,2->分钟,3->小时,4->天 
     * @param date 参考时间 
     * @return 返回Date对象 
     */  
    public static Date getPreTime(double num,int type, Date date){  
        long nowLong=date.getTime();//将参考日期转换为毫秒时间  
        Date time = new Date(nowLong-formatToTimeMillis(num, type));//减去时间差毫秒数  
        return time;  
    }  
    /** 
     * 获取某一指定时间的后一段时间 
     * @param num 时间差数值 
     * @param type 时间差类型：1->秒,2->分钟,3->小时,4->天 
     * @param date 参考时间 
     * @return 返回格式化时间字符串 
     */  
    public static String getNextTimeStr(double num,int type, Date date){  
        long nowLong=date.getTime();//将参考日期转换为毫秒时间  
        Date time = new Date(nowLong+formatToTimeMillis(num, type));//加上时间差毫秒数  
        return getTimeByDate(time);  
    }  
    /** 
     * 获取某一指定时间的后一段时间 
     * @param num 时间差数值 
     * @param type 时间差类型：1->秒,2->分钟,3->小时,4->天 
     * @param date 参考时间 
     * @return 返回Date对象 
     */  
    public static Date getNextTime(double num,int type, Date date){  
        long nowLong=date.getTime();//将参考日期转换为毫秒时间  
        Date time = new Date(nowLong+formatToTimeMillis(num, type));//加上时间差毫秒数  
        return time;  
    }  
}
