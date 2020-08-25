package com.cust.hrms.models;

public class EmployeeRole {
	private int employeeRoleId;
	private int employeeId;
	private int roleId;
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
	public int getEmployeeRoleId() {
		return employeeRoleId;
	}
	public void setEmployeeRoleId(int employeeRoleId) {
		this.employeeRoleId = employeeRoleId;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
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
		return "EmployeeRole [employeeRoleId=" + employeeRoleId + ", employeeId=" + employeeId + ", roleId=" + roleId
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", createdBy=" + createdBy + ", updatedBy="
				+ updatedBy + "]";
	}
	
	
	
}
