package com.cust.hrms.dao;

import java.sql.*;
import com.cust.hrms.models.*;

public class EmployeeRoleDao {

	DbConnection dbcon = new DbConnection();
	private String query; 
	private int count;
	private Statement stat;
	private PreparedStatement ps;
	private ResultSet rs;
	private EmployeeRole er;
	
	
	public int createEmployeeRole(EmployeeRole e) {
		er = e;
		query = "insert into employee_roles (employee_id, role_id, created_by) values (?, ?, ?)";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, er.getEmployeeId());
			ps.setInt(2, er.getRoleId());
			ps.setInt(3, er.getCreatedBy());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(); 
		}
		
		return count;
	}
	
	public boolean isEmployeeRoleExist(EmployeeRole e) {
		boolean result = false;
		er = e;
		query = "select count(*) as count_no from employee_roles where employee_id = ? and role_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, er.getEmployeeId());
			ps.setInt(2, er.getRoleId());
			rs = ps.executeQuery();
			if(rs.next()) {
				count = rs.getInt("count_no");
			}
			result = (count >= 1);
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return result;
	}
	
	public EmployeeRole getEmployeeRole(int employeeId, int roleId) {
		er = new EmployeeRole();
		query = "select * from employee_roles where employee_id = ? and role_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			ps.setInt(2, roleId);
			rs = ps.executeQuery();
			if(rs.next()) {
				er.setEmployeeRoleId(rs.getInt("employee_role_id"));
				er.setEmployeeId(rs.getInt("employee_id"));
				er.setRoleId(rs.getInt("role_id"));
				er.setCreatedAt(rs.getTimestamp("created_at").toString());
				er.setUpdatedAt(rs.getTimestamp("updated_at").toString());
				er.setCreatedBy(rs.getInt("created_by"));
				er.setUpdatedBy(rs.getInt("updated_by"));
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return er;
	}
	
	public int deleteEmployeeRole(EmployeeRole e) {
		er = e;
		query = "delete from employee_roles where employee_role_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, er.getEmployeeRoleId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public ResultSet getAllEmployeeRoles() {
		query = "select * from employee_roles";
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
	
	public int updateEmployeeRole(EmployeeRole e) {
		er = e;
		query = "update employee_roles set employee_id = ?, role_id = ?, updated_by = ? where employee_role_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, er.getEmployeeId());
			ps.setInt(2, er.getRoleId());
			ps.setInt(3, er.getUpdatedBy());
			ps.setInt(4, er.getEmployeeRoleId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public boolean isEmployeeRoleExistOnUpdate(EmployeeRole r) {
		er = r;
		boolean result = false;
		query ="select count(*) as count_no from employee_roles where employee_role_id != ? and employee_id = ? and role_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, er.getEmployeeRoleId());
			ps.setInt(2, er.getEmployeeId());
			ps.setInt(3, er.getRoleId());
			rs = ps.executeQuery();
			while(rs.next()) {
				count = rs.getInt("count_no");
			}
			result = (count >= 1);
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return result;
	}
	
	public EmployeeRole getEmployeeRoleById(int employeeRoleId) {
		er = new EmployeeRole();
		query = "select * from employee_roles where employee_role_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeRoleId);
			rs = ps.executeQuery();
			if(rs.next()) {
				er.setEmployeeRoleId(rs.getInt("employee_role_id"));
				er.setEmployeeId(rs.getInt("employee_id"));
				er.setRoleId(rs.getInt("role_id"));
				er.setCreatedAt(rs.getTimestamp("created_at").toString());
				er.setUpdatedAt(rs.getTimestamp("updated_at").toString());
				er.setCreatedBy(rs.getInt("created_by"));
				er.setUpdatedBy(rs.getInt("updated_by"));
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return er;
	}
	
	public static void main(String args[]) {
		EmployeeRoleDao erd = new EmployeeRoleDao();
		RoleDao rd = new RoleDao();
		EmployeeRole er = new EmployeeRole();
		er.setEmployeeId(2);
		er.setRoleId(rd.getRoleId("super admin"));
		int count = erd.createEmployeeRole(er);
		System.out.println(count);
	}
}
