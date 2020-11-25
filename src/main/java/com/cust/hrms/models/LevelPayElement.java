package com.cust.hrms.models;

public class LevelPayElement {
	private int levelPayElementId;
	private int levelId;
	private int payElementId;
	private int createdBy;
	private int updatedBy;
	private String createdAt;
	private String updatedAt;
	public int getLevelPayElementId() {
		return levelPayElementId;
	}
	public void setLevelPayElementId(int levelPayElementId) {
		this.levelPayElementId = levelPayElementId;
	}
	public int getLevelId() {
		return levelId;
	}
	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}
	public int getPayElementId() {
		return payElementId;
	}
	public void setPayElementId(int payElementId) {
		this.payElementId = payElementId;
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
		return "LevelPayElement [levelPayElementId=" + levelPayElementId + ", levelId=" + levelId + ", payElementId="
				+ payElementId + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "]";
	}
	
	
}
