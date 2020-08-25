package com.cust.hrms.dao;

import java.sql.*;
import com.cust.hrms.models.*;

public class DepartmentDao {
	DbConnection dbcon = new DbConnection();
	private Statement stat;
	private PreparedStatement ps;
	private String query;
	private int count;
	private ResultSet rs;
	private Department dp;
	
	public int createDepartment(Department d) {
		dp = d;
		query = "insert into departments (name, code, created_by) values(?, ?, ?)";
		dp.setCode(dp.getName().replace(" ", "_").toLowerCase());
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, dp.getName());
			ps.setString(2, dp.getCode());
			ps.setInt(3, dp.getCreatedBy());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public boolean isDepartmentNameExist(String name) {
		boolean result = false;
		query = "select count(*) as count_no from departments where lower(name) = ?";
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
	
	public Department getDepartmentBycode(String code) {
		dp = new Department();
		query = "select * from departments where lower(code) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, code.toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				dp.setDepartmentId(rs.getInt("department_id"));
				dp.setName(rs.getString("name"));
				dp.setCode(rs.getString("code"));
				dp.setCreatedAt(rs.getTimestamp("created_at").toString());
				dp.setUpdatedAt(rs.getTimestamp("updated_at").toString());
				dp.setCreatedBy(rs.getInt("created_by"));
				dp.setUpdatedBy(rs.getInt("updated_by"));
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return dp;
	}
	
	public Department getDepartmentById(int departmentId) {
		dp = new Department();
		query = "select * from departments where department_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, departmentId);
			rs = ps.executeQuery();
			if(rs.next()) {
				dp.setDepartmentId(rs.getInt("department_id"));
				dp.setName(rs.getString("name"));
				dp.setCode(rs.getString("code"));
				dp.setCreatedBy(rs.getInt("created_by"));
				dp.setUpdatedBy(rs.getInt("updated_by"));
				dp.setCreatedAt(rs.getTimestamp("created_at").toString());
				dp.setUpdatedAt(rs.getTimestamp("updated_at").toString());
			}
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return dp;
	}
	
	public int updateDepartment(Department d) {
		dp = d;
		query = "update departments set name = ?, code = ?, updated_by = ? where department_id = ?";
		dp.setCode(dp.getName().replace(" ", "_").toLowerCase());
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, dp.getName());
			ps.setString(2, dp.getCode());
			ps.setInt(3, dp.getUpdatedBy());
			ps.setInt(4, dp.getDepartmentId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public boolean isNameExistByAnotherDepartment(Department d) {
		boolean result = false;
		dp = d;
		query="select count(*) as count_no from departments where department_id != ? and lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, dp.getDepartmentId());
			ps.setString(2, dp.getName().toLowerCase());
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
	
	public int deleteDepartment(Department d) {
		dp = d;
		query="delete from departments where department_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, dp.getDepartmentId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public ResultSet getAllDepartment() {
		query = "select * from departments order by name asc";
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
	
	public ResultSet getAllDepartmentonUpdate(int departmentId) {
		query = "select * from departments where department_id != ? order by name asc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, departmentId);
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return rs;
	}
	
	public String getDepartmentName(int departmentId) {
		String result = null;
		query = "select name from departments where department_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, departmentId);
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
	
	public int getDepartmentId(String code) {
		int result = 0;
		query = "select department_id from departments where lower(code) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, code.toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getInt("department_id");
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return result;
	}
	
	public static void main(String args[]) {
		DepartmentDao dd = new DepartmentDao();
		int result = dd.getDepartmentId("inFormation_technology");
		System.out.println(result);
	}
}
