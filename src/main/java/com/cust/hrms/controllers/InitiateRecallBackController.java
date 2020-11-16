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

@WebServlet("/initiateRecallBack")
public class InitiateRecallBackController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = request.getRequestDispatcher("initiateLeaveCallBackWithPay.jsp");
		int updatedBy = 0;
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			updatedBy = (int)session.getAttribute("employeeId");
		}
		HrmsEmail he = new HrmsEmail();
		LeaveNotification ln = new LeaveNotification();
		LeaveTypeDao ltd = new LeaveTypeDao();
		LeaveStatusDao lsd = new LeaveStatusDao();
		LeaveDao ld = new LeaveDao();
		RoleDao rod = new RoleDao();
		DateDao dd = new DateDao();
		EmployeeDao ed = new EmployeeDao();
		EmployeeRoleDao erd = new EmployeeRoleDao();
		int leaveId = Integer.parseInt(request.getParameter("leaveId"));
		Leave l = ld.getLeaveById(leaveId);
		LeaveType lt = ltd.getLeaveTypeById(l.getLeaveTypeId());
		l.setLeaveStatusId(lsd.getLeaveStatusId("initiate_recall_back"));
		String comment = request.getParameter("comment");
		l.setComment(comment);
		l.setUpdatedBy(updatedBy);
		//Update leave in the database table
		int count = ld.updateLeave(l);
		
		if(count >= 1) {
			/*Send email notification
			 * 1) HR Admin*/
			if(he.isEmailEnable()) {
				LeaveEmailMessage lem = new LeaveEmailMessage();
				int hrRoleId = rod.getRoleIdBycode("hr_admin");
				int hrEmployeesId[] = erd.getRoleEmployeesId(hrRoleId);
				String hrEmailAddress[] = ed.getEmployeeEmailsArray(hrEmployeesId);
				Employee emp = ed.getEmployeeById(l.getEmployeeId());
				String employeeFullName = emp.getFirstName()+" "+emp.getMiddleName()+" "+emp.getLastName();
				Employee sup = ed.getEmployeeById(l.getSupervisorId());
				String supervisorFullName = sup.getFirstName()+" "+sup.getMiddleName()+" "+sup.getLastName();
				
				//1) HR Admin
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
						"with_pay"
				};
				lem.getHrInitializedLeaveRecallBackMessage(hrEmailAddress, hrData);
			}
			session.setAttribute("success", ln.getInitiateLeaveRecallBackMessage(true));
			response.sendRedirect("supervisorApprovedLeaveRequestReportWithPay.jsp");
		}
		else {
			session.setAttribute("error", ln.getInitiateLeaveRecallBackMessage(false));
			rd.forward(request, response);
		}
	}

}
