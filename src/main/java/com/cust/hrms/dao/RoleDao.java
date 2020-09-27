package com.cust.hrms.dao;

import com.cust.hrms.models.*;
import java.sql.*;

public class RoleDao {
	private Statement stat;
	private PreparedStatement ps;
	private ResultSet rs;
	private int count;
	private String query;
	DbConnection dbcon = new DbConnection();
	private Role r;
	
	public int createRole(Role role) {
		r = role;
		query = "insert into roles (name,created_by) values(?, ?, ?)";
		r.setCode(r.getName().replace(" ", "_").toLowerCase());
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, r.getName());
			ps.setString(2, r.getCode());
			ps.setInt(3, r.getCreatedBy());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public boolean isRoleNameExist(String name) {
		boolean result = false;
		query = "select count(*) as count_no from roles where lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, name.toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				count = rs.getInt("count_no");
			}
			result = (count >= 1);
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return result;
	}
	
	public boolean isRoleNameExistByAnother(Role role) {
		boolean result = false;
		r = role;
		query = "select count(*) as count_no from roles where role_id != ? and lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, r.getRoleId());
			ps.setString(2, r.getName().toLowerCase());
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
	
	public Role getRole(String name) {
		r = new Role();
		query = "select * from roles where lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, name.toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				r.setRoleId(rs.getInt("role_id"));
				r.setName(rs.getString("name"));
				r.setCode(rs.getString("code"));
				r.setCreatedAt(rs.getTimestamp("created_at").toString());
				r.setUpdatedAt(rs.getTimestamp("updated_at").toString());
				r.setCreatedBy(rs.getInt("created_by"));
				r.setUpdatedBy(rs.getInt("updated_by"));
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return r;
	}
	
	
	public int updateRole(Role ro) {
		r = ro;
		query = "update roles set name = ?, updated_by = ?, code = ? where role_id = ?";
		r.setCode(r.getName().replace(" ", "_").toLowerCase());
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, r.getName());
			ps.setInt(2, r.getUpdatedBy());
			ps.setString(3, r.getCode());
			ps.setInt(4, r.getRoleId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public int deleteRole(Role ro) {
		r = ro;
		query = "delete from roles where role_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, r.getRoleId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public int getRoleId(String name) {
		int result = 0;
		query = "select role_id from roles where lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, name.toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getInt("role_id");	
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return result;
	}
	
	public String getRoleName(int roleId) {
		String result = null;
		query = "select name from roles where role_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, roleId);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				result = rs.getString("name");
			}
			
			//close database connection
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return result;
	}
	
	public ResultSet getAllRoles() {
		query = "select * from roles order by name asc";
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
	
	public ResultSet getAllRolesOnUpdate(int roleId) {
		query = "select * from roles where role_id != ? order by name asc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, roleId);
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return rs;
	}
	
	public Role getRoleById(int roleId) {
		r = new Role();
		query = "select * from roles where role_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, roleId);
			rs = ps.executeQuery();
			if(rs.next()) {
				r.setRoleId(rs.getInt("role_id"));
				r.setName(rs.getString("name"));
				r.setCode(rs.getString("code"));
				r.setCreatedAt(rs.getTimestamp("created_at").toString());
				r.setUpdatedAt(rs.getTimestamp("updated_at").toString());
				r.setCreatedBy(rs.getInt("created_by"));
				r.setUpdatedBy(rs.getInt("updated_by"));
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return r;
	}
	
	public Role getRoleByCode(String code) {
		r = new Role();
		query = "select * from roles where lower(code) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, code.toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				r.setRoleId(rs.getInt("role_id"));
				r.setName(rs.getString("name"));
				r.setCode(rs.getString("code"));
				r.setCreatedAt(rs.getTimestamp("created_at").toString());
				r.setUpdatedAt(rs.getTimestamp("updated_at").toString());
				r.setCreatedBy(rs.getInt("created_by"));
				r.setUpdatedBy(rs.getInt("updated_by"));
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return r;
	}
	
	public int getRoleIdBycode(String code) {
		int result = 0;
		query = "select role_id from roles where lower(code) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, code.toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) result = rs.getInt("role_id");
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return result;
	}
	
	public static void main(String args[]) {
		RoleDao rd = new RoleDao();
		Role r = new Role();
		String roleName = rd.getRoleName(5);
		System.out.println(roleName);
	}
		
}
