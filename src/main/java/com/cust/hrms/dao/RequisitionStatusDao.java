package com.cust.hrms.dao;

import java.sql.*;
import com.cust.hrms.models.*;

public class RequisitionStatusDao {
	private DbConnection dbcon = new DbConnection();
	private RequisitionStatus rqs;
	private String query;
	private int count;
	private Statement stat;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public int createRequisitionStatus(RequisitionStatus rqs) {
		rqs.setCode(rqs.getName().trim().replace(" ", "_").toLowerCase());
		query = "insert into requisition_statues (name, code, created_by) values(?, ?, ?)";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, rqs.getName().trim());
			ps.setString(2, rqs.getCode());
			ps.setInt(3, rqs.getCreatedBy());
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
		query = "select count(*) as count_no from requisition_statues where lower(name) = ?";
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
	
	public RequisitionStatus getRequisitionStatusById(int requisitionStatusId) {
		rqs = new RequisitionStatus();
		query = "select * from requisition_statues where requisition_status_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, requisitionStatusId);
			rs = ps.executeQuery();
			if(rs.next()) {
				rqs.setRequisitionStatusId(rs.getInt("requisition_status_id"));
				rqs.setName(rs.getString("name"));
				rqs.setCode(rs.getString("code"));
				rqs.setCreatedBy(rs.getInt("created_by"));
				rqs.setUpdatedBy(rs.getInt("updated_by"));
				rqs.setCreatedAt(rs.getTimestamp("created_at").toString());
				rqs.setUpdatedAt(rs.getTimestamp("updated_at").toString());
			}
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rqs;
	}
	
	public RequisitionStatus getRequisitionStatusByCode(String code) {
		rqs = new RequisitionStatus();
		query = "select * from requisition_statues where lower(code) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, code.trim().toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				rqs.setRequisitionStatusId(rs.getInt("requisition_status_id"));
				rqs.setName(rs.getString("name"));
				rqs.setCode(rs.getString("code"));
				rqs.setCreatedBy(rs.getInt("created_by"));
				rqs.setUpdatedBy(rs.getInt("updated_by"));
				rqs.setCreatedAt(rs.getTimestamp("created_at").toString());
				rqs.setUpdatedAt(rs.getTimestamp("updated_at").toString());
			}
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rqs;
	}
	
	public int updateRequisitionStatus(RequisitionStatus rqs) {
		rqs.setCode(rqs.getName().trim().toLowerCase().replace(" ", "_"));
		query = "update requisition_statues set name = ?, code = ?, updated_by = ? where requisition_status_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, rqs.getName().trim());
			ps.setString(2, rqs.getCode());
			ps.setInt(3, rqs.getUpdatedBy());
			ps.setInt(4, rqs.getRequisitionStatusId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public boolean isNameExistOnUpdate(RequisitionStatus rqs) {
		boolean result = false;
		query = "select count(*) as count_no from requisition_statues where requisition_status_id != ? and lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, rqs.getRequisitionStatusId());
			ps.setString(2, rqs.getName().trim().toLowerCase());
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
	
	public int deleteRequisitionStatus(RequisitionStatus rqs) {
		query = "delete from requisition_statues where requisition_status_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, rqs.getRequisitionStatusId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public ResultSet getAllRequisitionStatusReport() {
		query = "select * from requisition_statues order by name asc";
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
		RequisitionStatusDao rsd = new RequisitionStatusDao();
		RequisitionStatus rs = rsd.getRequisitionStatusByCode("testing");
		int count = rsd.deleteRequisitionStatus(rs);
		System.out.println(count);
	}
}
