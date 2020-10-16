package com.cust.hrms.dao;

import java.sql.*;
import com.cust.hrms.models.*;

public class LeaveTypeDao {
	private DbConnection dbcon = new DbConnection();
	private LeaveType lt;
	private int count;
	private String query;
	private Statement stat;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public int createLeaveType(LeaveType lt) {
		query = "insert into leave_types (name, code, days, created_by) values(?, ?, ?, ?)";
		lt.setCode(lt.getName().replace(" ", "_").toLowerCase());
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, lt.getName());
			ps.setString(2,lt.getCode());
			ps.setInt(3, lt.getDays());
			ps.setInt(4, lt.getCreatedBy());
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
		query = "select count(*) as count_no from leave_types where lower(name) = ?";
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
	
	public LeaveType getLeaveTypeByCode(String code) {
		lt = new LeaveType();
		query = "select * from leave_types where lower(code) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, code.toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				lt.setLeaveTypeId(rs.getInt("leave_type_id"));
				lt.setName(rs.getString("name"));
				lt.setCode(rs.getString("code"));
				lt.setDays(rs.getInt("days"));
				lt.setCreatedBy(rs.getInt("created_by"));
				lt.setCreatedAt(rs.getTimestamp("created_at").toString());
				lt.setUpdatedBy(rs.getInt("updated_by"));
				lt.setUpdatedAt(rs.getTimestamp("updated_at").toString());
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return lt;
	}
	
	public LeaveType getLeaveTypeById(int leaveTypeId) {
		lt = new LeaveType();
		query = "select * from leave_types where leave_type_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, leaveTypeId);
			rs = ps.executeQuery();
			if(rs.next()) {
				lt.setLeaveTypeId(rs.getInt("leave_type_id"));
				lt.setName(rs.getString("name"));
				lt.setCode(rs.getString("code"));
				lt.setDays(rs.getInt("days"));
				lt.setCreatedBy(rs.getInt("created_by"));
				lt.setCreatedAt(rs.getTimestamp("created_at").toString());
				lt.setUpdatedBy(rs.getInt("updated_by"));
				lt.setUpdatedAt(rs.getTimestamp("updated_at").toString());
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return lt;
	}
	
	public int updateLeaveType(LeaveType lt) {
		query = "update leave_types set name = ?, code = ?, days = ?, updated_by = ? where leave_type_id = ?";
		lt.setCode(lt.getName().replace(" ", "_").toLowerCase());
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, lt.getName());
			ps.setString(2, lt.getCode());
			ps.setInt(3, lt.getDays());
			ps.setInt(4, lt.getUpdatedBy());
			ps.setInt(5, lt.getLeaveTypeId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public boolean isNameExistOnUpdate(LeaveType lt) {
		boolean result = false;
		query = "select count(*) as count_no from leave_types where leave_type_id != ? and lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, lt.getLeaveTypeId());
			ps.setString(2, lt.getName().toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) count = rs.getInt("count_no");
			result = (count >= 1);
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return result;
	}
	
	public int deleteLeaveType(LeaveType lt) {
		query = "delete from leave_types where leave_type_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, lt.getLeaveTypeId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public int getLeaveTypeId(String code) {
		int result = 0;
		query = "select leave_type_id from leave_types where lower(code) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, code.toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) result = rs.getInt("leave_type_id");
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return result;
	}
	
	public String getLeaveTypeName(int leaveTypeId) {
		String result = null;
		query = "select name from leave_types where leave_type_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, leaveTypeId);
			rs = ps.executeQuery();
			if(rs.next()) result = rs.getString("name");
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return result;
	}
	
	public ResultSet getAllLeaveType() {
		query = "select * from leave_types order by name asc";
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
	
	public ResultSet getAllLeaveTypeOnUpdate(int leaveTypeId) {
		query = "select * from leave_types where leave_type_id != ? order by name asc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, leaveTypeId);
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public int getLeaveTypeDays(int leaveTypeId) {
		int result = 0;
		query = "select days from leave_types where leave_type_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, leaveTypeId);
			rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getInt("days");
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return result;
	}
	
	public String getLeaveTypeCode(int leaveTypeId) {
		String result = null;
		query = "select code from leave_types where leave_type_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, leaveTypeId);
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
	
	public static void main(String args[]) {
		LeaveTypeDao ltd = new LeaveTypeDao();
		int days = ltd.getLeaveTypeDays(5);
		System.out.println(days);
	}
}
