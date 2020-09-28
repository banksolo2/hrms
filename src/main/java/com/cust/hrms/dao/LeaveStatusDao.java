package com.cust.hrms.dao;

import java.sql.*;
import com.cust.hrms.models.*;

public class LeaveStatusDao {
	private DbConnection dbcon = new DbConnection();
	private LeaveTypeDao ltd = new LeaveTypeDao();
	private LeaveStatus ls;
	private String query;
	private int count;
	private Statement stat;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public int createLeaveStatus(LeaveStatus ls) {
		query = "insert into leave_statues (name, code) values(?, ?)";
		dbcon.getConnection();
		String code = ls.getName().replace(" ", "_").toLowerCase();
		ls.setCode(code);
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, ls.getName());
			ps.setString(2, ls.getCode());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public boolean isNameExist(String name) {
		boolean result = false;
		query = "select count(*) as count_no from leave_statues where lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, name.toLowerCase());
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
	
	public LeaveStatus getLeaveStatusById(int leaveStatusId) {
		ls = new LeaveStatus();
		query = "select * from leave_statues where leave_status_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, leaveStatusId);
			rs = ps.executeQuery();
			if(rs.next()) {
				ls.setLeaveStatusId(rs.getInt("leave_status_id"));
				ls.setName(rs.getString("name"));
				ls.setCode(rs.getString("code"));
				ls.setCreatedAt(rs.getTimestamp("created_at").toString());
				ls.setUpdatedAt(rs.getTimestamp("updated_at").toString());
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return ls;
	}
	
	public LeaveStatus getLeaveStatusByCode(String code) {
		ls = new LeaveStatus();
		query = "select * from leave_statues where lower(code) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, code.toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				ls.setLeaveStatusId(rs.getInt("leave_status_id"));
				ls.setName(rs.getString("name"));
				ls.setCode(rs.getString("code"));
				ls.setCreatedAt(rs.getTimestamp("created_at").toString());
				ls.setUpdatedAt(rs.getTimestamp("updated_at").toString());
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return ls;
	}
	
	public int updateLeaveStatus(LeaveStatus ls) {
		query = "update leave_statues set name = ?, code = ? where leave_status_id = ?";
		String code = ls.getName().replace(" ", "_").toLowerCase();
		ls.setCode(code);
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, ls.getName());
			ps.setString(2, ls.getCode());
			ps.setInt(3, ls.getLeaveStatusId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public boolean isNameExistOnUpdate(LeaveStatus ls) {
		boolean result = false;
		query = "select count(*) as count_no from leave_statues where leave_status_id != ? and name = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, ls.getLeaveStatusId());
			ps.setString(2, ls.getName());
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
	
	public int deleteLeaveStatus(LeaveStatus ls) {
		query = "delete from leave_statues where leave_status_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, ls.getLeaveStatusId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public ResultSet getAllLeaveStatues() {
		query = "select * from leave_statues";
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
	
	public int getLeaveStatusId(String code) {
		int result = 0;
		query = "select leave_status_id from leave_statues where lower(code) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, code.toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getInt("leave_status_id");
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return result;
	}
	
	public String getLeaveStatusCode(int leaveStatusId) {
		String result = null;
		query = "select code from leave_statues where leave_status_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, leaveStatusId);
			rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getString("code");
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return result; 
	}
	
	public String getName(int leaveStatusId) {
		String result = null;
		query = "select name from leave_statues where leave_status_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, leaveStatusId);
			rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getString("name");
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return result;
	}
	
	//Get employee save as option
	public ResultSet getEmployeeSaveAsOptions() {
		query = "select * from leave_statues where code in (?, ?) order by name asc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, "drafted");
			ps.setString(2, "sent_to_supervisor_for_approval");
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getEmployeeSaveAsOptionsOnUpdateById(int leaveStatusId) {
		query = "select * from leave_statues where code in (?, ?) and leave_status_id != ? order by name asc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, "drafted");
			ps.setString(2, "sent_to_supervisor_for_approval");
			ps.setInt(3, leaveStatusId);
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getEmployeeSaveAsOptionsOnUpdateByCode(String code) {
		query = "select * from leave_statues where lower(code) in (?, ?) and code != ? order by name asc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1,  "drafted");
			ps.setString(2, "sent_to_supervisor_for_approval");
			ps.setString(3, code.toLowerCase());
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	//Get leave status name by code
	public String getLeaveStatusNameByCode(String code) {
		String result = null;
		query = "select name from leave_statues where lower(code) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, code.toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) result = rs.getString("name");
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return result;
	}
	
	//Get supervisor save as options
	public ResultSet getSupervisorSaveAsOptionOne(int leaveStatusId) {
		query = "select * from leave_statues where code in (?, ?, ?) and leave_status_id != ? order by name asc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, "approved");
			ps.setString(2, "sent_back_for_correction");
			ps.setString(3, "declined");
			ps.setInt(4, leaveStatusId);
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getSupervisorSaveAsOptionTwo() {
		query = "select * from leave_statues where code in (?, ?, ?) order by name asc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, "sent_to_hr_for_approval");
			ps.setString(2, "declined");
			ps.setString(3, "sent_back_for_correction");
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return rs;
	}
	
	public ResultSet getSupervisorSaveAsOption(int leaveTypeId, int leaveStatusId, String withPay) {
		if(leaveTypeId == ltd.getLeaveTypeId("annual")  && withPay.toLowerCase().equals("yes")) {
			rs = getSupervisorSaveAsOptionOne(leaveStatusId);
		}
		else {
			rs = getSupervisorSaveAsOptionTwo();
		}
		return rs;
	}
	
	public ResultSet getHrSaveAsOption() {
		query = "select * from leave_statues where code in (?, ?, ?)  order by name asc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, "approved");
			ps.setString(2, "declined");
			ps.setString(3, "sent_back_for_correction");
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getHrLeaveOptionForPendingRecallBack() {
		query = "select * from leave_statues where code in (?, ?) order by name asc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, "approved_recalled_back");
			ps.setString(2, "decline_leave_recall_back");
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	
	public static void main(String args[]) {
		LeaveStatusDao lsd = new LeaveStatusDao();
		ResultSet rs = lsd.getEmployeeSaveAsOptionsOnUpdateByCode("drafted");
		try {
			while(rs.next()) {
				System.out.println(rs.getString("name"));
			}
		}
		catch(Exception ex) {
			System.out.println(ex.fillInStackTrace());
		}
	}
}