package com.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.Assert;

import com.web.domain.UserSession;
import com.web.exception.NoSessionException;

public class SessionUtils {
	private static final String USER = "USER_SESSION";
	/**
	 * 注销用户session
	 * @param request
	 */
	public static void invalidUser( HttpServletRequest request ) {
		request.getSession().invalidate();
		
	}
	/**
	 * 用户信息存入session
	 * @param request
	 * @param us
	 */
	public static void putUser( HttpServletRequest request, UserSession us ) {
		HttpSession s = request.getSession();
		s.setAttribute( SessionUtils.USER, us );
	}
	/**
	 * Session中取出用户信息
	 * @param request
	 * @param response
	 * @return
	 */
	public static UserSession getUser( HttpServletRequest request, HttpServletResponse response ) {

		return getUser(request);
		
	}
	
	/**
	 * Session中取出用户信息
	 * @param request
	 * @return
	 */
	public static UserSession getUser( HttpServletRequest request ) {
		Assert.notNull( request );
		HttpSession s = request.getSession(false);
		if( s == null ){
			throw new NoSessionException( );
		}
		UserSession us =  (UserSession) s.getAttribute( SessionUtils.USER );
		if( us == null ){
			throw new NoSessionException( );
		}

		return us;

	}
	/**
	 * Session中取出用户信息
	 * @param session
	 * @return
	 */
	public static UserSession getUser( HttpSession session ) {
		Assert.notNull( session );
		return (UserSession) session.getAttribute( SessionUtils.USER );
	}
}
