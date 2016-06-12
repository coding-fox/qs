package org.brjia.qs.round;

import java.util.Date;

public class UploadInfo {

	private Integer summaryId;
	private Integer areaId;
	private String uploadUserName;
	private String gangName;
	private String areaName;
	private Date uploadTime;
	
	public Integer getSummaryId() {
		return summaryId;
	}
	public void setSummaryId(Integer summaryId) {
		this.summaryId = summaryId;
	}
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public String getUploadUserName() {
		return uploadUserName;
	}
	public void setUploadUserName(String uploadUserName) {
		this.uploadUserName = uploadUserName;
	}
	public String getGangName() {
		return gangName;
	}
	public void setGangName(String gangName) {
		this.gangName = gangName;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	
	
}
