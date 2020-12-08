package com.cust.hrms.dao;

import java.sql.*;
import com.cust.hrms.models.*;

public class PayElementDeductionTypeDao {
	private DbConnection dbcon = new DbConnection();
	private PayElementDeductionType pedt = new PayElementDeductionType();
	private String query;
	private int count;
	private PreparedStatement ps;
	private Statement stat;
	private ResultSet rs;
	
	public int createPayElementDeductionTypeDao(PayElementDeductionType pedt) {
		pedt.setCode(pedt.getName().trim().toLowerCase().replace(" ", "_"));
		query = "insert into pay_element_deduction_types (name, code, created_by) values(?, ?, ?)";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, pedt.getName().trim());
			ps.setString(2, pedt.getCode());
			ps.setInt(3, pedt.getCreatedBy());
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
		query = "select count(*) as count_no from pay_element_deduction_types where lower(name) = ?";
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
	
	public PayElementDeductionType getPayElementDeductionTypeByCode(String code) {
		pedt = new PayElementDeductionType();
		query = "select * from pay_element_deduction_types where lower(code) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, code.trim().toLowerCase());
			rs = ps.executeQuery();
			while(rs.next()) {
				pedt.setPayElementDeductionTypeId(rs.getInt("pay_element_deduction_type_id"));
				pedt.setName(rs.getString("name"));
				pedt.setCode(rs.getString("code"));
				pedt.setCreatedBy(rs.getInt("created_by"));
				pedt.setUpdatedBy(rs.getInt("updated_by"));
				pedt.setCreatedAt(rs.getTimestamp("created_at").toString());
				pedt.setUpdatedAt(rs.getTimestamp("updated_at").toString());
			}
			rs.close();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return pedt;
	} 
	
	public PayElementDeductionType getPayElementDeductionTypeById(int payElementDeductionTypeId) {
		pedt = new PayElementDeductionType();
		query = "select * from pay_element_deduction_types where pay_element_deduction_type_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, payElementDeductionTypeId);
			rs = ps.executeQuery();
			if(rs.next()) {
				pedt.setPayElementDeductionTypeId(rs.getInt("pay_element_deduction_type_id"));
				pedt.setName(rs.getString("name"));
				pedt.setCode(rs.getString("code"));
				pedt.setCreatedBy(rs.getInt("created_by"));
				pedt.setUpdatedBy(rs.getInt("updated_by"));
				pedt.setCreatedAt(rs.getTimestamp("created_at").toString());
				pedt.setUpdatedAt(rs.getTimestamp("updated_at").toString());
			}
			rs.close();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return pedt;
	}
	
	public int updatePayElementDeductionType(PayElementDeductionType pedt) {
		pedt.setCode(pedt.getName().trim().toLowerCase().replace(" ", "_"));
		query = "update pay_element_deduction_types set name = ?, code = ?, updated_by = ? "
				+ "where pay_element_deduction_type_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, pedt.getName().trim());
			ps.setString(2, pedt.getCode());
			ps.setInt(3, pedt.getUpdatedBy());
			ps.setInt(4, pedt.getPayElementDeductionTypeId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public boolean isNameExistOnUpdate(PayElementDeductionType pedt) {
		boolean result = false;
		query = "select count(*) as count_no from pay_element_deduction_types where pay_element_deduction_type_id != ? "
				+ "and lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, pedt.getPayElementDeductionTypeId());
			ps.setString(2, pedt.getName().toLowerCase().trim());
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
	
	public int deletePayElementDeductionType(PayElementDeductionType pedt) {
		query = "delete from pay_element_deduction_types where pay_element_deduction_type_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, pedt.getPayElementDeductionTypeId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public int getPayElementDeductionTypeId(String code) {
		int result = 0;
		query = "select pay_element_deduction_type_id from pay_element_deduction_types where lower(code) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, code.trim().toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getInt("pay_element_deduction_type_id");
			}
			rs.close();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return result;
	}
	
	public String getName(int payElementDeductionTypeId) {
		String result = "";
		query = "select name from pay_element_deduction_types where pay_element_deduction_type_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, payElementDeductionTypeId);
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
	
	public ResultSet getPayElementDeductionTypeOptions(int payElementDeductionTypeId) {
		query = "select * from pay_element_deduction_types where pay_element_deduction_type_id != ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, payElementDeductionTypeId);
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getPayElementDeductionTypesReport() {
		query = "select * from pay_element_deduction_types";
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
		PayElementDeductionTypeDao pedtd = new PayElementDeductionTypeDao();
		String name = pedtd.getName(1);
		System.out.println(name);
	}
	
}
