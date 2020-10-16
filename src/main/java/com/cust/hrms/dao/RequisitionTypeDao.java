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
	
	public static void main(String args[]) {
		RequisitionTypeDao rtd = new RequisitionTypeDao();
		RequisitionType rt = rtd.getRequisitionTypeById(1);
		System.out.println(rt.toString());
	}
}
