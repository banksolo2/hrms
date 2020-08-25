package com.cust.hrms.dao;

import java.sql.*;
import com.cust.hrms.models.*;

public class DepartmentHeadDao {
	
	private DbConnection dbcon = new DbConnection();
	private DepartmentHead dh;
	private String query;
	private int count;
	private Statement stat;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public int createDepartmentHead(DepartmentHead dh) {
		query = "insert into department_heads (employee_id, department_id, created_by) values (?, ?, ?)";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, dh.getEmployeeId());
			ps.setInt(2, dh.getDepartmentId());
			ps.setInt(3, dh.getCreatedBy());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public boolean isEmployeeExistInDepartment(DepartmentHead dh) {
		boolean result = false;
		query = "select count(*) as count_no from employees where employee_id = ? and department_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, dh.getEmployeeId());
			ps.setInt(2, dh.getDepartmentId());
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
	
	public boolean isDepartmentHeadExistByDepartment(int departmentId) {
		boolean result = false;
		query = "select count(*) as count_no from department_heads where department_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, departmentId);
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
	
	public DepartmentHead getDepartmentHeadById(int departmentHeadId) {
		dh = new DepartmentHead();
		query = "select * from department_heads where department_head_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, departmentHeadId);
			rs = ps.executeQuery();
			if(rs.next()) {
				dh.setDepartmentHeadId(rs.getInt("department_head_id"));
				dh.setEmployeeId(rs.getInt("employee_id"));
				dh.setDepartmentId(rs.getInt("department_id"));
				dh.setCreatedAt(rs.getTimestamp("created_at").toString());
				dh.setUpdatedAt(rs.getTimestamp("updated_at").toString());
				dh.setCreatedBy(rs.getInt("created_by"));
				dh.setUpdatedBy(rs.getInt("updated_by"));
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return dh;
	}
	
	public int updateDepartmentHead(DepartmentHead dh) {
		query = "update department_heads set employee_id = ?, department_id = ?, updated_by = ? where department_head_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, dh.getEmployeeId());
			ps.setInt(2, dh.getDepartmentId());
			ps.setInt(3, dh.getUpdatedBy());
			ps.setInt(4, dh.getDepartmentHeadId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public boolean isDepartmentHeadExistByDepartmentOnUpdate(DepartmentHead dh) {
		boolean result = false;
		query = "select count(*) as count_no from department_heads where department_head_id != ? and department_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, dh.getDepartmentHeadId());
			ps.setInt(2, dh.getDepartmentId());
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
	
	public int deleteDepartmentHead(DepartmentHead dh) {
		query = "delete from department_heads where department_head_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, dh.getDepartmentHeadId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public ResultSet getAllDepartmentHead() {
		query = "select * from department_heads";
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
	
	public int getDepartmentHeadId(int departmentId) {
		int result = 0;
		query = "select employee_id from department_heads where department_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, departmentId);
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
	
	public static void main(String args[]) {
		DepartmentHeadDao dhd = new DepartmentHeadDao();
		int result = dhd.getDepartmentHeadId(3);
		System.out.println(result);
	}
}
