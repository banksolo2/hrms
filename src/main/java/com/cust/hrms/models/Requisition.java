package com.cust.hrms.models;

public class Requisition {
	private int requisitionId;
	private String createdDate;
	private int requisitionTypeId;
	private String subject;
	private int requesterId;
	private int supervisorId;
	private String recipients;
	private String fileUrl;
	private int requisitionStatusId;
	private int createdBy;
	private int updatedBy;
	private String createdAt;
	private String updatedAt;
	public int getRequisitionId() {
		return requisitionId;
	}
	public void setRequisitionId(int requisitionId) {
		this.requisitionId = requisitionId;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public int getRequisitionTypeId() {
		return requisitionTypeId;
	}
	public void setRequisitionTypeId(int requisitionTypeId) {
		this.requisitionTypeId = requisitionTypeId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public int getRequesterId() {
		return requesterId;
	}
	public void setRequesterId(int requesterId) {
		this.requesterId = requesterId;
	}
	public int getSupervisorId() {
		return supervisorId;
	}
	public void setSupervisorId(int supervisorId) {
		this.supervisorId = supervisorId;
	}
	public String getRecipients() {
		return recipients;
	}
	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public int getRequisitionStatusId() {
		return requisitionStatusId;
	}
	public void setRequisitionStatusId(int requisitionStatusId) {
		this.requisitionStatusId = requisitionStatusId;
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
		return "Requisition [requisitionId=" + requisitionId + ", createdDate=" + createdDate + ", requisitionTypeId="
				+ requisitionTypeId + ", subject=" + subject + ", requesterId=" + requesterId + ", supervisorId="
				+ supervisorId + ", recipients=" + recipients + ", fileUrl=" + fileUrl + ", requisitionStatusId="
				+ requisitionStatusId + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
	
}
