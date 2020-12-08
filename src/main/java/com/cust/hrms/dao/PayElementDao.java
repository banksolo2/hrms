package com.cust.hrms.dao;

import java.sql.*;
import com.cust.hrms.models.*;

public class PayElementDao {
	private DbConnection dbcon = new DbConnection();
	private String query;
	private int count;
	private Statement stat;
	private PreparedStatement ps;
	private PayElement pe;
	private ResultSet rs;
	
	public int createPayElement(PayElement pe) {
		pe.setCode(pe.getName().trim().toLowerCase().replace(" ", "_"));
		query = "insert into pay_elements (name, code, element_status_id, description, created_by, start_date, end_date) "
				+ "values(?, ?, ?, ?, ?, ?, ?)";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, pe.getName().trim());
			ps.setString(2, pe.getCode());
			ps.setInt(3, pe.getElementStatusId());
			ps.setString(4, pe.getDescription().trim());
			ps.setInt(5, pe.getCreatedBy());
			ps.setDate(6, Date.valueOf(pe.getStartDate()));
			ps.setDate(7, Date.valueOf(pe.getEndDate()));
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
		query = "select count(*) as count_no from pay_elements where lower(name) = ?";
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
	
	public PayElement getPayElementById(int payElementId) {
		pe = new PayElement();
		query = "select * from pay_elements where pay_element_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, payElementId);
			rs = ps.executeQuery();
			if(rs.next()) {
				pe.setPayElementId(rs.getInt("pay_element_id"));
				pe.setName(rs.getString("name"));
				pe.setCode(rs.getString("code"));
				pe.setElementStatusId(rs.getInt("element_status_id"));
				pe.setDescription(rs.getString("description"));
				pe.setCreatedBy(rs.getInt("created_by"));
				pe.setUpdatedBy(rs.getInt("updated_by"));
				pe.setCreatedAt(rs.getTimestamp("created_at").toString());
				pe.setUpdatedAt(rs.getTimestamp("updated_at").toString());
				pe.setStartDate(String.valueOf(rs.getDate("start_date")));
				pe.setEndDate(String.valueOf(rs.getDate("end_date")));
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return pe;
	}
	
	public PayElement getPayElementByCode(String code) {
		pe = new PayElement();
		query = "select * from pay_elements where lower(code) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, code.trim().toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				pe.setPayElementId(rs.getInt("pay_element_id"));
				pe.setName(rs.getString("name"));
				pe.setCode(rs.getString("code"));
				pe.setElementStatusId(rs.getInt("element_status_id"));
				pe.setDescription(rs.getString("description"));
				pe.setCreatedBy(rs.getInt("created_by"));
				pe.setUpdatedBy(rs.getInt("updated_by"));
				pe.setCreatedAt(rs.getTimestamp("created_at").toString());
				pe.setUpdatedAt(rs.getTimestamp("updated_at").toString());
				pe.setStartDate(String.valueOf(rs.getDate("start_date")));
				pe.setEndDate(String.valueOf(rs.getDate("end_date")));
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return pe;
	}
	
	public int updatePayElement(PayElement pe) {
		pe.setCode(pe.getName().trim().toLowerCase().replace(" ", "_"));
		query = "update pay_elements set name = ?, code = ?, element_status_id = ?, description = ?, updated_by = ?, "
				+ "start_date = ?, end_date = ? where pay_element_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, pe.getName().trim());
			ps.setString(2, pe.getCode());
			ps.setInt(3, pe.getElementStatusId());
			ps.setString(4, pe.getDescription().trim());
			ps.setInt(5, pe.getUpdatedBy());
			ps.setDate(6, Date.valueOf(pe.getStartDate()));
			ps.setDate(7, Date.valueOf(pe.getEndDate()));
			ps.setInt(8, pe.getPayElementId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public boolean isNameExistOnUpdate(PayElement pe) {
		boolean result = false;
		query = "select count(*) as count_no from pay_elements where pay_element_id != ? and lower(name) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, pe.getPayElementId());
			ps.setString(2, pe.getName().trim().toLowerCase());
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
	
	public int deletePayElement(PayElement pe) {
		query = "delete from pay_elements where pay_element_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, pe.getPayElementId());
			count = ps.executeUpdate();
			deleteLevelPayElementByPayElementId(pe.getPayElementId());
			deleteBoundaryByPayElementId(pe.getPayElementId());
			deleteEmployeePayElementByPayElementId(pe.getPayElementId());
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public ResultSet getAllPayElementReport() {
		query = "select * from pay_elements";
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
	
	public ResultSet getPayElementOptionOne(int payElementId) {
		query = "select * from pay_elements where pay_element_id != ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, payElementId);
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getPayElementOptionTwo(int payElementId, int elementStatusId) {
		DateDao dd = new DateDao();
		String todayDate = dd.getTodayDate();
		query = "select * from pay_elements where pay_element_id != ? and element_status_id = ? and"
				+ "? between start_date and end_date";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, payElementId);
			ps.setInt(2, elementStatusId);
			ps.setDate(3, Date.valueOf(todayDate));
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public int getPayElementId(String code) {
		int result = 0;
		query = "select pay_element_id from pay_elements where lower(code) = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, code.trim().toLowerCase());
			rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getInt("pay_element_id");
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return result;
	}
	
	public String getName(int payElementId) {
		String result = null;
		query = "select name from pay_elements where pay_element_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, payElementId);
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
	
	public String[] getPayElementNames(int payElementsId[]) {
		String result[] = new String[payElementsId.length];
		String quote = "";
		for(int i = 0; i < payElementsId.length; i++) {
			quote += payElementsId[i];
			if(i != (payElementsId.length - 1)) {
				quote +=",";
			}
		}
		query = "select name from pay_elements where pay_element_id in ("+quote+")";
		dbcon.getConnection();
		try {
			stat = dbcon.con.createStatement();
			rs = stat.executeQuery(query);
			int i = 0;
			while(rs.next()) {
				result[i] = rs.getString("name");
				i++;
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return result;
	}
	
	public int deleteLevelPayElementByPayElementId(int payElementId) {
		query = "delete from levels_pay_elements where pay_element_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, payElementId);
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public int deleteBoundaryByPayElementId(int payElementId) {
		query = "delete from boundaries where pay_element_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, payElementId);
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	public int deleteEmployeePayElementByPayElementId(int payElementId) {
		query = "delete from employee_pay_elements where pay_element_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, payElementId);
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public int getInvalidPayElementsIdCount() {
		DateDao dd = new DateDao();
		ElementStatusDao esd = new ElementStatusDao();
		query = "select count(*) as count_no from pay_elements where element_status_id = ? "
				+ "and ? not between start_date and end_date";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, esd.getElementStatusId("active"));
			ps.setDate(2, Date.valueOf(dd.getTodayDate()));
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
	
	public int[] getInvalidPayElementsId() {
		DateDao dd = new DateDao();
		ElementStatusDao esd = new ElementStatusDao();
		int length = getInvalidPayElementsIdCount();
		if(length >= 1) {
			int result[] = new int[length];
			query = "select pay_element_id from pay_elements where element_status_id = ? "
					+ "and ? not between start_date and end_date";
			dbcon.getConnection();
			try {
				ps = dbcon.con.prepareStatement(query);
				ps.setInt(1, esd.getElementStatusId("active"));
				ps.setDate(2, Date.valueOf(dd.getTodayDate()));
				rs = ps.executeQuery();
				int i = 0;
				while(rs.next()) {
					result[i] = rs.getInt("pay_element_id");
					i++;
				}
				dbcon.con.close();
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
	
	public static void main(String args[]) {
		PayElementDao ped = new PayElementDao();
		int result[] = ped.getInvalidPayElementsId();
		for(int x : result) {
			System.out.println(x);
		}
	}
}
