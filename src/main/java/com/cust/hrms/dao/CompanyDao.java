package com.cust.hrms.dao;

import java.sql.*;
import com.cust.hrms.models.*;

public class CompanyDao {
	
	private DbConnection dbcon = new DbConnection();
	private String query;
	private int count;
	private Statement stat;
	private PreparedStatement ps;
	private ResultSet rs;
	private Company com;
	
	public int createCompany(Company c) {
		com = c;
		query = "insert into companies (name, code_name, created_by) values(?, ?, ?)";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, com.getName());
			ps.setString(2, com.getCodeName());
			ps.setInt(3, com.getCreatedBy());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public boolean isCompanyNameExist(String name) {
		boolean result = false;
		query = "select count(*) as count_no from companies where lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, name.toLowerCase());
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
	
	public Company getCompany(String name) {
		com = new Company();
		query = "select * from companies where lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, name.toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				com.setCompanyId(rs.getInt("company_id"));
				com.setName(rs.getString("name"));
				com.setCodeName(rs.getString("code_name"));
				com.setCreatedAt(rs.getTimestamp("created_at").toString());
				com.setUpdatedAt(rs.getTimestamp("updated_at").toString());
				com.setCreatedBy(rs.getInt("created_by"));
				com.setUpdatedBy(rs.getInt("updated_by"));
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return com;
	}
	
	public int updateCompany(Company c) {
		com = c;
		query = "update companies set name = ?, code_name = ?, updated_by = ? where company_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, com.getName());
			ps.setString(2, com.getCodeName());
			ps.setInt(3, com.getUpdatedBy());
			ps.setInt(4, com.getCompanyId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public boolean isCompanyNameExistByanother(Company c) {
		boolean result = false;
		com = c;
		query = "select count(*) as count_no from companies where company_id != ? and lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, com.getCompanyId());
			ps.setString(2, com.getName().toLowerCase());
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
	
	public int deleteCompany(Company c) {
		com = c;
		query = "delete from companies where company_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, com.getCompanyId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public ResultSet getAllCompany() {
		query = "select * from companies order by name asc";
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
	
	public ResultSet getAllCompanyOnUpdate(int companyId) {
		query = "select * from companies where company_id != ? order by name asc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, companyId);
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return rs;
	}
	
	public String getCompanyName(int companyId) {
		String result = null;
		query = "select name from companies where company_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, companyId);
			rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getString("name");
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return result;
	}
	
	public Company getCompanyById(int companyId) {
		com = new Company();
		query = "select * from companies where company_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, companyId);
			rs = ps.executeQuery();
			if(rs.next()) {
				com.setCompanyId(rs.getInt("company_id"));
				com.setName(rs.getString("name"));
				com.setCodeName(rs.getString("code_name"));
				com.setCreatedAt(rs.getTimestamp("created_at").toString());
				com.setUpdatedAt(rs.getTimestamp("upated_at").toString());
				com.setCreatedBy(rs.getInt("created_by"));
				com.setUpdatedBy(rs.getInt("updated_by"));
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return com;
	}
	
	public static void main(String args[]) {
		CompanyDao cd = new CompanyDao();
		String result = cd.getCompanyName(2);
		System.out.println(result);
	}
}
