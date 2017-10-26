package com.web.domain;

import java.util.Date;

public class Users {
	private Integer id;
	private String username;
	private String password;
	private String allow_borrow;
	private String allow_comment;
	private String allow_lend;
	private String be_lender_time;
	private String email;
	private String email_validate;
	private String enable;
	private String idcard_validate;
	private String lastest_login_ip;
	private Date lastest_login_time;
	private String mobile;
	private String mobile_validate;
	private String nick_name;
	private String password_cash;
	private Date reg_time;
	private String userinfo_finished;
	private String user_type;
	private String utm_source_type;
	private String utm_source;
	private String validate_char;
	private String workinfo_finished;
	private String is_create_acct;
	
	private String id_card;
	private String real_name;
	
	private String sina_idcard_validate;
	private String is_create_sina_acct;
	private String deposit_dock_userid;
	/**
	 * update by LeuT
	 * 2014-7-24
	 * 邀请码
	 */
	private String invitation_code;
	/*public Users(String username,String password){
		this.username=username;
		this.password=password;
	}*/

	//update LeuT @2014-5-9
	private String show_login_id;//用户登录页面显示唯一ID（id_+USER_NICK_NAME_SEQ.nextval）
	private Integer login_error_times;//用户登录密码输入错误次数

	/**
	 * 微博UID
	 * update by LeuT
	 * 2014-9-13
	 */
	private String sina_uid;
	
	/**
	 * 对接用户是否完成网站注册,网站注册用户此字段为空，
	 * 新浪用户未完成注册此字段为0，完成注册后此字段为1
	 */
	private String is_finish_register;
	
	private String regFlag;//注册标识（0未注册，1注册）
	
	/**
	 * 其它个人邀请码(注册时前台录入的邀请码)
	 * update 2014-10-14
	 */
	private String inviter_invitation_code;
	
	/**
	 * CPA 增加对接易瑞特tid
	 * update 2015-06-16
	 */
	private String merchant_uid;
	
	private String login_token;
	
	public String getUsername() {
		return username;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAllow_borrow() {
		return allow_borrow;
	}
	public void setAllow_borrow(String allow_borrow) {
		this.allow_borrow = allow_borrow;
	}
	public String getAllow_comment() {
		return allow_comment;
	}
	public void setAllow_comment(String allow_comment) {
		this.allow_comment = allow_comment;
	}
	public String getAllow_lend() {
		return allow_lend;
	}
	public void setAllow_lend(String allow_lend) {
		this.allow_lend = allow_lend;
	}
	public String getBe_lender_time() {
		return be_lender_time;
	}
	public void setBe_lender_time(String be_lender_time) {
		this.be_lender_time = be_lender_time;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail_validate() {
		return email_validate;
	}
	public void setEmail_validate(String email_validate) {
		this.email_validate = email_validate;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	public String getIdcard_validate() {
		return idcard_validate;
	}
	public void setIdcard_validate(String idcard_validate) {
		this.idcard_validate = idcard_validate;
	}
	public String getLastest_login_ip() {
		return lastest_login_ip;
	}
	public void setLastest_login_ip(String lastest_login_ip) {
		this.lastest_login_ip = lastest_login_ip;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getMobile_validate() {
		return mobile_validate;
	}
	public void setMobile_validate(String mobile_validate) {
		this.mobile_validate = mobile_validate;
	}
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	public String getPassword_cash() {
		return password_cash;
	}
	public void setPassword_cash(String password_cash) {
		this.password_cash = password_cash;
	}
	
	public Date getReg_time() {
		return reg_time;
	}

	public void setReg_time(Date reg_time) {
		this.reg_time = reg_time;
	}
	public String getUserinfo_finished() {
		return userinfo_finished;
	}
	public void setUserinfo_finished(String userinfo_finished) {
		this.userinfo_finished = userinfo_finished;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	public String getUtm_source_type() {
		return utm_source_type;
	}
	public void setUtm_source_type(String utm_source_type) {
		this.utm_source_type = utm_source_type;
	}
	public String getUtm_source() {
		return utm_source;
	}
	public void setUtm_source(String utm_source) {
		this.utm_source = utm_source;
	}
	public String getValidate_char() {
		return validate_char;
	}
	public void setValidate_char(String validate_char) {
		this.validate_char = validate_char;
	}
	public String getWorkinfo_finished() {
		return workinfo_finished;
	}
	public void setWorkinfo_finished(String workinfo_finished) {
		this.workinfo_finished = workinfo_finished;
	}
	public String getIs_create_acct() {
		return is_create_acct;
	}
	public void setIs_create_acct(String is_create_acct) {
		this.is_create_acct = is_create_acct;
	}
	public String getId_card() {
		return id_card;
	}

	public void setId_card(String id_card) {
		this.id_card = id_card;
	}

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public String getShow_login_id() {
		return show_login_id;
	}

	public void setShow_login_id(String show_login_id) {
		this.show_login_id = show_login_id;
	}

	public Integer getLogin_error_times() {
		return login_error_times;
	}

	public void setLogin_error_times(Integer login_error_times) {
		this.login_error_times = login_error_times;
	}

	public Date getLastest_login_time() {
		return lastest_login_time;
	}

	public void setLastest_login_time(Date lastest_login_time) {
		this.lastest_login_time = lastest_login_time;
	}

	public String getInvitation_code() {
		return invitation_code;
	}

	public void setInvitation_code(String invitation_code) {
		this.invitation_code = invitation_code;
	}

	public String getSina_uid() {
		return sina_uid;
	}

	public void setSina_uid(String sina_uid) {
		this.sina_uid = sina_uid;
	}

	public String getIs_finish_register() {
		return is_finish_register;
	}

	public void setIs_finish_register(String is_finish_register) {
		this.is_finish_register = is_finish_register;
	}

	public String getRegFlag() {
		return regFlag;
	}

	public void setRegFlag(String regFlag) {
		this.regFlag = regFlag;
	}

	public String getInviter_invitation_code() {
		return inviter_invitation_code;
	}

	public void setInviter_invitation_code(String inviter_invitation_code) {
		this.inviter_invitation_code = inviter_invitation_code;
	}

	public String getLogin_token() {
		return login_token;
	}

	public void setLogin_token(String login_token) {
		this.login_token = login_token;
	}

	public String getSina_idcard_validate() {
		return sina_idcard_validate;
	}

	public void setSina_idcard_validate(String sina_idcard_validate) {
		this.sina_idcard_validate = sina_idcard_validate;
	}

	public String getIs_create_sina_acct() {
		return is_create_sina_acct;
	}

	public void setIs_create_sina_acct(String is_create_sina_acct) {
		this.is_create_sina_acct = is_create_sina_acct;
	}

	public String getDeposit_dock_userid() {
		return deposit_dock_userid;
	}

	public void setDeposit_dock_userid(String deposit_dock_userid) {
		this.deposit_dock_userid = deposit_dock_userid;
	}

	public String getMerchant_uid() {
		return merchant_uid;
	}

	public void setMerchant_uid(String merchant_uid) {
		this.merchant_uid = merchant_uid;
	}
}
