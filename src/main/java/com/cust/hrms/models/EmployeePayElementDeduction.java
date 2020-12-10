package com.cust.hrms.models;

public class EmployeePayElementDeduction {
	private int employeePayElementDeductionId;
	private int employeeId;
	private int payElementDeductionTypeId;
	private double amount;
	private String monthNo;
	private String year;
	private int createdBy;
	private int updatedBy;
	private String createdAt;
	private String updatedAt;
	public int getEmployeePayElementDeductionId() {
		return employeePayElementDeductionId;
	}
	public void setEmployeePayElementDeductionId(int employeePayElementDeductionId) {
		this.employeePayElementDeductionId = employeePayElementDeductionId;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public int getPayElementDeductionTypeId() {
		return payElementDeductionTypeId;
	}
	public void setPayElementDeductionTypeId(int payElementDeductionTypeId) {
		this.payElementDeductionTypeId = payElementDeductionTypeId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getMonthNo() {
		return monthNo;
	}
	public void setMonthNo(String monthNo) {
		this.monthNo = monthNo;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
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
		return "EmployeePayElementDeduction [employeePayElementDeductionId=" + employeePayElementDeductionId
				+ ", employeeId=" + employeeId + ", payElementDeductionTypeId=" + payElementDeductionTypeId
				+ ", amount=" + amount + ", monthNo=" + monthNo + ", year=" + year + ", createdBy=" + createdBy
				+ ", updatedBy=" + updatedBy + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
	
}
