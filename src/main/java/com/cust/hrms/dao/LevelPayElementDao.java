package com.cust.hrms.dao;

import java.sql.*;
import com.cust.hrms.models.*;

public class LevelPayElementDao {
	private DbConnection dbcon = new DbConnection();
	private int count;
	private String query;
	private Statement stat;
	private PreparedStatement ps;
	private ResultSet rs;
	private LevelPayElement lpe;
	
	public int createLevelPayElement(LevelPayElement lpe) {
		query = "insert into levels_pay_elements (level_id, pay_element_id, created_by) values(?, ?, ?)";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, lpe.getLevelId());
			ps.setInt(2, lpe.getPayElementId());
			ps.setInt(3, lpe.getCreatedBy());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public boolean isLevelPayElementExist(LevelPayElement lpe) {
		boolean result = false;
		query = "select count(*) as count_no from levels_pay_elements where level_id = ? and pay_element_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, lpe.getLevelId());
			ps.setInt(2, lpe.getPayElementId());
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
	
	public LevelPayElement getLevelPayElementById(int levelPayElementId) {
		lpe = new LevelPayElement();
		query = "select * from levels_pay_elements where level_pay_element_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, levelPayElementId);
			rs = ps.executeQuery();
			if(rs.next()) {
				lpe.setLevelPayElementId(rs.getInt("level_pay_element_id"));
				lpe.setLevelId(rs.getInt("level_id"));
				lpe.setPayElementId(rs.getInt("pay_element_id"));
				lpe.setCreatedBy(rs.getInt("created_by"));
				lpe.setCreatedAt(rs.getTimestamp("created_at").toString());
				lpe.setUpdatedBy(rs.getInt("updated_by"));
				lpe.setUpdatedAt(rs.getTimestamp("updated_at").toString());
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return lpe;
	}
	
	public LevelPayElement getLevelPayElement(int levelId, int payElementId) {
		lpe = new LevelPayElement();
		query = "select * from levels_pay_elements where level_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, levelId);
			ps.setInt(2, payElementId);
			rs = ps.executeQuery();
			if(rs.next()) {
				lpe.setLevelPayElementId(rs.getInt("level_pay_element_id"));
				lpe.setLevelId(rs.getInt("level_id"));
				lpe.setPayElementId(rs.getInt("pay_element_id"));
				lpe.setCreatedBy(rs.getInt("created_by"));
				lpe.setCreatedAt(rs.getTimestamp("created_at").toString());
				lpe.setUpdatedBy(rs.getInt("updated_by"));
				lpe.setUpdatedAt(rs.getTimestamp("updated_at").toString());
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return lpe;
	}
	
	public int updateLevelPayElement(LevelPayElement lpe) {
		query = "update levels_pay_elements set level_id = ?, pay_element_id = ?, updated_by = ? "
				+ "where level_pay_element_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, lpe.getLevelId());
			ps.setInt(2, lpe.getPayElementId());
			ps.setInt(3, lpe.getUpdatedBy());
			ps.setInt(4, lpe.getLevelPayElementId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public boolean isLevelPayElementExistOnUpdate(LevelPayElement lpe) {
		boolean result = false;
		query = "select count(*) as count_no from levels_pay_elements where level_pay_element_id != ? and "
				+ "level_id = ? and pay_element_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, lpe.getLevelPayElementId());
			ps.setInt(2, lpe.getLevelId());
			ps.setInt(3, lpe.getPayElementId());
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
	
	public int deleteLevelPayElement(LevelPayElement lpe) {
		query = "delete from levels_pay_elements where level_pay_element_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, lpe.getLevelPayElementId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public ResultSet getLevelPayElementReport() {
		query = "select * from levels_pay_elements";
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
	
	public int getLevelPayElementCount(int levelId) {
		query = "select count(*) as count_no from levels_pay_elements where level_id = ?";
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
	
	public int[] getLevelPayElementsId(int levelId) {
		int length = getLevelPayElementCount(levelId);
		int result[] = new int[length];
		int i = 0;
		query = "select pay_element_id from levels_pay_elements where level_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, levelId);
			rs = ps.executeQuery();
			while(rs.next()) {
				result[i] = rs.getInt("pay_element_id");
				i++;
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return result;
	}
	
	
	public static void main(String args[]) {
		LevelPayElementDao lped = new LevelPayElementDao();
		LevelDao ld = new LevelDao();
		PayElementDao ped = new PayElementDao();
		int result[] = lped.getLevelPayElementsId(4);
		for(int x : result) {
			System.out.println(x);
		}
	}
}
