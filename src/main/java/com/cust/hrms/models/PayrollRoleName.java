package com.cust.hrms.models;

public class PayrollRoleName {
	private int payrollRoleNameId;
	private String name;
	private String code;
	private int createdBy;
	private int updatedBy;
	private String createdAt;
	private String updatedAt;
	public int getPayrollRoleNameId() {
		return payrollRoleNameId;
	}
	public void setPayrollRoleNameId(int payrollRoleNameId) {
		this.payrollRoleNameId = payrollRoleNameId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
		return "PayrollRoleName [payrollRoleNameId=" + payrollRoleNameId + ", name=" + name + ", code=" + code
				+ ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + "]";
	}
	
	
}
