package org.brjia.qs.util;

import java.util.Date;

/**
 * 用户信息
 * @author hujiawei
 *
 */
public class UserInfo {
	/**
	 * 名称
	 */
	private String email;
	private Date createTime;
	private String name;
	private Integer userId;
	private Integer memberId;
	/**
	 * 帮会id
	 */
	private Integer gangId;
//	private String gangName;
	/**
	 * 家族id
	 */
	private Integer familyId;
//	private String familyName;
	/**
	 * 服务器id
	 */
	private Integer areaId;
//	private String areaName;
	/**
	 * 倩女id
	 */
	private Integer officeId;
	/**
	 * 玩家等级
	 */
	private String level;
	/**
	 * 玩家角色
	 */
	private String role;
	/**
	 * 是否是匿名角色
	 * @return
	 */
	public boolean isAnyone(){
		return memberId==null;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public Integer getGangId() {
		return gangId;
	}
	public void setGangId(Integer gangId) {
		this.gangId = gangId;
	}
	public Integer getFamilyId() {
		return familyId;
	}
	public void setFamilyId(Integer familyId) {
		this.familyId = familyId;
	}
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public Integer getOfficeId() {
		return officeId;
	}
	public void setOfficeId(Integer officeId) {
		this.officeId = officeId;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	

}
