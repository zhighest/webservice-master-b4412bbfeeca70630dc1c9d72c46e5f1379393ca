package com.web.domain;

import java.io.Serializable;
import java.util.Date;

public class UserRecePayCard  implements Serializable {

	/**
	 * 用户回款卡号设置
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;//自增id
	private int bank_id;//银行id
	private String bank_name;//银行名称
	private int user_id;//用户id
	private char is_valid;//是否可用（y/n）
	private String card_number;//银行卡号
	private char is_binding;//是否为现绑定回款号（y/n）
	private Date create_time;//创建时间
	private Date update_time;//更新时间
	private String phone_no;//预留手机号
	private int receive_certificate_type;//收款方证件类型(暂时只支持:身份证):1、省份证
	private String receive_certificate_no;//收款方证件预留号码
	private int media_type;//媒介类型(暂时只支持:手机)：1、手机
	private String create_per;//创建人
	private String update_per;//更新人
	private String account_name;//开户名
	private String province;//开户省份
	private String city;//开户城市
	private String subbranch;//支行
	private String sina_card_id;//新浪钱包系统卡id
	private String binding_req_num;
	private String city_name;//开户城市名称
	private String llpay_no_agree;//连连支付签约协议号
	private String llpay_oid_paybill;//连连支付的支付订单编号
	private String picture;
	
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getLlpay_no_agree() {
		return llpay_no_agree;
	}
	public void setLlpay_no_agree(String llpay_no_agree) {
		this.llpay_no_agree = llpay_no_agree;
	}
	public String getLlpay_oid_paybill() {
		return llpay_oid_paybill;
	}
	public void setLlpay_oid_paybill(String llpay_oid_paybill) {
		this.llpay_oid_paybill = llpay_oid_paybill;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBank_id() {
		return bank_id;
	}
	public void setBank_id(int bank_id) {
		this.bank_id = bank_id;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public char getIs_valid() {
		return is_valid;
	}
	public void setIs_valid(char is_valid) {
		this.is_valid = is_valid;
	}
	public String getCard_number() {
		return card_number;
	}
	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}
	public char getIs_binding() {
		return is_binding;
	}
	public void setIs_binding(char is_binding) {
		this.is_binding = is_binding;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}
	public int getReceive_certificate_type() {
		return receive_certificate_type;
	}
	public void setReceive_certificate_type(int receive_certificate_type) {
		this.receive_certificate_type = receive_certificate_type;
	}
	public String getReceive_certificate_no() {
		return receive_certificate_no;
	}
	public void setReceive_certificate_no(String receive_certificate_no) {
		this.receive_certificate_no = receive_certificate_no;
	}
	public int getMedia_type() {
		return media_type;
	}
	public void setMedia_type(int media_type) {
		this.media_type = media_type;
	}
	public String getCreate_per() {
		return create_per;
	}
	public void setCreate_per(String create_per) {
		this.create_per = create_per;
	}
	public String getUpdate_per() {
		return update_per;
	}
	public void setUpdate_per(String update_per) {
		this.update_per = update_per;
	}
	public String getAccount_name() {
		return account_name;
	}
	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getSubbranch() {
		return subbranch;
	}
	public void setSubbranch(String subbranch) {
		this.subbranch = subbranch;
	}
	public String getSina_card_id() {
		return sina_card_id;
	}
	public void setSina_card_id(String sina_card_id) {
		this.sina_card_id = sina_card_id;
	}
	public String getBinding_req_num() {
		return binding_req_num;
	}
	public void setBinding_req_num(String binding_req_num) {
		this.binding_req_num = binding_req_num;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

}
