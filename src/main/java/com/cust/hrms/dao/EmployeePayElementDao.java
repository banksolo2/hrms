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
		query = "insert into employee_pay_elements (employee_id, level_id, pay_element_id, boundary_id, amount, created_by, "
				+ "start_date, end_date) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?)";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, epe.getEmployeeId());
			ps.setInt(2, epe.getLevelId());
			ps.setInt(3, epe.getPayElementId());
			ps.setInt(4, epe.getBoundaryId());
			ps.setDouble(5, epe.getAmount());
			ps.setInt(6, epe.getCreatedBy());
			ps.setDate(7, Date.valueOf(epe.getStartDate()));
			ps.setDate(8, Date.valueOf(epe.getEndDate()));
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
				epe.setStartDate(rs.getDate("start_date").toString());
				epe.setEndDate(rs.getDate("end_date").toString());
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
				epe.setStartDate(rs.getDate("start_date").toString());
				epe.setEndDate(rs.getDate("end_date").toString());
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return epe;
	}
	
	public int updateEmployeePayElement(EmployeePayElement epe) {
		query = "update employee_pay_elements set amount = ?, updated_by = ?, start_date = ?, end_date = ? where "
				+ "employee_pay_element_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setDouble(1, epe.getAmount());
			ps.setInt(2, epe.getUpdatedBy());
			ps.setDate(3, Date.valueOf(epe.getStartDate()));
			ps.setDate(4, Date.valueOf(epe.getEndDate()));
			ps.setInt(5, epe.getEmployeePayElementId());
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
	
	public ResultSet getEmployeesPayElementReport() {
		query = "select distinct employee_id, level_id from employee_pay_elements";
		dbcon.getConnection();
		try {
			stat = dbcon.con.createStatement();
			rs = stat.executeQuery(query);
		}
		catch(SQLException ex) {
			System.out.println(ex.toString());
		}
		return rs;
	}
	
	public int getEmployeePayElementCount(int employeeId) {
		query = "select count(*) as count_no from employee_pay_elements where employee_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			rs = ps.executeQuery();
			if(rs.next()) {
				count = rs.getInt("count_no");
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public int[] getEmployeePayElementsId(int employeeId) {
		int length = getEmployeePayElementCount(employeeId);
		if(length >= 1) {
			int result[] = new int[length];
			query = "select employee_pay_element_id from employee_pay_elements where employee_id = ?";
			dbcon.getConnection();
			try {
				ps = dbcon.con.prepareStatement(query);
				ps.setInt(1, employeeId);
				rs = ps.executeQuery();
				int i = 0;
				while(rs.next()) {
					result[i] = rs.getInt("employee_pay_element_id");
					i++;
				}
				dbcon.con.close();
			}
			catch(SQLException ex) {
				System.out.println(ex.fillInStackTrace());
			}
			return result;
		}
		else {
			int result[] = {0};
			return result;
		}
	}
	
	public ResultSet getEmployeePayElementByArray(int employeePayElementId[]) {
		String quote = "";
		for(int i = 0; i < employeePayElementId.length; i++) {
			quote += employeePayElementId[i];
			if(i != (employeePayElementId.length - 1)) {
				quote += ",";
			}
		}
		PayElementDao ped = new PayElementDao();
		int payElementsId[] = ped.getInvalidPayElementsId();
		String quote2 = "";
		for(int i = 0; i < payElementsId.length; i++) {
			quote2 += payElementsId[i];
			if(i != (payElementsId.length - 1)) {
				quote2 += ",";
			}
		}
		query = "select * from employee_pay_elements where employee_pay_element_id in ("+quote+") and "
				+ "pay_element_id not in ("+quote2+")";
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
	
	public ResultSet getEmployeePayElementByEmployee(int employeeId, String date) {
		Date dates = Date.valueOf(date);
		query = "select * from employee_pay_elements where employee_id = ? and ( ? between start_date and end_date)";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			ps.setDate(2, dates);
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public static void main(String args[]) {
		EmployeePayElementDao eped = new EmployeePayElementDao();
		try {
			ResultSet rs = eped.getEmployeePayElementByEmployee(2, "2020-12-07");
			while(rs.next()) {
				System.out.println(rs.getDouble("amount"));
			}
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
	}
}
