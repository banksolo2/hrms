package com.cust.hrms.dao;

import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.cust.hrms.models.*;
import java.util.*;

import javax.swing.JOptionPane;

public class EmployeeDao {
	DbConnection dbcon = new DbConnection();
	private PreparedStatement ps;
	private Statement stmt;
	private String query;
	private ResultSet rs;
	private int count;
	private Employee emp;
	//private RandomAccessFile file;
	
	public int createEmployee(Employee e) {
		emp = e;
		query = "insert into employees (first_name, middle_name, last_name, date_of_employment, email, employee_status_id,"
				+" state_id, mobile_number, department_id, level_id, branch_id, company_id, title, date_of_birth, "
				+ "name_initials, gender_id, martial_status_id, current_address, personal_email, password, staff_id, "
				+ "leave_supervisor_id, personal_production_target, created_by) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		dbcon.getConnection();
		try
		{
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, emp.getFirstName());
			ps.setString(2, emp.getMiddleName());
			ps.setString(3, emp.getLastName());
			//Date dateEmp = (Date) new SimpleDateFormat("dd/MM/yyyy").parse(emp.getDateOfBirth());
			ps.setDate(4, Date.valueOf(emp.getDateOfEmployment()));
			ps.setString(5, emp.getEmail());
			ps.setInt(6, emp.getEmployeeStatusId());
			ps.setInt(7, emp.getStateId());
			ps.setString(8, emp.getMobileNumber());
			ps.setInt(9, emp.getDepartmentId());
			ps.setInt(10, emp.getLevelId());
			ps.setInt(11, emp.getBranchId());
			ps.setInt(12, emp.getCompanyId());
			ps.setString(13, emp.getTitle());
			//Date dob = (Date) new SimpleDateFormat("dd/mm/yyyy").parse(emp.getDateOfBirth());
			ps.setDate(14, Date.valueOf(emp.getDateOfBirth()));
			ps.setString(15, emp.getNameInitials());
			ps.setInt(16, emp.getGenderId());
			ps.setInt(17, emp.getMartialStatusId());
			ps.setString(18, emp.getCurrentAddress());
			ps.setString(19, emp.getPersonalEmail());
			ps.setString(20, hashPassword(emp.getPassword()));
			ps.setString(21, emp.getStaffId());
			ps.setInt(22, emp.getLeaveSupervisorId());
			ps.setDouble(23, emp.getPersonalProductionTarget());
			ps.setInt(24, emp.getCreatedBy());
			count = ps.executeUpdate();
			dbcon.con.close();
			
		}
		/*catch(ParseException pex) {
			System.out.println(pex.fillInStackTrace());
		}*/
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public Employee getEmployee(String email) {
		emp = new Employee();
		query = "select * from employees where email = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, email);
			rs = ps.executeQuery();
			if(rs.next()) {
				emp.setEmployeeId(rs.getInt("employee_id"));
				emp.setFirstName(rs.getString("first_name"));
				emp.setMiddleName(rs.getString("middle_name"));
				emp.setLastName(rs.getString("last_name"));
				emp.setDateOfEmployment(rs.getDate("date_of_employment").toString());
				emp.setEmail(rs.getString("email"));
				emp.setEmployeeStatusId(rs.getInt("employee_status_id"));
				emp.setStateId(rs.getInt("state_id"));
				emp.setMobileNumber(rs.getString("mobile_number"));
				emp.setDepartmentId(rs.getInt("department_id"));
				emp.setLevelId(rs.getInt("level_id"));
				emp.setBranchId(rs.getInt("branch_id"));
				emp.setCompanyId(rs.getInt("company_id"));
				emp.setTitle(rs.getString("title"));
				emp.setDateOfBirth(rs.getDate("date_of_birth").toString());
				emp.setNameInitials(rs.getString("name_initials"));
				emp.setGenderId(rs.getInt("gender_id"));
				emp.setMartialStatusId(rs.getInt("martial_status_id"));
				emp.setCurrentAddress(rs.getString("current_address"));
				emp.setPersonalEmail(rs.getString("personal_email"));
				emp.setPassword(rs.getString("password"));
				emp.setStaffId(rs.getString("staff_id"));
				emp.setLeaveSupervisorId(rs.getInt("leave_supervisor_id"));
				emp.setPersonalProductionTarget(rs.getDouble("personal_production_target"));
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return emp;
	}
	
	public boolean isEmailExist(String email) {
		boolean result = false;
		query = "select count(*) as count_no from employees where email = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, email);
			rs = ps.executeQuery();
			if(rs.next()) {
				count = rs.getInt("count_no");
			}
			result = (count >= 1) ? true : false;
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return result;
	}
	
	public boolean isEmailExistByAnotherEmployee(Employee e) {
		boolean result = false;
		emp = e;
		query = "select count(*) as count_no from employees where employee_id != ? and email = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, emp.getEmployeeId());
			ps.setString(2, emp.getEmail());
			rs = ps.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt("count_no");
			}
			result = (count >= 1) ? true : false;
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return result;
	}
	
	public int updateEmployee(Employee e) {
		emp = e;
		query = "update employees set first_name = ?, middle_name = ?, last_name = ?, date_of_employment = ?, "
				+ "email = ?, employee_status_id = ?, state_id = ?, mobile_number = ?, department_id = ?, "
				+ "level_id = ?, branch_id = ?, company_id = ?, title = ?, date_of_birth = ?, name_initials = ?, "
				+ "gender_id = ?, martial_status_id = ?, current_address = ?, personal_email = ?, staff_id = ?, "
				+ "leave_supervisor_id = ?, personal_production_target = ?, updated_by = ? "
				+ "where employee_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, emp.getFirstName());
			ps.setString(2, emp.getMiddleName());
			ps.setString(3, emp.getLastName());
			ps.setDate(4, Date.valueOf(emp.getDateOfEmployment()));
			ps.setString(5, emp.getEmail());
			ps.setInt(6, emp.getEmployeeStatusId());
			ps.setInt(7, emp.getStateId());
			ps.setString(8, emp.getMobileNumber());
			ps.setInt(9, emp.getDepartmentId());
			ps.setInt(10, emp.getLevelId());
			ps.setInt(11, emp.getBranchId());
			ps.setInt(12, emp.getCompanyId());
			ps.setString(13, emp.getTitle());
			ps.setDate(14, Date.valueOf(emp.getDateOfBirth()));
			ps.setString(15, emp.getNameInitials());
			ps.setInt(16, emp.getGenderId());
			ps.setInt(17, emp.getMartialStatusId());
			ps.setString(18, emp.getCurrentAddress());
			ps.setString(19, emp.getPersonalEmail());
			ps.setString(20, emp.getStaffId());
			ps.setInt(21, emp.getLeaveSupervisorId());
			ps.setDouble(22, emp.getPersonalProductionTarget());
			ps.setInt(23, emp.getUpdatedBy());
			ps.setInt(24, emp.getEmployeeId());
			
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public int deleteEmployee(Employee e) {
		emp = e;
		query = "delete from employees where employee_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, emp.getEmployeeId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public ResultSet getAllEmployee() {
		query = "select * from employees order by first_name asc";
		dbcon.getConnection();
		
		try {
			stmt = dbcon.con.createStatement();
			rs = stmt.executeQuery(query);
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return rs;
	}
	
	public ResultSet getAllEmployeeOnUpdate(int employeeId) {
		query = "select * from employees where employee_id != ? order by first_name asc";
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
	
	public String hashPassword(String password){
        String hashPassword = null;

        try{
            //Create MessageDigest instance of MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte bytes[] = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < bytes.length; i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            hashPassword = sb.toString();
        }
        catch(NoSuchAlgorithmException ex){
            System.out.println(ex.fillInStackTrace());
        }
        return hashPassword;
    }
	
	public boolean login(String email, String password) {
		String hashPassword = hashPassword(password);
		emp = getEmployee(email);
		return (hashPassword.equals(emp.getPassword()));
	}
	
	public int updatePassword(Employee e) {
		emp = e;
		query = "update employees set password = ? where employee_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, hashPassword(emp.getPassword()));
			ps.setInt(2, emp.getEmployeeId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public int getEmployeeId(String email) {
		int result = 0;
		query = "select employee_id from employees where email = ? ";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, email);
			rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getInt("employee_id");
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return result;
	}
	
	public boolean isStaffIdExist(String staffId) {
		boolean result = false;
		query = "select count(*) as count_no from employees where staff_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, staffId);
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
	
	public String getEmployeeName(int employeeId) {
		String value = null;
		query = "select * from employees where employee_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			rs = ps.executeQuery();
			while(rs.next()) {
				value = rs.getString("first_name")+" "+rs.getString("middle_name")+" "+rs.getString("last_name")+" ("+rs.getString("staff_id")+")";
			}
			dbcon.con.close();
		}
		
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return value;
	}
	
	public boolean isStaffIdExistOnUpdate(int employeeId, String staffId) {
		boolean result = false;
		query = "select count(*) as count_no from employees where employee_id != ? and lower(staff_id) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			ps.setString(2, staffId.toLowerCase());
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
	
	public Employee getEmployeeById(int employeeId) {
		emp = new Employee();
		query = "select * from employees where employee_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			rs = ps.executeQuery();
			if(rs.next()) {
				emp.setEmployeeId(rs.getInt("employee_id"));
				emp.setFirstName(rs.getString("first_name"));
				emp.setMiddleName(rs.getString("middle_name"));
				emp.setLastName(rs.getString("last_name"));
				emp.setDateOfEmployment(rs.getDate("date_of_employment").toString());
				emp.setEmail(rs.getString("email"));
				emp.setEmployeeStatusId(rs.getInt("employee_status_id"));
				emp.setStateId(rs.getInt("state_id"));
				emp.setMobileNumber(rs.getString("mobile_number"));
				emp.setDepartmentId(rs.getInt("department_id"));
				emp.setLevelId(rs.getInt("level_id"));
				emp.setBranchId(rs.getInt("branch_id"));
				emp.setCompanyId(rs.getInt("company_id"));
				emp.setTitle(rs.getString("title"));
				emp.setDateOfBirth(rs.getDate("date_of_birth").toString());
				emp.setNameInitials(rs.getString("name_initials"));
				emp.setGenderId(rs.getInt("gender_id"));
				emp.setMartialStatusId(rs.getInt("martial_status_id"));
				emp.setCurrentAddress(rs.getString("current_address"));
				emp.setPersonalEmail(rs.getString("personal_email"));
				emp.setPassword(rs.getString("password"));
				emp.setStaffId(rs.getString("staff_id"));
				emp.setLeaveSupervisorId(rs.getInt("leave_supervisor_id"));
				emp.setPersonalProductionTarget(rs.getDouble("personal_production_target"));
				emp.setCreatedBy(rs.getInt("created_by"));
				emp.setUpdatedBy(rs.getInt("updated_by"));
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return emp;
	}
	
	public boolean isPersonalProductionTargetValid(String value) {
		try {
			Double.parseDouble(value);
			return true;
		}
		catch(NumberFormatException ex) {
			return false;
		}
	}
	
	
	
	public static void main(String args[]) {
		EmployeeDao ed = new EmployeeDao();
		boolean result = ed.isPersonalProductionTargetValid("20000.0");
		System.out.println(result);
	}
}
