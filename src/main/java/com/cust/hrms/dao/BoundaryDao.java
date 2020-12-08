package com.cust.hrms.dao;

import java.sql.*;
import com.cust.hrms.models.*;

public class BoundaryDao {
	private DbConnection dbcon = new DbConnection();
	private EmployeePayElementDao eped = new EmployeePayElementDao();
	private EmployeeDao ed = new EmployeeDao();
	private int count;
	private String query;
	private Statement stat;
	private PreparedStatement ps;
	private ResultSet rs;
	private Boundary b;
	
	public int createBoundary(Boundary b) {
		query = "insert into boundaries (level_pay_element_id, level_id, pay_element_id, highest_amount, lowest_amount, "
				+ "default_amount, created_by) values(?, ?, ?, ?, ?, ?, ?)";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, b.getLevelPayElementId());
			ps.setInt(2, b.getLevelId());
			ps.setInt(3, b.getPayElementId());
			ps.setDouble(4, b.getHighestAmount());
			ps.setDouble(5, b.getLowestAmount());
			ps.setDouble(6, b.getDefaultAmount());
			ps.setInt(7, b.getCreatedBy());
			count = ps.executeUpdate();
			Boundary bo = getBoundaryByLevelPayElementId(b.getLevelPayElementId());
			int employeesId[] = ed.getLevelEmployeesId(b.getLevelId());
			for(int x : employeesId) {
				if(x != 0) {
					EmployeePayElement epe = new EmployeePayElement();
					epe.setEmployeeId(x);
					epe.setLevelId(b.getLevelId());
					epe.setPayElementId(b.getPayElementId());
					epe.setBoundaryId(bo.getBoundaryId());
					epe.setAmount(b.getDefaultAmount());
					epe.setCreatedBy(b.getCreatedBy());
					//Check if employee pay element already exist
					boolean isEmployeePayElementExist = eped.isEmployeePayElementExist(epe);
					if(isEmployeePayElementExist == false) {
						eped.createEmployeePayElement(epe);
					}
				}
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public boolean isLowestAmountValid(double lowestAmount, double highestAmount) {
		return (highestAmount > lowestAmount);
	}
	
	public boolean isDefaultAmountValid(double defaultAmount, double lowestAmount, double highestAmount) {
		return (defaultAmount >= lowestAmount && defaultAmount <= highestAmount);
	}
	
	public Boundary getBoundaryById(int boundaryId) {
		b = new Boundary();
		query = "select * from boundaries where boundary_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, boundaryId);
			rs = ps.executeQuery();
			if(rs.next()) {
				b.setBoundaryId(rs.getInt("boundary_id"));
				b.setLevelPayElementId(rs.getInt("level_pay_element_id"));
				b.setLevelId(rs.getInt("level_id"));
				b.setPayElementId(rs.getInt("pay_element_id"));
				b.setHighestAmount(rs.getDouble("highest_amount"));
				b.setLowestAmount(rs.getDouble("lowest_amount"));
				b.setDefaultAmount(rs.getDouble("default_amount"));
				b.setCreatedBy(rs.getInt("created_by"));
				b.setUpdatedBy(rs.getInt("updated_by"));
				b.setCreatedAt(rs.getTimestamp("created_at").toString());
				b.setUpdatedAt(rs.getTimestamp("updated_at").toString());
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return b;
	}
	
	public Boundary getBoundaryByLevelPayElementId(int levelPayElementId) {
		b = new Boundary();
		query = "select * from boundaries where level_pay_element_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, levelPayElementId);
			rs = ps.executeQuery();
			if(rs.next()) {
				b.setBoundaryId(rs.getInt("boundary_id"));
				b.setLevelPayElementId(rs.getInt("level_pay_element_id"));
				b.setLevelId(rs.getInt("level_id"));
				b.setPayElementId(rs.getInt("pay_element_id"));
				b.setHighestAmount(rs.getDouble("highest_amount"));
				b.setLowestAmount(rs.getDouble("lowest_amount"));
				b.setDefaultAmount(rs.getDouble("default_amount"));
				b.setCreatedBy(rs.getInt("created_by"));
				b.setUpdatedBy(rs.getInt("updated_by"));
				b.setCreatedAt(rs.getTimestamp("created_at").toString());
				b.setUpdatedAt(rs.getTimestamp("updated_at").toString());
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return b;
	}
	
	public int updateBoundary(Boundary b) {
		query = "update boundaries set highest_amount = ?, lowest_amount =  ?, default_amount = ?, updated_by = ? "
				+ "where boundary_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setDouble(1, b.getHighestAmount());
			ps.setDouble(2, b.getLowestAmount());
			ps.setDouble(3, b.getDefaultAmount());
			ps.setInt(4, b.getUpdatedBy());
			ps.setInt(5, b.getBoundaryId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public int deleteBoundary(Boundary b) {
		query = "delete from boundaries where boundary_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, b.getBoundaryId());
			count = ps.executeUpdate();
			deleteEmployeePayElement(b.getBoundaryId());
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public int getBoundaryCount() {
		query = "select count(*) as count_no from boundaries";
		dbcon.getConnection();
		try {
			stat = dbcon.con.createStatement();
			rs = stat.executeQuery(query);
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
	
	public int[] getCreatedLevelPayElementsId() {
		int length = getBoundaryCount();
		if(length >= 1) {
			int result[] = new int[length];
			query = "select level_pay_element_id from boundaries";
			dbcon.getConnection();
			try {
				stat = dbcon.con.createStatement();
				rs = stat.executeQuery(query);
				int i = 0;
				while(rs.next()) {
					result[i] = rs.getInt("level_pay_element_id");
					i++;
				}
			}
			catch(SQLException ex) {
				System.out.println(ex.fillInStackTrace());
			}
			return result;
		}
		else {
			int result[] = {0};
			return result;
		}
	
	}
	 
	public ResultSet getUncreatedBoundaryReport(int levelPayElementsId[]) {
		String placeHolder = "";
		for(int i = 0; i < levelPayElementsId.length; i++) {
			placeHolder += "?";
			if(i != (levelPayElementsId.length - 1)) {
				placeHolder = ",";
			}
		}
		query = "select * from levels_pay_elements";
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
	
	public ResultSet getAllBoundariesReport() {
		PayElementDao ped = new PayElementDao();
		int payElementsId[] = ped.getInvalidPayElementsId();
		String quote = "";
		for(int i = 0; i < payElementsId.length; i++) {
			quote += payElementsId[i];
			if(i != (payElementsId.length - 1)) {
				quote += ",";
			}
		}
		query = "select * from boundaries where pay_element_id not in ("+quote+")";
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
	
	public boolean isAmountValid(String amount) {
		boolean result = false;
		try {
			Double.parseDouble(amount);
			result = true;
		}
		catch(Exception ex) {
			result = false;
		}
		return result;
	}
	
	public boolean isBoundaryExist(int levelPayElementId) {
		boolean result = false;
		query = "select count(*) as count_no from boundaries where level_pay_element_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, levelPayElementId);
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
	
	public int deleteEmployeePayElement(int boundaryId) {
		query = "delete from employee_pay_elements where boundary_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, boundaryId);
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public int getBoundaryCountByLevelId(int levelId) {
		query = "select count(*) as count_no from boundaries where level_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, levelId);
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
	
	public int[] getBoundaryIdByLevelId(int levelId) {
		int length = getBoundaryCountByLevelId(levelId);
		int result[] = new int[length];
		query = "select boundary_id from boundaries where level_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, levelId);
			rs = ps.executeQuery();
			int i = 0;
			while(rs.next()) {
				result[i] = rs.getInt("boundary_id");
				i++;
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return result;
	}
	

	
	public static void main(String args[]) {
		BoundaryDao bd = new BoundaryDao();
		int result[] = bd.getBoundaryIdByLevelId(6);
		for(int x : result) {
			System.out.println(x);
		}
	}
}
