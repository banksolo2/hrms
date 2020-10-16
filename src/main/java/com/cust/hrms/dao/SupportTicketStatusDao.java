package com.cust.hrms.dao;

import java.sql.*;
import com.cust.hrms.models.*;

public class SupportTicketStatusDao {
	private DbConnection dbcon = new DbConnection();
	private SupportTicketStatus sts;
	private int count;
	private String query;
	private Statement stat;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public int createSupportTicketStatus(SupportTicketStatus sts) {
		query = "insert into support_ticket_statues (name, code) values(?, ?)";
		sts.setCode(sts.getName().trim().replace(" ", "_").toLowerCase());
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, sts.getName().trim());
			ps.setString(2, sts.getCode().trim());
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
		query = "select count(*) as count_no from support_ticket_statues where lower(name) = ?";
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
	
	public SupportTicketStatus getSupportTicketStatusById(int supportTicketStatusId) {
		sts = new SupportTicketStatus();
		query = "select * from support_ticket_statues where support_ticket_status_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, supportTicketStatusId);
			rs = ps.executeQuery();
			if(rs.next()) {
				sts.setSupportTicketStatusId(rs.getInt("support_ticket_status_id"));
				sts.setName(rs.getString("name"));
				sts.setCode(rs.getString("code"));
				sts.setCreatedAt(rs.getTimestamp("created_at").toString());
				sts.setUpdatedAt(rs.getTimestamp("updated_at").toString());
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return sts;
	}
	
	public SupportTicketStatus getSupportTicketStatusByCode(String code) {
		sts = new SupportTicketStatus();
		query = "select * from support_ticket_statues where lower(code) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, code.trim().toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				sts.setSupportTicketStatusId(rs.getInt("support_ticket_status_id"));
				sts.setName(rs.getString("name"));
				sts.setCode(rs.getString("code"));
				sts.setCreatedAt(rs.getTimestamp("created_at").toString());
				sts.setUpdatedAt(rs.getTimestamp("updated_at").toString());
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return sts;
	}
	
	public int updateSupportTicketStatus(SupportTicketStatus sts) {
		query = "update support_ticket_statues set name = ?, code = ? where support_ticket_status_id = ?";
		sts.setCode(sts.getName().trim().replace(" ", "_").toLowerCase());
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, sts.getName().trim());
			ps.setString(2, sts.getCode().trim());
			ps.setInt(3, sts.getSupportTicketStatusId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public boolean isNameExistOnUpdate(SupportTicketStatus sts) {
		boolean result = false;
		query = "select count(*) as count_no from support_ticket_statues where support_ticket_status_id != ? and lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, sts.getSupportTicketStatusId());
			ps.setString(2, sts.getName().trim().toLowerCase());
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
	
	public int deleteSupportTicketStatus(SupportTicketStatus sts) {
		query = "delete from support_ticket_statues where support_ticket_status_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, sts.getSupportTicketStatusId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public int getSupportTicketStatusId(String code) {
		int result = 0;
		query = "select support_ticket_status_id from support_ticket_statues where lower(code) = ? ";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, code.trim().toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getInt("support_ticket_status_id");
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return result;
	}
	
	public String getSupportTicketStatusName(int supportTicketStatusId) {
		String result = null;
		query = "select name from support_ticket_statues where support_ticket_status_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, supportTicketStatusId);
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
	
	public ResultSet getSupportTicketStatusResolvedOption() {
		query = "select * from support_ticket_statues where code in (?, ?) order by name asc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, "closed");
			ps.setString(2, "unresolved");
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public static void main(String args[]) {
		SupportTicketStatusDao stsd = new SupportTicketStatusDao();
		String result = stsd.getSupportTicketStatusName(3);
		System.out.println(result);
	}
}
