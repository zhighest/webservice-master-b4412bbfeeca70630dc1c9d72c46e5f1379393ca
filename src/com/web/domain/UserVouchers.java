package com.web.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UserVouchers implements Serializable{

	
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long user_id;
	private Long voucher_type_id;
	private Date startdate;
	private Date enddate;
	private String status;
	private String orderid;
	private BigDecimal voucher_amount;
	private BigDecimal investment_amount;
	private String gift_staff;
	private String source;
	private Date create_time;
	private String create_per;
	private Date update_time;
	private String update_per;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Long getVoucher_type_id() {
		return voucher_type_id;
	}
	public void setVoucher_type_id(Long voucher_type_id) {
		this.voucher_type_id = voucher_type_id;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public BigDecimal getVoucher_amount() {
		return voucher_amount;
	}
	public void setVoucher_amount(BigDecimal voucher_amount) {
		this.voucher_amount = voucher_amount;
	}
	public BigDecimal getInvestment_amount() {
		return investment_amount;
	}
	public void setInvestment_amount(BigDecimal investment_amount) {
		this.investment_amount = investment_amount;
	}
	public String getGift_staff() {
		return gift_staff;
	}
	public void setGift_staff(String gift_staff) {
		this.gift_staff = gift_staff;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getCreate_per() {
		return create_per;
	}
	public void setCreate_per(String create_per) {
		this.create_per = create_per;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public String getUpdate_per() {
		return update_per;
	}
	public void setUpdate_per(String update_per) {
		this.update_per = update_per;
	}
	
}
