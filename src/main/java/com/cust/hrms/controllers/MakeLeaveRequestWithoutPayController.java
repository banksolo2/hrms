package com.cust.hrms.controllers;

import java.io.*;
import java.util.Calendar;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.email.*;
import com.cust.hrms.email.message.*;
import com.cust.hrms.notification.*;

@WebServlet("/makeLeaveRequestWithoutPay")
public class MakeLeaveRequestWithoutPayController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		HttpSession session = request.getSession();
		int createdBy = 0;
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			createdBy = (int)session.getAttribute("employeeId");
		}
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = request.getRequestDispatcher("makeLeaveRequestWithoutPay.jsp");
		LeaveDao ld = new LeaveDao();
		HrmsEmail he = new HrmsEmail();
		LeaveNotification ln = new LeaveNotification();
		RedZoneDao rzd = new RedZoneDao();
		LeaveTypeDao ltd = new LeaveTypeDao();
		LeaveStatusDao lsd = new LeaveStatusDao();
		EmployeeDao ed = new EmployeeDao();
		DateDao dd = new DateDao();
		String year = request.getParameter("year");
		Leave l = new Leave();
		l.setWithPay("no");
		int employeeId = Integer.parseInt(request.getParameter("employeeId"));
		l.setEmployeeId(employeeId);
		int supervisorId = Integer.parseInt(request.getParameter("supervisorId"));
		l.setSupervisorId(supervisorId);
		l.setCreatedBy(createdBy);
		int departmentId = Integer.parseInt(request.getParameter("departmentId"));
		l.setDepartmentId(departmentId);
		String leaveTypeCode = request.getParameter("leaveTypeId");
		LeaveType lt = ltd.getLeaveTypeByCode(leaveTypeCode);
		l.setLeaveTypeId(lt.getLeaveTypeId());
		String dates[] = (request.getParameter("dates")).split(" - ");
		l.setStartDate(dd.convertDateFormat(dates[0], "/"));
		l.setEndDate(dd.convertDateFormat(dates[1], "/"));
		l.setResumptionDate(dd.addDaysSkippingWeekends(l.getEndDate(), 1));
		l.setNoOfDays(ld.getNoOfDays(l.getStartDate(), l.getEndDate()));
		int primaryReliefOfficeId = Integer.parseInt(request.getParameter("primaryReliefOfficeId"));
		l.setPrimaryReliefOfficeId(primaryReliefOfficeId);
		String secondaryReliefOffice = request.getParameter("secondaryReliefOfficeId").trim();
		int secondaryReliefOfficeId = 0;
		if(secondaryReliefOffice.length() >= 1) {
			secondaryReliefOfficeId = Integer.parseInt(secondaryReliefOffice);
		}
		l.setSecondaryReliefOfficeId(secondaryReliefOfficeId);
		int leaveStatusId = Integer.parseInt(request.getParameter("leaveStatusId"));
		LeaveStatus ls = lsd.getLeaveStatusById(leaveStatusId);
		l.setLeaveStatusId(leaveStatusId);
		l.setInlineWithLeavePlan("no");
		String staffToNotify = ed.convertEmployeeIdArraysToString(request.getParameterValues("staffToNotify"));
		l.setStaffToNotify(staffToNotify);
		
		//Check if employee leave days is valid
		boolean isEmployeeLeaveDaysValid = ld.isEmployeeLeavedaysValid(l.getEmployeeId(), l.getLeaveTypeId(), year, l.getNoOfDays(), l.getWithPay());
		if(isEmployeeLeaveDaysValid) {
			//Check if leave request is in a red zone period
			RedZone rz = new RedZone();
			rz.setDepartmentId(l.getDepartmentId());
			rz.setDateFrom(l.getStartDate());
			rz.setDateTo(l.getEndDate());
			boolean isDatesInRedZone = rzd.isDateInRedZone(rz);
			if(isDatesInRedZone == false) {
				//Save leave request in the database
				int count = ld.createLeave(l);
				String message = null;
				if(count >= 1) {
					if(ls.getCode().equals("drafted")) {
						message = ln.getDraftedLeaveMessage(true);
					}
					else {
						message = ln.getSentLeaveToSupervisorForApprovalMessage(true);
						/*Send email notification
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
					response.sendRedirect("makeLeaveRequestWithoutPay.jsp");
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
