package com.cust.hrms.dao;

import java.sql.*;
import java.sql.Date;

import com.cust.hrms.models.*;
import java.text.*;
import java.util.*;

public class BranchDao {
	private Statement stat;
	private PreparedStatement ps;
	private DbConnection dbcon = new DbConnection();
	private String query;
	private int count;
	private ResultSet rs;
	private Branch br;
	
	public int createBranch(Branch b) {
		br = b;
		query = "insert into branches (name, created_by) values(?, ?)";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, br.getName());
			ps.setInt(2, br.getCreatedBy());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public boolean isBranchNameExist(String name) {
		boolean result = false;
		query = "select count(*) as count_no from branches where lower(name) = ?";
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
	
	public Branch getBranch(String name) {
		br = new Branch();
		query = "select * from branches where lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, name.toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				br.setBranchId(rs.getInt("branch_id"));
				br.setName(rs.getString("name"));
				br.setCreatedAt(rs.getTimestamp("created_at").toString());
				br.setUpdatedAt(rs.getTimestamp("updated_at").toString());
				br.setCreatedBy(rs.getInt("created_by"));
				br.setUpdatedBy(rs.getInt("updated_by"));
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return br;
	}
	
	public int updateBranch(Branch b) {
		br = b;
		query = "update branches set name = ?, updated_by = ? where branch_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, br.getName());
			ps.setInt(2, br.getUpdatedBy());
			ps.setInt(3, br.getBranchId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public boolean isBranchNameExistByAnother(Branch b) {
		boolean result = false;
		br = b;
		query = "select count(*) as count_no from branches where branch_id != ? and lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, br.getBranchId());
			ps.setString(2, br.getName().toLowerCase());
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
	
	public int deleteBranch(Branch b) {
		br = b;
		query = "delete from branches where branch_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, br.getBranchId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public ResultSet getAllBranches() {
		query = "select * from branches order by name asc";
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
	
	public ResultSet getAllBranchesOnUpdate(int branchId) {
		query = "select * from branches where branch_id != ? order by name asc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, branchId);
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return rs;
	}
	
	public String getBranchName(int branchId) {
		String value = null;
		query = "select name from branches where branch_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, branchId);
			rs = ps.executeQuery();
			if(rs.next()) {
				value = rs.getString("name");
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return value;
	}
	
	public Branch getBranchById(int branchId) {
		br = new Branch();
		query = "select * from branches where branch_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, branchId);
			rs = ps.executeQuery();
			if(rs.next()) {
				br.setBranchId(rs.getInt("branch_id"));
				br.setName(rs.getString("name"));
				br.setCreatedAt(rs.getTimestamp("created_at").toString());
				br.setUpdatedAt(rs.getTimestamp("updated_at").toString());
				br.setCreatedBy(rs.getInt("created_by"));
				br.setUpdatedBy(rs.getInt("updated_by"));
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return br;
	}
	
	
	public static void main(String args[]) {
		
	}
}
