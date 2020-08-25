package com.cust.hrms.dao;

import java.sql.*;
import com.cust.hrms.models.*;

public class MartialStatusDao {
	private DbConnection dbcon = new DbConnection();
	private String query;
	private int count;
	private boolean result = false;
	private Statement stat;
	private PreparedStatement ps;
	private ResultSet rs;
	private MartialStatus ms;
	
	public int createMartialStatus(MartialStatus m) {
		ms = m;
		query = "insert into martial_statuses (name, created_by) values(?, ?)";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, ms.getName());
			ps.setInt(2, ms.getCreatedBy());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public boolean isMartialStatusExist(String name) {
		query = "select count(*) as count_no from martial_statuses where lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, name.toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				count = rs.getInt("count_no");
			}
			result =(count >= 1);
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return result;
	}
	
	public MartialStatus getMartialStatus(String name) {
		ms = new MartialStatus();
		query = "select * from martial_statuses where lower(name) = ? ";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, name.toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				ms.setMartialStatusId(rs.getInt("martial_status_id"));
				ms.setName(rs.getString("name"));
				ms.setCreatedAt(rs.getTimestamp("created_at").toString());
				ms.setUpdatedAt(rs.getTimestamp("updated_at").toString());
				ms.setCreatedBy(rs.getInt("created_by"));
				ms.setUpdatedBy(rs.getInt("updated_by"));
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return ms;
	}
	
	public int updateMartialStatus(MartialStatus m) {
		ms = m;
		query = "update martial_statuses set name = ?, updated_by = ? where martial_status_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, ms.getName());
			ps.setInt(2, ms.getUpdatedBy());
			ps.setInt(3, ms.getMartialStatusId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public boolean isMartialStatusExistByAnother(MartialStatus m) {
		ms = m;
		query = "select count(*) as count_no from martial_statuses where martial_status_id != ? and lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, ms.getMartialStatusId());
			ps.setString(2, ms.getName().toLowerCase());
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
	
	public int deleteMartialStatus(MartialStatus m) {
		ms = m;
		query = "delete from martial_statuses where martial_status_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, ms.getMartialStatusId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex){
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public ResultSet getAllMartialStatuses() {
		query = "select * from martial_statuses order by name asc";
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
	
	public ResultSet getAllMartialStatusesOnUpdate(int martialStatusId) {
		query = "select * from martial_statuses where martial_status_id != ? order by name asc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, martialStatusId);
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return rs;
	}
	
	public String getMartialStatusName(int martialStatusId) {
		String value = null;
		query = "select name from martial_statuses where martial_status_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, martialStatusId);
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
	
	public MartialStatus getMartialStatusById(int martialStatusId) {
		ms = new MartialStatus();
		query = "select * from martial_statuses where martial_status_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, martialStatusId);
			rs = ps.executeQuery();
			if(rs.next()) {
				ms.setMartialStatusId(rs.getInt("martial_status_id"));
				ms.setName(rs.getString("name"));
				ms.setCreatedAt(rs.getTimestamp("created_at").toString());
				ms.setUpdatedAt(rs.getTimestamp("updated_at").toString());
				ms.setCreatedBy(rs.getInt("created_by"));
				ms.setUpdatedBy(rs.getInt("updated_by"));
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return ms;
	}
	
	public static void main(String args[]) {
		MartialStatusDao msd = new MartialStatusDao();
		msd.ms = msd.getMartialStatus("testing");
		msd.count = msd.deleteMartialStatus(msd.ms);
		System.out.println(msd.count);
	}
}
