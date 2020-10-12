package com.cust.hrms.dao;

import java.sql.*;
import com.cust.hrms.models.*;

public class SupportTicketDao {
	private DbConnection dbcon = new DbConnection();
	private SupportTicketStatusDao stsd = new SupportTicketStatusDao();
	private SupportTicket st;
	private String query;
	private int count;
	private Statement stat;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public int createSupportTicketForDepartment(SupportTicket st) {
		query = "insert into support_tickets (issue_report_date, issue_type_id, issue_description, issue_for, department_id, "
				+ "created_by, support_ticket_status_id, file_url) values(?, ?, ?, ?, ?, ?, ?, ?)";
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
			ps.setInt(7, st.getSupportTicketStatusId());
			ps.setString(8, st.getFileUrl());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public int createSupportTicketByForEmployees(SupportTicket st) {
		query = "insert into support_tickets (issue_report_date, issue_type_id, issue_description, issue_for, employees, "
				+ "created_by, support_ticket_status_id, file_url) values(?, ?, ?, ?, ?, ?, ?, ?)";
		st.setIssueFor("employees");
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setDate(1, Date.valueOf(st.getIssueReportDate()));
			ps.setInt(2, st.getIssueTypeId());
			ps.setString(3, st.getIssueDescription());
			ps.setString(4, st.getIssueFor());
			ps.setString(5, st.getEmployees());
			ps.setInt(6, st.getCreatedBy());
			ps.setInt(7, st.getSupportTicketStatusId());
			ps.setString(8, st.getFileUrl());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public boolean isSupportTicketExist(SupportTicket st) {
		boolean result = false;
		if(st.getIssueFor().equals("department")) {
			query = "select count(*) as count_no from support_tickets where created_by = ? and "
					+ "support_ticket_status_id in (?, ?) and issue_type_id = ? and department_id = ?";
		}
		else {
			query = "select count(*) as count_no from support_tickets where created_by = ? and "
					+ "support_ticket_status_id in (?, ?) and issue_type_id = ?";
		}
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			if(st.getIssueFor().equals("department")) {
				ps.setInt(1, st.getCreatedBy());
				ps.setInt(2, stsd.getSupportTicketStatusId("pending"));
				ps.setInt(3, stsd.getSupportTicketStatusId("resolved"));
				ps.setInt(4, st.getIssueTypeId());
				ps.setInt(5, st.getDepartmentId());
			}
			else {
				ps.setInt(1, st.getCreatedBy());
				ps.setInt(2, stsd.getSupportTicketStatusId("pending"));
				ps.setInt(3, stsd.getSupportTicketStatusId("resolved"));
				ps.setInt(4, st.getIssueTypeId());
			}
			rs = ps.executeQuery();
			if(rs.next()) {
				count = rs.getInt("count_no");
			}
			result = (count >= 1);
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return result;
	}
	
	public SupportTicket getSupportTicketById(int supportTicketId) {
		st = new SupportTicket();
		query = "select * from support_tickets where support_ticket_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, supportTicketId);
			rs = ps.executeQuery();
			if(rs.next()) {
				st.setSupportTicketId(rs.getInt("support_ticket_id"));
				st.setIssueReportDate(rs.getDate("issue_report_date").toString());
				st.setIssueTypeId(rs.getInt("issue_type_id"));
				st.setIssueDescription(rs.getString("issue_description"));
				st.setFileUrl(rs.getString("file_url"));
				st.setIssueFor(rs.getString("issue_for"));
				st.setDepartmentId(rs.getInt("department_id"));
				st.setEmployees(rs.getString("employees"));
				st.setSupportTicketStatusId(rs.getInt("support_ticket_status_id"));
				st.setComment(rs.getString("comment"));
				st.setResolvedBy(rs.getInt("resolved_by"));
				st.setCreatedBy(rs.getInt("created_by"));
				st.setUpdatedBy(rs.getInt("updated_by"));
				st.setCreatedAt(rs.getTimestamp("created_at").toString());
				st.setUpdatedAt(rs.getTimestamp("updated_at").toString());
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return st;
	}
	
	
	public int[] getEmployeesId(String employees) {
		employees = employees.replace("'", "");
		String arr[] = employees.split(":");
		int result[] = new int[arr.length];
		int i = 0;
		for(String x : arr) {
			result[i] = Integer.parseInt(x);
			i++;
		}
		return result;
	}
	
	public int updateSupportTicket(SupportTicket st) {
		query = "update support_tickets set issue_report_date = ?, issue_type_id = ?, issue_description = ?, file_url = ?, "
				+ "issue_for = ?, department_id = ?, employees = ?, support_ticket_status_id = ?, comment = ?, updated_by= ?,  "
				+ "resolved_by = ? where support_ticket_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setDate(1, Date.valueOf(st.getIssueReportDate()));
			ps.setInt(2, st.getIssueTypeId());
			ps.setString(3, st.getIssueDescription());
			ps.setString(4, st.getFileUrl());
			ps.setString(5, st.getIssueFor());
			ps.setInt(6, st.getDepartmentId());
			ps.setString(7, st.getEmployees());
			ps.setInt(8, st.getSupportTicketStatusId());
			ps.setString(9, st.getComment());
			ps.setInt(10, st.getUpdatedBy());
			ps.setInt(11, st.getResolvedBy());
			ps.setInt(12, st.getSupportTicketId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public boolean isSupportTicketExistOnUpdate(SupportTicket st) {
		boolean result = false;
		if(st.getIssueFor().equals("department")) {
			query = "select count(*) as count_no from support_tickets where support_ticket_id != ? and created_by = ? and "
					+ "support_ticket_status_id in (?, ?) and issue_type_id = ? and department_id = ?";
		}
		else {
			query = "select count(*) as count_no from support_tickets where support_ticket_id != ? and created_by = ? and "
					+ "support_ticket_status_id in (?, ?) and issue_type_id = ?";
		}
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			if(st.getIssueFor().equals("department")) {
				ps.setInt(1, st.getSupportTicketId());
				ps.setInt(2, st.getCreatedBy());
				ps.setInt(3, stsd.getSupportTicketStatusId("pending"));
				ps.setInt(4, stsd.getSupportTicketStatusId("resolved"));
				ps.setInt(5, st.getIssueTypeId());
				ps.setInt(6, st.getDepartmentId());
			}
			else {
				ps.setInt(1, st.getSupportTicketId());
				ps.setInt(2, st.getCreatedBy());
				ps.setInt(3, stsd.getSupportTicketStatusId("pending"));
				ps.setInt(4, stsd.getSupportTicketStatusId("resolved"));
				ps.setInt(5, st.getIssueTypeId());
			}
			rs = ps.executeQuery();
			if(rs.next()) {
				count = rs.getInt("count_no");
			}
			result = (count >= 1);
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return result;
	}
	
	public int deleteSupportTicket(SupportTicket st) {
		query = "delete from support_tickets where support_ticket_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, st.getSupportTicketId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public ResultSet getEmployeeSupportTicketReport(int employeeId, String ticketFor, int supportTicketStatusId) {
		query = "select * from support_tickets where created_by = ? and lower(issue_for) = ? and "
				+ " support_ticket_status_id = ? order by issue_report_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			ps.setString(2, ticketFor.trim().toLowerCase());
			ps.setInt(3, supportTicketStatusId);
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getEmployeePendingSupportTicketReport(int employeeId, String ticketFor) {
		query = "select * from support_tickets where created_by = ? and lower(issue_for) = ? and "
				+ " support_ticket_status_id in (?, ?) order by issue_report_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			ps.setString(2, ticketFor.trim().toLowerCase());
			ps.setInt(3, stsd.getSupportTicketStatusId("pending"));
			ps.setInt(4, stsd.getSupportTicketStatusId("unresolved"));
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getEmployeeSupportTicketReportHistory(int employeeId, String ticketFor) {
		query = "select * from support_tickets where created_by = ? and lower(issue_for) = ? order by issue_report_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			ps.setString(2, ticketFor.trim().toLowerCase());
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getSupportTicketForEmployeesPendingReport(int employeeId) {
		query = "select * from support_tickets where lower(issue_for) = ? and employees like ? and support_ticket_status_id in (?, ?) "
				+ "order by issue_report_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, "employees");
			ps.setString(2, "%'"+employeeId+"'%");
			ps.setInt(3, stsd.getSupportTicketStatusId("pending"));
			ps.setInt(4, stsd.getSupportTicketStatusId("unresolved"));
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getSupportTicketHistoryForEmployeesReport(int employeeId) {
		query = "select * from support_tickets where lower(issue_for) = ? and employees like ? "
				+ "order by issue_report_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, "employees");
			ps.setString(2, "%'"+employeeId+"'%");
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	
	public ResultSet getSupportTicketForDepartmentReport(int departmentId, int supportTicketStatusId) {
		query = "select * from support_tickets where lower(issue_for) = ? and department_id = ?  and support_ticket_status_id = ? "
				+ "order by issue_report_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, "department");
			ps.setInt(2, departmentId);
			ps.setInt(3, supportTicketStatusId);
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getSupportTicketForDepartmentPendingReport(int departmentId) {
		query = "select * from support_tickets where lower(issue_for) = ? and department_id = ? "
				+ "and support_ticket_status_id in (?, ?) order by issue_report_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, "department");
			ps.setInt(2, departmentId);
			ps.setInt(3, stsd.getSupportTicketStatusId("pending"));
			ps.setInt(4, stsd.getSupportTicketStatusId("unresolved"));
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	
	
	public ResultSet getHRSupportTicketReport() {
		query= "select * from support_tickets order by issue_report_date desc";
		dbcon.getConnection();
		try {
			stat = dbcon.con.createStatement();
			rs = stat.executeQuery(query);
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public static void main(String args[]) {
		SupportTicketDao std = new SupportTicketDao();
		SupportTicket st = std.getSupportTicketById(3);
		int count = std.deleteSupportTicket(st);
		System.out.println(count);
	}
}
