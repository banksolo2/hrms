package com.cust.hrms.dao;

import java.sql.*;
import com.cust.hrms.models.*;

public class ElementStatusDao {
	DbConnection dbcon = new DbConnection(); 
	private int count;
	private String query;
	private Statement stat;
	private PreparedStatement ps;
	private ResultSet rs;
	private ElementStatus es;
	
	public int createElementStatus(ElementStatus es) {
		query = "insert into element_statues (name, code) values(?, ?)";
		es.setCode(es.getName().trim().toLowerCase().replace(" ", "_"));
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, es.getName().trim());
			ps.setString(2, es.getCode());
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
		query = "select count(*) as count_no from element_statues where lower(name) = ?";
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
	
	public ElementStatus getElementStatusById(int elementStatusId) {
		es = new ElementStatus();
		query = "select * from element_statues where element_status_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, elementStatusId);
			rs = ps.executeQuery();
			if(rs.next()) {
				es.setElementStatusId(rs.getInt("element_status_id"));
				es.setName(rs.getString("name"));
				es.setCode(rs.getString("code"));
				es.setCreatedAt(rs.getTimestamp("created_at").toString());
				es.setCreatedAt(rs.getTimestamp("updated_at").toString());
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return es;
	}
	
	public ElementStatus getElementStatusByCode(String code) {
		es = new ElementStatus();
		query = "select * from element_statues where lower(code) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, code.trim().toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				es.setElementStatusId(rs.getInt("element_status_id"));
				es.setName(rs.getString("name"));
				es.setCode(rs.getString("code"));
				es.setCreatedAt(rs.getTimestamp("created_at").toString());
				es.setCreatedAt(rs.getTimestamp("updated_at").toString());
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return es;
	}
	
	public int updateElementStatus(ElementStatus es) {
		es.setCode(es.getName().trim().toLowerCase().replace(" ", "_"));
		query = "update element_statues set name = ?, code = ? where element_status_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, es.getName().trim());
			ps.setString(2, es.getCode());
			ps.setInt(3, es.getElementStatusId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex);
		}
		return count;
	}
	
	public boolean isNameExistOnUpdate(ElementStatus es) {
		boolean result = false;
		query = "select count(*) as count_no from element_statues where element_status_id != ? and lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, es.getElementStatusId());
			ps.setString(2, es.getName().trim().toLowerCase());
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
	
	public int deleteElementStatus(ElementStatus es) {
		query = "delete from element_statues where element_status_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, es.getElementStatusId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public int getElementStatusId(String code) {
		int result = 0;
		query = "select element_status_id from element_statues where lower(code) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, code.trim().toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getInt("element_status_id");
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return result;
	}
	
	public String getElementStatusName(int elementStatusId) {
		String result = null;
		query = "select name from element_statues where element_status_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, elementStatusId);
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
	
	public ResultSet getElementOption(int elementStatusId) {
		query = "select * from element_statues where element_status_id != ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, elementStatusId);
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getElementStatuesReport() {
		query = "select * from element_statues";
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
	
	public static void main(String args[]) {
		ElementStatusDao esd = new ElementStatusDao();
		String name = esd.getElementStatusName(2);
		System.out.println(name);
	}
}
