package com.cust.hrms.dao;

import java.sql.*;
import com.cust.hrms.models.*;

public class EmployeePayrollRoleDao {
	private DbConnection dbcon = new DbConnection();
	private RoleDao rd = new RoleDao();
	private EmployeePayrollRole epr;
	private int count;
	private String query;
	private Statement stat;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public int createEmployeePayrollRole(EmployeePayrollRole epr) {
		query = "insert into employee_payroll_roles (employee_id, payroll_role_name_id, created_by) values(?, ?, ?)";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, epr.getEmployeeId());
			ps.setInt(2, epr.getPayrollRoleNameId());
			ps.setInt(3, epr.getCreatedBy());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public boolean isEmployeeExist(int employeeId) {
		boolean result = false;
		query = "select count(*) as count_no from employee_payroll_roles where employee_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
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
	
	public EmployeePayrollRole getEmployeePayrollRoleById(int employeePayrollRoleId) {
		epr = new EmployeePayrollRole();
		query = "select * from employee_payroll_roles where employee_payroll_role_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeePayrollRoleId);
			rs = ps.executeQuery();
			if(rs.next()) {
				epr.setEmployeePayrollRoleId(rs.getInt("employee_payroll_role_id"));
				epr.setEmployeeId(rs.getInt("employee_id"));
				epr.setPayrollRoleNameId(rs.getInt("payroll_role_name_id"));
				epr.setCreatedBy(rs.getInt("created_by"));
				epr.setUpdatedBy(rs.getInt("updated_by"));
				epr.setUpdatedAt(rs.getTimestamp("updated_at").toString());
				epr.setCreatedAt(rs.getTimestamp("created_at").toString());
			}
			rs.close();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return epr;
	}
	
	public EmployeePayrollRole getEmployeePayrollRoleByEmployeeId(int employeeId) {
		epr = new EmployeePayrollRole();
		query = "select * from employee_payroll_roles where employee_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			rs = ps.executeQuery();
			if(rs.next()) {
				epr.setEmployeePayrollRoleId(rs.getInt("employee_payroll_role_id"));
				epr.setEmployeeId(rs.getInt("employee_id"));
				epr.setPayrollRoleNameId(rs.getInt("payroll_role_name_id"));
				epr.setCreatedBy(rs.getInt("created_by"));
				epr.setUpdatedBy(rs.getInt("updated_by"));
				epr.setUpdatedAt(rs.getTimestamp("updated_at").toString());
				epr.setCreatedAt(rs.getTimestamp("created_at").toString());
			}
			rs.close();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return epr;
	}
	
	public int updateEmployeePayrollRole(EmployeePayrollRole epr) {
		query = "update employee_payroll_roles set employee_id = ?, payroll_role_name_id = ?, updated_by = ? "
				+ "where employee_payroll_role_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, epr.getEmployeeId());
			ps.setInt(2, epr.getPayrollRoleNameId());
			ps.setInt(3, epr.getUpdatedBy());
			ps.setInt(4, epr.getEmployeePayrollRoleId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public boolean isEmployeeExistOnUpdate(EmployeePayrollRole epr) {
		boolean result = false;
		query = "select count(*) as count_no from employee_payroll_roles where employee_payroll_role_id != ? "
				+ "and employee_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, epr.getEmployeePayrollRoleId());
			ps.setInt(2, epr.getEmployeeId());
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
	
	public int deleteEmployeePayrollRole(EmployeePayrollRole epr) {
		query = "delete from employee_payroll_roles where employee_payroll_role_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, epr.getEmployeePayrollRoleId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public ResultSet getEmployeePayrollRolesReport() {
		query = "select * from employee_payroll_roles";
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
	
	public int getEmployeeHrAdminRoleCount() {
		int hrAdminRoleId = rd.getRoleIdBycode("hr_admin");
		query = "select count(*) as count_no from employee_roles where role_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, hrAdminRoleId);
			rs = ps.executeQuery();
			if(rs.next()) {
				count = rs.getInt("count_no");
			}
			rs.close();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public int[] getEmployeeHrAdminRoleIds() {
		int hrAdminRoleId = rd.getRoleIdBycode("hr_admin");
		int length = getEmployeeHrAdminRoleCount();
		int result[] = new int[length];
		query = "select employee_id from employee_roles where role_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, hrAdminRoleId);
			rs = ps.executeQuery();
			int i = 0;
			while(rs.next()) {
				result[i] = rs.getInt("employee_id");
				i++;
			}
			rs.close();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return result;
	}
	
	public ResultSet getEmployeeHrOption(int employeeId) {
		int employeeIds[] = getEmployeeHrAdminRoleIds();
		String placeHolder = "";
		for(int i = 0; i < employeeIds.length; i++) {
			placeHolder += employeeIds[i];
			if(i != (employeeIds.length - 1)) {
				placeHolder += ",";
			}
		}
		query = "select * from employees where employee_id in ("+placeHolder+") and employee_id != ? order by first_name asc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public int getEmployeePayrollRoleNameId(int employeeId) {
		query = "select payroll_role_name_id from employee_payroll_roles where employee_id = ?";
		int result = 0;
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
			rs.close();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return result;
	}
	
	public static void main(String args[]) {
		EmployeePayrollRoleDao eprd = new EmployeePayrollRoleDao();
		int employeePayrollRoleNameId = eprd.getEmployeePayrollRoleNameId(2);
		System.out.println(employeePayrollRoleNameId);
	}
}
