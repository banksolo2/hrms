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

@WebServlet("/initiateRecallBackWithoutPay")
public class InitiateRecallBackWithoutPayController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		PrintWriter out = response.getWriter();
		NotificationMessageDao nmd = new NotificationMessageDao();
		HttpSession session = request.getSession();
		int updatedBy = 0;
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			updatedBy = (int)session.getAttribute("employeeId");
		}
		RequestDispatcher rd = request.getRequestDispatcher("initiateLeaveCallBackWithoutPay.jsp");
		int leaveId = Integer.parseInt(request.getParameter("leaveId"));
		LeaveDao ld = new LeaveDao();
		LeaveTypeDao ltd = new LeaveTypeDao();
		LeaveStatusDao lsd = new LeaveStatusDao();
		DateDao dd = new DateDao();
		RoleDao rod = new RoleDao();
		EmployeeDao ed = new EmployeeDao();
		EmployeeRoleDao erd = new EmployeeRoleDao();
		LeaveNotification ln = new LeaveNotification();
		HrmsEmail he = new HrmsEmail();
		Leave l = ld.getLeaveById(leaveId);
		String comment = request.getParameter("comment");
		
		l.setComment(comment);
		int leaveStatusId = lsd.getLeaveStatusId("initiate_recall_back");
		LeaveStatus ls = lsd.getLeaveStatusById(leaveStatusId);
		LeaveType lt = ltd.getLeaveTypeById(l.getLeaveTypeId());
		l.setLeaveStatusId(leaveStatusId);
		l.setUpdatedBy(updatedBy);
		
		
		int count = ld.updateLeave(l);
		if(count >= 1) {
			session.setAttribute("success", ln.getInitiateLeaveRecallBackMessage(true));
			/*Sent Email Notification
			 * 1) HR Admin*/
			if(he.isEmailEnable()) {
				LeaveEmailMessage lem = new LeaveEmailMessage();
				Employee emp = ed.getEmployeeById(l.getEmployeeId());
				Employee sup = ed.getEmployeeById(l.getSupervisorId());
				String employeeFullName = emp.getFirstName()+" "+emp.getMiddleName()+" "+emp.getLastName();
				String supervisorFullName = sup.getFirstName()+" "+sup.getMiddleName()+" "+sup.getLastName();
				int hrRoleId = rod.getRoleIdBycode("hr_admin");
				int hrEmployeesId[] = erd.getRoleEmployeesId(hrRoleId);
				String hrEmailAddress[] = ed.getEmployeeEmailsArray(hrEmployeesId);
				/* Data values order {
				 *1) Number of Leave Days ========================================================[0]
				 *2) Leave Type ==================================================================[1]
				 *3) Start Date ==================================================================[2]
				 *4) End Date ====================================================================[3]
				 *5) Employee Full Name ==========================================================[4]
				 *6) Employee Staff ID ===========================================================[5]
				 *7) Initialized By Full Name ====================================================[6]
				 *8) Initialized By Staff ID =====================================================[7]
				 *9) Comment =====================================================================[8]
				 *10)Leave Options [with_pay or without_pay] =====================================[9]
				 * }*/
				String hrData[] = {
					String.valueOf(l.getNoOfDays()),
					lt.getName(),
					dd.changeFormatDate(l.getStartDate()),
					dd.changeFormatDate(l.getEndDate()),
					employeeFullName,
					emp.getStaffId(),
					supervisorFullName,
					sup.getStaffId(),
					l.getComment(),
					"without_pay"
				};
				lem.getHrInitializedLeaveRecallBackMessage(hrEmailAddress, hrData);
			}
			response.sendRedirect("supervisorApprovedLeaveRequestReportWithoutPay.jsp");
		}
		else {
			session.setAttribute("error", ln.getInitiateLeaveRecallBackMessage(false));
			rd.forward(request, response);
		}
	}
}
