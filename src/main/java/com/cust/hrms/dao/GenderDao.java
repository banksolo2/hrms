package com.cust.hrms.dao;

import java.sql.*;
import com.cust.hrms.models.*;

public class GenderDao {
	private DbConnection dbcon = new DbConnection();
	private String query;
	private int count;
	private PreparedStatement ps;
	private Statement stat;
	private ResultSet rs;
	private Gender gd;
	private boolean result = false;
	
	
	public int createGender(Gender g) {
		gd = g;
		query = "insert into genders (name, created_by) values(?, ?)";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, gd.getName());
			ps.setInt(2, gd.getCreatedBy());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public boolean isGenderNameExist(String name) {
		query = "select count(*) as count_no from genders where lower(name) = ?";
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
	
	public Gender getGender(String name) {
		gd = new Gender();
		query = "select * from genders where lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, name.toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				gd.setGenderId(rs.getInt("gender_id"));
				gd.setName(rs.getString("name"));
				gd.setCreatedAt(rs.getTimestamp("created_at").toString());
				gd.setUpdatedAt(rs.getTimestamp("updated_at").toString());
				gd.setCreatedBy(rs.getInt("created_by"));
				gd.setUpdatedBy(rs.getInt("updated_by"));
			}
			dbcon.con.close();
			
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return gd;
	}
	
	public int updateGender(Gender g) {
		gd = g;
		query = "update genders set name = ?, updated_by = ? where gender_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, gd.getName());
			ps.setInt(2, gd.getUpdatedBy());
			ps.setInt(3, gd.getGenderId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public boolean isGenderNameExistByAnother(Gender g) {
		gd = g;
		query = "select count(*) as count_no from genders where gender_id != ? and lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, gd.getGenderId());
			ps.setString(2, gd.getName().toLowerCase());
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
	
	public int deleteGender(Gender g) {
		gd = g;
		query = "delete from genders where gender_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, gd.getGenderId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public ResultSet getAllGenders() {
		query = "Select * from genders order by name asc";
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
	
	public ResultSet getAllGendersOnUpdate(int genderId) {
		query = "select * from genders where gender_id != ? order by name asc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, genderId);
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return rs;
	}
	
	public String getGenderName(int genderId) {
		String value = null;
		query = "select name from genders where gender_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, genderId);
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
	
	public Gender getGenderById(int genderId) {
		gd = new Gender();
		query = "select * from genders where gender_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, genderId);
			rs = ps.executeQuery();
			if(rs.next()) {
				gd.setGenderId(rs.getInt("gender_id"));
				gd.setName(rs.getString("name"));
				gd.setCreatedAt(rs.getTimestamp("created_at").toString());
				gd.setUpdatedAt(rs.getTimestamp("updated_at").toString());
				gd.setCreatedBy(rs.getInt("created_by"));
				gd.setUpdatedBy(rs.getInt("updated_by"));
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return gd;
	}
	
	public static void main(String args[]) {
		GenderDao gd  = new GenderDao();
		String result = gd.getGenderName(1);
		System.out.println(result);
		
	}
}
