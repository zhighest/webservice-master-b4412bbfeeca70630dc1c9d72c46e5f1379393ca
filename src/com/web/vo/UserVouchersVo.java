package com.web.vo;

import java.util.List;

import com.web.domain.Scattered;
import com.web.domain.VouchersRule;

public class UserVouchersVo {
	
	
	
	
	public String getVoucherTypeId() {
		return voucherTypeId;
	}
	public void setVoucherTypeId(String voucherTypeId) {
		this.voucherTypeId = voucherTypeId;
	}
	private Long userId;
	private String voucherAmount;
	private String voucherDesc;
	private String scatteredLoanDesc;
	private String validityPeriodDesc;
	private String voucherId;
	private String voucherTypeId;
	private String investmentAmount;
	
	public String getInvestmentAmount() {
		return investmentAmount;
	}
	public void setInvestmentAmount(String investmentAmount) {
		this.investmentAmount = investmentAmount;
	}
	
	
	public VouchersRule vouchersRule;
	
	public List<Scattered> scatteredList ;
	
	public List<Scattered> getScatteredList() {
		return scatteredList;
	}
	public void setScatteredList(List<Scattered> scatteredList) {
		this.scatteredList = scatteredList;
	}
	
	public VouchersRule getVouchersRule() {
		return vouchersRule;
	}
	public void setVouchersRule(VouchersRule vouchersRule) {
		this.vouchersRule = vouchersRule;
	}
	
	public String getVoucherId() {
		return voucherId;
	}
	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getVoucherAmount() {
		return voucherAmount;
	}
	public void setVoucherAmount(String voucherAmount) {
		this.voucherAmount = voucherAmount;
	}
	public String getVoucherDesc() {
		return voucherDesc;
	}
	public void setVoucherDesc(String voucherDesc) {
		this.voucherDesc = voucherDesc;
	}
	public String getScatteredLoanDesc() {
		return scatteredLoanDesc;
	}
	public void setScatteredLoanDesc(String scatteredLoanDesc) {
		this.scatteredLoanDesc = scatteredLoanDesc;
	}
	public String getValidityPeriodDesc() {
		return validityPeriodDesc;
	}
	public void setValidityPeriodDesc(String validityPeriodDesc) {
		this.validityPeriodDesc = validityPeriodDesc;
	}
	
	
}
