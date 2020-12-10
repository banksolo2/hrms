package com.cust.hrms.dao;

import java.sql.*;
import com.cust.hrms.models.*;

public class MonthDao {
  private DbConnection dbcon = new DbConnection();
  private Month m;
  private String query;
  private int count;
  private Statement stat;
  private PreparedStatement ps;
  private ResultSet rs;
  
  public Month getMonth(String no) {
	  m = new Month();
	  query = "select * from months where no = ?";
	  dbcon.getConnection();
	  try {
		  ps = dbcon.con.prepareStatement(query);
		  ps.setString(1, no.trim());
		  rs = ps.executeQuery();
		  if(rs.next()) {
			  m.setNo(rs.getString("no"));
			  m.setName(rs.getString("name"));
		  }
		  rs.close();
		  dbcon.con.close();
	  }
	  catch(SQLException ex) {
		  System.out.println(ex.fillInStackTrace());
	  }
	  return m;
  }
  
  public String getNo(String name) {
	  String result = "00";
	  query = "select no from months where lower(name) = ?";
	  dbcon.getConnection();
	  try {
		  ps = dbcon.con.prepareStatement(query);
		  ps.setString(1, name.trim().toLowerCase());
		  rs = ps.executeQuery();
		  if(rs.next()) {
			  result = rs.getString("no");
		  }
		  rs.close();
		  dbcon.con.close();
	  }
	  catch(SQLException ex) {
		  System.out.println(ex.fillInStackTrace());
	  }
	  return result;
  }
  
  public String getName(String no) {
	  String result = null;
	  query = "select name from months where no = ?";
	  dbcon.getConnection();
	  try {
		  ps = dbcon.con.prepareStatement(query);
		  ps.setString(1, no.trim());
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
  
  public ResultSet getMonthOption(String no) {
	  query = "select * from months where no != ?";
	  dbcon.getConnection();
	  try {
		  ps = dbcon.con.prepareStatement(query);
		  ps.setString(1, no.trim());
		  rs = ps.executeQuery();
	  }
	  catch(SQLException ex) {
		  System.out.println(ex.fillInStackTrace());
	  }
	  return rs;
  }
  
  public static void main(String args[]) {
	  MonthDao md = new MonthDao();
	  String result = md.getName("05");
	  System.out.println(result);
  }
}
