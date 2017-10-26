package com.web.util.tongdun;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class TongdunProperUtil {
	private static final Log logger = LogFactory.getLog(TongdunProperUtil.class);
	public static String url = "";    //调用内网项目URL地址（邮箱功能）
	public static String src = "";    //前端用
	public static String fpHost = ""; //前端用

	static{
		try {
			Properties  props=PropertiesLoaderUtils.loadAllProperties("tongdun.properties");
			url = props.getProperty("url");
			src = props.getProperty("src");
			fpHost = props.getProperty("fpHost");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("读取配置文件tongdun.properties失败");
		}
	}
	
}
