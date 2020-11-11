package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;
import com.cust.hrms.email.*;
import com.cust.hrms.email.message.*;

@WebServlet("/editHrPendingLeaveRequestWithout")
public class EditHrPendingLeaveRequestWithoutController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		int updatedBy = 0;
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			updatedBy = (int)session.getAttribute("employeeId");
		}
		RequestDispatcher rd = request.getRequestDispatcher("hrSupervisorLeavePendingApprovalWithout.jsp");
		PrintWriter out = response.getWriter();
		HrmsEmail he = new HrmsEmail();
		EmployeeDao ed = new EmployeeDao();
		DateDao dd = new DateDao();
		LeaveTypeDao ltd = new LeaveTypeDao();
		LeaveStatusDao lsd = new LeaveStatusDao();
		RoleDao rod = new RoleDao();
		EmployeeRoleDao erd = new EmployeeRoleDao();
		LeaveNotification ln = new LeaveNotification();
		LeaveDao ld = new LeaveDao();
		int leaveId = Integer.parseInt(request.getParameter("leaveId"));
		Leave l = ld.getLeaveById(leaveId);
		l.setUpdatedBy(updatedBy);
		LeaveType lt = ltd.getLeaveTypeById(l.getLeaveTypeId());
		int leaveStatusId = Integer.parseInt(request.getParameter("leaveStatusId"));
		LeaveStatus ls = lsd.getLeaveStatusById(leaveStatusId);
		String comment = request.getParameter("comment");
		l.setLeaveStatusId(leaveStatusId);
		int commentLength = comment.trim().length();
		if((ls.getCode().equals("declined") || ls.getCode().equals("sent_back_for_correction")) && (commentLength < 1)) {
			session.setAttribute("error", "Please provide a comment.....");
			rd.forward(request, response);
		}
		else {
			l.setComment(comment);
			String message = null;
			//Update leave in database
			int count = ld.updateLeave(l);
			if(count >= 1) {
				Employee emp = ed.getEmployeeById(l.getEmployeeId());
				LeaveEmailMessage lem = new LeaveEmailMessage();
				Employee sup = ed.getEmployeeById(l.getSupervisorId());
				String employeeFullName = emp.getFirstName()+" "+emp.getMiddleName()+" "+emp.getLastName();
				String supervisorFullName = sup.getFirstName()+" "+sup.getMiddleName()+" "+sup.getLastName();
				int hrRoleId = rod.getRoleIdBycode("hr_admin");
				int hrEmployeesId[] = erd.getRoleEmployeesId(hrRoleId);
				String hrEmailAddress[] = ed.getEmployeeEmailsArray(hrEmployeesId);
				if(ls.getCode().equals("declined")) {
					message = ln.getDeclinedLeaveRequestMessage(true);
					/* Sent Email Notification 
					 * 1) Employee
					 * 2) Supervisor
					 * 3) HR*/
					if(he.isEmailEnable()) {
						//1) Employee
						String employeeEmailAddress[] = { emp.getEmail() };
						/*Data values order {
						 * 1) Employee Name Initials =======================================[0]
						 * 2) No of leave days =============================================[1]
						 * 3) Leave type ===================================================[2]
						 * 4) Start Date ===================================================[3]
						 * 5) End Date =====================================================[4]
						 * 6) Declined By Name =============================================[5]
						 * 7) Declined By Staff ID =========================================[6]
						 * 8) Comment ======================================================[7]
						 * 9) Leave Option [with_pay or without_pay] =======================[8]
						 * }*/
						String employeeData[] = {
								emp.getNameInitials(),
								String.valueOf(l.getNoOfDays()),
								lt.getName(),
								dd.changeFormatDate(l.getStartDate()),
								dd.changeFormatDate(l.getEndDate()),
								"hr",
								"",
								l.getComment(),
								"without_pay"
						};
						lem.getEmployeeDeclinedLeaveRequestMessage(employeeEmailAddress, employeeData);
						
						//2) Supervisor
						String supervisorEmailAddress[] = { sup.getEmail() };
						/*Data values order{
						 * 1) Supervisor initials ====================================================[0]
						 * 2) Number of leave days ===================================================[1]
						 * 3) Leave Type =============================================================[2]
						 * 4) Requester Full Name ====================================================[3]
						 * 5) Requester staff ID =====================================================[4]
						 * 6) Start Date =============================================================[5]
						 * 7) End Date ===============================================================[6]
						 * 8) Comment ================================================================[7]
						 * 9) Leave Option [with_pay or without_pay] =================================[8]
						 * }*/
						String supervisorData[] = {
							sup.getNameInitials(),
							String.valueOf(l.getNoOfDays()),
							lt.getName(),
							employeeFullName,
							emp.getStaffId(),
							dd.changeFormatDate(l.getStartDate()),
							dd.changeFormatDate(l.getEndDate()),
							l.getComment(),
							"without_pay"
						};
						lem.getSupervisorDeclinedLeaveRequest(supervisorEmailAddress, supervisorData);
						
						//3) HR
						/*Data values order{
						 * 1) Number of leave days ===================================================[0]
						 * 2) Leave Type =============================================================[1]
						 * 3) Requester Full Name ====================================================[2]
						 * 4) Requester staff ID =====================================================[3]
						 * 5) Start Date =============================================================[4]
						 * 6) End Date ===============================================================[5]
						 * 7) Declined By Full Name ==================================================[6]
						 * 8) Declined By Staff ID ===================================================[7]
						 * 9) Comment ================================================================[8]
						 * 10) Leave Option [with_pay or without_pay] ================================[9]
						 * }*/
						String hrData[] = {
								String.valueOf(l.getNoOfDays()),
								lt.getName(),
								employeeFullName,
								emp.getStaffId(),
								dd.changeFormatDate(l.getStartDate()),
								dd.changeFormatDate(l.getEndDate()),
								"hr",
								"",
								l.getComment(),
								"without_pay"
						};
						lem.getHrLeaveRequestDeclinedMessage(hrEmailAddress, hrData);
						
					}
				}
				else if(ls.getCode().equals("sent_back_for_correction")) {
					message = ln.getSentLeaveBackForCorrectionMessage(true);
					/* Send Email Notification 
					 * 1) Employee*/
					if(he.isEmailEnable()) {
						//1) Employee
						String employeeEmailAddress[] = { emp.getEmail() };
						/*Data values order {
						 * 1) Employee Name Initials =========================================================[0]
						 * 2) Number Of Leave Days ===========================================================[1]
						 * 3) Leave Type =====================================================================[2]
						 * 4) Start Date =====================================================================[3]
						 * 5) End Date =======================================================================[4]
						 * 6) Correction By Name =============================================================[5]
						 * 7) Correction Staff ID ============================================================[6]
						 * 8) Comment ========================================================================[7]
						 * 9) Leave Option [with_pay or without_pay] =========================================[8]
						 * }*/
						String employeeData [] = {
								emp.getNameInitials(),
								String.valueOf(l.getNoOfDays()),
								lt.getName(),
								dd.changeFormatDate(l.getStartDate()),
								dd.changeFormatDate(l.getEndDate()),
								"hr",
								"",
								l.getComment(),
								"without_pay"
						};
						lem.getEmployeeLeaveRequestSentBackForCorrection(employeeEmailAddress, employeeData);
					}
				}
				else {
					message = ln.getApprovedLeaveMessage(true);
					/*Sent Email Notification
					 * 1) Employee
					 * 2) HR
					 * 3) Relief Officers
					 * 4) Staff to be Notified*/
					if(he.isEmailEnable()) {
						//1) Employee
						String employeeEmailAddress[] = { emp.getEmail() };
						/*data values order{
						 * 1) employee Initials [0]
						 * 2) number of days [1]
						 * 3) Leave Type [2]
						 * 4) Start date [3]
						 * 5) End date	[4]
						 * 6) Resumption Date [5]
						 * 7) Leave Option [ with_Pay or without_pay ] [6]
						 * }*/
						String employeeData[] = {
								emp.getNameInitials(),
								String.valueOf(l.getNoOfDays()),
								lt.getName(),
								dd.changeFormatDate(l.getStartDate()),
								dd.changeFormatDate(l.getEndDate()),
								dd.changeFormatDate(l.getResumptionDate()),
								"without_pay"
						};
						lem.getEmployeeApprovedLeaveRequestMessage(employeeEmailAddress, employeeData);
						
						//2) HR
						/* Data value order{
						 * 1) No of leave days ===============================[0]
						 * 2) Leave Type =====================================[1]
						 * 3) Employee Full Name =============================[2]
						 * 4) Employee Staff ID ==============================[3]
						 * 5) Start Date =====================================[4]
						 * 6) End Date =======================================[5]
						 * 7) Resumption Date ================================[6]
						 * 8) Approver Name ==================================[7]
						 * 9) Approver Staff ID ==============================[8]
						 * 10) Leave Option [with_pay or without_pay] ========[9]
						 * }*/
						String hrData[] = {
								String.valueOf(l.getNoOfDays()),
								lt.getName(),
								employeeFullName,
								emp.getStaffId(),
								dd.changeFormatDate(l.getStartDate()),
								dd.changeFormatDate(l.getEndDate()),
								dd.changeFormatDate(l.getResumptionDate()),
								"hr",
								"",
								"without_pay"
						};
						lem.getHrApprovedLeaveRequestMessage(hrEmailAddress, hrData);
						
						//3) Relief Officers
						/* Data values order {
						 * 1) Employee full name ======================================[0]
						 * 2) Staff ID ================================================[1]
						 * 3) No of days ==============================================[2]
						 * 4) Leave Type ==============================================[3]
						 * 5) Start Date ==============================================[4]
						 * 6) End Date ================================================[5]
						 * 7) Resumption Date =========================================[6]
						 * 8) Employee Name Initials ==================================[7]
						 * 9) Leave Option [with_pay or Without_pay] ==================[8]
						 * } */
						String reliefOfficerData[] = {
								employeeFullName,
								emp.getStaffId(),
								String.valueOf(l.getNoOfDays()),
								lt.getName(),
								dd.changeFormatDate(l.getStartDate()),
								dd.changeFormatDate(l.getEndDate()),
								dd.changeFormatDate(l.getResumptionDate()),
								emp.getNameInitials(),
								"without_pay"
						};
						if(l.getSecondaryReliefOfficeId() == 0) {
							String emailAddress[] = { ed.getEmployeeEmail(l.getPrimaryReliefOfficeId()) };
							lem.getReliefOfficerLeaveRequestApprovedMessage(emailAddress, reliefOfficerData);
						}
						else {
							String emailAddress[] = {
									ed.getEmployeeEmail(l.getPrimaryReliefOfficeId()),
									ed.getEmployeeEmail(l.getSecondaryReliefOfficeId())
							};
							lem.getReliefOfficerLeaveRequestApprovedMessage(emailAddress, reliefOfficerData);
						}
						
						//4) Staff to be Notified
						int employeesId[] = ed.getEmployeesId(l.getStaffToNotify());
						String staffEmailAddress[] = ed.getEmployeeEmailsArray(employeesId);
						/* Data values order {
						 * 1) Employee Full Name =============================[0]
						 * 2) Staff ID =======================================[1]
						 * 3) No Of Days =====================================[2]
						 * 4) Leave Type =====================================[3]
						 * 5) Start Date =====================================[4]
						 * 6) End Date =======================================[5]
						 * 7) Resumption Date ================================[6]
						 * 8) Leave Option [with_pay or without_pay] =========[7]
						 * }*/
						String staffData[] = {
							employeeFullName,
							emp.getStaffId(),
							String.valueOf(l.getNoOfDays()),
							lt.getName(),
							dd.changeFormatDate(l.getStartDate()),
							dd.changeFormatDate(l.getEndDate()),
							dd.changeFormatDate(l.getResumptionDate()),
							"without_pay"
						};
						lem.getStaffNotifyLeaveRequestApprovedMessage(staffEmailAddress, staffData);
					}
				}
				session.setAttribute("success", message);
				session.setAttribute("pageTo", "hrLeavePendingApprovalWithoutPay.jsp");
				rd = request.getRequestDispatcher("success");
				rd.forward(request, response);
			}
			else {
				if(ls.getCode().equals("declined")) {
					message = ln.getDeclinedLeaveRequestMessage(false);
				}
				else if(ls.getCode().equals("sent_back_for_correction")) {
					message = ln.getSentLeaveBackForCorrectionMessage(false);
				}
				else {
					message = ln.getApprovedLeaveMessage(false);
				}
				session.setAttribute("error", message);
				rd.forward(request, response);
			}
		}
	}

}
