package com.cust.hrms.models;

public class PayrollRoleLevel {
	private int payrollRoleLevelId;
	private int payrollRoleNameId;
	private int levelId;
	private int createdBy;
	private int updatedBy;
	private String createdAt;
	private String updatedAt;
	public int getPayrollRoleLevelId() {
		return payrollRoleLevelId;
	}
	public void setPayrollRoleLevelId(int payrollRoleLevelId) {
		this.payrollRoleLevelId = payrollRoleLevelId;
	}
	public int getPayrollRoleNameId() {
		return payrollRoleNameId;
	}
	public void setPayrollRoleNameId(int payrollRoleNameId) {
		this.payrollRoleNameId = payrollRoleNameId;
	}
	public int getLevelId() {
		return levelId;
	}
	public void setLevelId(int levelId) {
		this.levelId = levelId;
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
		return "PayrollRoleLevel [payrollRoleLevelId=" + payrollRoleLevelId + ", payrollRoleNameId=" + payrollRoleNameId
				+ ", levelId=" + levelId + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
	
}
