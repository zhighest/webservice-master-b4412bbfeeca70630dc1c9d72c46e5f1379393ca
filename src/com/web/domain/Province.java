package com.web.domain;

import java.io.Serializable;
import java.util.List;

public class Province implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String provinceId;
	private String provinceName;
	private String provinceCode;
	private List<City> cityList;
	
	
	
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public List<City> getCityList() {
		return cityList;
	}
	public void setCityList(List<City> cityList) {
		this.cityList = cityList;
	}
}
