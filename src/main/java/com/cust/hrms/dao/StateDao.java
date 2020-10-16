package com.cust.hrms.dao;

import com.cust.hrms.models.*;
import java.sql.*;

public class StateDao {
	
	private Statement stat;
	private PreparedStatement ps;
	private int count;
	private String query;
	DbConnection dbcon = new DbConnection();
	private State state;
	private ResultSet rs;
	private boolean result;
	
	public int createState(State s) {
		state = s;
		query = "insert into states (name, created_by) values(?, ?)";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, state.getName());
			ps.setInt(2, state.getCreatedBy());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public boolean isStateNameExist(String name){
		query = "select count(*) as count_no from states where lower(name) = ?";
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
	
	public boolean isStateNameExistByAnother(State s) {
		state = s;
		query = "select count(*) as count_no from states where state_id != ? and lower(name) = ? ";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, state.getStateId());
			ps.setString(2, state.getName().toLowerCase());
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
	
	public State getState(String name) {
		state = new State();
		query = "select * from states where lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, name.toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()){
				state.setStateId(rs.getInt("state_id"));
				state.setName(rs.getString("name"));
				state.setCreatedAt(rs.getTimestamp("created_at").toString());
				state.setUpdatedAt(rs.getTimestamp("updated_at").toString());
				state.setCreatedBy(rs.getInt("created_by"));
				state.setUpdatedBy(rs.getInt("updated_by"));
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return state;
	}
	
	public int updateState(State s) {
		state = s;
		query = "update states set name = ?, updated_by = ? where state_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, state.getName());
			ps.setInt(2, state.getUpdatedBy());
			ps.setInt(3, state.getStateId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public int deleteState(State s) {
		state = s;
		query = "delete from states where state_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, state.getStateId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public ResultSet getAllState() {
		query= "select * from states order by name asc";
		dbcon.getConnection();
		try {
			stat = dbcon.con.createStatement();
			rs = stat.executeQuery(query);
		}
		catch(SQLException ex) {
			System.out.println(ex.getMessage());
		}
		
		return rs;
	}
	
	public ResultSet getAllStateOnUpdate(int stateId) {
		query = "select * from states where state_id != ? order by name asc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, stateId);
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return rs;
	}
	
	public String getStateName(int stateId) {
		String value = null;
		query = "select name from states where state_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, stateId);
			rs = ps.executeQuery();
			if(rs.next()) {
				value = rs.getString("name");
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return value;
	}
	
	public State getStateById(int stateId) {
		state = new State();
		query = "select * from states where state_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, stateId);
			rs = ps.executeQuery();
			if(rs.next()) {
				state.setStateId(rs.getInt("state_id"));
				state.setName(rs.getString("name"));
				state.setCreatedAt(rs.getTimestamp("created_at").toString());
				state.setUpdatedAt(rs.getTimestamp("updated_at").toString());
				state.setCreatedBy(rs.getInt("created_by"));
				state.setUpdatedBy(rs.getInt("updated_by"));
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return state;
	}
	
	public static void main(String args[]) {
		StateDao sd = new StateDao();
		State s = sd.getStateById(2);
		System.out.println(s.toString());
	}

}
