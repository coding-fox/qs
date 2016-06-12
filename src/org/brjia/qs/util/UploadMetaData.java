package org.brjia.qs.util;

import java.util.Date;

public class UploadMetaData {

	private Integer sid;
	private Integer eid;
	private Integer year;
	private Integer week;
	private Integer round;
	private String uploadUserName;
	private Date fightTime;
	private Date createTime;
	/**
	 * 声势值
	 */
	private Integer impetus;
	private Integer value;//show value
	
	
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public Integer getImpetus() {
		return impetus;
	}
	public void setImpetus(Integer impetus) {
		this.value=impetus;
		this.impetus = impetus;
	}
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public Integer getEid() {
		return eid;
	}
	public void setEid(Integer eid) {
		this.eid = eid;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getWeek() {
		return week;
	}
	public void setWeek(Integer week) {
		this.week = week;
	}
	public Integer getRound() {
		return round;
	}
	public void setRound(Integer round) {
		this.round = round;
	}
	public String getUploadUserName() {
		return uploadUserName;
	}
	public void setUploadUserName(String uploadUserName) {
		this.uploadUserName = uploadUserName;
	}
	
	public Date getFightTime() {
		return fightTime;
	}
	public void setFightTime(Date fightTime) {
		this.fightTime = fightTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
