package com.cust.hrms.dao;

import java.sql.*;
import com.cust.hrms.models.*;

public class LeaveDao {
	private DbConnection dbcon = new DbConnection();
	private DateDao dd = new DateDao();
	private LeaveTypeDao ltd = new LeaveTypeDao();
	private Leave l;
	private String query;
	private int count;
	private Statement stat;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public int createLeave(Leave l) {
		query = "insert into leaves (employee_id, department_id, supervisor_id, leave_type_id, start_date, end_date,"
				+ " resumption_date, no_of_days, primary_relief_office_id, secondary_relief_office_id, staff_to_notify,"
				+ " inline_with_leave_plan, leave_status_id, created_by) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, l.getEmployeeId());
			ps.setInt(2, l.getDepartmentId());
			ps.setInt(3, l.getSupervisorId());
			ps.setInt(4, l.getLeaveTypeId());
			ps.setDate(5, Date.valueOf(l.getStartDate()));
			ps.setDate(6, Date.valueOf(l.getEndDate()));
			ps.setDate(7, Date.valueOf(l.getResumptionDate()));
			ps.setInt(8, l.getNoOfDays());
			ps.setInt(9, l.getPrimaryReliefOfficeId());
			ps.setInt(10, l.getSecondaryReliefOfficeId());
			ps.setString(11, l.getStaffToNotify());
			ps.setString(12, l.getInlineWithLeavePlan());
			ps.setInt(13, l.getLeaveStatusId());
			ps.setInt(14, l.getCreatedBy());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public boolean isStartAndEndDateExist(Leave l){
		boolean result = false;
		Date startDate = Date.valueOf(l.getStartDate());
		Date endDate = Date.valueOf(l.getEndDate());
		query = "select count(*) as count_no from leaves where employee_id = ? and start_date between ? and ? or"
				+ " end_date between ? and ? ";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, l.getEmployeeId());
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
	
	public Leave getLeaveById(int leaveId) {
		l = new Leave();
		query = "select * from leaves where leave_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, leaveId);
			rs = ps.executeQuery();
			if(rs.next()) {
				l.setLeaveId(rs.getInt("leave_id"));
				l.setEmployeeId(rs.getInt("employee_id"));
				l.setDepartmentId(rs.getInt("department_id"));
				l.setSupervisorId(rs.getInt("supervisor_id"));
				l.setLeaveTypeId(rs.getInt("leave_type_id"));
				l.setStartDate(rs.getDate("start_date").toString());
				l.setEndDate(rs.getDate("end_date").toString());
				l.setResumptionDate(rs.getDate("resumption_date").toString());
				l.setNoOfDays(rs.getInt("no_of_days"));
				l.setPrimaryReliefOfficeId(rs.getInt("primary_relief_office_id"));
				l.setSecondaryReliefOfficeId(rs.getInt("secondary_relief_office_id"));
				l.setStaffToNotify(rs.getString("staff_to_notify"));
				l.setComment(rs.getString("comment"));
				l.setInlineWithLeavePlan(rs.getString("inline_with_leave_plan"));
				l.setLeaveStatusId(rs.getInt("leave_status_id"));
				l.setCreatedBy(rs.getInt("created_by"));
				l.setCreatedAt(rs.getTimestamp("created_at").toString());
				l.setUpdatedBy(rs.getInt("updated_by"));
				l.setUpdatedAt(rs.getTimestamp("updated_at").toString());
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return l;
	}
	
	public static void main(String args[]) {
		LeaveDao ld = new LeaveDao();
		Leave l = ld.getLeaveById(1);
		System.out.println(l);
	}
}
