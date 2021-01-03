package com.cust.hrms.dao;

import java.sql.*;
import com.cust.hrms.models.*;

public class EmployeePayElementDeductionDao {
	private DbConnection dbcon = new DbConnection();
	private EmployeePayElementDeduction eped;
	private int count;
	private String query;
	private Statement stat;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public int createEmployeePayElementDeduction(EmployeePayElementDeduction eped) {
		query = "insert into employee_pay_element_deductions (employee_id, pay_element_deduction_type_id, amount, start_date, "
				+ "end_date, created_by) values(?, ?, ?, ?, ?, ?)";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, eped.getEmployeeId());
			ps.setInt(2, eped.getPayElementDeductionTypeId());
			ps.setDouble(3, eped.getAmount());
			ps.setDate(4, Date.valueOf(eped.getStartDate()));
			ps.setDate(5, Date.valueOf(eped.getEndDate()));
			ps.setInt(6, eped.getCreatedBy());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public boolean isAmountValid(String amount) {
		try {
			Double.valueOf(amount);
			return true;
		}
		catch(Exception ex) {
			return false;
		}
	}
	
	public boolean isYearValid(String year) {
		int length = year.trim().length();
		if(length == 4) {
			try {
				Integer.parseInt(year);
				return true;
			}
			catch(Exception ex) {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	public EmployeePayElementDeduction getEmployeePayElementDeductionById(int employeePayElementDeductionId) {
		eped = new EmployeePayElementDeduction();
		query = "select * from employee_pay_element_deductions where employee_pay_element_deduction_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeePayElementDeductionId);
			rs = ps.executeQuery();
			while(rs.next()) {
				eped.setEmployeePayElementDeductionId(rs.getInt("employee_pay_element_deduction_id"));
				eped.setEmployeeId(rs.getInt("employee_id"));
				eped.setPayElementDeductionTypeId(rs.getInt("pay_element_deduction_type_id"));
				eped.setAmount(rs.getDouble("amount"));
				eped.setStartDate(rs.getDate("start_date").toString());
				eped.setEndDate(rs.getDate("end_date").toString());
				eped.setCreatedBy(rs.getInt("created_by"));
				eped.setUpdatedBy(rs.getInt("updated_by"));
				eped.setCreatedAt(rs.getTimestamp("created_at").toString());
				eped.setUpdatedAt(rs.getTimestamp("updated_at").toString());
			}
			rs.close();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return eped;
	}
	
	public int updateEmployeePayElementDeduction(EmployeePayElementDeduction eped) {
		query = "update employee_pay_element_deductions set employee_id = ?, pay_element_deduction_type_id = ?, "
				+ "amount = ?, start_date = ?, end_date = ?, updated_by = ? where "
				+ "employee_pay_element_deduction_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, eped.getEmployeeId());
			ps.setInt(2, eped.getPayElementDeductionTypeId());
			ps.setDouble(3, eped.getAmount());
			ps.setDate(4, Date.valueOf(eped.getStartDate()));
			ps.setDate(5, Date.valueOf(eped.getEndDate()));
			ps.setInt(6, eped.getUpdatedBy());
			ps.setInt(7, eped.getEmployeePayElementDeductionId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public int deleteEmployeePayElementDeduction(EmployeePayElementDeduction eped) {
		query = "delete from employee_pay_element_deductions where employee_pay_element_deduction_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, eped.getEmployeePayElementDeductionId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public ResultSet getAllEmployeePayElementDeductionsReport() {
		query = "select * from employee_pay_element_deductions order by start_date desc";
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
	
	public ResultSet getEmployeePayElementDeduction(int employeeId, String date) {
		query = "select * from employee_pay_element_deductions where employee_id = ? and (? between start_date and end_date)";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			ps.setDate(2, Date.valueOf(date));
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public double getEmployeePayElementDeductionTotal(int employeeId, String date) {
		double result = 0;
		Date dat = Date.valueOf(date);
		query = "select sum(amount) as total from employee_pay_element_deductions where employee_id = ? and (? between start_date and end_date)";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			ps.setDate(2, dat);
			rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getDouble("total");
			}
			rs.close();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return result;
	}
	
	public int getEmployeeDeductionCount(int employeeId, String date) {
		query = "select count(*) as count_no from employee_pay_element_deductions where employee_id = ? "
				+ "and (? between start_date and end_date)";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			ps.setDate(2, Date.valueOf(date));
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
	
	public static void main(String args[]) {
		EmployeePayElementDeductionDao epedd = new EmployeePayElementDeductionDao();
		int count = epedd.getEmployeeDeductionCount(9, "2021-02-10");
		System.out.println(count);
	}
}
