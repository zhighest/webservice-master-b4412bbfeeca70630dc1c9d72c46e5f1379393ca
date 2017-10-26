package com.web.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RateRisesCoupons implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long user_id;
	private Date validity_period;
	private String product_id;
	private Integer rate_rises_days;
	private BigDecimal rate_rises;
	private String status;
	private Date begin_date;
	private Date end_date;
	private Long order_id;
	private String mobile;
	private Date create_time;
	private Date update_time;
	private String get_way;
	private String use_flag;
	private Integer remanDays;
	
	//日期参数的格式化
	private String validity_period_str;
	private String begin_date_str;
	private String end_date_str;
	private String create_time_str;
	private String update_time_str;
	//产品名称格式化
	private String product_id_str;
	
	
	
	
	
	public String getProduct_id_str() {
		return product_id_str;
	}
	public void setProduct_id_str(String product_id_str) {
		this.product_id_str = product_id_str;
	}
	public String getUse_flag() {
		return use_flag;
	}
	public void setUse_flag(String use_flag) {
		this.use_flag = use_flag;
	}
	public String getValidity_period_str() {
		return validity_period_str;
	}
	public void setValidity_period_str(String validity_period_str) {
		this.validity_period_str = validity_period_str;
	}
	public String getBegin_date_str() {
		return begin_date_str;
	}
	public void setBegin_date_str(String begin_date_str) {
		this.begin_date_str = begin_date_str;
	}
	public String getEnd_date_str() {
		return end_date_str;
	}
	public void setEnd_date_str(String end_date_str) {
		this.end_date_str = end_date_str;
	}
	public String getCreate_time_str() {
		return create_time_str;
	}
	public void setCreate_time_str(String create_time_str) {
		this.create_time_str = create_time_str;
	}
	public String getUpdate_time_str() {
		return update_time_str;
	}
	public void setUpdate_time_str(String update_time_str) {
		this.update_time_str = update_time_str;
	}
	public String getGet_way() {
		return get_way;
	}
	public void setGet_way(String get_way) {
		this.get_way = get_way;
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
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
	public Date getValidity_period() {
		return validity_period;
	}
	public void setValidity_period(Date validity_period) {
		this.validity_period = validity_period;
	}
	public Integer getRate_rises_days() {
		return rate_rises_days;
	}
	public void setRate_rises_days(Integer rate_rises_days) {
		this.rate_rises_days = rate_rises_days;
	}
	public BigDecimal getRate_rises() {
		return rate_rises;
	}
	public void setRate_rises(BigDecimal rate_rises) {
		this.rate_rises = rate_rises;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getBegin_date() {
		return begin_date;
	}
	public void setBegin_date(Date begin_date) {
		this.begin_date = begin_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	public Long getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}
	
	public Integer getRemanDays() {
		return remanDays;
	}
	public void setRemanDays(Integer remanDays) {
		this.remanDays = remanDays;
	}
	
	
}
