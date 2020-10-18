package com.cust.hrms.dao;

import java.sql.*;
import com.cust.hrms.models.*;

public class RequisitionDao {
	private DbConnection dbcon = new DbConnection();
	private RequisitionStatusDao rsd = new RequisitionStatusDao();
	private RequisitionTypeDao rtd = new RequisitionTypeDao();
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
	
	public static void main(String args[]) {
		RequisitionDao rd = new RequisitionDao();
		DateDao dd = new DateDao();
		RequisitionStatusDao rsd = new RequisitionStatusDao();
		Requisition r = new Requisition();
		r.setCreatedDate(dd.getTodayDate());
		r.setRequisitionTypeId(3);
		r.setSubject("Testing");
		r.setRequesterId(4);
		r.setSupervisorId(2);
		r.setRecipients("'2'|'7'");
		r.setFileUrl("#");
		r.setRequisitionStatusId(1);
		r.setCreatedBy(3);
	}
}
