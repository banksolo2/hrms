package com.cust.hrms.controllers;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.email.*;
import com.cust.hrms.email.message.*;
import com.cust.hrms.notification.*;

@WebServlet("/editHrLeaveRecallBackWithoutPay")
public class EditHrLeaveRecallBackWithoutPayController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		int updatedBy = 0;
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			updatedBy = (int)session.getAttribute("employeeId");
			
		}
		RequestDispatcher rd = request.getRequestDispatcher("hrEditPendingLeaveRecallBackWithoutPay.jsp");
		int leaveId = Integer.parseInt(request.getParameter("leaveId"));
		HrmsEmail he = new HrmsEmail();
		LeaveEmailMessage lem = new LeaveEmailMessage();
		LeaveDao ld = new LeaveDao();
		Leave l = ld.getLeaveById(leaveId);
		LeaveStatusDao lsd = new LeaveStatusDao();
		LeaveTypeDao ltd = new LeaveTypeDao();
		LeaveType lt = ltd.getLeaveTypeById(l.getLeaveTypeId());
		LeaveNotification ln = new LeaveNotification();
		DateDao dd = new DateDao();
		l.setUpdatedBy(updatedBy);
		int leaveStatusId = Integer.parseInt(request.getParameter("leaveStatusId"));
		LeaveStatus ls = lsd.getLeaveStatusById(leaveStatusId);
		String comment = request.getParameter("comment");
		int commentLength = comment.trim().length();
		if(ls.getCode().equals("decline_leave_recall_back") && (commentLength  < 1)) {
			session.setAttribute("error", "Please provide a comment......");
			rd.forward(request, response);
		}
		else {
			l.setComment(comment);
			NotificationMessageDao nmd = new NotificationMessageDao();
			EmployeeDao ed = new EmployeeDao();
			Employee emp = ed.getEmployeeById(l.getEmployeeId());
			Employee sup = ed.getEmployeeById(l.getSupervisorId());
			Employee upd = ed.getEmployeeById(updatedBy);
			RoleDao rod = new RoleDao();
			EmployeeRoleDao erd = new EmployeeRoleDao();
			int hrRoleId = rod.getRoleIdBycode("hr_admin");
			String message = null;
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String today = sdf.format(date);
			int count = 0;
			if(ls.getCode().equals("decline_leave_recall_back")) {
				l.setLeaveStatusId(lsd.getLeaveStatusId("approved"));
				count = ld.updateLeave(l);
				if(count >= 1) {
					message = ln.getDeclinedLeaveRecallBackMessage(true);
					/*send email notification
					 * 1) Supervisor*/
					if(he.isEmailEnable()) {
						//1) Supervisor
						String supervisorEmailAddress[] = { sup.getEmail() };
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
								String.valueOf(l.getNoOfDays()),
								lt.getName(),
								emp.getFirstName()+" "+emp.getMiddleName()+" "+emp.getLastName(),
								emp.getStaffId(),
								dd.changeFormatDate(l.getStartDate()),
								dd.changeFormatDate(l.getEndDate()),
								l.getComment(),
								"without_pay"
						};
						lem.getSupervisorDelinedLeaveRecallBackRequestMessage(supervisorEmailAddress, supervisorData);
					}
					session.setAttribute("success", message);
					session.setAttribute("pageTo", "hrLeaveRecallBackPendingApprovalWithoutPay.jsp");
					rd = request.getRequestDispatcher("success");
					rd.forward(request, response);
					
				}
				else {
					message = ln.getDeclinedLeaveRecallBackMessage(false);
					session.setAttribute("error", message);
					rd.forward(request, response);
				}
			}
			else {
				boolean isStartDateBeforeToday = dd.isStartDateValid(l.getStartDate());
				boolean isEndDateBeforeToday = dd.isStartDateValid(l.getEndDate());
				
				if(isStartDateBeforeToday == true) {
					l.setLeaveStatusId(leaveStatusId);
					count = ld.updateLeave(l);
					if(count >= 1) {
						message = ln.getApprovedLeaveRecalledBackMessage(true);
						/*Send Email Notification
						 * 1) Employee
						 * 2) Supervisor*/
						if(he.isEmailEnable()) {
							String newResumptionDate = dd.addDaysSkippingWeekends(today, 1);
							//1) Employee
							String employeeEmailAddress[] = { emp.getEmail() };
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
									sup.getFirstName()+" "+sup.getMiddleName()+" "+sup.getLastName(),
									sup.getStaffId(),
									lt.getName(),
									dd.changeFormatDate(newResumptionDate),
									"without_pay"
							};
							lem.getEmployeeApprovedLeaveRecalledBackMessage(employeeEmailAddress, employeeData);
							
							//2) Supervisor
							String supervisorEmailAddress[] = { sup.getEmail() };
							/*Data values order {
							 * 1) Supervisor Name Initials =============================================================[0]
							 * 2) Employee Full Name ===================================================================[1]
							 * 3) Staff ID =============================================================================[2]
							 * 4) Leave Option [with_pay or without_pay] ===============================================[3]
							 * }*/
							String supervisorData[] = {
									sup.getNameInitials(),
									emp.getFirstName()+" "+emp.getMiddleName()+" "+emp.getLastName(),
									emp.getStaffId(),
									"without_pay"
							};
							lem.getSupervisorApprovedLeaveRecalledBackMessage(supervisorEmailAddress, supervisorData);
							
						}
						session.setAttribute("success", message);
						session.setAttribute("pageTo", "hrLeaveRecallBackPendingApprovalWithoutPay.jsp");
						rd = request.getRequestDispatcher("success");
						rd.forward(request, response);
					}
					else {
						message = ln.getApprovedLeaveRecalledBackMessage(false);
						session.setAttribute("error", message);
						rd.forward(request, response);
					}
				}
				else if((isStartDateBeforeToday == false) && (isEndDateBeforeToday == true)) {
					l.setLeaveStatusId(leaveStatusId);
					count = ld.updateLeave(l);
					if(count >= 1) {
						l.setLeaveId(0);
						l.setEndDate(today);
						l.setResumptionDate(dd.addDaysSkippingWeekends(today, 1));
						l.setNoOfDays(ld.getNoOfDays(l.getStartDate(), l.getEndDate()));
						l.setLeaveStatusId(lsd.getLeaveStatusId("approved"));
						count = ld.createLeave(l);
						if(count >= 1) {
							//message = nmd.getApprovedLeaveRecallBackMessage("success");
							message = ln.getApprovedLeaveRecalledBackMessage(true);
							/*Sent Email Notification 
							 * 1) Employee
							 * 2) Supervisor*/
							if(he.isEmailEnable()) {
								//1) Employee
								String employeeEmailAddress[] = { emp.getEmail() };
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
										sup.getFirstName()+" "+sup.getMiddleName()+" "+ sup.getLastName(),
										sup.getStaffId(),
										lt.getName(),
										l.getResumptionDate(),
										"without_pay"
								};
								lem.getEmployeeApprovedLeaveRecalledBackMessage(employeeEmailAddress, employeeData);
								
								//2) Supervisor
								String supervisorEmailAddress[] = { sup.getEmail() };
								/*Data values order {
								 * 1) Supervisor Name Initials =============================================================[0]
								 * 2) Employee Full Name ===================================================================[1]
								 * 3) Staff ID =============================================================================[2]
								 * 4) Leave Option [with_pay or without_pay] ===============================================[3]
								 * }*/
								String supervisorData[] = {
									sup.getNameInitials(),
									emp.getFirstName()+" "+emp.getMiddleName()+" "+emp.getLastName(),
									emp.getStaffId(),
									"without_pay"
								};
								lem.getSupervisorApprovedLeaveRecalledBackMessage(supervisorEmailAddress, supervisorData);
							}
							
							session.setAttribute("success", message);
							session.setAttribute("pageTo", "hrLeaveRecallBackPendingApprovalWithoutPay.jsp");
							rd = request.getRequestDispatcher("success");
							rd.forward(request, response);
						}
						else {
							//message = nmd.getApprovedLeaveRecallBackMessage("error");
							message = ln.getApprovedLeaveRecalledBackMessage(false);
							session.setAttribute("error", message);
							rd.forward(request, response);
						}
					}
					else {
						//message = nmd.getApprovedLeaveRecallBackMessage("error");
						message = ln.getApprovedLeaveRecalledBackMessage(false);
						session.setAttribute("error", message);
						rd.forward(request, response);
					}
				}
				else {
					//message = nmd.getApprovedLeaveRecallBackMessage("error");
					message = ln.getApprovedLeaveRecalledBackMessage(false);
					session.setAttribute("error", message);
					rd.forward(request, response);
				}
			}
		}
	}
}
