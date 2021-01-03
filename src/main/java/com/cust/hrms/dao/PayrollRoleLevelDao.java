package com.cust.hrms.dao;

import java.sql.*;
import com.cust.hrms.models.*;

public class PayrollRoleLevelDao {
	private DbConnection dbcon = new DbConnection();
	private PayrollRoleLevel prl;
	private int count;
	private String query;
	private Statement stat;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public int createPayrollRoleLevel(PayrollRoleLevel prl) {
		query = "insert into payroll_role_levels (payroll_role_name_id, level_id, created_by) values(?, ?, ?)";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, prl.getPayrollRoleNameId());
			ps.setInt(2, prl.getLevelId());
			ps.setInt(3, prl.getCreatedBy());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public boolean isPayrollRoleLevelExist(PayrollRoleLevel prl) {
		boolean result = false;
		query = "select count(*) as count_no from payroll_role_levels where payroll_role_name_id = ? and level_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, prl.getPayrollRoleNameId());
			ps.setInt(2, prl.getLevelId());
			rs = ps.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
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
	
	public PayrollRoleLevel getPayrollRoleLevelById(int payrollRoleLevelId) {
		prl = new PayrollRoleLevel();
		query = "select * from payroll_role_levels where payroll_role_level_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, payrollRoleLevelId);
			rs = ps.executeQuery();
			if(rs.next()) {
				prl.setPayrollRoleLevelId(rs.getInt("payroll_role_level_id"));
				prl.setPayrollRoleNameId(rs.getInt("payroll_role_name_id"));
				prl.setLevelId(rs.getInt("level_id"));
				prl.setCreatedBy(rs.getInt("created_by"));
				prl.setUpdatedBy(rs.getInt("updated_by"));
				prl.setCreatedAt(rs.getTimestamp("created_at").toString());
				prl.setUpdatedAt(rs.getTimestamp("updated_at").toString());
			}
			rs.close();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return prl;
	}
	
	public int updatePayrollRoleLevel(PayrollRoleLevel prl) {
		query = "update payroll_role_levels set payroll_role_name_id = ?, level_id = ?, updated_by = ? where payroll_role_level_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, prl.getPayrollRoleNameId());
			ps.setInt(2, prl.getLevelId());
			ps.setInt(3, prl.getUpdatedBy());
			ps.setInt(4, prl.getPayrollRoleLevelId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public boolean isPayrollRoleLevelExistOnUpdate(PayrollRoleLevel prl) {
		boolean result = false;
		query = "select count(*) as count_no from payroll_role_levels where payroll_role_level_id != ? and payroll_role_name_id = ? "
				+ "and level_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, prl.getPayrollRoleLevelId());
			ps.setInt(2, prl.getPayrollRoleNameId());
			ps.setInt(3, prl.getLevelId());
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
	
	public int deletePayrollRoleLevel(PayrollRoleLevel prl) {
		query = "delete from payroll_role_levels where payroll_role_level_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, prl.getPayrollRoleLevelId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public ResultSet getAllPayrollRoleNameReport() {
		query = "select distinct payroll_role_name_id from payroll_role_levels";
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
	
	public ResultSet getAllPayrollRoleLevelReport(int payrollRoleNameId) {
		query = "select * from payroll_role_levels where payroll_role_name_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, payrollRoleNameId);
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public int getPayrollRoleLevelCount(int payrollRoleNameId) {
		query = "select count(*) as count_no from payroll_role_levels where payroll_role_name_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, payrollRoleNameId);
			rs = ps.executeQuery();
			if(rs.next()) {
				count = rs.getInt("count_no");
			}
			rs.close();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public int[] getPayrollRoleLevelIds(int payrollRoleNameId) {
		int length = getPayrollRoleLevelCount(payrollRoleNameId);
		int result[] = new int[length];
		count = 0;
		query = "select level_id from payroll_role_levels where payroll_role_name_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, payrollRoleNameId);
			rs = ps.executeQuery();
			while(rs.next()) {
				result[count] = rs.getInt(1);
				count++;
			}
			rs.close();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return result;
	}
	
	public ResultSet getAllPayrollRoles() {
		query = "select * from payroll_role_names where lower(code) != ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, "global_officer");
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public static void main(String args[]) {
		PayrollRoleNameDao prnd = new PayrollRoleNameDao();
		PayrollRoleLevelDao prld = new PayrollRoleLevelDao();
		int levelIds[] = prld.getPayrollRoleLevelIds(prnd.getPayrollRoleNameId("global_officer"));
		for(int x : levelIds) {
			System.out.println(x);
		}
	}
}
