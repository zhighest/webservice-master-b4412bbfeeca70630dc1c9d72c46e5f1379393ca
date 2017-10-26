package com.web.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class VouchersRule  implements Serializable {

	
	
	private static final long serialVersionUID = 1L;

	private Long id;
	private String voucher_type_desc;
	private String product_type;
	private String product_type_desc;
	private Integer minlockperiod;
	private BigDecimal voucher_amount;
	private Integer validity_period;
	private BigDecimal investment_amount;
	private Integer score_rule;
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
	public String getVoucher_type_desc() {
		return voucher_type_desc;
	}
	public void setVoucher_type_desc(String voucher_type_desc) {
		this.voucher_type_desc = voucher_type_desc;
	}
	public String getProduct_type() {
		return product_type;
	}
	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}
	public String getProduct_type_desc() {
		return product_type_desc;
	}
	public void setProduct_type_desc(String product_type_desc) {
		this.product_type_desc = product_type_desc;
	}
	public Integer getMinlockperiod() {
		return minlockperiod;
	}
	public void setMinlockperiod(Integer minlockperiod) {
		this.minlockperiod = minlockperiod;
	}
	public BigDecimal getVoucher_amount() {
		return voucher_amount;
	}
	public void setVoucher_amount(BigDecimal voucher_amount) {
		this.voucher_amount = voucher_amount;
	}
	public Integer getValidity_period() {
		return validity_period;
	}
	public void setValidity_period(Integer validity_period) {
		this.validity_period = validity_period;
	}
	public BigDecimal getInvestment_amount() {
		return investment_amount;
	}
	public void setInvestment_amount(BigDecimal investment_amount) {
		this.investment_amount = investment_amount;
	}
	public Integer getScore_rule() {
		return score_rule;
	}
	public void setScore_rule(Integer score_rule) {
		this.score_rule = score_rule;
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
