package com.cust.hrms.dao;

import java.sql.*;
import java.sql.Date;
import java.text.*;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.cust.hrms.models.*;
import java.util.*;

import javax.swing.JOptionPane;

public class EmployeeDao {
	DbConnection dbcon = new DbConnection();
	private EmployeeStatusDao esd = new EmployeeStatusDao();
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
				+ "leave_supervisor_id, personal_production_target, created_by, bank_id, account_no) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
			ps.setInt(25, emp.getBankId());
			ps.setString(26, emp.getAccountNo());
			count = ps.executeUpdate();
			if(count >= 1) {
				Employee ep = getEmployee(emp.getEmail());
				createEmployeePayElement(ep);
			}
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
				emp.setBankId(rs.getInt("bank_id"));
				emp.setAccountNo(rs.getString("account_no"));
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
		query = "select count(*) as count_no from employees where email = ? and employee_status_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, email);
			ps.setInt(2,  esd.getEmployeeStatusIdByCode("active"));
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
		query = "select count(*) as count_no from employees where employee_id != ? and email = ? and employee_status_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, emp.getEmployeeId());
			ps.setString(2, emp.getEmail());
			ps.setInt(3,  esd.getEmployeeStatusIdByCode("active"));
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
		int levelId = getEmployeeLevelId(e.getEmployeeId());
		query = "update employees set first_name = ?, middle_name = ?, last_name = ?, date_of_employment = ?, "
				+ "email = ?, employee_status_id = ?, state_id = ?, mobile_number = ?, department_id = ?, "
				+ "level_id = ?, branch_id = ?, company_id = ?, title = ?, date_of_birth = ?, name_initials = ?, "
				+ "gender_id = ?, martial_status_id = ?, current_address = ?, personal_email = ?, staff_id = ?, "
				+ "leave_supervisor_id = ?, personal_production_target = ?, updated_by = ?, "
				+ "bank_id = ?, account_no = ? "
				+ "where employee_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, e.getFirstName());
			ps.setString(2, e.getMiddleName());
			ps.setString(3, e.getLastName());
			ps.setDate(4, Date.valueOf(e.getDateOfEmployment()));
			ps.setString(5, e.getEmail());
			ps.setInt(6, e.getEmployeeStatusId());
			ps.setInt(7, e.getStateId());
			ps.setString(8, e.getMobileNumber());
			ps.setInt(9, e.getDepartmentId());
			ps.setInt(10, e.getLevelId());
			ps.setInt(11, e.getBranchId());
			ps.setInt(12, e.getCompanyId());
			ps.setString(13, e.getTitle());
			ps.setDate(14, Date.valueOf(e.getDateOfBirth()));
			ps.setString(15, e.getNameInitials());
			ps.setInt(16, e.getGenderId());
			ps.setInt(17, e.getMartialStatusId());
			ps.setString(18, e.getCurrentAddress());
			ps.setString(19, e.getPersonalEmail());
			ps.setString(20, e.getStaffId());
			ps.setInt(21, e.getLeaveSupervisorId());
			ps.setDouble(22, e.getPersonalProductionTarget());
			ps.setInt(23, e.getUpdatedBy());
			ps.setInt(24, e.getBankId());
			ps.setString(25, e.getAccountNo());
			ps.setInt(26, e.getEmployeeId());
			count = ps.executeUpdate();
			dbcon.con.close();
			if(count >= 1) {
				if(levelId != e.getLevelId()) {
					//Delete employee old pay element
					deleteEmplyeePayElementByEmployeeId(e.getEmployeeId());
					//Create new Employee pay Element
					createEmployeePayElement(e);
				}
			}
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
			if(count >= 1) {
				deleteEmplyeePayElementByEmployeeId(emp.getEmployeeId());
				deleteEmployeeRoles(emp.getEmployeeId());
				deleteEmployeeLeavePlans(emp.getEmployeeId());
				deleteEmployeeLeaves(emp.getEmployeeId());
			}
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
		query = "select * from employees where employee_status_id = ? and employee_id != ? order by first_name asc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, esd.getEmployeeStatusIdByCode("active"));
			ps.setInt(2, employeeId);
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
				emp.setBankId(rs.getInt("bank_id"));
				emp.setAccountNo(rs.getString("account_no"));
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
	
	public int getEmployeeLevelId(int employeeId) {
		int result = 0;
		query = "select level_id from employees where employee_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getInt("level_id");
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return result;
	}
	
	public int getEmployeeDepartmentId(int employeeId) {
		int result = 0;
		query = "select department_id from employees where employee_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
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
	
	public int getEmployeeLeaveSupervisorId(int employeeId) {
		int result = 0;
		query = "select leave_supervisor_id from employees where employee_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getInt("leave_supervisor_id");
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return result;
	}
	
	public String getEmployeeEmail(int employeeId) {
		String result = null;
		query = "select email from employees where employee_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getString("email");
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return result;
	}
	
	public String[] getEmployeesEmail(int employeesId[]) {
		String result[] = new String[employeesId.length];
		int i = 0;
		for(int x : employeesId) {
			result[i] = getEmployeeEmail(x);
			i++;
		}
		return result;
	}
	
	public int getDepartmentEmployeesCount(int departmentId) {
		int result = 0;
		query = "select count(*) as count_no from employees where department_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, departmentId);
			rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getInt("count_no");
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return result;
	}
	
	
	public String[] getDepartmentEmployeesEmail(int departmentId) {
		count = getDepartmentEmployeesCount(departmentId);
		String result[] = new String[count];
		query = "select email from employees where department_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, departmentId);
			rs = ps.executeQuery();
			int i = 0;
			while(rs.next()) {
				result[i] = rs.getString("email");
				i++;
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return result;
	}
	
	public int[] getEmployeesId(String employees) {
		employees = employees.replace("'", "");
		String arr[] = employees.split(":");
		int result[] = new int[arr.length];
		int i = 0;
		for(String x : arr) {
			result[i] = Integer.parseInt(x);
			i++;
		}
		return result;
	}
	
	public boolean isSupervisor(int employeeId) {
		boolean result = false;
		query = "select count(*) as count_no from employees where leave_supervisor_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
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
	
	public int getSupervisorId(int employeeId) {
		int result = 0;
		query = "select leave_supervisor_id from employees where employee_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getInt("leave_supervisor_id");
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return result;
	}
	
	public ResultSet getEmployeeOption(String employeesId) {
		employeesId = employeesId.replace("'", "").replace(":", ",");
		query = "select * from employees where employee_id not in ("+employeesId+") order by first_name asc";
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
	
	public ResultSet getEmployeeArrayOptionInteger(int employeesId[]) {
		String values = "";
		for(int i = 0; i < employeesId.length; i++) {
			values += employeesId[i];
			if(i != (employeesId.length - 1)) {
				values += ",";
			}
		}
		query = "select * from employees where employee_id not in ("+values+")";
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
	
	public ResultSet getEmployeeArrayOptionString(String employeesId[]) {
		String values = "";
		if(employeesId.length >= 1) { 
			for(int i = 0; i < employeesId.length; i++) {
				values += employeesId[i];
				if(i != (employeesId.length - 1)) {
					values += ",";
				}
			}
			query = "select * from employees where employee_id not in ("+values+") order by first_name asc";
		}
		else {
			query = "select * from employees order by first_name asc";
		}
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
	
	public String convertEmployeeIdArraysToString(String employeesId[]) {
		String result = "";
		for(int i = 0; i < employeesId.length; i++) {
			result +="'"+employeesId[i]+"'";
			if(i != (employeesId.length - 1)) {
				result +=":";
			}
		}
		return result;
	}
	
	public String[] getEmployeeEmailsArray(int employeesId[]) {
		String result[] = new String[employeesId.length];
		String quotes = "";
		for(int i = 0; i < employeesId.length; i++) {
			quotes += employeesId[i];
			if( i != (employeesId.length - 1)) {
				quotes +=",";
			}
		}
		query = "select email from employees where employee_id in ("+quotes+")";
		dbcon.getConnection();
		try {
			stmt = dbcon.con.createStatement();
			rs = stmt.executeQuery(query);
			int i = 0;
			while(rs.next()) {
				result[i] = rs.getString("email");
				i++;
			}
			
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return result;
	}
	
	public String getEmployeesSpecialFormat(String employeesId[]) {
		String result = "";
		for(int i = 0; i < employeesId.length; i++) {
			result += "'"+ employeesId[i] + "'";
			if(i != (employeesId.length - 1)) {
				result += ":";
			}
		}
		return result;
	}
	
	public int getLevelEmployeeCount(int levelId) {
		query = "select count(*) as count_no from employees where level_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, levelId);
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
	
	public int[] getLevelEmployeesId(int levelId) {
		int length = getLevelEmployeeCount(levelId);
		if(length >= 1) {
			int result[] = new int[length];
			query = "select employee_id from employees where level_id = ?";
			dbcon.getConnection();
			try {
				ps = dbcon.con.prepareStatement(query);
				ps.setInt(1, levelId);
				rs = ps.executeQuery();
				int i = 0;
				while(rs.next()) {
					result[i] = rs.getInt("employee_id");
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

	public int deleteEmplyeePayElementByEmployeeId(int employeeId) {
		query = "delete from employee_pay_elements where employee_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			count = ps.executeUpdate();
			//dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public int createEmployeePayElement(Employee e) {
		BoundaryDao bd = new BoundaryDao();
		EmployeePayElementDao eped = new EmployeePayElementDao();
		PayElementDao ped = new PayElementDao();
		int boundariesId[] = bd.getBoundaryIdByLevelId(e.getLevelId());
		for(int x : boundariesId) {
			Boundary b = bd.getBoundaryById(x);
			EmployeePayElement epe = new EmployeePayElement();
			PayElement pe = ped.getPayElementById(b.getPayElementId());
			epe.setEmployeeId(e.getEmployeeId());
			epe.setLevelId(e.getLevelId());
			epe.setPayElementId(b.getPayElementId());
			epe.setBoundaryId(b.getBoundaryId());
			epe.setAmount(b.getDefaultAmount());
			epe.setCreatedBy(e.getCreatedBy());
			epe.setStartDate(pe.getStartDate());
			epe.setEndDate(pe.getEndDate());
			count += eped.createEmployeePayElement(epe);
		}
		return count;
	}
	
	public int deleteEmployeeRoles(int employeeId) {
		query = "delete from employee_roles where employee_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public int deleteEmployeeLeavePlans(int employeeId) {
		query = "delete from leave_plans where employee_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public int deleteEmployeeLeaves(int employeeId) {
		query = "delete from leaves where employee_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	
	public int getEmployeeStatusTypeCount(int employeeStatusId) {
		query = "select count(*) as count_no from employees where employee_status_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeStatusId);
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
	
	public int[] getStatusTypeEmployeesId(int employeeStatusId) {
		int length = getEmployeeStatusTypeCount(employeeStatusId);
		int result[] = new int[length];
		query = "select employee_id from employees where employee_status_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeStatusId);
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
	
	public ResultSet getEmployeeByLevels(int levelsId[]) {
		int length = levelsId.length;
		String quote = "";
		for(int i = 0; i < length; i++) {
			quote +="?";
			if(i != (length - 1)) {
				quote +=",";
			}
		}
		query = "select * from employees where level_id in ("+quote+")";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			count = 1;
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
	
	
	public static void main(String args[]) {
		EmployeeDao ed = new EmployeeDao();
		Employee e = ed.getEmployee("seunolo2@gmail.com");
		e.setBankId(1);
		e.setAccountNo("2083541214");
		int count = ed.updateEmployee(e);
		System.out.println(count);
	}	
	
}