package com.web.util;


public final class Consts {

	// 短信基本信息
	public static final String USER = "shzdat";
	public static final String PW = "zhengdayidai";
	public static final int DC = 15;
	public static final String GET_URL = "http://sms.imoso.com.cn:9898/sms/mt?";

	// 短信余额URL
	public static final String CHECKBALANCEURL = "http://116.213.72.20/SMSHttpService/Balance.aspx";
	// 发送短信URL
	public static final String SMSURL = "http://116.213.72.20/SMSHttpService/send.aspx";

	// 鼓钱包
	public static final int GQB = 103;
	// 返回码
	public static final String SUCCESS_CODE = "00000";
	public static final String CHECK_CODE = "00001";
	public static final String SESSION_LOSE_CODE = "00002";
	public static final String ERROR_CODE = "00003";
	public static final String SECURITY_CODE = "00004";

	public static final String SUCCESS_DESCRIBE = "success";


	
	public static final String SUCCESS_CN = "1";
	public static final String ERROR_CN = "0";
	public static final String PHONECHECK2 = "^\\d{11}$";
	
	/**
	 * 返回码标识
	 */
	public static final String RES_CODE = "rescode";
	/**
	 * 返回描述信息
	 */
	public static final String RES_MSG = "resmsg";
	/**
	 * 返回中文描述信息
	 */
	public static final String RES_MSG_CN = "resmsg_cn";
	/**
	 * 返回列表信息标识
	 */
	public static final String LIST = "list";
	
	/**
	 * 返回分页信息标识
	 */
	public static final String PAGE_OBJECT = "pageObject";
	
	/**
	 * 返回信息总条数标识
	 */
	public static final String TOTAL_NUM = "totalNum";
	
	/**
	 * 错误描述信息
	 */
	public static final String ERROR_DESC = "网络或服务器连接异常,请稍后再试!";
	
	
	// 手机号段
	public static final String PHONECHECK = "^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$";		
	
	
	/**
	 * 分页常量
	 * 
	 */
	public class Page {
		/** 默认页大小 */
		public static final int PAGE_SIZE = 10;
		/** 默认当前页 */
		public static final int PAGE_CURRENT = 1;
		/** 上送页大小默认名 */
		public static final String PAGE_SIZE_NAME = "pageSize";
		/** 上送当前页默认名 */
		public static final String PAGE_CURRENT_NAME = "current";
		/** 返回列表默认名 */
		public static final String PAGE_LIST = "list";
		/** 返回列表大小默认名 */
		public static final String PAGE_LIST_SIZE = "listSize";
		/**每页返回最大条数**/
		public static final int PAGE_SIZE_MAX = 100;
	}

	/**
	 * 错误信息
	 * 
	 */
	public class ErrorMsg {
		/** 业务异常、系统异常 **/
		public static final String MSG00001 = "businesseError";
		/** 请选择银行 **/
		public static final String MSG00002 = "bank.is.null";
		/** 请输入银行卡号 **/
		public static final String MSG00003 = "bankcard.is.null";
		/** 请输入验证码 **/
		public static final String MSG00004 = "captcha.is.null";
		/** 验证码错误 **/
		public static final String MSG00005 = "check.captcha.failed";
		/** 请选择登陆类别 **/
		public static final String MSG00006 = "logintype.is.null";
		/** 该用户已绑定手机 **/
		public static final String MSG00007 = "mobile.is.bound";
		/** 手机号格式错误 **/
		public static final String MSG00008 = "mobile.is.non-standard";
		/** 请输入手机号 **/
		public static final String MSG00009 = "mobile.is.null";
		/** 用户名存在多个邮箱 **/
		public static final String MSG00010 = "multiple.mailboxes.existed";
		/** 请输入新密码 **/
		public static final String MSG00011 = "new.password.is.null";
		/** 旧密码错误 **/
		public static final String MSG00012 = "old.password.is.incorrect";
		/** 请输入旧密码 **/
		public static final String MSG00013 = "old.password.is.null";
		/** 密码必须为6-20位数字或字母 **/
		public static final String MSG00014 = "password.is.non-standard";
		/** 请输入密码 **/
		public static final String MSG00015 = "password.is.null";
		/** 起购金额为1000元 **/
		public static final String MSG00016 = "purchaseAmount.is.less.than.1000.yuan";
		/** 余额获取有误，请刷新页面 **/
		public static final String MSG00017 = "sufficient.funds.is.incorrect";
		/** 邮箱用户请登录e贷网站找回密码 **/
		public static final String MSG00018 = "user.is.email";
		/** 该用户已存在 **/
		public static final String MSG00019 = "user.is.existed";
		/** 请输入用户名 **/
		public static final String MSG00020 = "user.is.null";
		/** 用户名不存在 **/
		public static final String MSG00021 = "user.not.exist";
		/** 该用户没有经过实名认证 **/
		public static final String MSG00022 = "user.not.realNameAuthentication";
		/** 用户名或密码错误 **/
		public static final String MSG00023 = "userOrPassword.is.incorrect";

		/** 版本号为空 **/
		public static final String MSG00024 = "appVersion.is.null";
		/** 版本号不正确 **/
		public static final String MSG00025 = "appVersion.is.incorrect";
		/** 操作系统为空 **/
		public static final String MSG00026 = "osName.is.null";
		/** 操作系统不正确 **/
		public static final String MSG00027 = "osName.is.incorrect";
		
		/** 该用户未绑定手机号 **/
		public static final String MSG00028 = "mobile.is.non-bound";
		/** 参数非法 **/
		public static final String MSG00029 = "argument.is.invalid";

	}
	
	/**
	 * 产品购买渠道（1：web 2：mobile 3：web and mobile）
	 */
	public class FinanceBuyChannel{
		/** web **/
		public static final String BUYCHANNELFORWEB = "1";
		/** mobile **/
		public static final String BUYCHANNELFORMOBILE = "2";
		/** web and mobile **/
		public static final String BUYCHANNELFORWANDM= "3";
		
	}
	
	/**
	 * 循环匹配类型（1、本息循环 2、本金循环3、不循环）
	 */
	public class CycleMatchType{
		/** 1、本息循环 **/
		public static final String SERVICECYCLE  = "1";
		/** 2、本金循环 **/
		public static final String PRINCIPALCYCLE  = "2";
		/** 3、不循环 **/
		public static final String NOCYCLE= "3";
		
	}
	
	/**
	 * BL后台数据库的系统参数设置表system_setting,取出相应参数对应的值
	 * 规定该表中的name值,该表中的name值是唯一的
	 * */
	public class SystemSettingParam {
		/** 第三方的固定提现费 */
		public static final String EXTRACT_FIXED_FEE_MAX = "extract.fixed.fee.max";
		/** 提现费率(理财人) */
		public static final String EXTRACT_FEE_RATE_CREDIT = "extract.fee.rate.credit";
	}
	
	/**
	 * BL后台数据库的系统参数设置表system_setting,取出相应参数对应的值,用户头像文件上传
	 * 规定该表中的name值,该表中的name值是唯一的,根据name值取value值
	 * */
	public class SystemSettingUpload {
		/** ftp地址 */
		public static final String UPLOAD_FTPURL = "ftpUrl1";
		/** ftp用户名 */
		public static final String UPLOAD_FTPURL_USERLOGIN = "ftpUrl1_userLogin";
		/** ftp密码*/
		public static final String UPLOAD_FTPURL_PASSWORD = "ftpUrl1_password";
		
		/** 头像路径（原图） */
		public static final String UPLOAD_HEAD_IMAGE_PATH = "ftpHeadImage_path";
		/** 头像路径（缩略图） */
		public static final String UPLOAD_DISPOSE_IMAGE_PATH = "ftpDisposeImage_path";
		/** 移动mobile访问路径 */
		public static final String UPLOAD_MOBILE_VISIT_IP = "mobile_visit_Ip";
	}
	
	/**
	 * 消息推送参数
	 * *//*
	public static class PushInfo {
		*//** 项目代号 *//*
		public static final String PROJECT_NO = (String) WebServiceUtil.getPushSetting().get("projectNo");
		*//** 生产秘钥  *//*
		public static final String KEY = (String) WebServiceUtil.getPushSetting().get("key");
	}*/

}
