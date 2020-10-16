package com.cust.hrms.models;

public class Level {
	private int levelId;
	private String name;
	private String code;
	private String createdAt;
	private String updatedAt;
	private int leaveDays;
	private int createdBy;
	private int updatedBy;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getLeaveDays() {
		return leaveDays;
	}
	public void setLeaveDays(int leaveDays) {
		this.leaveDays = leaveDays;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public int getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}
	public int getLevelId() {
		return levelId;
	}
	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	@Override
	public String toString() {
		return "Level [levelId=" + levelId + ", name=" + name + ", code=" + code + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", leaveDays=" + leaveDays + ", createdBy=" + createdBy + ", updatedBy="
				+ updatedBy + "]";
	}
	
	
	
}
