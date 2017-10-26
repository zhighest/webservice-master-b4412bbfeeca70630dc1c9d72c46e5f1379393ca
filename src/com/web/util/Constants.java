package com.web.util;

public class Constants {
	public static String DEFAULT_TOKEN_MSG_JSP = "unSubmit.jsp" ;
    public static String TOKEN_VALUE ;
    public static String DEFAULT_TOKEN_NAME = "springMVCtoken";
    
    /**
     * 
     */
	public static final String SESSION_NAME = "lincomb_wxSessionID";

	/**
	 * cookie的生存周期 单位：秒 (1年)
	 */
    public static final int COOKIE_ALIVE_ONE_YEAR = 24 * 60 * 60 * 365;

	/**
	 * cookie的生存周期 单位：秒 (2周)
	 */
    public static final int COOKIE_ALIVE_TWO_WEEK = 24 * 60 * 60 * 14;
    
    /**
     * cookie的生存周期 单位：秒 (6小时（6 * 60 * 60），2017-08-11 10:04:01 变更为15分钟) 
     */
    public static final int COOKIE_ALIVE_SIX_HOUR = 60 * 15;

    /**
     * cookie生存周期 关闭浏览器就退出
     */
    public static final int COOKIE_ALIVE_NOT_SAVE = -1;

    /**
     * 获取用户采用的KEY
     */
    public static final String USER_SESSION = "USER_SESSION";
    
    
    /**
	 * cookie的生存周期 单位：秒 (2周)
	 */
    public static final int WEIXIN_OPENID_COOKIE_ALIVE_TWO_WEEK = 24 * 60 * 60 * 14;
    /**
     *获取OPENID采用的KEY 
     */
    public static final String WEIXIN_OPENID ="WEIXIN_OPENID";

}
