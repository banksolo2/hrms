package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import java.util.*;
import com.cust.hrms.email.*;
import com.cust.hrms.notification.*;
import com.cust.hrms.email.message.*;

@WebServlet("/editDraftedLeaveRequestWithPay")
public class EditDraftedLeaveRequestWithPayController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		int updatedBy = 0;
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			updatedBy = (int)session.getAttribute("employeeId");
		}
		RequestDispatcher rd = request.getRequestDispatcher("editDraftedLeaveRequest.jsp");
		PrintWriter out = response.getWriter();
		LeaveTypeDao ltd = new LeaveTypeDao();
		LeaveStatusDao lsd = new LeaveStatusDao();
		LeaveDao ld = new LeaveDao();
		DateDao dd = new DateDao();
		RedZoneDao rzd = new RedZoneDao();
		EmployeeDao ed = new EmployeeDao();
		LeaveNotification ln = new LeaveNotification();
		LeavePlanDao lpd = new LeavePlanDao();
		HrmsEmail he = new HrmsEmail();
		int leaveId = Integer.parseInt(request.getParameter("leaveId"));
		Leave l = ld.getLeaveById(leaveId);
		String leaveTypeCode = request.getParameter("leaveTypeId");
		LeaveType lt = ltd.getLeaveTypeByCode(leaveTypeCode);
		l.setLeaveTypeId(lt.getLeaveTypeId());
		String dates[] = request.getParameter("dates").split(" - ");
		l.setStartDate(dd.convertDateFormat(dates[0], "/"));
		l.setEndDate(dd.convertDateFormat(dates[1], "/"));
		l.setResumptionDate(dd.addDaysSkippingWeekends(l.getEndDate(), 1));
		l.setNoOfDays(ld.getNoOfDays(l.getStartDate(), l.getEndDate()));
		if(lt.getCode().trim().equals("annual")) {
			String inLineWithLeavePlan = request.getParameter("inLineWithLeavePlan");
			l.setInlineWithLeavePlan(inLineWithLeavePlan);
		}
		else {
			l.setInlineWithLeavePlan("no");
		}
		String employeesId[] = request.getParameterValues("staffToNotify");
		String staffToNotify = ed.convertEmployeeIdArraysToString(employeesId);
		l.setStaffToNotify(staffToNotify);
		int primaryReliefOfficeId = Integer.parseInt(request.getParameter("primaryReliefOfficeId"));
		l.setPrimaryReliefOfficeId(primaryReliefOfficeId);
		String secondaryReliefOffice = request.getParameter("secondaryReliefOfficeId");
		int secondaryReliefOfficeId = 0;
		if(secondaryReliefOffice.trim().length() >= 1) {
			secondaryReliefOfficeId = Integer.parseInt(secondaryReliefOffice);
		}
		l.setSecondaryReliefOfficeId(secondaryReliefOfficeId);
		int leaveStatusId = Integer.parseInt(request.getParameter("leaveStatusId"));
		l.setLeaveStatusId(leaveStatusId);
		LeaveStatus ls = lsd.getLeaveStatusById(leaveStatusId);
		String currentYear  = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		l.setUpdatedBy(updatedBy);
		
		//Check if leave request in line with leave Plan
		if(l.getInlineWithLeavePlan().trim().equals("yes")) {
			LeavePlan lp = new LeavePlan();
			lp.setEmployeeId(l.getEmployeeId());
			lp.setStartDate(l.getStartDate());
			lp.setEndDate(l.getEndDate());
			boolean isLeaveRequestInlineWithLeavePlan = lpd.isLeaveRequestInLineWithLeavePlan(lp);
			if(isLeaveRequestInlineWithLeavePlan == false) {
				session.setAttribute("error", ln.getInlineWithLeavePlanErrorMessage());
				rd.forward(request, response);
			}
		}
		
		// Check if employee leave days is valid
		boolean isEmployeeLeaveDaysValid = ld.isEmployeeLeavedaysValid(l.getEmployeeId(), l.getLeaveTypeId(), currentYear, l.getNoOfDays(), l.getWithPay());
		if(isEmployeeLeaveDaysValid == true) {
			//check if leave request id in a red zone period
			RedZone rz = new RedZone();
			rz.setDepartmentId(l.getDepartmentId());
			rz.setDateFrom(l.getStartDate());
			rz.setDateTo(l.getEndDate());
			boolean isLeaveRequestInRedZone = rzd.isDateInRedZone(rz);
			if(isLeaveRequestInRedZone == false) {
				//Update leave request in the database
				int count = ld.updateLeave(l);
				String message = null;
				if(count >= 1) {
					//out.println(l.toString());
					if(ls.getCode().equals("drafted")) {
						message = ln.getDraftedLeaveMessage(true);
					}
					else {
						message = ln.getSentLeaveToSupervisorForApprovalMessage(true);
						/*Send Email Notification
						 * 1) employee
						 * 2) Supervisor*/
						if(he.isEmailEnable()) {
							LeaveEmailMessage lem = new LeaveEmailMessage();
							//1) Employee
							//Get employee details
							Employee emp = ed.getEmployeeById(l.getEmployeeId());
							String employeeEmailAddress[] = { emp.getEmail()};
							String employeeData[] = {
								emp.getNameInitials(),
								"with_pay"
							};
							lem.getEmployeeSendLeaveToSupervisorForApprovalMessage(employeeEmailAddress, employeeData);
							
							//2) Supervisor
							//Get supervisor details
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
									"with_pay"
							};
							lem.getSupervisorLeaveAwaitingApproval(supervisorEmailAddress, supervisorData);
						}
						
					}
					session.setAttribute("success", message);
					response.sendRedirect("employeeDraftedLeaveRequestWithPay.jsp");
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