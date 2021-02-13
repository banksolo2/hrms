package com.cust.hrms.dao;

import java.sql.*;
import com.cust.hrms.models.*;

public class BankDao {
	private DbConnection dbcon = new DbConnection();
	private Bank b;
	private int count;
	private String query;
	private PreparedStatement ps;
	private Statement stat;
	private ResultSet rs;
	
	public int createBank(Bank b) {
		b.setCode(b.getName().trim().toLowerCase().replace(" ", "_"));
		query = "insert into banks (name, code) values(?, ?)";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, b.getName().trim());
			ps.setString(2, b.getCode());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public boolean isBankExist(String name) {
		boolean result = false;
		query = "select count(*) as count_no from banks where lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, name.trim().toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				count = rs.getInt("count_no");
			}
			result = (count >= 1);
			rs.close();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return result;
	}
	
	public Bank getBankById(int bankId) {
		b = new Bank();
		query = "select * from banks where bank_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, bankId);
			rs = ps.executeQuery();
			if(rs.next()) {
				b.setBankId(rs.getInt("bank_id"));
				b.setName(rs.getString("name"));
				b.setCode(rs.getString("code"));
				b.setCreatedAt(rs.getTimestamp("created_at").toString());
				b.setUpdatedAt(rs.getTimestamp("updated_at").toString());
			}
			rs.close();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return b;
	}
	
	public Bank getBankByCode(String code) {
		b = new Bank();
		query = "select * from banks where lower(code) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, code.trim().toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				b.setBankId(rs.getInt("bank_id"));
				b.setName(rs.getString("name"));
				b.setCode(rs.getString("code"));
				b.setCreatedAt(rs.getTimestamp("created_at").toString());
				b.setUpdatedAt(rs.getTimestamp("updated_at").toString());
			}
			rs.close();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return b;
	}
	
	public int updateBank(Bank b) {
		b.setCode(b.getName().trim().toLowerCase().replace(" ","_"));
		query = "update banks set name = ?, code = ? where bank_id = ?";
		//get database connection
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, b.getName());
			ps.setString(2, b.getCode());
			ps.setInt(3, b.getBankId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	//Check if bank exist on update
	public boolean isBankExistOnUpdate(Bank b) {
		boolean result = false;
		query = "select count(*) as count_no from banks where bank_id != ? and lower(name) = ?";
		//get database connection
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, b.getBankId());
			ps.setString(2, b.getName().trim().toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				count = rs.getInt("count_no");
			}
			result = (count >= 1);
			rs.close();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.toString());
		}
		return result;
	}
	
	public int deleteBank(Bank b) {
		query = "delete from banks where bank_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, b.getBankId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.print(ex.fillInStackTrace());
		}
		return count;
	}
	
	public ResultSet getBankOptions(int bankId) {
		query = "select * from banks where bank_id != ? order by name asc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, bankId);
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getAllBanks() {
		query = "select * from banks order by name asc";
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
	
	
	public String getName(int bankId) {
		String result = null;
		query = "select name from banks where bank_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, bankId);
			rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getString("name");
			}
			rs.close();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return result;
	}
	
	public int getBankId(String code) {
		int result = 0;
		query = "select bank_id from banks where lower(code) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, code.trim().toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getInt("bank_id");
			}
			rs.close();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return result;
	}
	
	public static void main(String args[]) {
		BankDao bd = new BankDao();
		int bankId = bd.getBankId("access_bank_plc");
		System.out.println(bankId);
	}
}
