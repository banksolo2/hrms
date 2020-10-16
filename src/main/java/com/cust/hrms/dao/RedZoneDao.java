package com.cust.hrms.dao;

import java.sql.*;
import com.cust.hrms.models.*;

public class RedZoneDao {
	private DbConnection dbcon = new DbConnection();
	private RedZone rz;
	private String query;
	private int count;
	private Statement stat;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public int createRedZone(RedZone rz) {
		query = "insert into red_zones (description, date_from, date_to, department_id, created_by) values(?, ?, ?, ?, ?)";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, rz.getDescription());
			ps.setDate(2, Date.valueOf(rz.getDateFrom()));
			ps.setDate(3, Date.valueOf(rz.getDateTo()));
			ps.setInt(4, rz.getDepartmentId());
			ps.setInt(5, rz.getCreatedBy());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public int createRedZoneAllDepartment(RedZone rz) {
		DepartmentDao dd = new DepartmentDao();
		query = "insert into red_zones (description, date_from, date_to, department_id, created_by) values(?, ?, ?, ?, ?)";
		dbcon.getConnection();
		try {
			rs = dd.getAllDepartment();
			while(rs.next()) {
				ps = dbcon.con.prepareStatement(query);
				ps.setString(1, rz.getDescription());
				ps.setDate(2, Date.valueOf(rz.getDateFrom()));
				ps.setDate(3, Date.valueOf(rz.getDateTo()));
				ps.setInt(4, rs.getInt("department_id"));
				ps.setInt(5, rz.getCreatedBy());
				count += ps.executeUpdate();
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public RedZone getRedZoneById(int redZoneId) {
		rz = new RedZone();
		query = "select * from red_zones where red_zone_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, redZoneId);
			rs = ps.executeQuery();
			if(rs.next()) {
				rz.setRedZoneId(rs.getInt("red_zone_id"));
				rz.setDescription(rs.getString("description"));
				rz.setDateFrom(rs.getDate("date_from").toString());
				rz.setDateTo(rs.getDate("date_to").toString());
				rz.setDepartmentId(rs.getInt("department_id"));
				rz.setCreatedBy(rs.getInt("created_by"));
				rz.setCreatedAt(rs.getTimestamp("created_at").toString());
				rz.setUpdatedBy(rs.getInt("updated_by"));
				rz.setUpdatedAt(rs.getTimestamp("updated_at").toString());
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return rz;
	}
	
	public int updateRedZone(RedZone rz) {
		query = "update red_zones set description = ?, date_from = ?, date_to = ?, department_id = ?, updated_by = ? where red_zone_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, rz.getDescription());
			ps.setDate(2, Date.valueOf(rz.getDateFrom()));
			ps.setDate(3, Date.valueOf(rz.getDateTo()));
			ps.setInt(4, rz.getDepartmentId());
			ps.setInt(5, rz.getUpdatedBy());
			ps.setInt(6, rz.getRedZoneId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public int deleteRedZone(RedZone rz) {
		query = "delete from red_zones where red_zone_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, rz.getRedZoneId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public int deleteAllRedZone() {
		query = "delete from red_zones where red_zone_id != 0";
		dbcon.getConnection();
		try {
			stat = dbcon.con.createStatement();
			count = stat.executeUpdate(query);
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public ResultSet getAllRedZone() {
		query = "select * from red_zones order by date_from asc";
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
	
	public boolean isDateInRedZone(RedZone rz) {
		boolean result = false;
		Date startDate = Date.valueOf(rz.getDateFrom());
		Date endDate = Date.valueOf(rz.getDateTo());
		query = "select count(*) as count_no from red_zones where department_id = ? and "
				+ "(date_from between ? and ?) or (date_to between ? and ?) ";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, rz.getDepartmentId());
			ps.setDate(2, startDate);
			ps.setDate(3, endDate);
			ps.setDate(4, startDate);
			ps.setDate(5, endDate);
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
	
	public static void main(String args[]) {
		RedZoneDao rzd = new RedZoneDao();
		RedZone rz = new RedZone();
		rz.setDepartmentId(6);
		rz.setDateFrom("2020-11-25");
		rz.setDateTo("2020-11-30");
		boolean result = rzd.isDateInRedZone(rz);
		System.out.println(result);
	}
}
