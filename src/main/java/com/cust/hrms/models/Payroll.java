package com.cust.hrms.models;

public class Payroll {
	private int payrollId;
	private int employeePayElementId;
	private int employeeId;
	private int levelId;
	private int payElementid;
	private double amount;
	private String monthNo;
	private String year;
	private int createdBy;
	private int updatedBy;
	private String createdAt;
	private String updatedAt;
	public int getPayrollId() {
		return payrollId;
	}
	public void setPayrollId(int payrollId) {
		this.payrollId = payrollId;
	}
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
	public int getPayElementid() {
		return payElementid;
	}
	public void setPayElementid(int payElementid) {
		this.payElementid = payElementid;
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
		return "Payroll [payrollId=" + payrollId + ", employeePayElementId=" + employeePayElementId + ", employeeId="
				+ employeeId + ", levelId=" + levelId + ", payElementid=" + payElementid + ", amount=" + amount
				+ ", monthNo=" + monthNo + ", year=" + year + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
	
}
