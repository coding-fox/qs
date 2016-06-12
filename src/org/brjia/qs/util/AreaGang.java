package org.brjia.qs.util;

import java.util.Date;
import java.util.List;

public class AreaGang {

	private Integer areaId;
	private String areaName;
	List<Gang> gangs;
	
	private Integer createUserId;
	private String createUserName;
	private Date createTime;
	private String office;
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public List<Gang> getGangs() {
		return gangs;
	}
	public void setGangs(List<Gang> gangs) {
		this.gangs = gangs;
	}
	public Integer getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getOffice() {
		return office;
	}
	public void setOffice(String office) {
		this.office = office;
	}
	
}
