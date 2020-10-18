package com.cust.hrms.dao;

import java.sql.*;
import com.cust.hrms.models.*;

public class RequisitionTypeDao {
	private DbConnection dbcon = new DbConnection();
	private RequisitionType rt;
	private String query;
	private int count;
	private Statement stat;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public int createRequisitionType(RequisitionType rt) {
		query = "insert into requisition_types (name, code, created_by) values(?, ?, ?)";
		dbcon.getConnection();
		rt.setCode(rt.getName().trim().toLowerCase().replace(" ", "_"));
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, rt.getName().trim());
			ps.setString(2, rt.getCode());
			ps.setInt(3, rt.getCreatedBy());
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
		query = "select count(*) as count_no from requisition_types where lower(name) = ?";
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
	
	public RequisitionType getRequisitionTypeById(int requisitionTypeId) {
		rt = new RequisitionType();
		query = "select * from requisition_types where requisition_type_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, requisitionTypeId);
			rs = ps.executeQuery();
			if(rs.next()) {
				rt.setRequisitionTypeId(rs.getInt("requisition_type_id"));
				rt.setName(rs.getString("name"));
				rt.setCode(rs.getString("code"));
				rt.setCreatedBy(rs.getInt("created_by"));
				rt.setUpdatedBy(rs.getInt("updated_by"));
				rt.setCreatedAt(rs.getTimestamp("created_at").toString());
				rt.setUpdatedAt(rs.getTimestamp("updated_at").toString());
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rt;
	}
	
	public RequisitionType getRequisitionTypeByCode(String code) {
		rt = new RequisitionType();
		query = "select * from requisition_types where lower(code) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, code.trim().toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				rt.setRequisitionTypeId(rs.getInt("requisition_type_id"));
				rt.setName(rs.getString("name"));
				rt.setCode(rs.getString("code"));
				rt.setCreatedBy(rs.getInt("created_by"));
				rt.setUpdatedBy(rs.getInt("updated_by"));
				rt.setCreatedAt(rs.getTimestamp("created_at").toString());
				rt.setUpdatedAt(rs.getTimestamp("updated_at").toString());
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rt;
	}
	
	public int updateRequisitionType(RequisitionType rt) {
		query = "update requisition_types set name = ?, code = ?, updated_by = ? where requisition_type_id = ?";
		dbcon.getConnection();
		rt.setCode(rt.getName().trim().replace(" ", "_").toLowerCase());
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, rt.getName().trim());
			ps.setString(2, rt.getCode());
			ps.setInt(3, rt.getUpdatedBy());
			ps.setInt(4, rt.getRequisitionTypeId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public boolean isNameExistOnUpdate(RequisitionType rt) {
		boolean result = false;
		query = "select count(*) as count_no from requisition_types where requisition_type_id != ? and lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, rt.getRequisitionTypeId());
			ps.setString(2, rt.getName().trim().toLowerCase());
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
	
	public int deleteRequisitionType(RequisitionType rt) {
		query = "delete from requisition_types where requisition_type_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, rt.getRequisitionTypeId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public ResultSet getRequisitionTypesReport() {
		query = "select * from requisition_types";
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
	
	public ResultSet getRequisitionTypesOption(int requisitionTypeId) {
		query = "select * from requisition_types where requisition_types != ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, requisitionTypeId);
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public String getName(int requisitionTypeId) {
		String result = null;
		query = "select name from requisition_types where requisition_type_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, requisitionTypeId);
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
	
	public int getRequisitionTypeId(String code) {
		int result = 0;
		query = "select requisition_type_id from requisition_types where lower(code) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, code.trim().toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getInt("requisition_type_id");
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return result;
	}
	
	
	public static void main(String args[]) {
		RequisitionTypeDao rtd = new RequisitionTypeDao();
		int result = rtd.getRequisitionTypeId("game_test");
		System.out.println(result);
	}
}
