package com.web.util.weixinAbout;

import java.io.Serializable;
import java.util.Date;

/***
 * 
 * @author wangenlai
 * TODO 临时票据JSAPI_TICKET(临时票据)  ACCESSTOKEN(全局TOKEN) ENABLE(是否启用(Y/N)) CREATE_TIME(创建时间) CREATE_PER(创建人) UPDATE_TIME(更新时间) 
 */
public class Signature implements Serializable{

	/**
	 * 序列版本编号
	 */
	private static final long serialVersionUID = 8991418129954512824L;
	private Integer id;
	private String jsapid_Ticket;//临时票据
	private String accesstoken;//全局TOKEN
	private Integer enable;  //是否启用(Y/N)
	private Date createTime;//创建时间
	private String createPer;//创建人
	private Date updateTime;//更新时间
	private String updatePer;//更新人
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getJsapid_Ticket() {
		return jsapid_Ticket;
	}
	public void setJsapid_Ticket(String jsapid_Ticket) {
		this.jsapid_Ticket = jsapid_Ticket;
	}
	public String getAccesstoken() {
		return accesstoken;
	}
	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}
	public Integer getEnable() {
		return enable;
	}
	public void setEnable(Integer enable) {
		this.enable = enable;
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
	
}
