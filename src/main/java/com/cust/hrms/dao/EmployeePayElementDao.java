package com.cust.hrms.dao;

import java.sql.*;
import com.cust.hrms.models.*;

public class EmployeePayElementDao {
	private DbConnection dbcon = new DbConnection();
	private int count;
	private String query;
	private Statement stat;
	private PreparedStatement ps;
	private ResultSet rs;
	private EmployeePayElement epe;
	
	public int createEmployeePayElement(EmployeePayElement epe) {
		query = "insert into employee_pay_elements (employee_id, level_id, pay_element_id, boundary_id, amount, created_by) "
				+ "values (?, ?, ?, ?, ?, ?)";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, epe.getEmployeeId());
			ps.setInt(2, epe.getLevelId());
			ps.setInt(3, epe.getPayElementId());
			ps.setInt(4, epe.getBoundaryId());
			ps.setDouble(5, epe.getAmount());
			ps.setInt(6, epe.getCreatedBy());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public boolean isEmployeePayElementExist(EmployeePayElement epe) {
		boolean result = false;
		query = "select count(*) as count_no from employee_pay_elements where employee_id = ? and level_id = ? "
				+ "and pay_element_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, epe.getEmployeeId());
			ps.setInt(2, epe.getLevelId());
			ps.setInt(3, epe.getPayElementId());
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
	
	public EmployeePayElement getEmployeePayElementById(int employeePayElementId) {
		epe = new EmployeePayElement();
		query = "select * from employee_pay_elements where employee_pay_element_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeePayElementId);
			rs = ps.executeQuery();
			if(rs.next()) {
				epe.setEmployeePayElementId(rs.getInt("employee_pay_element_id"));
				epe.setEmployeeId(rs.getInt("employee_id"));
				epe.setLevelId(rs.getInt("level_id"));
				epe.setPayElementId(rs.getInt("pay_element_id"));
				epe.setBoundaryId(rs.getInt("boundary_id"));
				epe.setAmount(rs.getDouble("amount"));
				epe.setCreatedBy(rs.getInt("created_by"));
				epe.setUpdatedBy(rs.getInt("updated_by"));
				epe.setCreatedAt(rs.getTimestamp("created_at").toString());
				epe.setUpdatedAt(rs.getTimestamp("updated_at").toString());
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return epe;
	}
	
	public EmployeePayElement getEmployeePayElement(int employeeId, int levelId, int payElementId) {
		epe = new EmployeePayElement();
		query = "select * from employee_pay_elements where employee_id = ? and level_id = ? and pay_element_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			ps.setInt(2, levelId);
			ps.setInt(3, payElementId);
			rs = ps.executeQuery();
			if(rs.next()) {
				epe.setEmployeePayElementId(rs.getInt("employee_pay_element_id"));
				epe.setEmployeeId(rs.getInt("employee_id"));
				epe.setLevelId(rs.getInt("level_id"));
				epe.setPayElementId(rs.getInt("pay_element_id"));
				epe.setBoundaryId(rs.getInt("boundary_id"));
				epe.setAmount(rs.getDouble("amount"));
				epe.setCreatedBy(rs.getInt("created_by"));
				epe.setUpdatedBy(rs.getInt("updated_by"));
				epe.setCreatedAt(rs.getTimestamp("created_at").toString());
				epe.setUpdatedAt(rs.getTimestamp("updated_at").toString());
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return epe;
	}
	
	public int updateEmployeePayElement(EmployeePayElement epe) {
		query = "update employee_pay_elements set amount = ?, updated_by = ? where employee_pay_element_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setDouble(1, epe.getAmount());
			ps.setInt(2, epe.getUpdatedBy());
			ps.setInt(3, epe.getEmployeePayElementId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public int deleteEmployeePayElement(EmployeePayElement epe) {
		query = "delete from employee_pay_elements where employee_pay_element_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, epe.getEmployeePayElementId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public ResultSet getAllEmployeePayElementReport() {
		query = "select * from employee_pay_elements";
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
		EmployeePayElementDao eped = new EmployeePayElementDao();
		EmployeePayElement epe = eped.getEmployeePayElement(2,2, 4);
		int count = eped.deleteEmployeePayElement(epe);
		System.out.println(count);
	}
}
