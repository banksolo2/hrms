package com.cust.hrms.controllers;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.email.*;
import com.cust.hrms.email.message.*;
import com.cust.hrms.notification.*;

@WebServlet("/editDraftedLeaveRequestWithoutPay")
public class EditDraftedLeaveRequestWithoutPayController extends HttpServlet {
	
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
		RequestDispatcher rd = request.getRequestDispatcher("editDraftedLeaveRequestWithoutPay.jsp");
		LeaveNotification ln = new LeaveNotification();
		HrmsEmail he = new HrmsEmail();
		LeaveStatusDao lsd = new LeaveStatusDao();
		LeaveTypeDao ltd = new LeaveTypeDao();
		EmployeeDao ed = new EmployeeDao();
		LeaveDao ld = new LeaveDao();
		DateDao dd = new DateDao();
		RedZoneDao rzd = new RedZoneDao();
		String year = request.getParameter("year");
		int leaveId = Integer.parseInt(request.getParameter("leaveId"));
		Leave l = ld.getLeaveById(leaveId);
		l.setUpdatedBy(updatedBy);
		String leaveTypeCode = request.getParameter("leaveTypeId");
		LeaveType lt = ltd.getLeaveTypeByCode(leaveTypeCode);
		l.setLeaveTypeId(lt.getLeaveTypeId());
		String dates[] = request.getParameter("dates").split(" - ");
		l.setStartDate(dd.convertDateFormat(dates[0], "/"));
		l.setEndDate(dd.convertDateFormat(dates[1], "/"));
		l.setResumptionDate(dd.addDaysSkippingWeekends(l.getEndDate(), 1));
		l.setNoOfDays(ld.getNoOfDays(l.getStartDate(), l.getEndDate()));
		l.setStaffToNotify(ed.convertEmployeeIdArraysToString(request.getParameterValues("staffToNotify")));
		int primaryReliefOfficeId = Integer.parseInt(request.getParameter("primaryReliefOfficeId"));
		l.setPrimaryReliefOfficeId(primaryReliefOfficeId);
		String secondaryReliefOffice = request.getParameter("secondaryReliefOfficeId").trim();
		l.setSecondaryReliefOfficeId(0);
		if(secondaryReliefOffice.length() >= 1){
			l.setSecondaryReliefOfficeId(Integer.parseInt(secondaryReliefOffice));
		}
		int leaveStatusId = Integer.parseInt(request.getParameter("leaveStatusId"));
		l.setLeaveStatusId(leaveStatusId);
		LeaveStatus ls = lsd.getLeaveStatusById(leaveStatusId);
		
		
		/* Validate employee leave request
		 * 1) Check if employee leave days is valid
		 * 2) Check if employee leave request is in a red zone period*/
		
		//1) Check if employee leave days is valid
		boolean isEmployeeLeaveDaysValid = ld.isEmployeeLeavedaysValid(l.getEmployeeId(), l.getLeaveTypeId(), year, l.getNoOfDays(), l.getWithPay());
		if(isEmployeeLeaveDaysValid) {
			//2) Check if employee leave request is in a red zone period
			RedZone rz = new RedZone();
			rz.setDepartmentId(l.getDepartmentId());
			rz.setDateFrom(l.getStartDate());
			rz.setDateTo(l.getEndDate());
			boolean isLeaveRequestInRedZone = rzd.isDateInRedZone(rz);
			if(isLeaveRequestInRedZone == false) {
				// Update leave request in the database
				int count = ld.updateLeave(l);
				String message = null;
				if(count >= 1) {
					if(ls.getCode().equals("drafted")) {
						message = ln.getDraftedLeaveMessage(true);
					}
					else {
						message = ln.getSentLeaveToSupervisorForApprovalMessage(true);
						/*Send email notification to:
						 * 1) Employee
						 * 2) Supervisor*/
						if(he.isEmailEnable()) {
							LeaveEmailMessage lem = new LeaveEmailMessage();
							//1) Employee
							Employee emp = ed.getEmployeeById(l.getEmployeeId());
							String employeeEmailAddress[] = { emp.getEmail() };
							String employeeData[] = {
									emp.getNameInitials(),
									"without_pay"
							};
							lem.getEmployeeSendLeaveToSupervisorForApprovalMessage(employeeEmailAddress, employeeData);
							
							//2) Supervisor
							Employee sup = ed.getEmployeeById(l.getSupervisorId());
							String supervisorEmailAddress[] = { sup.getEmail() };
							String supervisorData[] = {
									sup.getNameInitials(),
									String.valueOf(l.getNoOfDays()),
									lt.getName(),
									dd.changeFormatDate(l.getStartDate()),
									dd.changeFormatDate(l.getEndDate()),
									dd.changeFormatDate(l.getResumptionDate()),
									emp.getFirstName()+" "+emp.getMiddleName()+" "+emp.getLastName(),
									emp.getStaffId(),
									"without_pay"
							};
							lem.getSupervisorLeaveAwaitingApproval(supervisorEmailAddress, supervisorData);
						}
					}
					session.setAttribute("success", message);
					response.sendRedirect("employeeDraftedLeaveRequestWithoutPay.jsp");
				}
				else {
					if(ls.getCode().equals("drafted")) {
						message = ln.getDraftedLeaveMessage(false);
					}
					else {
						message = ln.getSentLeaveToSupervisorForApprovalMessage(false);
					}
					session.setAttribute("error", message);
					rd.forward(request, response);
				}
			}
			else {
				session.setAttribute("error", ln.getLeaveRedZoneErrorMessage());
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", ln.getEmployeeLeaveDaysValidErrorMessage());
			rd.forward(request, response);
		}
	}
}
