package com.cust.hrms.dao;

import java.sql.*;

public class DbConnection {
	Connection con;
	
	public void getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrms", "root", "");
			//System.out.println("Success");
		}
		catch(Exception ex) {
			System.out.println(ex.fillInStackTrace());
		}
	}
	
	/*public static void main(String args[]) {
		getConnection();
	}*/
}
