package com.cust.hrms.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.Calendar;

import com.cust.hrms.models.*;

public class LeavePlanDao {
	private DbConnection dbcon = new DbConnection();
	private LeavePlanStatusDao lpsd = new LeavePlanStatusDao();
	private EmployeeDao ed = new EmployeeDao();
	private LevelDao ld = new LevelDao();
	private LeavePlan lp;
	private int count;
	private String query;
	private Statement stat;
	private PreparedStatement ps;
	private ResultSet rs;
	private final String CURRENT_YEAR = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	
	public int createLeavePlan(LeavePlan lp) {
		query = "insert into leave_plans (employee_id, department_id, department_head_id, start_date, end_date, no_of_days, "
				+ "leave_plan_status_id, on_behalf, created_by) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		//lp.setNoOfDays(getNoOfDays(lp.getStartDate(), lp.getEndDate()));
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
	
	public boolean isLeavePlanExist(LeavePlan lp) {
		boolean result = false;
		Date startDate = Date.valueOf(lp.getStartDate());
		Date endDate = Date.valueOf(lp.getEndDate());
		query = "select count(*) as count_no from leave_plans where employee_id = ? and start_date between ? and ? or end_date between ? and ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, lp.getEmployeeId());
			ps.setDate(2, startDate);
			ps.setDate(3, endDate);
			ps.setDate(4, startDate);
			ps.setDate(5, endDate);
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
	
	public int getNoOfDays(String startDate, String endDate) {
		HolidayDao hd = new HolidayDao();
		DateDao dd = new DateDao();
		int noOfDays = dd.getNoOfDaysFromTwoDate(startDate, endDate);
		int holidayDays = hd.getHolidayDays(startDate, endDate);
		
		return (noOfDays - holidayDays);
	}
	
	public int getEmployeeLeavePlanDaysByStatus(int employeeId, int leavePlanStatusId, String year) {
		int result = 0;
		query = "select sum(no_of_days) as days from leave_plans where employee_id = ? and leave_plan_status_id = ? "
				+ " and start_date like ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			ps.setInt(2, leavePlanStatusId);
			ps.setString(3, "%"+year+"%");
			rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getInt("days");
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
	
		return result;
	}
	
	//use to get number of days of pending leave plan request
	public int getNoOfLeavePlanDaysPendingApproval(int employeeId, String year) {
		int result = 0;
		query = "select sum(no_of_days) as days from leave_plans where employee_id = ? and leave_plan_status_id not in (?,?,?,?) "
				+ "and start_date like ?";
		dbcon.getConnection();
		//System.out.println("Declined ID: "+lpsd.getLeavePlanStatusId("declined"));
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			ps.setInt(2, lpsd.getLeavePlanStatusId("drafted"));
			ps.setInt(3, lpsd.getLeavePlanStatusId("approved"));
			ps.setInt(4, lpsd.getLeavePlanStatusId("declined"));
			ps.setInt(5, lpsd.getLeavePlanStatusId("send_back_for_correction"));
			ps.setString(6, "%"+year+"%");
			rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getInt("days");
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return result;
	}
	
	//Check validate employee leave plan days
	public boolean isEmployeeLeavePlanRequestedDaysValid(int employeeId, String year, int days) {
		int employeeLevelId = ed.getEmployeeLevelId(employeeId);
		//System.out.println(employeeLevelId);
		int employeeLeaveDays = ld.getLevelLeaveDays(employeeLevelId);
		//System.out.println(employeeLeaveDays);
		int employeeApprovedLeavePlanDays = getEmployeeLeavePlanDaysByStatus(employeeId, lpsd.getLeavePlanStatusId("approved"), year);
		//System.out.println(employeeApprovedLeavePlanDays);
		int employeePendingLeavePlanDays = getNoOfLeavePlanDaysPendingApproval(employeeId, year);
		//System.out.println(employeePendingLeavePlanDays);
		int totalLeavePlanMade = days + employeeApprovedLeavePlanDays + employeePendingLeavePlanDays;
		System.out.println("Total leave plan made: "+ totalLeavePlanMade);
		
		return (totalLeavePlanMade <= employeeLeaveDays);
	}
	
	//get Leave plan details
	public LeavePlan getLeavePlan(int leavePlanId) {
		lp = new LeavePlan();
		query = "select * from leave_plans where leave_plan_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, leavePlanId);
			rs = ps.executeQuery();
			if(rs.next()) {
				lp.setLeavePlanId(rs.getInt("leave_plan_id"));
				lp.setEmployeeId(rs.getInt("employee_id"));
				lp.setDepartmentId(rs.getInt("department_id"));
				lp.setDepartmentHeadId(rs.getInt("department_head_id"));
				lp.setStartDate(rs.getDate("start_date").toString());
				lp.setEndDate(rs.getDate("end_date").toString());
				lp.setNoOfDays(rs.getInt("no_of_days"));
				lp.setLeavePlanStatusId(rs.getInt("leave_plan_status_id"));
				lp.setOnBehalf(rs.getString("on_behalf"));
				lp.setComment(rs.getString("comment"));
				lp.setCreatedBy(rs.getInt("created_by"));
				lp.setCreatedAt(rs.getTimestamp("created_at").toString());
				lp.setUpdatedBy(rs.getInt("updated_by"));
				lp.setUpdatedAt(rs.getTimestamp("updated_at").toString());
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return lp;
	}

	// Update leave plan 
	public int updateLeavePlan(LeavePlan lp) {
		query = "update leave_plans set start_date = ?, end_date = ?, no_of_days = ?, leave_plan_status_id = ?, comment = ?, updated_by = ? "
				+ "where employee_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setDate(1, Date.valueOf(lp.getStartDate()));
			ps.setDate(2, Date.valueOf(lp.getEndDate()));
			ps.setInt(3, lp.getNoOfDays());
			ps.setInt(4, lp.getLeavePlanStatusId());
			ps.setString(5, lp.getComment());
			ps.setInt(6, lp.getUpdatedBy());
			ps.setInt(7, lp.getEmployeeId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	//Delete leave plan 
	public int deleteLeavePlan(LeavePlan lp) {
		query = "delete from leave_plans where leave_plan_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, lp.getLeavePlanId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	//get employee approved leave plans Report
	public ResultSet getEmployeeApprovedLeavePlansReport(int employeeId) {
		query = "select * from leave_plans where employee_id = ? and leave_plan_status_id = ? and start_date like ? order by start_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			ps.setInt(2, lpsd.getLeavePlanStatusId("approved"));
			ps.setString(3, "%"+CURRENT_YEAR+"%");
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return rs;
	}
	
	//get employee pending leave plan report
	public ResultSet getEmployeePendingLeavePlansReport(int employeeId) {
		query = "select * from leave_plans where employee_id = ? and leave_plan_status_id = ? and start_date like ? order by start_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			ps.setInt(2, lpsd.getLeavePlanStatusId("send_for_approval"));
			//ps.setInt(3, lpsd.getLeavePlanStatusId("send_back_for_correction"));
			ps.setString(3, "%"+CURRENT_YEAR+"%");
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return rs;
	}
	
	//get employee drafted leave plan report
	public ResultSet getEmployeeDraftedLeavePlanReport(int employeeId) {
		query = "select * from leave_plans where employee_id = ? and leave_plan_status_id = ? and start_date like ? order by start_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			ps.setInt(2, lpsd.getLeavePlanStatusId("drafted"));
			ps.setString(3, "%"+CURRENT_YEAR+"%");
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return rs;
	}
	
	//get employee leave plan report send back for correction report
	public ResultSet getEmployeeLeavePlanSendBackForCorrectReport(int employeeId) {
		query = "select * from leave_plans where employee_id = ? and leave_plan_status_id = ? and start_date like ? order by start_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			ps.setInt(2, lpsd.getLeavePlanStatusId("send_back_for_correction"));
			ps.setString(3, "%"+CURRENT_YEAR+"%");
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return rs;
	}
	
	//Get employee leave plans history report
	public ResultSet getEmployeeLeavePlansHistoryReport(int employeeId) {
		query = "select * from leave_plans where employee_id = ? order by start_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	//get employee leave plans declined Report
	public ResultSet getEmployeeDeclinedLeavePlanReport(int employeeId) {
		query = "select * from leave_plans where employee_id = ? and leave_plan_status_id = ? and start_date like ? order by start_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			ps.setInt(2, lpsd.getLeavePlanStatusId("declined"));
			ps.setString(3, "%"+CURRENT_YEAR+"%");
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	//Get employee leave plans approved Report for department head
	public ResultSet getEmployeesApprovedLeavePlansReport(int departmentHeadId) {
		query = "select * from leave_plans where department_head_id = ? and leave_plan_status_id = ? and start_date like ? order by start_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, departmentHeadId);
			ps.setInt(2, lpsd.getLeavePlanStatusId("approved"));
			ps.setString(3, "%"+CURRENT_YEAR+"%");
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	//Get employee leave plans request Report for department Head
	public ResultSet getEmployeesLeavePlanSentForApproval(int departmentHeadId) {
		query = "select * from leave_plans where department_head_id = ? and leave_plan_status_id = ? and start_date like ? order by start_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, departmentHeadId);
			ps.setInt(2, lpsd.getLeavePlanStatusId("send_for_approval"));
			ps.setString(3, "%"+CURRENT_YEAR+"%");
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	//Get employees leave plans history Report for department head
	public ResultSet getEmployeesLeavePlanHistoryReport(int departmentHeadId) {
		query = "select * from leave_plans where department_head_id = ?  and leave_plan_status_id in (?, ?, ?) order by start_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, departmentHeadId);
			ps.setInt(2, lpsd.getLeavePlanStatusId("send_back_for_correction"));
			ps.setInt(3, lpsd.getLeavePlanStatusId("approved"));
			ps.setInt(4, lpsd.getLeavePlanStatusId("declined"));
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	//Get employees leave plans declined Report for department head
	public ResultSet getEmployeesLeavePlanDeclinedReport(int departmentHeadId) {
		query = "select * from leave_plans where department_head_id = ? and leave_plan_status_id = ? and start_date like ? order by start_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, departmentHeadId);
			ps.setInt(2, lpsd.getLeavePlanStatusId("declined"));
			ps.setString(3, "%"+CURRENT_YEAR+"%");
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	//Check if leave request is in with leave plan
	public boolean isLeaveRequestInLineWithLeavePlan(LeavePlan lp) {
		boolean result = false;
		query = "select count(*) as count_no from leave_plans where employee_id = ? and start_date = ? and end_date = ? "
				+ "and leave_plan_status_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, lp.getEmployeeId());
			ps.setDate(2, Date.valueOf(lp.getStartDate()));
			ps.setDate(3, Date.valueOf(lp.getEndDate()));
			ps.setInt(4, lpsd.getLeavePlanStatusId("approved"));
			rs = ps.executeQuery();
			if(rs.next()) count = rs.getInt("count_no");
			result = (count >= 1);
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return result;
	}
	
	public static void main(String args[]) {
		LeavePlanDao lpd = new LeavePlanDao();
		LeavePlan lp = new LeavePlan();
		lp.setEmployeeId(5);
		lp.setStartDate("2020-09-28");
		lp.setEndDate("2020-10-02");
		boolean result = lpd.isLeaveRequestInLineWithLeavePlan(lp);
		System.out.println(result);
	}
}
