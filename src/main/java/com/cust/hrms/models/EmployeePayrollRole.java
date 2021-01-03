package com.cust.hrms.models;

public class EmployeePayrollRole {
	private int employeePayrollRoleId;
	private int employeeId;
	private int payrollRoleNameId;
	private int createdBy;
	private int updatedBy;
	private String createdAt;
	private String updatedAt;
	public int getEmployeePayrollRoleId() {
		return employeePayrollRoleId;
	}
	public void setEmployeePayrollRoleId(int employeePayrollRoleId) {
		this.employeePayrollRoleId = employeePayrollRoleId;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public int getPayrollRoleNameId() {
		return payrollRoleNameId;
	}
	public void setPayrollRoleNameId(int payrollRoleNameId) {
		this.payrollRoleNameId = payrollRoleNameId;
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
		return "EmployeePayrollRole [employeePayrollRoleId=" + employeePayrollRoleId + ", employeeId=" + employeeId
				+ ", payrollRoleNameId=" + payrollRoleNameId + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
	
}
