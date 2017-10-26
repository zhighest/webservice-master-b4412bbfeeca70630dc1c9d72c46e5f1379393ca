package com.web.util;
/**
 * 字符串工具类
 * @author ZuoJun
 * @date 2016-6-7 18:07
 *
 */
public class StringUtil {
	
	/**
	 * 判断字符串不为空
	 * @param source
	 * @return
	 */
	public static boolean isNotEmpty(String source){
		
		if(source != null && !"".equals(source) && !"null".equals(source)){
			
			return true;
			
		}else{
			
			return false;
		}
		
	}
	
	/**
	 * 判断字符串为空
	 * @param source
	 * @return
	 */
	public static boolean isEmpty(String source){
		
		return !isNotEmpty(source);
		
	}

}
