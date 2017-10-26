package com.web.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Scattered implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long bidId;
	private String productName;
	private String borrowType;
	private Double annualRate;
	private Integer remanPeriods;
	private String repaymentType;
	private BigDecimal finishedRatio;
	private BigDecimal remanAmount;
	private BigDecimal saleTotalAmount;
	private BigDecimal contactAmount;
	private Date startSellingTime;
	private Date endSaleTime;
	private String endSaleReason;
	private String sellingState;
	private String openSelling;
	private BigDecimal investMinAmount;
	private BigDecimal perServingAmount;
	private String isRecommend;
	private String scalSource;
	private Integer needDisposeOrdnum;
	private Date createTime;
	private String createPer;
	private Date updateTime;
	private String updatePer;
	private Long planId;
	private Long productId; 
	private String sellingType;
	
	public Long getPlanId() {
		return planId;
	}
	public void setPlanId(Long planId) {
		this.planId = planId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getBidId() {
		return bidId;
	}
	public void setBidId(Long bidId) {
		this.bidId = bidId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getBorrowType() {
		return borrowType;
	}
	public void setBorrowType(String borrowType) {
		this.borrowType = borrowType;
	}
	public Double getAnnualRate() {
		return annualRate;
	}
	public void setAnnualRate(Double annualRate) {
		this.annualRate = annualRate;
	}
	public Integer getRemanPeriods() {
		return remanPeriods;
	}
	public void setRemanPeriods(Integer remanPeriods) {
		this.remanPeriods = remanPeriods;
	}
	public String getRepaymentType() {
		return repaymentType;
	}
	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}
	public BigDecimal getFinishedRatio() {
		return finishedRatio;
	}
	public void setFinishedRatio(BigDecimal finishedRatio) {
		this.finishedRatio = finishedRatio;
	}
	public BigDecimal getRemanAmount() {
		return remanAmount;
	}
	public void setRemanAmount(BigDecimal remanAmount) {
		this.remanAmount = remanAmount;
	}
	public BigDecimal getSaleTotalAmount() {
		return saleTotalAmount;
	}
	public void setSaleTotalAmount(BigDecimal saleTotalAmount) {
		this.saleTotalAmount = saleTotalAmount;
	}
	public BigDecimal getContactAmount() {
		return contactAmount;
	}
	public void setContactAmount(BigDecimal contactAmount) {
		this.contactAmount = contactAmount;
	}
	public Date getStartSellingTime() {
		return startSellingTime;
	}
	public void setStartSellingTime(Date startSellingTime) {
		this.startSellingTime = startSellingTime;
	}
	public Date getEndSaleTime() {
		return endSaleTime;
	}
	public void setEndSaleTime(Date endSaleTime) {
		this.endSaleTime = endSaleTime;
	}
	public String getEndSaleReason() {
		return endSaleReason;
	}
	public void setEndSaleReason(String endSaleReason) {
		this.endSaleReason = endSaleReason;
	}
	public String getSellingState() {
		return sellingState;
	}
	public void setSellingState(String sellingState) {
		this.sellingState = sellingState;
	}
	public String getOpenSelling() {
		return openSelling;
	}
	public void setOpenSelling(String openSelling) {
		this.openSelling = openSelling;
	}
	public BigDecimal getInvestMinAmount() {
		return investMinAmount;
	}
	public void setInvestMinAmount(BigDecimal investMinAmount) {
		this.investMinAmount = investMinAmount;
	}
	public BigDecimal getPerServingAmount() {
		return perServingAmount;
	}
	public void setPerServingAmount(BigDecimal perServingAmount) {
		this.perServingAmount = perServingAmount;
	}
	public String getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}
	public String getScalSource() {
		return scalSource;
	}
	public void setScalSource(String scalSource) {
		this.scalSource = scalSource;
	}
	public Integer getNeedDisposeOrdnum() {
		return needDisposeOrdnum;
	}
	public void setNeedDisposeOrdnum(Integer needDisposeOrdnum) {
		this.needDisposeOrdnum = needDisposeOrdnum;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreatePer() {
		return createPer;
	}
	public void setCreatePer(String createPer) {
		this.createPer = createPer;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdatePer() {
		return updatePer;
	}
	public void setUpdatePer(String updatePer) {
		this.updatePer = updatePer;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getSellingType() {
		return sellingType;
	}
	public void setSellingType(String sellingType) {
		this.sellingType = sellingType;
	}
}
