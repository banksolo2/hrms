package com.cust.hrms.dao;

import java.sql.*;
import com.cust.hrms.models.*;

public class EmployeeStatusDao {
	DbConnection dbcon = new DbConnection();
	private Statement stat;
	private PreparedStatement ps;
	private ResultSet rs;
	private int count;
	private String query;
	private EmployeeStatus es;
	private boolean result;
	
	public int createEmployeeStatus(EmployeeStatus e) {
		es = e;
		es.setCode(es.getName().trim().toLowerCase().replace(" ", "_"));
		query = "insert into employee_status (name, code, created_by) values(?, ?, ?)";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, es.getName().trim());
			ps.setString(2, es.getCode());
			ps.setInt(3, es.getCreatedBy());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public boolean isEmployeeStatusNameExist(String name) {
		query = "select count(*) as count_no from employee_status where lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, name.trim().toLowerCase());
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
	
	public EmployeeStatus getEmployeeStatus(String name) {
		es = new EmployeeStatus();
		query = "select * from employee_status where lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, name.toLowerCase().trim());
			rs = ps.executeQuery();
			if(rs.next()) {
				es.setEmployeeStatusId(rs.getInt("employee_status_id"));
				es.setName(rs.getString("name"));
				es.setCode(rs.getString("code"));
				es.setCreatedAt(rs.getTimestamp("created_at").toString());
				es.setUpdatedAt(rs.getTimestamp("updated_at").toString());
				es.setCreatedBy(rs.getInt("created_by"));
				es.setUpdatedBy(rs.getInt("updated_by"));
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return es;
	}
	
	public EmployeeStatus getEmployeeStatusById(int employeeStatusId) {
		es = new EmployeeStatus();
		query = "select * from employee_status where employee_status_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeStatusId);
			rs = ps.executeQuery();
			if(rs.next()) {
				es.setEmployeeStatusId(rs.getInt("employee_status_id"));
				es.setName(rs.getString("name"));
				es.setCode(rs.getString("code"));
				es.setCreatedAt(rs.getTimestamp("created_at").toString());
				es.setUpdatedAt(rs.getTimestamp("updated_at").toString());
				es.setCreatedBy(rs.getInt("created_by"));
				es.setUpdatedBy(rs.getInt("updated_by"));
			}
			rs.close();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return es;
	}
	
	public EmployeeStatus getEmployeeStatusByCode(String code) {
		es = new EmployeeStatus();
		query = "select * from employee_status where lower(code) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, code.trim().toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				es.setEmployeeStatusId(rs.getInt("employee_status_id"));
				es.setName(rs.getString("name"));
				es.setCode(rs.getString("code"));
				es.setCreatedAt(rs.getTimestamp("created_at").toString());
				es.setUpdatedAt(rs.getTimestamp("updated_at").toString());
				es.setCreatedBy(rs.getInt("created_by"));
				es.setUpdatedBy(rs.getInt("updated_by"));
			}
			rs.close();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.toString());
		}
		return es;
	}
	
	public int updateEmployeeStatus(EmployeeStatus e) {
		es = e;
		es.setCode(es.getName().trim().toLowerCase().replace(" ", "_"));
		query = "update employee_status set name = ?, code = ?, updated_by = ? where employee_status_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, es.getName().trim());
			ps.setString(2, es.getCode());
			ps.setInt(3, es.getUpdatedBy());
			ps.setInt(4, es.getEmployeeStatusId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public int deletedEmployeeStatus(EmployeeStatus e) {
		es = e;
		query = "delete from employee_status where employee_status_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, es.getEmployeeStatusId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public ResultSet getAllEmployeeStatus() {
		query = "select * from employee_status order by name asc";
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
	
	public ResultSet getAllEmployeeStatusOnUpdate(int employeeStatusId) {
		query = "select * from employee_status where employee_status_id != ? order by name asc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeStatusId);
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return rs;
	}
	
	public String getEmployeeStatusName(int employeeStatusId) {
		String value = null;
		query = "select name from employee_status where employee_status_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeStatusId);
			rs = ps.executeQuery();
			if(rs.next()) {
				value = rs.getString("name");
			}
			dbcon.con.close();
		}
		catch(SQLException ex){
			System.out.println(ex.fillInStackTrace());
		}
		
		return value;
	}
	
	public int getEmployeeStatusIdByCode(String code) {
		int result = 0;
		query = "select employee_status_id from employee_status where lower(code) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, code.trim().toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getInt("employee_status_id");
			}
			rs.close();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return result;
	}
	
	public boolean isNameExistOnUpdate(EmployeeStatus es) {
		boolean result = false;
		query = "select count(*) as count_no from employee_status where employee_status_id != ? and lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, es.getEmployeeStatusId());
			ps.setString(2, es.getName().trim().toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				count = rs.getInt("count_no");
			}
			result = (count >= 1);
			rs.close();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return result;
	}
	
	public static void main(String args[]) {
		EmployeeStatusDao esd = new EmployeeStatusDao();
		EmployeeStatus es = esd.getEmployeeStatusById(3);
		es.setName("Actives");
		boolean result = esd.isNameExistOnUpdate(es);
		System.out.println(result);
	}
	
}
