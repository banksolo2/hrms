package com.cust.hrms.dao;

import java.sql.*;
import com.cust.hrms.models.*;

public class PayrollDao {
	private DbConnection dbcon = new DbConnection();
	private EmployeePayElementDao eped = new EmployeePayElementDao();
	private Payroll p;
	private int count;
	private String query;
	private Statement stat;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public int createPayroll(Payroll p) {
		query = "insert into payrolls (employee_pay_element_id, employee_id, level_id, pay_element_id, amount, month_no, "
				+ "year, created_by) values(?, ?, ?, ?, ?, ?, ?, ?)";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, p.getEmployeePayElementId());
			ps.setInt(2, p.getEmployeeId());
			ps.setInt(3, p.getLevelId());
			ps.setInt(4, p.getPayElementid());
			ps.setDouble(5, p.getAmount());
			ps.setString(6, p.getMonthNo().trim());
			ps.setString(7, p.getYear().trim());
			ps.setInt(8, p.getCreatedBy());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public boolean isPayrollExist(Payroll p) {
		boolean result = false;
		query = "select count(*) as count_no from payrolls where employee_id = ? and level_id = ? and "
				+ "pay_element_id = ? and month_no = ? and year = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, p.getEmployeeId());
			ps.setInt(2, p.getLevelId());
			ps.setInt(3, p.getPayElementid());
			ps.setString(4, p.getMonthNo().trim());
			ps.setString(5, p.getYear().trim());
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
	
	public Payroll getPayrollById(int payrollId) {
		p = new Payroll();
		query = "select * from payrolls where payroll_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, payrollId);
			rs = ps.executeQuery();
			if(rs.next()) {
				p.setPayrollId(rs.getInt("payroll_id"));
				p.setEmployeePayElementId(rs.getInt("employee_pay_element_id"));
				p.setEmployeeId(rs.getInt("employee_id"));
				p.setLevelId(rs.getInt("level_id"));
				p.setPayElementid(rs.getInt("pay_element_id"));
				p.setAmount(rs.getDouble("amount"));
				p.setMonthNo(rs.getString("month_no"));
				p.setYear(rs.getString("year"));
				p.setCreatedBy(rs.getInt("created_by"));
				p.setUpdatedBy(rs.getInt("updated_by"));
				p.setCreatedAt(rs.getTimestamp("created_at").toString());
				p.setUpdatedAt(rs.getTimestamp("updated_at").toString());
			}
			rs.close();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return p;
	}
	
	public Payroll getPayroll(int employeeId, int payElementId, String monthNo, String year) {
		p = new Payroll();
		query = "select * from payrolls where employee_id = ? and pay_element_id = ? and month_no = ? and year = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			ps.setInt(2, payElementId);
			ps.setString(3, monthNo.trim());
			ps.setString(4, year.trim());
			rs = ps.executeQuery();
			if(rs.next()) {
				p.setPayrollId(rs.getInt("payroll_id"));
				p.setEmployeePayElementId(rs.getInt("employee_pay_element_id"));
				p.setEmployeeId(rs.getInt("employee_id"));
				p.setLevelId(rs.getInt("level_id"));
				p.setPayElementid(rs.getInt("pay_element_id"));
				p.setAmount(rs.getDouble("amount"));
				p.setMonthNo(rs.getString("month_no"));
				p.setYear(rs.getString("year"));
				p.setCreatedBy(rs.getInt("created_by"));
				p.setUpdatedBy(rs.getInt("updated_by"));
				p.setCreatedAt(rs.getTimestamp("created_at").toString());
				p.setUpdatedAt(rs.getTimestamp("updated_at").toString());
			}
			rs.close();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return p;
	}
	
	public int updatePayroll(Payroll p) {
		query = "update payrolls set amount = ?, month_no = ?, year = ?, updated_by = ? where payroll_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setDouble(1, p.getAmount());
			ps.setString(2, p.getMonthNo().trim());
			ps.setString(3, p.getYear().trim());
			ps.setInt(4, p.getUpdatedBy());
			ps.setInt(5, p.getPayrollId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public boolean isPayrollExistOnUpdate(Payroll p) {
		boolean result = false;
		query = "select count(*) as count_no from payrolls where payroll_id != ? and employee_id = ? and pay_element_id = ? "
				+ "and month_no = ? and year = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, p.getPayrollId());
			ps.setInt(2, p.getEmployeeId());
			ps.setInt(3, p.getPayElementid());
			ps.setString(4, p.getMonthNo().trim());
			ps.setString(5, p.getYear());
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
	
	public int deletePayroll(Payroll p) {
		query = "delete from payrolls where payroll_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, p.getPayrollId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public ResultSet getEmployeePayRoll(int employeeId, String monthNo, String year) {
		query = "select * from payrolls where employee_id = ? and month_no = ? and year = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			ps.setString(2, monthNo.trim());
			ps.setString(3, year.trim());
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getAllEmployees(int payrollRoleNameId) {
		EmployeeStatusDao esd = new EmployeeStatusDao();
		query = "select * from employees where employee_status_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, esd.getEmployeeStatusIdByCode("active"));
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getAllEmployeesByRole(int payrollRoleNameId) {
		PayrollRoleLevelDao prld = new PayrollRoleLevelDao();
		EmployeeStatusDao esd = new EmployeeStatusDao();
		int levelsId[] = prld.getPayrollRoleLevelIds(payrollRoleNameId);
		int length = levelsId.length;
		String quotes = "";
		for(int i = 0; i < length; i++) {
			quotes += "?";
			if(i != (length - 1)) {
				quotes += ",";
			}
		}
		query = "select * from employees where employee_status_id = ? and level_id in ("+quotes+")";
		count = 2;
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, esd.getEmployeeStatusIdByCode("active"));
			for(int x : levelsId) {
				ps.setInt(count, x);
				count++;
			}
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public double getEmployeePayrollTotal(int employeeId, String monthNo, String year) {
		double result = 0;
		query = "select sum(amount) as total from payrolls where employee_id = ? and month_no = ? and year = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			ps.setString(2, monthNo.trim());
			ps.setString(3, year.trim());
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
	
	public static void main(String args[]) {
		PayrollDao pd = new PayrollDao();
		double total = pd.getEmployeePayrollTotal(2, "12", "2020");
		System.out.println("Total: "+ total);
		
	}
}
