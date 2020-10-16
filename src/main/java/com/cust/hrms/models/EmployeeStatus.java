package com.cust.hrms.models;

public class EmployeeStatus {
	private int employeeStatusId;
	private String name;
	private String createdAt;
	private String updatedAt;
	public int getEmployeeStatusId() {
		return employeeStatusId;
	}
	public void setEmployeeStatusId(int employeeStatusId) {
		this.employeeStatusId = employeeStatusId;
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
		return "EmployeeStatus [employeeStatusId=" + employeeStatusId + ", name=" + name + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "]";
	}
	
	
}
