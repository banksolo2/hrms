package com.cust.hrms.dao;

import java.sql.*;
import com.cust.hrms.models.*;

public class PayrollRoleNameDao {
	private DbConnection dbcon = new DbConnection();
	private PayrollRoleName prn;
	private int count;
	private String query;
	private Statement stat;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public int createPayrollRoleName(PayrollRoleName prn) {
		prn.setCode(prn.getName().trim().toLowerCase().replace(" ", "_"));
		query = "insert into payroll_role_names (name, code, created_by) values(?, ?, ?)";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, prn.getName().trim());
			ps.setString(2, prn.getCode());
			ps.setInt(3, prn.getCreatedBy());
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
		query = "select count(*) as count_no from payroll_role_names where lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, name.trim().toLowerCase());
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
	
	public PayrollRoleName getPayrollRoleNameById(int payrollRoleNameId) {
		prn = new PayrollRoleName();
		query = "select * from payroll_role_names where payroll_role_name_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, payrollRoleNameId);
			rs = ps.executeQuery();
			if(rs.next()) {
				prn.setPayrollRoleNameId(rs.getInt("payroll_role_name_id"));
				prn.setName(rs.getString("name"));
				prn.setCode(rs.getString("code"));
				prn.setCreatedAt(rs.getTimestamp("created_at").toString());
				prn.setUpdatedAt(rs.getTimestamp("updated_at").toString());
				prn.setCreatedBy(rs.getInt("created_by"));
				prn.setUpdatedBy(rs.getInt("updated_by"));
			}
			rs.close();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return prn;
	}
	
	public PayrollRoleName getPayrollRoleNameByCode(String code) {
		prn = new PayrollRoleName();
		query = "select * from payroll_role_names where lower(code) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, code.trim().toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				prn.setPayrollRoleNameId(rs.getInt("payroll_role_name_id"));
				prn.setName(rs.getString("name"));
				prn.setCode(rs.getString("code"));
				prn.setCreatedAt(rs.getTimestamp("created_at").toString());
				prn.setUpdatedAt(rs.getTimestamp("updated_at").toString());
				prn.setCreatedBy(rs.getInt("created_by"));
				prn.setUpdatedBy(rs.getInt("updated_by"));
			}
			rs.close();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return prn;
	}
	
	public int updatePayrollRoleName(PayrollRoleName prn) {
		query = "update payroll_role_names set name = ?, code = ?, updated_by = ? where payroll_role_name_id = ?";
		prn.setCode(prn.getName().trim().toLowerCase().replace(" ", "_"));
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, prn.getName().trim());
			ps.setString(2, prn.getCode());
			ps.setInt(3, prn.getUpdatedBy());
			ps.setInt(4, prn.getPayrollRoleNameId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public boolean isNameExistOnUpdate(PayrollRoleName prn) {
		boolean result = false;
		query = "select count(*) as count_no from payroll_role_names where payroll_role_name_id != ? and lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, prn.getPayrollRoleNameId());
			ps.setString(2, prn.getName().trim().toLowerCase());
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
	
	public int deletePayrollRoleName(PayrollRoleName prn) {
		query = "delete from payroll_role_names where payroll_role_name_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, prn.getPayrollRoleNameId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public ResultSet getPayrollRoleNameOption(int payrollRoleNameId) {
		query = "select * from payroll_role_names where payroll_role_name_id != ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, payrollRoleNameId);
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getAllPayrollRoleNames() {
		query = "select * from payroll_role_names";
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
	
	public String getName(int payrollRoleNameId) {
		String result = null;
		query = "select name from payroll_role_names where payroll_role_name_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, payrollRoleNameId);
			rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getString("name");
			}
			rs.close();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return result;
	}
	
	public int getPayrollRoleNameId(String code) {
		int result = 0;
		query = "select payroll_role_name_id from payroll_role_names where lower(code) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, code.trim().toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getInt("payroll_role_name_id");
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
		PayrollRoleNameDao prnd = new PayrollRoleNameDao();
		int result = prnd.getPayrollRoleNameId("officer");
		System.out.println(result);
	}
}
