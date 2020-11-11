package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.email.*;
import com.cust.hrms.notification.*;
import com.cust.hrms.email.message.*;

@WebServlet("/editSupervisorPendingLeaveRequestWithoutPay")
public class EditSupervisorPendingLeaveRequestWithoutPayController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		int updatedBy = 0;
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			updatedBy = (int)session.getAttribute("employeeId");
		}
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = request.getRequestDispatcher("editSupervisorLeavePendingApprovalWithoutPay.jsp");
		EmployeeDao ed = new EmployeeDao();
		LeaveTypeDao ltd = new LeaveTypeDao();
		LeaveStatusDao lsd = new LeaveStatusDao();
		DateDao dd = new DateDao();
		LeaveDao ld = new LeaveDao();
		RoleDao rod = new RoleDao();
		EmployeeRoleDao erd = new EmployeeRoleDao();
		LeaveNotification ln = new LeaveNotification();
		HrmsEmail he = new HrmsEmail();
		int leaveId = Integer.parseInt(request.getParameter("leaveId"));
		Leave l = ld.getLeaveById(leaveId);
		l.setUpdatedBy(updatedBy);
		LeaveType lt = ltd.getLeaveTypeById(l.getLeaveTypeId());
		int leaveStatusId = Integer.parseInt(request.getParameter("leaveStatusId"));
		LeaveStatus ls = lsd.getLeaveStatusById(leaveStatusId);
		l.setLeaveStatusId(leaveStatusId);
		String comment = request.getParameter("comment");
		int commentLength = comment.trim().length();
		if((ls.getCode().equals("declined") || ls.getCode().equals("sent_back_for_correction")) &&  (commentLength < 1)) {	
			session.setAttribute("error", "Please provide a comment");
			rd.forward(request, response);
		}
		else {
			l.setComment(comment);
			
			int count = ld.updateLeave(l);
			String message = null;
			if(count >= 1) {
				LeaveEmailMessage lem = new LeaveEmailMessage();
				Employee emp = ed.getEmployeeById(l.getEmployeeId());
				Employee sup = ed.getEmployeeById(l.getSupervisorId());
				String employeeFullName = emp.getFirstName()+" "+emp.getMiddleName()+" "+emp.getLastName();
				String supervisorFullName = sup.getFirstName()+" "+sup.getMiddleName()+" "+sup.getLastName();
				int roleId = rod.getRoleIdBycode("hr_admin");
				int hrEmployeesId[] = erd.getRoleEmployeesId(roleId);
				String hrAdminEmailAddress[] = ed.getEmployeeEmailsArray(hrEmployeesId);
				if(ls.getCode().equals("declined")) {
					message = ln.getDeclinedLeaveRequestMessage(true);
					/*Send email notification
					 * 1) Employee
					 * 2) HR Admin
					 * */
					if(he.isEmailEnable()) {
						//1) Employee
						String employeeEmail[] = { emp.getEmail() };
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
								supervisorFullName,
								sup.getStaffId(),
								l.getComment(),
								"without_pay"
						};
						lem.getEmployeeDeclinedLeaveRequestMessage(employeeEmail,employeeData);
						
						//2) HR Admin
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
						String hrAdminData[] = {
								String.valueOf(l.getNoOfDays()),
								lt.getName(),
								employeeFullName,
								emp.getStaffId(),
								dd.changeFormatDate(l.getStartDate()),
								dd.changeFormatDate(l.getEndDate()),
								supervisorFullName,
								sup.getStaffId(),
								l.getComment(),
								"without_pay"
						};
						lem.getHrLeaveRequestDeclinedMessage(hrAdminEmailAddress, hrAdminData);
					}
				}
				else if(ls.getCode().equals("sent_back_for_correction")) {
					message = ln.getSentLeaveBackForCorrectionMessage(true);
					/*Email Notification
					 * 1) Employee 
					 * */
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
						String employeeData[] = {
								emp.getNameInitials(),
								String.valueOf(l.getNoOfDays()),
								lt.getName(),
								dd.changeFormatDate(l.getStartDate()),
								dd.changeFormatDate(l.getEndDate()),
								supervisorFullName,
								sup.getStaffId(),
								l.getComment(),
								"without_pay"
						};
						lem.getEmployeeLeaveRequestSentBackForCorrection(employeeEmailAddress, employeeData);
					}
				}
				else {
					message = ln.getSentLeaveToHrForApprovalMessage(true);
					/*Send Email Notification
					 * 1) employee
					 * 2) HR Admin*/
					if(he.isEmailEnable()) {
						//1) Employee
						String employeeEmailAddress[] = { emp.getEmail() };
						/* Data values order {
						 * 1) Employee Name initials ==============================[0]
						 * 2) Leave Option [with_pay or without_pay] ==============[1]
						 * }*/
						String employeeData[] = {
								emp.getNameInitials(),
								"without_pay"
						};
						lem.getEmployeeLeaveSentHrForApprovalMessage(employeeEmailAddress, employeeData);
						
						//2) Hr Admin
						/*Data values order {
						 * 1) No of days =====================================[0]
						 * 2) Leave Type =====================================[1]
						 * 3) Employee Full Name =============================[2]
						 * 4) Employee Staff ID ==============================[3]
						 * 5) Start Date =====================================[4]
						 * 6) End Date =======================================[5]
						 * 7) Resumption Date ================================[6]
						 * 8) Leave Type Option [with_pay or without_pay] ====[7]
						 * }*/
						String hrAdminData[] = {
								String.valueOf(l.getNoOfDays()),
								lt.getName(),
								employeeFullName,
								emp.getStaffId(),
								dd.changeFormatDate(l.getStartDate()),
								dd.changeFormatDate(l.getEndDate()),
								dd.changeFormatDate(l.getResumptionDate()),
								"without_pay"
						};
						lem.getHrLeaveRequestAwaitingApproval(hrAdminEmailAddress, hrAdminData);
					}
				}
				session.setAttribute("success", message);
				session.setAttribute("pageTo", "supervisorLeavePendingApprovalWithoutPay.jsp");
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
					message = ln.getSentLeaveToHrForApprovalMessage(false);
				}
				session.setAttribute("error", message);
				rd.forward(request, response);
			}
		}
	}
}
