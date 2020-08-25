package com.cust.hrms.dao;

import java.sql.*;
import com.cust.hrms.models.*;

public class LevelDao {
	private Statement stat;
	private PreparedStatement ps;
	private DbConnection dbcon = new DbConnection();
	private String query;
	private int count;
	private Level lv;
	private ResultSet rs;
	
	public int createLevel(Level l) {
		lv = l;
		lv.setCode(lv.getName().replace(" ", "_").toLowerCase());
		query = "insert into levels (name, code, leave_days, created_by) values(?, ?, ?, ?)";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, lv.getName());
			ps.setString(2, lv.getCode());
			ps.setInt(3, lv.getLeaveDays());
			ps.setInt(4, lv.getCreatedBy());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public boolean isLevelNameExist(String name) {
		boolean result = false;
		query = "select count(*) as count_no from levels where lower(name) = ?";
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
	
	public Level getLevelByCode(String code) {
		lv = new Level();
		query = "select * from levels where lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, code.toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				lv.setLevelId(rs.getInt("level_id"));
				lv.setName(rs.getString("name"));
				lv.setCode(rs.getString("code"));
				lv.setLeaveDays(rs.getInt("leave_days"));
				lv.setCreatedAt(rs.getTimestamp("created_at").toString());
				lv.setUpdatedAt(rs.getTimestamp("updated_at").toString());
				lv.setCreatedBy(rs.getInt("created_by"));
				lv.setUpdatedBy(rs.getInt("updated_by"));
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return lv;
	}
	
	public Level getLevelById(int levelId) {
		lv = new Level();
		query = "select * from levels where level_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, levelId);
			rs = ps.executeQuery();
			if(rs.next()) {
				lv.setLevelId(rs.getInt("level_id"));
				lv.setName(rs.getString("name"));
				lv.setCode(rs.getString("code"));
				lv.setLeaveDays(rs.getInt("leave_days"));
				lv.setCreatedAt(rs.getTimestamp("created_at").toString());
				lv.setUpdatedAt(rs.getTimestamp("updated_at").toString());
				lv.setCreatedBy(rs.getInt("created_by"));
				lv.setUpdatedBy(rs.getInt("updated_by"));
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return lv;
	}
	
	public int updateLevel(Level l) {
		lv = l;
		lv.setCode(lv.getName().replace(" ", "_").toLowerCase());
		query = "update levels set name = ?, code = ?, leave_days = ?, updated_by = ? where level_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, lv.getName());
			ps.setString(2, lv.getCode());
			ps.setInt(3, lv.getLeaveDays());
			ps.setInt(4, lv.getUpdatedBy());
			ps.setInt(5, lv.getLevelId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public boolean isLevelNameExistByAnother(Level l) {
		lv = l;
		boolean result = false;
		query = "select count(*) as count_no from levels where level_id != ? and lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, lv.getLevelId());
			ps.setString(2, lv.getName().toLowerCase());
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
	
	public int deleteLevel(Level l) {
		lv = l;
		query = "delete from levels where level_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, lv.getLevelId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public ResultSet getAllLevels() {
		query = "select * from levels order by name asc";
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
	
	public ResultSet getAllLevelsOnUpdate(int levelId) {
		query = "select * from levels where level_id != ? order by name asc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, levelId);
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return rs;
	}
	
	public String getLevelName (int levelId) {
		String value = null;
		query = "select name from levels where level_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, levelId);
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
	
	public int getLevelId(String code) {
		int value = 0;
		query = "select level_id from levels where lower(code) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, code.toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				value = rs.getInt("level_id");
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			
		}
		
		return value;
	}
	
	public static void main(String args[]) {
		LevelDao ld = new LevelDao();
		String result = ld.getLevelName(1);
		System.out.println(result);
	}
}
