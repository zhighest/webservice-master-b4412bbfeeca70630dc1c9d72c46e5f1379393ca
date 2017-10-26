package com.web.actions.weixin.common;

public class WeiXinUserDto {

	//手机号
	private String phoneNum;
	//交易密码
	private String passwordCash;
	//
	private String type;
	//验证码
	private String checkCode;
	//邀请人
	private String invitationCd;
	//微信ID
	private String weixinId;
	//登录密码
	private String password;
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getPasswordCash() {
		return passwordCash;
	}

	public void setPasswordCash(String passwordCash) {
		this.passwordCash = passwordCash;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public String getInvitationCd() {
		return invitationCd;
	}

	public void setInvitationCd(String invitationCd) {
		this.invitationCd = invitationCd;
	}

	public String getWeixinId() {
		return weixinId;
	}

	public void setWeixinId(String weixinId) {
		this.weixinId = weixinId;
	}

}
