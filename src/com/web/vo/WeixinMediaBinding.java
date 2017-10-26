package com.web.vo;

import java.io.Serializable;
import java.util.Date;

/***
 * 
 * @author wangenlai
 * TODO 媒体绑定（1、腾讯微信；2、腾讯微博；3、新浪微博））
 */
public class WeixinMediaBinding implements Serializable{

	/**
	 * 序列版本编号
	 */
	private static final long serialVersionUID = 1993944057661157517L;
	private Integer id;
	private String mediaType;//MEDIA_TYPE 媒介类型（1、腾讯微信；2、腾讯微博；3、新浪微博）
	private String mediaUid;//MEDIA_UID  媒介UID
	private Integer userId; //USER_ID 用户ID 
	private String invitationCode;//INVITATION_CODE  邀请码
	private String status;//STATUS 有效状态（0：无效；1：有效）
	private Date createTime;//CREATE_TIME 创建时间
	private String createPer;//CREATE_PER 创建人
	private Date updateTime;//UPDATE_TIME 更新时间
	private String updatePer;//UPDATE_PER 更新人
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMediaType() {
		return mediaType;
	}
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getMediaUid() {
		return mediaUid;
	}
	public void setMediaUid(String mediaUid) {
		this.mediaUid = mediaUid;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getInvitationCode() {
		return invitationCode;
	}
	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
