package com.web.util;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class SpringMvcToken extends SimpleTagSupport {

	@Override
	public void doTag() throws JspException, IOException {
		 // 获取页面输出流，并输出字符串
		String token_value=Constants.TOKEN_VALUE;
		
		String token="";
		token = new BigInteger(165, new Random()).toString(36)
                 .toUpperCase();
		if(token_value==null || "".equals(token_value)){
			token_value=token;
		}
		System.out.println("************************************^^^^^^^^^^^^^^^^^^^^^^^^^="+token_value);
       // getJspContext().getOut().write("<f:hidden path='"+Constants.DEFAULT_TOKEN_NAME+"'/>");
        getJspContext().getOut().write("<input id='"+Constants.DEFAULT_TOKEN_NAME+"' name='"+Constants.DEFAULT_TOKEN_NAME+"' type='hidden' value='"+token_value+"'/>");
        
	}

}
