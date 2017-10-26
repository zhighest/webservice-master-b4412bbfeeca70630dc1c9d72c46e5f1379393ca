package com.web.domain;

import java.io.Serializable;

public class Bank  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String bank_code;
	private String bank_name;
	private String eanble;
	private String picture;
	private String ylbankcode;
	private String support_type;
	private String platform_en_name;
	private String pay_platform_id;
	
	
	
	public String getPay_platform_id() {
		return pay_platform_id;
	}
	public void setPay_platform_id(String pay_platform_id) {
		this.pay_platform_id = pay_platform_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBank_code() {
		return bank_code;
	}
	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public String getEanble() {
		return eanble;
	}
	public void setEanble(String eanble) {
		this.eanble = eanble;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getYlbankcode() {
		return ylbankcode;
	}
	public void setYlbankcode(String ylbankcode) {
		this.ylbankcode = ylbankcode;
	}
	public String getSupport_type() {
		return support_type;
	}
	public void setSupport_type(String support_type) {
		this.support_type = support_type;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getPlatform_en_name() {
		return platform_en_name;
	}
	public void setPlatform_en_name(String platform_en_name) {
		this.platform_en_name = platform_en_name;
	}
	
}
