package com.web.domain;

import java.io.Serializable;

public class UserSession  implements Serializable{
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
	private String lastest_login_time;
	private String mobile;
	private String mobile_validate;
	private String nick_name;
	private String password_cash;
	private String reg_time;
	private String userinfo_finished;
	private String user_type;
	private String utm_source_type;
	private String utm_source;
	private String validate_char;
	private String workinfo_finished;
	private String is_create_acct;
	private String show_login_id;//用户登录页面显示唯一ID（id_+USER_NICK_NAME_SEQ.nextval）
	
	private String emailValiCode;
	private String mobileValiCode;
	
	/** 真实姓名 */
	private String realName;
	/** 身份证号 */
	private String idCard;
	
	/**
	 * update by LeuT
	 * 2014-7-24
	 * 邀请码
	 */
	private String invitation_code;
	/**
	 * 微博UID
	 * update by LeuT
	 * 2014-9-20
	 */
	private String sina_uid;
	
	/**
	 * 对接用户是否完成网站注册,网站注册用户此字段为空，
	 * 新浪用户未完成注册此字段为0，完成注册后此字段为1
	 */
	private String is_finish_register;
	
	private String sina_idcard_validate;
	private String is_create_sina_acct;
	private String deposit_dock_userid;
	
	private String borrowLenderFlag;
	public String getBorrowLenderFlag() {
		return borrowLenderFlag;
	}

	public void setBorrowLenderFlag(String borrowLenderFlag) {
		this.borrowLenderFlag = borrowLenderFlag;
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
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
	public String getLastest_login_time() {
		return lastest_login_time;
	}
	public void setLastest_login_time(String lastest_login_time) {
		this.lastest_login_time = lastest_login_time;
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
	public String getReg_time() {
		return reg_time;
	}
	public void setReg_time(String reg_time) {
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
	public String getEmailValiCode() {
		return emailValiCode;
	}
	public void setEmailValiCode(String emailValiCode) {
		this.emailValiCode = emailValiCode;
	}
	public String getMobileValiCode() {
		return mobileValiCode;
	}
	public void setMobileValiCode(String mobileValiCode) {
		this.mobileValiCode = mobileValiCode;
	}
	public String getShow_login_id() {
		return show_login_id;
	}
	public void setShow_login_id(String show_login_id) {
		this.show_login_id = show_login_id;
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
	/** 真实姓名 */
	public String getRealName() {
		return realName;
	}
	/** 真实姓名 */
	public void setRealName(String realName) {
		this.realName = realName;
	}
	/** 身份证号 */
	public String getIdCard() {
		return idCard;
	}
	/** 身份证号 */
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	@Override
	public String toString() {
		return "UserSession [id=" + id + ", username=" + username + ", password=" + password
				+ ", allow_borrow=" + allow_borrow + ", allow_comment=" + allow_comment + ", allow_lend=" + allow_lend
				+ ", be_lender_time=" + be_lender_time + ", email=" + email + ", email_validate=" + email_validate
				+ ", enable=" + enable + ", idcard_validate=" + idcard_validate + ", lastest_login_ip="
				+ lastest_login_ip + ", lastest_login_time=" + lastest_login_time + ", mobile=" + mobile
				+ ", mobile_validate=" + mobile_validate + ", nick_name=" + nick_name + ", password_cash="
				+ password_cash + ", reg_time=" + reg_time + ", userinfo_finished=" + userinfo_finished + ", user_type="
				+ user_type + ", utm_source_type=" + utm_source_type + ", utm_source=" + utm_source + ", validate_char="
				+ validate_char + ", workinfo_finished=" + workinfo_finished + ", is_create_acct=" + is_create_acct
				+ ", show_login_id=" + show_login_id + ", emailValiCode=" + emailValiCode + ", mobileValiCode="
				+ mobileValiCode + ", realName=" + realName + ", idCard=" + idCard + ", invitation_code="
				+ invitation_code + ", sina_uid=" + sina_uid + ", is_finish_register=" + is_finish_register
				+ ", sina_idcard_validate=" + sina_idcard_validate + ", is_create_sina_acct=" + is_create_sina_acct
				+ ", deposit_dock_userid=" + deposit_dock_userid + ", borrowLenderFlag=" + borrowLenderFlag + "]";
	}
	
	
	
}
