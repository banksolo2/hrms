package com.cust.hrms.dao;

import java.sql.*;
import java.sql.Date;

import com.cust.hrms.models.*;
import java.util.*;

public class HolidayDao {
	private DbConnection dbcon = new DbConnection();
	private Statement stat;
	private PreparedStatement ps;
	private ResultSet rs;
	private int count;
	private String query;
	private Holiday h;
	
	public int createHoliday(Holiday h) {
		query = "insert into holidays (name, description, date_at, created_by) values(?, ?, ?, ?)";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, h.getName());
			ps.setString(2, h.getDescription());
			ps.setDate(3, Date.valueOf(h.getDateAt()));
			ps.setInt(4, h.getCreatedBy());
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
		query = "select count(*) as count_no from holidays where lower(name) = ?";
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
	
	public Holiday getHolidayByName(String name) {
		h = new Holiday();
		query = "select * from holidays where lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, name.toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				h.setHolidayId(rs.getInt("holiday_id"));
				h.setName(rs.getString("name"));
				h.setDescription(rs.getString("description"));
				h.setDateAt(rs.getDate("date_at").toString());
				h.setCreatedBy(rs.getInt("created_by"));
				h.setCreatedAt(rs.getTimestamp("created_at").toString());
				h.setUpdatedBy(rs.getInt("updated_by"));
				h.setUpdatedAt(rs.getTimestamp("updated_at").toString());
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return h;
	}
	
	public boolean isDateAtExist(Date date) {
		boolean result = false;
		query = "select count(*) as count_no from holidays where date_at = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setDate(1, date);
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
	
	public Holiday getHolidayById(int holidayId) {
		h = new Holiday();
		query = "select * from holidays where holiday_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, holidayId);
			rs = ps.executeQuery();
			if(rs.next()) {
				h.setHolidayId(rs.getInt("holiday_id"));
				h.setName(rs.getString("name"));
				h.setDescription(rs.getString("description"));
				h.setDateAt(rs.getDate("date_at").toString());
				h.setCreatedBy(rs.getInt("created_by"));
				h.setCreatedAt(rs.getTimestamp("created_at").toString());
				h.setUpdatedBy(rs.getInt("updated_by"));
				h.setUpdatedAt(rs.getTimestamp("updated_at").toString());
			}
			dbcon.con.close();

		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return h;
	}
	
	public int updateHoliday(Holiday h) {
		query = "update holidays set name = ?, description = ?, date_at = ?, updated_by = ? where holiday_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, h.getName());
			ps.setString(2, h.getDescription());
			ps.setDate(3, Date.valueOf(h.getDateAt()));
			ps.setInt(4, h.getUpdatedBy());
			ps.setInt(5, h.getHolidayId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public boolean isNameExistOnUpdate(Holiday h) {
		boolean result = false;
		query = "select count(*) as count_no from holidays where holiday_id != ? and lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, h.getHolidayId());
			ps.setString(2, h.getName().toLowerCase());
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
	
	public boolean isDateAtExistOnUpdate(Holiday h) {
		boolean result = false;
		query = "select count(*) as count_no from holidays where holiday_id != ? and date_at = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, h.getHolidayId());
			ps.setDate(2, Date.valueOf(h.getDateAt()));
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
	
	public int deleteHoliay(Holiday h) {
		query = "delete from holidays where holiday_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, h.getHolidayId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public ResultSet getAllHolidays() {
		query = "select * from holidays order by date_at asc";
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
	
	public int getHolidayDays(String startDate, String endDate) {
		query = "select count(*) as count_no from holidays where date_at between ? and ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setDate(1, Date.valueOf(startDate));
			ps.setDate(2, Date.valueOf(endDate));
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
	

	
	public static void main(String args[]){
		HolidayDao hd = new HolidayDao();
		int holidayDays = hd.getHolidayDays("2020-12-01", "2020-12-31");
		System.out.println(holidayDays);
	}
}
