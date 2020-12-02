package com.cust.hrms.models;

public class Boundary {
	private int boundaryId;
	private int levelPayElementId;
	private int levelId;
	private int payElementId;
	private double highestAmount;
	private double lowestAmount;
	private double defaultAmount;
	private int createdBy;
	private int updatedBy;
	private String createdAt;
	private String updatedAt;
	public int getBoundaryId() {
		return boundaryId;
	}
	public void setBoundaryId(int boundaryId) {
		this.boundaryId = boundaryId;
	}
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
	public double getHighestAmount() {
		return highestAmount;
	}
	public void setHighestAmount(double highestAmount) {
		this.highestAmount = highestAmount;
	}
	public double getLowestAmount() {
		return lowestAmount;
	}
	public void setLowestAmount(double lowestAmount) {
		this.lowestAmount = lowestAmount;
	}
	public double getDefaultAmount() {
		return defaultAmount;
	}
	public void setDefaultAmount(double defaultAmount) {
		this.defaultAmount = defaultAmount;
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
		return "Boundary [boundaryId=" + boundaryId + ", levelPayElementId=" + levelPayElementId + ", levelId="
				+ levelId + ", payElementId=" + payElementId + ", upperAmount=" + highestAmount + ", lowerAmount="
				+ lowestAmount + ", defaultAmount=" + defaultAmount + ", createdBy=" + createdBy + ", updatedBy="
				+ updatedBy + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
	
}
