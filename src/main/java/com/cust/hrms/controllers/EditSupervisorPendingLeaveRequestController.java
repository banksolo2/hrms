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

@WebServlet("/editSupervisorPendingLeaveRequest")
public class EditSupervisorPendingLeaveRequestController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		RequestDispatcher rd  = request.getRequestDispatcher("editSupervisorLeavePendingApproval.jsp");
		PrintWriter out = response.getWriter();
		int updatedBy = 0;
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			updatedBy = (int)session.getAttribute("employeeId");
		}
		DateDao dd = new DateDao();
		EmployeeDao ed = new  EmployeeDao();
		LeaveNotification ln = new LeaveNotification();
		HrmsEmail he = new HrmsEmail();
		RoleDao rod = new RoleDao();
		EmployeeRoleDao erd = new EmployeeRoleDao();
		LeaveDao ld = new LeaveDao();
		LeaveTypeDao ltd = new LeaveTypeDao();
		LeaveStatusDao lsd = new LeaveStatusDao();
		int leaveId = Integer.parseInt(request.getParameter("leaveId"));
		Leave l = ld.getLeaveById(leaveId);
		l.setUpdatedBy(updatedBy);
		LeaveType lt = ltd.getLeaveTypeById(l.getLeaveTypeId());
		int leaveStatusId = Integer.parseInt(request.getParameter("leaveStatusId"));
		LeaveStatus ls = lsd.getLeaveStatusById(leaveStatusId);
		l.setLeaveStatusId(leaveStatusId);
		String comment = request.getParameter("comment").trim();
		int commentLength = comment.length();
		if((commentLength < 1) && (ls.getCode().equals("declined") || ls.getCode().equals("sent_back_for_correction"))) {
			session.setAttribute("error", "Please provide a comment....");
			rd.forward(request, response);
		}
		else {
			l.setComment(comment);
			//update data in the database table
			int count = ld.updateLeave(l);
			String message = null;
			if(count >= 1) {
				LeaveEmailMessage lem = new LeaveEmailMessage();
				Employee emp = ed.getEmployeeById(l.getEmployeeId());
				Employee sup = ed.getEmployeeById(l.getSupervisorId());
				int hrRoleId = rod.getRoleIdBycode("hr_admin");
				int hrEmployeesId[] = erd.getRoleEmployeesId(hrRoleId);
				String hrEmailAddress[] = ed.getEmployeeEmailsArray(hrEmployeesId);
				String employeeEmailAddress[] = { emp.getEmail() };
				String supervisorEmailAddress[] = { sup.getEmail() };
				String employeeFullName = emp.getFirstName()+" "+emp.getMiddleName()+" "+emp.getLastName();
				String supervisorFullName = sup.getFirstName()+" "+sup.getMiddleName()+" "+sup.getLastName();
				if(ls.getCode().equals("sent_to_hr_for_approval")) {
					message = ln.getSentLeaveToHrForApprovalMessage(true);
					/*Sent email notification 
					 * 1) Employee
					 * 2) HR Admin*/
					if(he.isEmailEnable()) {
						//1)Employee
						/* Data values order {
						 * 1) Employee Name initials ==============================[0]
						 * 2) Leave Option [with_pay or without_pay] ==============[1]
						 * }*/
						String EmployeeData [] = {
							emp.getNameInitials(),
							"with_pay"
						};
						lem.getEmployeeLeaveSentHrForApprovalMessage(employeeEmailAddress, EmployeeData);
						
						//2)HR Admin
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
						String hrData[] = {
								String.valueOf(l.getNoOfDays()),
								lt.getName(),
								employeeFullName,
								emp.getStaffId(),
								dd.changeFormatDate(l.getStartDate()),
								dd.changeFormatDate(l.getEndDate()),
								dd.changeFormatDate(l.getResumptionDate()),
								"with_pay"
						};
						lem.getHrLeaveRequestAwaitingApproval(hrEmailAddress, hrData);
					}
				}
				else if(ls.getCode().equals("approved")) {
					message = ln.getApprovedLeaveMessage(true);
					/* Send email notification
					 * 1) Employee
					 * 2) Hr Admin
					 * 3) Staff To Notify
					 * 4) Relief Officers*/
					if(he.isEmailEnable()) {
						//1) Employee
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
							"with_pay"
						};
						lem.getEmployeeApprovedLeaveRequestMessage(employeeEmailAddress, employeeData);
						
						//2) HR Admin
						/* Data value order{
						 * 1) No of leave days ===============================[0]
						 * 2) Leave Type =====================================[1]
						 * 3) Employee Full Name =============================[2]
						 * 4) Employee Staff ID ==============================[3]
						 * 5) Start Date =====================================[4]
						 * 6) End Date =======================================[5]
						 * 7) Resumption Date ================================[6]
						 * 8) Supervisor Name ================================[7]
						 * 9) SuperVisor Staff ID ============================[8]
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
								supervisorFullName,
								sup.getStaffId(),
								"with_pay"
						};
						lem.getHrApprovedLeaveRequestMessage(hrEmailAddress, hrData);
						
						//3) Staff To Notify
						int staffEmployeesId[] = ed.getEmployeesId(l.getStaffToNotify());
						String staffEmailAddress[] = ed.getEmployeeEmailsArray(staffEmployeesId);
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
								"with_pay"
						};
						lem.getStaffNotifyLeaveRequestApprovedMessage(staffEmailAddress, staffData);
						
						//4) Relief Officers
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
						String reliefOfficerData[]  = {
							employeeFullName,
							emp.getStaffId(),
							String.valueOf(l.getNoOfDays()),
							lt.getName(),
							dd.changeFormatDate(l.getStartDate()),
							dd.changeFormatDate(l.getEndDate()),
							dd.changeFormatDate(l.getResumptionDate()),
							emp.getNameInitials(),
							"with_pay"
						};
						if(l.getSecondaryReliefOfficeId() == 0) {
							String reliefOfficersEmailAddress[] = { ed.getEmployeeEmail(l.getPrimaryReliefOfficeId()) };
							lem.getReliefOfficerLeaveRequestApprovedMessage(reliefOfficersEmailAddress, reliefOfficerData);
						}
						else {
							String reliefOfficersEmailAddress[] = { 
									ed.getEmployeeEmail(l.getPrimaryReliefOfficeId()),
									ed.getEmployeeEmail(l.getSecondaryReliefOfficeId())
							};
							lem.getReliefOfficerLeaveRequestApprovedMessage(reliefOfficersEmailAddress, reliefOfficerData);
						}
					}
				}
				else if(ls.getCode().equals("sent_back_for_correction")) {
					message = ln.getSentLeaveBackForCorrectionMessage(true);
					/*Sent email notification 
					 * 1) Employee */
					if(he.isEmailEnable()) {
						// 1) Employee
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
								"with_pay"
						};
						lem.getEmployeeLeaveRequestSentBackForCorrection(employeeEmailAddress, employeeData);
					}
				}
				else if(ls.getCode().equals("declined")) {
					message = ln.getDeclinedLeaveRequestMessage(true);
					/*Sent email notification 
					 * 1) Employee 
					 * 2) HR Admin
					 **/
					if(he.isEmailEnable()) {
						//1) Employee
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
								"with_pay"
						};
						lem.getEmployeeDeclinedLeaveRequestMessage(employeeEmailAddress, employeeData);
						
						// 2) HR Admin
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
								supervisorFullName,
								sup.getStaffId(),
								l.getComment(),
								"with_pay"
						};
						lem.getHrLeaveRequestDeclinedMessage(hrEmailAddress, hrData);
					}
				}
				session.setAttribute("success", message);
				session.setAttribute("pageTo", "supervisorLeavePendingApprovalWithPay.jsp");
				rd = request.getRequestDispatcher("success");
				rd.forward(request, response);
			}
			else {
				if(ls.getCode().equals("sent_to_hr_for_approval")) {
					message = ln.getSentLeaveToHrForApprovalMessage(false);
				}
				else if(ls.getCode().equals("approved")) {
					message = ln.getApprovedLeaveMessage(false);
				}
				else if(ls.getCode().equals("sent_back_for_correction")) {
					message = ln.getSentLeaveBackForCorrectionMessage(false);
				}
				else if(ls.getCode().equals("declined")) {
					message = ln.getDeclinedLeaveRequestMessage(false);
				}
				session.setAttribute("error", message);
				rd.forward(request, response);
			}
		}
	}

}
