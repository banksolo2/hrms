package com.cust.hrms.models;

import java.sql.Timestamp;
import java.util.*;
import java.text.SimpleDateFormat;

public class Employee {
	private int employeeId;
	private String firstName;
	private String middleName;
	private String lastName;
	private String dateOfEmployment;
	private String email;
	private int employeeStatusId;
	private int stateId;
	private String mobileNumber;
	private int departmentId;
	private int levelId;
	private int branchId;
	private int companyId;
	private String staffId;
	private String title;
	private String dateOfBirth;
	private String nameInitials;
	private int genderId;
	private int martialStatusId;
	private String currentAddress;
	private String personalEmail;
	private String createdAt;
	private String updatedAt;
	private String password;
	private Timestamp lastLoginAt;
	private int leaveSupervisorId;
	private double personalProductionTarget;
	private int createdBy;
	private int updatedBy;
	
	
	public int getLeaveSupervisorId() {
		return leaveSupervisorId;
	}
	public void setLeaveSupervisorId(int leaveSupervisorId) {
		this.leaveSupervisorId = leaveSupervisorId;
	}
	public double getPersonalProductionTarget() {
		return personalProductionTarget;
	}
	public void setPersonalProductionTarget(double personalProductionTarget) {
		this.personalProductionTarget = personalProductionTarget;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Timestamp getLastLoginAt() {
		return lastLoginAt;
	}
	public void setLastLoginAt() {
		Date date = new Date();
		long time = date.getTime();
		this.lastLoginAt = new  Timestamp(time);
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getDateOfEmployment() {
		return dateOfEmployment;
	}
	public void setDateOfEmployment(String dateOfEmployment) {
		this.dateOfEmployment =  dateOfEmployment;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getEmployeeStatusId() {
		return employeeStatusId;
	}
	public void setEmployeeStatusId(int employeeStatusId) {
		this.employeeStatusId = employeeStatusId;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public int getStateId() {
		return stateId;
	}
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public int getLevelId() {
		return levelId;
	}
	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getNameInitials() {
		return nameInitials;
	}
	public void setNameInitials(String nameInitials) {
		this.nameInitials = nameInitials;
	}
	public int getGenderId() {
		return genderId;
	}
	public void setGenderId(int genderId) {
		this.genderId = genderId;
	}
	public int getMartialStatusId() {
		return martialStatusId;
	}
	public void setMartialStatusId(int martialStatusId) {
		this.martialStatusId = martialStatusId;
	}
	public String getCurrentAddress() {
		return currentAddress;
	}
	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}
	public String getPersonalEmail() {
		return personalEmail;
	}
	public void setPersonalEmail(String personalEmail) {
		this.personalEmail = personalEmail;
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
		return "Employee [employeeId=" + employeeId + ", firstName=" + firstName + ", middleName=" + middleName
				+ ", lastName=" + lastName + ", dateOfEmployment=" + dateOfEmployment + ", email=" + email
				+ ", employeeStatusId=" + employeeStatusId + ", stateId=" + stateId + ", mobileNumber=" + mobileNumber
				+ ", departmentId=" + departmentId + ", levelId=" + levelId + ", branchId=" + branchId + ", companyId="
				+ companyId + ", staffId=" + staffId + ", title=" + title + ", dateOfBirth=" + dateOfBirth
				+ ", nameInitials=" + nameInitials + ", genderId=" + genderId + ", martialStatusId=" + martialStatusId
				+ ", currentAddress=" + currentAddress + ", personalEmail=" + personalEmail + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", password=" + password + ", lastLoginAt=" + lastLoginAt
				+ ", leaveSupervisorId=" + leaveSupervisorId + ", personalProductionTarget=" + personalProductionTarget
				+ ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + "]";
	}
	
	
}
