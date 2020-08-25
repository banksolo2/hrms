package com.cust.hrms.dao;

import java.sql.*;
import com.cust.hrms.models.*;

public class LeavePlanDao {
	private DbConnection dbcon = new DbConnection();
	private LeavePlan lp;
	private int count;
	private String query;
	private Statement stat;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public int createLeavePlan(LeavePlan lp) {
		query = "insert into leave_plans (employee_id, department_id, department_head_id, start_date, end_date, no_of_days, "
				+ "leave_plan_status_id, on_behalf, created_by) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, lp.getEmployeeId());
			ps.setInt(2, lp.getDepartmentId());
			ps.setInt(3, lp.getDepartmentHeadId());
			ps.setDate(4, Date.valueOf(lp.getStartDate()));
			ps.setDate(5, Date.valueOf(lp.getEndDate()));
			ps.setInt(6, lp.getNoOfDays());
			ps.setInt(7, lp.getLeavePlanStatusId());
			ps.setString(8, lp.getOnBehalf());
			ps.setInt(9, lp.getCreatedBy());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	
	
	public static void main(String args[]) {
		LeavePlanDao lpd = new LeavePlanDao();
		DepartmentHeadDao dhd = new DepartmentHeadDao();
		LeavePlanStatusDao lpsd = new LeavePlanStatusDao();
		LeavePlan lp = new LeavePlan();
		lp.setEmployeeId(4);
		lp.setDepartmentId(3);
		lp.setDepartmentHeadId(dhd.getDepartmentHeadId(3));
		lp.setStartDate("2020-08-21");
		lp.setEndDate("2020-08-28");
		lp.setNoOfDays(6);
		lp.setLeavePlanStatusId(lpsd.getLeavePlanStatusId("send_for_approval"));
		lp.setOnBehalf("no");
		lp.setComment("Please approve leave plan");
		lp.setCreatedBy(4);
		
		int count = lpd.createLeavePlan(lp);
		System.out.println(count);
	}
}
