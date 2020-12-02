package com.cust.hrms.models;

public class EmployeePayElement {
	private int employeePayElementId;
	private int employeeId;
	private int levelId;
	private int payElementId;
	private int boundaryId;
	private double amount;
	private int createdBy;
	private int updatedBy;
	private String createdAt;
	private String updatedAt;
	public int getEmployeePayElementId() {
		return employeePayElementId;
	}
	public void setEmployeePayElementId(int employeePayElementId) {
		this.employeePayElementId = employeePayElementId;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
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
	public int getBoundaryId() {
		return boundaryId;
	}
	public void setBoundaryId(int boundaryId) {
		this.boundaryId = boundaryId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
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
		return "EmployeePayElement [employeePayElementId=" + employeePayElementId + ", employeeId=" + employeeId
				+ ", levelId=" + levelId + ", payElementId=" + payElementId + ", boundaryId=" + boundaryId + ", amount="
				+ amount + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "]";
	}
	
	
}
