package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.models.*;
import com.cust.hrms.dao.*;
import java.text.*;
import java.util.*;
import com.cust.hrms.email.*;
import com.cust.hrms.email.message.*;
import com.cust.hrms.notification.*;

@WebServlet("/editHrLeaveRecallBackWithPay")
public class EditHrLeaveRecallBackWithPayController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = request.getRequestDispatcher("hrEditPendingLeaveRecallBackWithPay.jsp");
		int updatedBy = 0;
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			updatedBy = (int)session.getAttribute("employeeId");
		}
		LeaveNotification ln = new LeaveNotification();
		DateDao dd = new DateDao();
		LeaveTypeDao ltd = new LeaveTypeDao();
		LeaveStatusDao lsd = new LeaveStatusDao();
		RoleDao rod = new RoleDao();
		EmployeeRoleDao erd = new EmployeeRoleDao();
		EmployeeDao ed = new EmployeeDao();
		HrmsEmail he = new HrmsEmail();
		LeaveDao ld = new LeaveDao();
		int leaveId = Integer.parseInt(request.getParameter("leaveId"));
		Leave l = ld.getLeaveById(leaveId);
		l.setUpdatedBy(updatedBy);
		LeaveType lt = ltd.getLeaveTypeById(l.getLeaveTypeId());
		int leaveStatusId = Integer.parseInt(request.getParameter("leaveStatusId"));
		LeaveStatus ls = lsd.getLeaveStatusById(leaveStatusId);
		l.setLeaveStatusId(leaveStatusId);
		String comment = request.getParameter("comment").trim();
		l.setComment(comment);
		//Email Information
		Employee emp = ed.getEmployeeById(l.getEmployeeId());
		String employeeFullName = emp.getFirstName()+" "+emp.getMiddleName()+" "+emp.getLastName();
		String employeeEmailAddress[] = { emp.getEmail() };
		Employee sup = ed.getEmployeeById(l.getSupervisorId());
		String supervisorFullName = sup.getFirstName()+" "+sup.getMiddleName()+" "+sup.getLastName();
		String supervisorEmailAddress[] = { sup.getEmail() };
		int hrAdminRoleId = rod.getRoleIdBycode("hr_admin");
		int hrAdminEmployeesId[] = erd.getRoleEmployeesId(hrAdminRoleId);
		String hrAdminEmailAddress[] = ed.getEmployeeEmailsArray(hrAdminEmployeesId);
		String leaveDays = String.valueOf(l.getNoOfDays());
		String startDate = dd.changeFormatDate(l.getStartDate());
		String endDate = dd.changeFormatDate(l.getEndDate());
		LeaveEmailMessage lem = new LeaveEmailMessage();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String today = sdf.format(date);
		String message = null;
		
		if(ls.getCode().equals("decline_leave_recall_back")) {
			if(comment.length() < 1) {
				session.setAttribute("error", "Please provide a comment...");
				rd.forward(request, response);
			}
			else {
				l.setLeaveStatusId(lsd.getLeaveStatusId("approved"));
				int count = ld.updateLeave(l);
				if(count >= 1) {
					/*Sent email notification 
					 * 1) Supervisor */
					if(he.isEmailEnable()) {
						//1) Supervisor
						/*Data values order {
						 * 1) Supervisor Name Initials =======================================================[0]
						 * 2) Number of Leave Days ===========================================================[1]
						 * 3) Leave Type =====================================================================[2]
						 * 4) Employee Full Name =============================================================[3]
						 * 5) Staff ID =======================================================================[4]
						 * 6) Start Date =====================================================================[5]
						 * 7) End Date =======================================================================[6]
						 * 8) Comment ========================================================================[7]
						 * 9) Leave Option [With_pay or without_pay] =========================================[8]
						 * }*/
						String supervisorData[] = {
								sup.getNameInitials(),
								leaveDays,
								lt.getName(),
								employeeFullName,
								emp.getStaffId(),
								startDate,
								endDate,
								l.getComment(),
								"with_pay"
						};
						lem.getSupervisorDelinedLeaveRecallBackRequestMessage(supervisorEmailAddress, supervisorData);
					}
					session.setAttribute("success", ln.getDeclinedLeaveRecallBackMessage(true));
					response.sendRedirect("hrLeaveRecallBackPendingApproval.jsp");
					/*session.setAttribute("pageTo", "hrLeaveRecallBackPendingApproval.jsp");
					rd = request.getRequestDispatcher("success");
					rd.forward(request, response);*/
				}
				else {
					session.setAttribute("error", ln.getDeclinedLeaveRecallBackMessage(false));
					rd.forward(request, response);
				}
			}
		}
		else {
			boolean isStartDateBeforeToday = dd.isStartDateValid(l.getStartDate());
			boolean isEndDateBeforeToday = dd.isStartDateValid(l.getEndDate());
			
			if(isStartDateBeforeToday == true) {
				int count = ld.updateLeave(l);
				if(count >= 1) {
					
					/*Send email notification
					 * 1) supervisor*/
					if(he.isEmailEnable()) {
						//1) Supervisor
						/*Data values order {
						 * 1) Supervisor Name Initials =============================================================[0]
						 * 2) Employee Full Name ===================================================================[1]
						 * 3) Staff ID =============================================================================[2]
						 * 4) Leave Option [with_pay or without_pay] ===============================================[3]
						 * }*/
						String supervisorData[] = {
								emp.getNameInitials(),
								employeeFullName,
								emp.getStaffId(),
								"with_pay"
						};
						lem.getSupervisorApprovedLeaveRecalledBackMessage(supervisorEmailAddress, supervisorData);
						
					}
					session.setAttribute("success", ln.getApprovedLeaveRecalledBackMessage(true));
					response.sendRedirect("hrLeaveRecallBackPendingApproval.jsp");
				}
				else {
					message = ln.getApprovedLeaveRecalledBackMessage(false);
					session.setAttribute("error", message);
					rd.forward(request, response);
				}
			}
			else if((isStartDateBeforeToday == false) && (isEndDateBeforeToday == true)) {
				int count = ld.updateLeave(l);
				if(count >= 1) {
					l.setLeaveId(0);
					l.setEndDate(today);
					l.setResumptionDate(dd.addDaysSkippingWeekends(today, 1));
					l.setNoOfDays(ld.getNoOfDays(l.getStartDate(), l.getEndDate()));
					l.setLeaveStatusId(lsd.getLeaveStatusId("approved"));
					count = ld.createLeave(l);
					if(count >= 1) {
						/*Sent Email Notification 
						 * 1) Employee
						 * 2) Supervisor */
						if(he.isEmailEnable()) {
							//1) Employee
							/* Data values order {
							 * 1) Employee Name Initials ==============================================================[0]
							 * 2) Supervisor Full Name ================================================================[1]
							 * 3) Supervisor Staff ID =================================================================[2]
							 * 4) Leave Type ==========================================================================[3]
							 * 5) Resumption Date =====================================================================[4]
							 * 6) Leave Options [with_pay or without_pay] =============================================[5]
							 * }*/
							String employeeData[] = {
									emp.getNameInitials(),
									supervisorFullName,
									sup.getStaffId(),
									lt.getName(),
									dd.changeFormatDate(l.getResumptionDate()),
									"with_pay"
							};
							lem.getEmployeeApprovedLeaveRecalledBackMessage(employeeEmailAddress, employeeData);
							
							//2)Supervisor
							/*Data values order {
							 * 1) Supervisor Name Initials =============================================================[0]
							 * 2) Employee Full Name ===================================================================[1]
							 * 3) Staff ID =============================================================================[2]
							 * 4) Leave Option [with_pay or without_pay] ===============================================[3]
							 * }*/
							String supervisorData[] = {
									sup.getNameInitials(),
									employeeFullName,
									emp.getStaffId(),
									"with_pay"
							};
							lem.getSupervisorApprovedLeaveRecalledBackMessage(supervisorEmailAddress, supervisorData);
						}
						session.setAttribute("success", ln.getApprovedLeaveRecalledBackMessage(true));
						response.sendRedirect("hrLeaveRecallBackPendingApproval.jsp");
					}
					else {
						session.setAttribute("error", ln.getApprovedLeaveRecalledBackMessage(false));
						rd.forward(request, response);
					}
				}
				else {
					message = ln.getApprovedLeaveRecalledBackMessage(false);
					session.setAttribute("error", message);
					rd.forward(request, response);
				}
			}
			else {
				session.setAttribute("error", ln.getApprovedLeaveRecalledBackMessage(false));
				rd.forward(request, response);
			}

		}
	}
	
}
