package com.cust.hrms.dao;

import java.sql.*;
import java.io.*;
import com.cust.hrms.models.*;

public class RequisitionDao {
	private DbConnection dbcon = new DbConnection();
	private RequisitionStatusDao rsd = new RequisitionStatusDao();
	private RequisitionTypeDao rtd = new RequisitionTypeDao();
	private FileUploadDao fud = new FileUploadDao();
	private Requisition r;
	private int count;
	private String query;
	private Statement stat;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public int createRequisition(Requisition r) {
		query = "insert into requisitions (created_date, requisition_type_id, subject, requester_id, supervisor_id, "
				+ "recipients, file_url, requisition_status_id, created_by) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setDate(1, Date.valueOf(r.getCreatedDate()));
			ps.setInt(2, r.getRequisitionTypeId());
			ps.setString(3, r.getSubject());
			ps.setInt(4, r.getRequesterId());
			ps.setInt(5, r.getSupervisorId());
			ps.setString(6, r.getRecipients());
			ps.setString(7, r.getFileUrl());
			ps.setInt(8, r.getRequisitionStatusId());
			ps.setInt(9, r.getCreatedBy());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public Requisition getRequisitionById(int requisitionId) {
		r = new Requisition();
		query = "select * from requisitions where requisition_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, requisitionId);
			rs = ps.executeQuery();
			if(rs.next()) {
				r.setRequisitionId(rs.getInt("requisition_id"));
				r.setCreatedDate(rs.getDate("created_date").toString());
				r.setRequisitionTypeId(rs.getInt("requisition_type_id"));
				r.setSubject(rs.getString("subject"));
				r.setRequesterId(rs.getInt("requester_id"));
				r.setSupervisorId(rs.getInt("supervisor_id"));
				r.setRecipients(rs.getString("recipients"));
				r.setFileUrl(rs.getString("file_url"));
				r.setRequisitionStatusId(rs.getInt("requisition_status_id"));
				r.setComment(rs.getString("comment"));
				r.setCreatedBy(rs.getInt("created_by"));
				r.setUpdatedBy(rs.getInt("updated_by"));
				r.setCreatedAt(rs.getTimestamp("created_at").toString());
				r.setUpdatedAt(rs.getTimestamp("updated_at").toString());
				r.setApprovedBy(rs.getInt("approved_by"));
				r.setDeclinedBy(rs.getInt("declined_by"));
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return r;
	}
	
	public int updateRequisition(Requisition r) {
		query = "update requisitions set requisition_type_id = ?, subject = ?, supervisor_id = ?, "
				+ "	recipients = ?, file_url = ?, requisition_status_id	= ?, updated_by = ?, comment = ?, "
				+ "approved_by = ?, declined_by = ? where requisition_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, r.getRequisitionTypeId());
			ps.setString(2, r.getSubject());
			ps.setInt(3, r.getSupervisorId());
			ps.setString(4, r.getRecipients());
			ps.setString(5, r.getFileUrl());
			ps.setInt(6, r.getRequisitionStatusId());
			ps.setInt(7, r.getUpdatedBy());
			ps.setString(8, r.getComment());
			ps.setInt(9, r.getApprovedBy());
			ps.setInt(10, r.getDeclinedBy());
			ps.setInt(11, r.getRequisitionId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public int deleteRequisition(Requisition r) {
		query = "delete from requisitions where requisition_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, r.getRequisitionId());
			count = ps.executeUpdate();
			if(count >= 1) {
				File file = new File(fud.getUrl()+r.getFileUrl());
				file.delete();
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public ResultSet getRequesterRequisitionReport(int requesterId, int requisitionStatusId) {
		query = "select * from requisitions where requester_id = ? and requisition_status_id = ? order by created_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, requesterId);
			ps.setInt(2, requisitionStatusId);
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getRequesterPendingRequisitionReport(int requesterId) {
		query = "select * from requisitions where requester_id = ? and requisition_status_id  in (?, ?) "
				+ "order by created_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, requesterId);
			ps.setInt(2, rsd.getRequisitionStatusId("sent_to_supervisor_for_authorization"));
			ps.setInt(3, rsd.getRequisitionStatusId("sent_to_recipient_for_approval"));
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getSupervisorRequisitionReport(int supervisorId, int requisitionStatusId) {
		query = "select * from requisitions where supervisor_id = ? and requisition_status_id = ? order by created_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, supervisorId);
			ps.setInt(2, requisitionStatusId);
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getRecipientRequisitionReport(int recipientId, int requisitionStatusId) {
		query = "select * from requisitions where recipients like ? and requisition_status_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, "%'"+recipientId+"'%");
			ps.setInt(2, requisitionStatusId);
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getHrHistoryRequisitionReport() {
		query = "select * from requisitions where requisition_status != ? order by created_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, rsd.getRequisitionStatusId("drafted"));
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	
	
	public static void main(String args[]) {
		RequisitionDao rd = new RequisitionDao();
		DateDao dd = new DateDao();
		RequisitionStatusDao rsd = new RequisitionStatusDao();
		Requisition r = rd.getRequisitionById(1);
		int count = rd.deleteRequisition(r);
		System.out.println(count);
	}
}
