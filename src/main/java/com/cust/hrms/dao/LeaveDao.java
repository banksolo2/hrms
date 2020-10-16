package com.cust.hrms.dao;

import java.sql.*;
import java.util.Calendar;

import com.cust.hrms.models.*;

public class LeaveDao {
	private DbConnection dbcon = new DbConnection();
	private EmployeeDao ed = new EmployeeDao();
	private LevelDao ld = new LevelDao();
	private DateDao dd = new DateDao();
	private LeaveTypeDao ltd = new LeaveTypeDao();
	private LeaveStatusDao lsd = new LeaveStatusDao();
	private Leave l;
	private String query;
	private int count;
	private Statement stat;
	private PreparedStatement ps;
	private ResultSet rs;
	private final String CURRENT_YEAR = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	
	public int createLeave(Leave l) {
		query = "insert into leaves (employee_id, department_id, supervisor_id, leave_type_id, start_date, end_date,"
				+ " resumption_date, no_of_days, primary_relief_office_id, secondary_relief_office_id, staff_to_notify,"
				+ " inline_with_leave_plan, leave_status_id, created_by, with_pay) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
			ps.setString(15, l.getWithPay());
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
		query = "select count(*) as count_no from leaves where employee_id = ? and "
				+ " leave_status_id != ?"
				+ "and start_date between ? and ? or"
				+ " end_date between ? and ? ";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, l.getEmployeeId());
			ps.setInt(2, lsd.getLeaveStatusId("declined"));
			ps.setDate(3, startDate);
			ps.setDate(4, endDate);
			ps.setDate(5, startDate);
			ps.setDate(6, endDate);
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
				l.setWithPay(rs.getString("with_pay"));
			}
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return l;
	}
	
	public int updateLeave(Leave l) {
		query = "update leaves set supervisor_id = ?, leave_type_id = ?, start_date = ?, end_date = ?, resumption_date = ?, "
				+ "no_of_days = ?, primary_relief_office_id = ?, secondary_relief_office_id = ?, staff_to_notify = ?, "
				+ "comment = ?, inline_with_leave_plan = ?, leave_status_id = ?, updated_by = ? "
				+ "where leave_id = ?";
		Date startDate = Date.valueOf(l.getStartDate());
		Date endDate = Date.valueOf(l.getEndDate());
		Date resumptionDate = Date.valueOf(l.getResumptionDate());
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, l.getSupervisorId());
			ps.setInt(2, l.getLeaveTypeId());
			ps.setDate(3, startDate);
			ps.setDate(4, endDate);
			ps.setDate(5, resumptionDate);
			ps.setInt(6, l.getNoOfDays());
			ps.setInt(7, l.getPrimaryReliefOfficeId());
			ps.setInt(8, l.getSecondaryReliefOfficeId());
			ps.setString(9, l.getStaffToNotify());
			ps.setString(10, l.getComment());
			ps.setString(11, l.getInlineWithLeavePlan());
			ps.setInt(12, l.getLeaveStatusId());
			ps.setInt(13, l.getUpdatedBy());
			ps.setInt(14, l.getLeaveId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return count;
	}
	
	public boolean isStartAndEndDateExistOnUpdate(Leave l) {
		boolean result = false;
		query = "select count(*) as count_no from leaves where leave_id != ? and employee_id = ? and "
				+ "(start_date between ? and ? or end_date between ? and ?)";
		Date startDate = Date.valueOf(l.getStartDate());
		Date endDate = Date.valueOf(l.getEndDate());
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, l.getLeaveId());
			ps.setInt(2, l.getEmployeeId());
			ps.setDate(3, startDate);
			ps.setDate(4, endDate);
			ps.setDate(5, startDate);
			ps.setDate(6, endDate);
			rs = ps.executeQuery();
			if(rs.next()) {
				count = rs.getInt("count_no");
			}
			result = (count >= 1);
			//System.out.println(count);
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		
		return result;
	}
	
	public int getEmployeeLeaveDays(int employeeId, int leaveTypeId, String withPay) {
		int result = 0;
		if(leaveTypeId == ltd.getLeaveTypeId("annual")) {
			if(withPay.toLowerCase().equals("yes")) {
				int levelId = ed.getEmployeeLevelId(employeeId);
				result = ld.getLevelLeaveDays(levelId);
			}
			else {
				result = ltd.getLeaveTypeDays(leaveTypeId);
			}
		}
		else {
			result = ltd.getLeaveTypeDays(leaveTypeId);
		}
		
		return result;
	}
	
	public int getEmployeeApprovedLeaveTypeDays(int employeeId, int leaveTypeId, String withPay, String year) {
		int result = 0;
		query = "select sum(no_of_days) as days from leaves where employee_id = ? and leave_type_id = ? and lower(with_pay) = ? and "
				+ "leave_status_id in (?, ?) and start_date like ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			ps.setInt(2, leaveTypeId);
			ps.setString(3, withPay.toLowerCase());
			ps.setInt(4, lsd.getLeaveStatusId("approved"));
			ps.setInt(5, lsd.getLeaveStatusId("initiate_recall_back"));
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
	
	public int getEmployeePendingLeaveTypeDays(int employeeId, int leaveTypeId, String withPay, String year) {
		int result = 0;
		query = "select sum(no_of_days) as days from leaves where employee_id = ? and leave_type_id = ? and lower(with_pay) = ? and "
				+ "leave_status_id in (?, ?, ?) and start_date like ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, employeeId);
			ps.setInt(2, leaveTypeId);
			ps.setString(3, withPay.toLowerCase());
			ps.setInt(4, lsd.getLeaveStatusId("sent_to_supervisor_for_approval"));
			ps.setInt(5, lsd.getLeaveStatusId("sent_to_hr_for_approval"));
			ps.setInt(6, lsd.getLeaveStatusId("sent_back_for_correction"));
			ps.setString(7, "%"+year+"%");
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
	
	public int deleteLeave(Leave l) {
		query = "delete from leaves where leave_id = ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, l.getLeaveId());
			count = ps.executeUpdate();
			dbcon.con.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return count;
	}
	
	public int getNoOfDays(String startDate, String endDate) {
		HolidayDao hd = new HolidayDao();
		DateDao dd = new DateDao();
		int noOfDays = dd.getNoOfDaysFromTwoDate(startDate, endDate);
		int holidayDays = hd.getHolidayDays(startDate, endDate);
		
		return (noOfDays - holidayDays);
	}
	
	public boolean isEmployeeLeavedaysValid(int employeeId, int leaveTypeId, String year, int days, String withPay) {
		boolean result = false;
		int leaveDays = getEmployeeLeaveDays(employeeId, leaveTypeId, withPay);
		int approvedLeaveDays = getEmployeeApprovedLeaveTypeDays(employeeId, leaveTypeId, withPay, year);
		int pendingLeaveDays = getEmployeePendingLeaveTypeDays(employeeId, leaveTypeId, withPay,year);
		int totalLeaveDays = approvedLeaveDays + pendingLeaveDays + days;
		result = (totalLeaveDays <= leaveDays);
		
		return result;
	}
	
	public ResultSet getEmployeeApprovedLeaveReport(int employeeId, String withPay) {
		query = "select * from leaves where lower(with_pay) = ? and employee_id = ? and leave_status_id = ? and start_date like ? order by start_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, withPay.toLowerCase());
			ps.setInt(2, employeeId);
			ps.setInt(3, lsd.getLeaveStatusId("approved"));
			ps.setString(4, "%"+CURRENT_YEAR+"%");
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getEmployeeDeclineLeaveReport(int employeeId, String withPay) {
		query = "select * from leaves where lower(with_pay) = ? and employee_id = ? and leave_status_id = ? and start_date like ? order by start_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, withPay.toLowerCase());
			ps.setInt(2, employeeId);
			ps.setInt(3, lsd.getLeaveStatusId("declined"));
			ps.setString(4, "%"+CURRENT_YEAR+"%");
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getEmployeePendingLeaveReport(int employeeId, String withPay) {
		query = "select * from leaves where lower(with_pay) = ? and employee_id = ? and leave_status_id in (?, ?) and start_date like ? order by start_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, withPay.toLowerCase());
			ps.setInt(2, employeeId);
			ps.setInt(3, lsd.getLeaveStatusId("sent_to_supervisor_for_approval"));
			ps.setInt(4, lsd.getLeaveStatusId("sent_to_hr_for_approval"));
			ps.setString(5, "%"+CURRENT_YEAR+"%");
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getEmployeeSentBackForCorrectionLeaveReport(int employeeId, String withPay) {
		query = "select * from leaves where lower(with_pay) = ? and employee_id = ? and leave_status_id = ? and start_date like ? order by start_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, withPay.toLowerCase());
			ps.setInt(2, employeeId);
			ps.setInt(3, lsd.getLeaveStatusId("sent_back_for_correction"));
			ps.setString(4, "%"+CURRENT_YEAR+"%");
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getEmployeeRecalledBackLeaveReport(int employeeId, String withPay) {
		query = "select * from leaves where lower(with_pay) = ? and employee_id = ? and leave_status_id = ? and start_date like ? order by start_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, withPay.toLowerCase());
			ps.setInt(2, employeeId);
			ps.setInt(3, lsd.getLeaveStatusId("approved_recalled_back"));
			ps.setString(4, "%"+CURRENT_YEAR+"%");
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getEmployeePendingRecallBackLeaveReport(int employeeId, String withPay) {
		query = "select * from leaves where lower(with_pay) = ? and employee_id = ? and leave_status_id = ? "
				+ "and start_date like ? order by start_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, withPay.toLowerCase());
			ps.setInt(2, employeeId);
			ps.setInt(3, lsd.getLeaveStatusId("initiate_recall_back"));
			ps.setString(4, "%"+CURRENT_YEAR+"%");
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getEmployeeHistoryLeaveReport(int employeeId, String withPay) {
		query = "select * from leaves where lower(with_pay) = ? and employee_id = ? order by start_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, withPay.toLowerCase());
			ps.setInt(2, employeeId);
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getEmployeeDraftedLeaveReport(int employeeId, String withPay) {
		query = "select * from leaves where lower(with_pay) = ? and employee_id = ? and leave_status_id = ? and start_date like ? order by start_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, withPay.toLowerCase());
			ps.setInt(2, employeeId);
			ps.setInt(3, lsd.getLeaveStatusId("drafted"));
			ps.setString(4, "%"+CURRENT_YEAR+"%");
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	
	public ResultSet getLeavePendingSupervisorApprovalReport(int supervisorId, String withPay) {
		query = "select * from leaves where lower(with_pay) = ? and supervisor_id = ? and leave_status_id = ? and start_date like ? order by start_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, withPay.toLowerCase());
			ps.setInt(2, supervisorId);
			ps.setInt(3, lsd.getLeaveStatusId("sent_to_supervisor_for_approval"));
			ps.setString(4, "%"+CURRENT_YEAR+"%");
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getSupervisorApprovedLeaveReport(int supervisorId, String withPay) {
		query = "select * from leaves where lower(with_pay) = ? and supervisor_id = ? and leave_status_id = ? and start_date like ? order by start_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, withPay.toLowerCase());
			ps.setInt(2, supervisorId);
			ps.setInt(3, lsd.getLeaveStatusId("approved"));
			ps.setString(4, "%"+CURRENT_YEAR+"%");
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getSupervisorPendingHrApprovalLeaveReport(int supervisorId, String withPay) {
		query = "select * from leaves where lower(with_pay) = ? and supervisor_id = ? and leave_status_id = ? and start_date like ? order by start_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, withPay.toLowerCase());
			ps.setInt(2, supervisorId);
			ps.setInt(3, lsd.getLeaveStatusId("sent_to_hr_for_approval"));
			ps.setString(4, "%"+CURRENT_YEAR+"%");
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getSupervisorDeclinedLeaveReport(int supervisorId, String withPay) {
		query = "select * from leaves where lower(with_pay) = ? and supervisor_id = ? and leave_status_id = ? and start_date like ? order by start_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, withPay.toLowerCase());
			ps.setInt(2, supervisorId);
			ps.setInt(3, lsd.getLeaveStatusId("declined"));
			ps.setString(4, "%"+CURRENT_YEAR+"%");
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getSupervisorSentBackForCorrectionLeaveReport(int supervisorId, String withPay) {
		query = "select * from leaves where lower(with_pay) = ? and supervisor_id = ? and leave_status_id = ? and start_date like ? order by start_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, withPay.toLowerCase());
			ps.setInt(2, supervisorId);
			ps.setInt(3, lsd.getLeaveStatusId("sent_back_for_correction"));
			ps.setString(4, "%"+CURRENT_YEAR+"%");
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getSupervisorLeaveHistoryReport(int supervisorId, String withPay) {
		query = "select * from leaves where lower(with_pay) = ? and supervisor_id = ? and leave_status_id != ? order by start_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, withPay.toLowerCase());
			ps.setInt(2, supervisorId);
			ps.setInt(3, lsd.getLeaveStatusId("drafted"));
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getSupervisorPendingLeaveRecallBackReport(int supervisorId, String withPay) {
		query = "select * from leaves where lower(with_pay) = ? and supervisor_id = ? and leave_Status_id = ? order by start_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, withPay.toLowerCase());
			ps.setInt(2, supervisorId);
			ps.setInt(3, lsd.getLeaveStatusId("initiate_recall_back"));
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getSupervisorLeaveRecallBackApprovedReport(int supervisorId, String withPay) {
		query = "select * from leaves where lower(with_pay) = ? and supervisor_id = ? and leave_status_id = ? order by start_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, withPay.toLowerCase());
			ps.setInt(2, supervisorId);
			ps.setInt(3, lsd.getLeaveStatusId("approved_recalled_back"));
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getHrPendingApprovalLeaveReport(String withPay) {
		query = "select * from leaves where lower(with_pay) = ? and leave_status_id = ? and start_date like ? order by start_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, withPay.toLowerCase());
			ps.setInt(2, lsd.getLeaveStatusId("sent_to_hr_for_approval"));
			ps.setString(3, "%"+CURRENT_YEAR+"%");
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	public ResultSet getHrApprovedLeaveReport(String withPay) {
		query = "select * from leaves where lower(with_pay) = ? and leave_status_id = ? and start_date like ? order by start_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, withPay.toLowerCase());
			ps.setInt(2, lsd.getLeaveStatusId("approved"));
			ps.setString(3, "%"+CURRENT_YEAR+"%");
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getHrDeclinedLeaveReport(String withPay) {
		query = "select * from leaves where lower(with_pay) = ? and leave_status_id = ? and start_date like ? order by start_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, withPay.toLowerCase());
			ps.setInt(2, lsd.getLeaveStatusId("declined"));
			ps.setString(3, "%"+CURRENT_YEAR+"%");
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getHrRecalledBackLeaveReport(String withPay) {
		query = "select * from leaves where lower(with_pay) = ? and leave_status_id = ? and start_date like ? order by start_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, withPay.toLowerCase());
			ps.setInt(2, lsd.getLeaveStatusId("initiate_recall_back"));
			ps.setString(3, "%"+CURRENT_YEAR+"%");
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getHrHistoryLeaveReport(String withPay) {
		query = "select * from leaves where lower(with_pay) = ? and leave_status_id != ? order by start_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, withPay.toLowerCase());
			ps.setInt(2, lsd.getLeaveStatusId("drafted"));
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public int getMonthLeaveDays(int employeeId, int leaveTypeId, String month, String year) {
		int result = 0;
		String mm = "00";
		if(month.toLowerCase().equals("january")) mm = "01";
		else if(month.toLowerCase().equals("february")) mm = "02";
		else if(month.toLowerCase().equals("march")) mm = "03";
		else if(month.toLowerCase().equals("april")) mm = "04";
		else if(month.toLowerCase().equals("may")) mm = "05";
		else if(month.toLowerCase().equals("june")) mm = "06";
		else if(month.toLowerCase().equals("july")) mm = "07";
		else if(month.toLowerCase().equals("august")) mm = "08";
		else if(month.toLowerCase().equals("september")) mm = "09";
		else if(month.toLowerCase().equals("october")) mm = "10";
		else if(month.toLowerCase().equals("november")) mm = "11";
		else if(month.toLowerCase().equals("december")) mm ="12";
		query = "select sum(no_of_days) from leaves where leave_type_id = ? and leave_status_id = ? and start_date like ?";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setInt(1, leaveTypeId);
			ps.setInt(2, lsd.getLeaveStatusId("approved"));
			ps.setString(3, year+"-"+mm+"%");
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return result;
	}
	
	public ResultSet getHrLeaveRequestSentBackForCorrectionReport(String withPay) {
		query = "select * from leaves where lower(with_pay) = ? and leave_status_id = ? and start_date like ? order by start_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, withPay.toLowerCase());
			ps.setInt(2, lsd.getLeaveStatusId("sent_back_for_correction"));
			ps.setString(3, "%"+CURRENT_YEAR+"%");
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	public ResultSet getHrApprovedLeaveRecallBackReport(String withPay) {
		query = "select * from leaves where lower(with_pay) = ? and leave_status_id = ? and start_date like ? "
				+ "order by start_date desc";
		dbcon.getConnection();
		try {
			ps = dbcon.con.prepareStatement(query);
			ps.setString(1, withPay.toLowerCase());
			ps.setInt(2, lsd.getLeaveStatusId("approved_recalled_back"));
			ps.setString(3, "%"+CURRENT_YEAR+"%");
			rs = ps.executeQuery();
		}
		catch(SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return rs;
	}
	
	
	public static void main(String args[]) {
		LeaveDao ld = new LeaveDao();
		int result = ld.getMonthLeaveDays(2, 2, "March", "2020");
		System.out.println(result);
	}
}
