package com.web.domain;

import java.io.Serializable;

public class Prize implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private Long id;
	
	/**
	 * 奖品
	 */
	private String prize;
	
	/**
	 * 期数
	 */
	private String installment;
	
	/**
	 * 名次
	 */
	private Integer sort;
	
	/**
	 * 类型
	 */
	private String kind;
	
	/**
	 * 奖参
	 */
	private String prize_msg;
	
	/**
	 * 是否兑过奖
	 */
	private String enable;
	
	/**
	 * 是否到兑奖时间
	 */
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPrize() {
		return prize;
	}

	public void setPrize(String prize) {
		this.prize = prize;
	}

	public String getInstallment() {
		return installment;
	}

	public void setInstallment(String installment) {
		this.installment = installment;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getPrize_msg() {
		return prize_msg;
	}

	public void setPrize_msg(String prize_msg) {
		this.prize_msg = prize_msg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
