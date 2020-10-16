package com.cust.hrms.dao;

import java.sql.*;
import java.util.*;
import com.cust.hrms.models.*;

public class IssueTypeDao {
	private DbConnection dbcon = new DbConnection();
	private IssueType it;
	private String query;
	private int count;
	private Statement stat;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public int createIssueType(IssueType it) {
		query = "insert into issue_types (name, code, created_by) values(?, ?, ?)";
		it.setCode(it.getName().trim().replace(" ", "_").toLowerCase());
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, it.getName().trim());
			ps.setString(2, it.getCode());
			ps.setInt(3, it.getCreatedBy());
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
		query = "select count(*) as count_no from issue_types where lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, name.trim().toLowerCase());
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
	
	public IssueType getIssueTypeById(int issueTypeId) {
		it = new IssueType();
		query = "select * from issue_types where issue_type_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, issueTypeId);
			rs = ps.executeQuery();
			if(rs.next()) {
				it.setIssueTypeId(rs.getInt("issue_type_id"));
				it.setName(rs.getString("name"));
				it.setCode(rs.getString("code"));
				it.setCreatedBy(rs.getInt("created_by"));
				it.setCreatedAt(rs.getTimestamp("created_at").toString());
				it.setUpdatedBy(rs.getInt("updated_by"));
				it.setUpdatedAt(rs.getTimestamp("updated_at").toString());;
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return it;
	}
	
	public IssueType getIssueTypeByCode(String code) {
		it = new IssueType();
		query = "select * from issue_types where lower(code) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, code.trim().toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				it.setIssueTypeId(rs.getInt("issue_type_id"));
				it.setName(rs.getString("name"));
				it.setCode(rs.getString("code"));
				it.setCreatedBy(rs.getInt("created_by"));
				it.setCreatedAt(rs.getTimestamp("created_at").toString());
				it.setUpdatedBy(rs.getInt("updated_by"));
				it.setUpdatedAt(rs.getTimestamp("updated_at").toString());;
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return it;
	}
	
	public int updateIssueType(IssueType it) {
		query = "update issue_types set name = ?, code = ?, updated_by = ? where issue_type_id = ?";
		it.setCode(it.getName().trim().replace(" ", "_").toLowerCase());
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, it.getName().trim());
			ps.setString(2, it.getCode().trim());
			ps.setInt(3, it.getUpdatedBy());
			ps.setInt(4, it.getIssueTypeId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public boolean isNameExistOnUpdate(IssueType it) {
		boolean result = false;
		query = "select count(*) as count_no from issue_types where issue_type_id != ? and lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, it.getIssueTypeId());
			ps.setString(2, it.getName().trim().toLowerCase());
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
	
	public int deleteIssueType(IssueType it) {
		query = "delete from issue_types where issue_type_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, it.getIssueTypeId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public ResultSet getIssueTypeReport() {
		query = "select * from issue_types order by name asc";
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
	
	public ResultSet getIssueTypeOptions(int issueTypeId) {
		query = "select * from issue_types where issue_type_id != ? order by name asc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, issueTypeId);
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public int getIssueTypeId(String code) {
		int result = 0;
		query = "select issue_type_id from issue_types where lower(code) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, code.trim().toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getInt("issue_type_id");
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return result;
	}
	
	public String getIssueTypeName(int issueTypeId) {
		String result = null;
		query = "select name from issue_types where issue_type_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, issueTypeId);
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
	
	public static void main(String args[]) {
		IssueTypeDao itd = new IssueTypeDao();
		String result = itd.getIssueTypeName(5);
		System.out.println(result);
	}
	
}
