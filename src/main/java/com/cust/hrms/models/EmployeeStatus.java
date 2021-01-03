package com.cust.hrms.models;

public class EmployeeStatus {
	private int employeeStatusId;
	private String name;
	private String code;
	private String createdAt;
	private String updatedAt;
	private int createdBy;
	private int updatedBy;
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
	public int getEmployeeStatusId() {
		return employeeStatusId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
		return "EmployeeStatus [employeeStatusId=" + employeeStatusId + ", name=" + name + ", code=" + code
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", createdBy=" + createdBy + ", updatedBy="
				+ updatedBy + "]";
	}
	
	
}
