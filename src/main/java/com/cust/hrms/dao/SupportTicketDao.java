package com.cust.hrms.dao;

import java.sql.*;
import com.cust.hrms.models.*;

public class SupportTicketDao {
	private DbConnection dbcon = new DbConnection();
	private SupportTicket st;
	private String query;
	private int count;
	private Statement stat;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public int createSupportTicketByDepartment(SupportTicket st) {
		query = "insert into support_tickets (issue_report_date, issue_type_id, issue_description, issue_for, department_id, "
				+ "created_by) values(?, ?, ?, ?, ?, ?)";
		st.setIssueFor("department");
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setDate(1, Date.valueOf(st.getIssueReportDate()));
			ps.setInt(2, st.getIssueTypeId());
			ps.setString(3, st.getIssueDescription());
			ps.setString(4, st.getIssueFor());
			ps.setInt(5, st.getDepartmentId());
			ps.setInt(6, st.getCreatedBy());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public static void main(String args[]) {
		SupportTicketDao std = new SupportTicketDao();
		SupportTicket st = new SupportTicket();
		st.setIssueReportDate("2020-10-05");
		st.setIssueTypeId(5);
		st.setIssueDescription("Error in the leave red zone.");
		st.setDepartmentId(3);
		st.setCreatedBy(2);
		int count = std.createSupportTicketByDepartment(st);
		System.out.println(count);
	}
}
