package com.cust.hrms.dao;

import java.sql.*;
import com.cust.hrms.models.*;

public class LeavePlanStatusDao {
	private DbConnection dbcon = new DbConnection();
	private LeavePlanStatus lps;
	private int count;
	private String query;
	private Statement stat;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public int createLeavePlanStatus(LeavePlanStatus lps) {
		query = "insert into leave_plan_statues (name, code) values(?, ?)";
		lps.setCode(lps.getName().replace(" ", "_").toLowerCase());
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, lps.getName());
			ps.setString(2, lps.getCode());
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
		query = "select count(*) as count_no from leave_plan_statues where lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, name.toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				count = rs.getInt("count_no");
			}
			result =(count >= 1);
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return result;
	}
	
	public LeavePlanStatus getLeavePlanStatusByCode(String code) {
		lps = new LeavePlanStatus();
		query = "select * from leave_plan_statues where lower(code) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, code.toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				lps.setLeavePlanStatusId(rs.getInt("leave_plan_status_id"));
				lps.setName(rs.getString("name"));
				lps.setCode(rs.getString("code"));
				lps.setCreatedAt(rs.getTimestamp("created_at").toString());
				lps.setUpdatedAt(rs.getTimestamp("updated_at").toString());
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return lps;
	}
	
	public LeavePlanStatus getLeavePlanStatusById(int leavePlanStatusId) {
		lps = new LeavePlanStatus();
		query = "select * from leave_plan_statues where leave_plan_status_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, leavePlanStatusId);
			rs = ps.executeQuery();
			if(rs.next()) {
				lps.setLeavePlanStatusId(rs.getInt("leave_plan_status_id"));
				lps.setName(rs.getString("name"));
				lps.setCode(rs.getString("code"));
				lps.setCreatedAt(rs.getTimestamp("created_at").toString());
				lps.setUpdatedAt(rs.getTimestamp("updated_at").toString());
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return lps;
	}
	
	public int updateLeavePlanStatus(LeavePlanStatus lps) {
		lps.setCode(lps.getName().replace(" ", "_").toLowerCase());
		query = "update leave_plan_statues set name = ?, code = ? where leave_plan_status_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, lps.getName());
			ps.setString(2, lps.getCode());
			ps.setInt(3, lps.getLeavePlanStatusId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public boolean isNameExistOnUpdate(LeavePlanStatus lps) {
		boolean result = false;
		query = "select count(*) as count_no from leave_plan_statues where leave_plan_status_id != ? and lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, lps.getLeavePlanStatusId());
			ps.setString(2, lps.getName().toLowerCase());
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
	
	public int deleteLeavePlanStatus(LeavePlanStatus lps) {
		query = "delete from leave_plan_statues where leave_plan_status_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, lps.getLeavePlanStatusId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public String getLeavePlanStatusName(int leavePlanStatusId) {
		String result = null;
		query = "select name from leave_plan_statues where leave_plan_status_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, leavePlanStatusId);
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
	
	public int getLeavePlanStatusId(String code) {
		int result = 0;
		query = "select leave_plan_status_id from leave_plan_statues where lower(code) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, code.toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getInt("leave_plan_status_id");
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return result;
	}
	
	public ResultSet getAllLeavePlanStatues() {
		query = "select * from leave_plan_statues order by name asc";
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
	
	// Get employee leave plan statues
	public ResultSet getEmployeeLeavePlanStatues() {
		query = "select * from leave_plan_statues where code in (?, ?) order by name asc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, "drafted");
			ps.setString(2, "send_for_approval");
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return rs;
	}
	
	//Get employee leave plan status on update
	public ResultSet getEmployeeLeavePlanStatuesOnUpdate(int leavePlanStatusId) {
		query = "select * from leave_plan_statues where code in (?, ?) and leave_plan_status_id != ? order by name asc";
		dbcon.getConnection();
		try {
			ps =  dbcon.con.prepareStatement(query);
			ps.setString(1, "drafted");
			ps.setString(2, "send_for_approval");
			ps.setInt(3, leavePlanStatusId);
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	//Get department head leave plan status on update
	public ResultSet getDepartmentHeadLeavePlanStatusOnUpdate(int leavePlanStatusId) {
		query = "select * from leave_plan_statues where code not in (?, ?) and leave_plan_status_id != ? order by name asc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, "drafted");
			ps.setString(2, "send_for_approval");
			ps.setInt(3, leavePlanStatusId);
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return rs;
	}
	
	public static void main(String args[]) {
		LeavePlanStatusDao lpsd = new LeavePlanStatusDao();
		ResultSet rs = lpsd.getAllLeavePlanStatues();
		//System.out.println();
	}
}
