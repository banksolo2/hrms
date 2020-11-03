package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.email.*;
import com.cust.hrms.email.message.*;
import com.cust.hrms.notification.*;
import java.util.*;
@WebServlet("/makeLeaveRequestWithPay")
public class MakeLeaveRequestWithPayController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
		HttpSession session = request.getSession();
		int createdBy = 0;
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			createdBy = (int)session.getAttribute("employeeId");
		}
		PrintWriter out = response.getWriter();
		String page = "makeLeaveRequestWithPay.jsp";
		RequestDispatcher rd = request.getRequestDispatcher("makeLeaveRequestWithPay.jsp");
		LeaveTypeDao ltd = new LeaveTypeDao();
		LeaveStatusDao lsd = new LeaveStatusDao();
		RedZoneDao rzd = new RedZoneDao();
		LeaveDao ld = new LeaveDao();
		DateDao dd = new DateDao();
		LeavePlanDao lpd = new LeavePlanDao();
		LeaveNotification ln = new LeaveNotification();
		LeaveEmailMessage lem = new LeaveEmailMessage();
		HrmsEmail he = new HrmsEmail();
		EmployeeDao ed = new EmployeeDao();
		Leave l = new Leave();
		l.setCreatedBy(createdBy);
		int employeeId = Integer.parseInt(request.getParameter("employeeId"));
		l.setEmployeeId(employeeId);
		int departmentId = Integer.parseInt(request.getParameter("departmentId"));
		l.setDepartmentId(departmentId);
		int supervisorId = Integer.parseInt(request.getParameter("supervisorId"));
		l.setSupervisorId(supervisorId);
		l.setWithPay("yes");
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
		String staffToNotify = "";
		for(int i = 0; i < employeesId.length; i++) {
			staffToNotify += "'"+employeesId[i]+"'";
			if(i != (employeesId.length - 1)) {
				staffToNotify += ":";
			}
		}
		l.setStaffToNotify(staffToNotify);
		int primaryReliefOfficeId = Integer.parseInt(request.getParameter("primaryReliefOfficeId"));
		l.setPrimaryReliefOfficeId(primaryReliefOfficeId);
		String secondaryReliefOffice = request.getParameter("secondaryReliefOfficeId");
		if(secondaryReliefOffice.trim().length() >= 1) {
			l.setSecondaryReliefOfficeId(Integer.parseInt(secondaryReliefOffice));
		}
		int leaveStatusId = Integer.parseInt(request.getParameter("leaveStatusId"));
		LeaveStatus ls = lsd.getLeaveStatusById(leaveStatusId);
		l.setLeaveStatusId(leaveStatusId);
		String currentYear  = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		//Check if leave request in Line with leave Plan
		/*if(l.getInlineWithLeavePlan().trim().toLowerCase().equals("yes") && lt.getCode().trim().equals("annual")) {
			LeavePlan lp = new LeavePlan();
			lp.setEmployeeId(l.getEmployeeId());
			lp.setStartDate(l.getStartDate());
			lp.setEndDate(l.getEndDate());
			boolean isLeaveRequestInLineWithLeavePlan = lpd.isLeaveRequestInLineWithLeavePlan(lp);
			if(isLeaveRequestInLineWithLeavePlan == false) {
				session.setAttribute("error", "Leave request is not in line with leave plan...");
				response.sendRedirect("makeLeaveRequestWithPay.jsp");
			}
		}*/
		
		boolean isEmployeeLeaveDaysValid = ld.isEmployeeLeavedaysValid(l.getEmployeeId(), l.getLeaveTypeId(), currentYear, l.getNoOfDays(), l.getWithPay());
		
		if(isEmployeeLeaveDaysValid == true) {
			//Check if leave request is in red zone
			RedZone rz = new RedZone();
			rz.setDepartmentId(l.getDepartmentId());
			rz.setDateFrom(l.getStartDate());
			rz.setDateTo(l.getEndDate());
			boolean isLeaveRequestInRedZone = rzd.isDateInRedZone(rz);
			if(isLeaveRequestInRedZone == false) {
				String message = null;
				int count = ld.createLeave(l);
				if(count >= 1) {
					if(ls.getCode().trim().equals("drafted")) {
						message = ln.getDraftedLeaveMessage(true);
					}
					else {
						message = ln.getSentLeaveToSupervisorForApprovalMessage(true);
						/* Send email notification
						 * 1) Employee
						 * 2) Supervisor*/
						if(he.isEmailEnable()) {
							//1) Employee
							Employee employee = ed.getEmployeeById(l.getEmployeeId());
							String employeeEmailAddress[] = { employee.getEmail()};
							String employeeData[] = {
									employee.getNameInitials(),
									"with_pay"
							};
							lem.getEmployeeSendLeaveToSupervisorForApprovalMessage(employeeEmailAddress, employeeData);
							
							//2)Supervisor
							Employee supervisor = ed.getEmployeeById(l.getSupervisorId());
							String supervisorEmail[] = { supervisor.getEmail() };
							String supervisorData[] = {
									supervisor.getNameInitials(),
									String.valueOf(l.getNoOfDays()),
									lt.getName(),
									l.getStartDate(),
									l.getEndDate(),
									l.getResumptionDate(),
									employee.getFirstName()+" "+employee.getMiddleName()+" "+employee.getLastName(),
									employee.getStaffId(),
									"with_pay"
							};
							lem.getSupervisorLeaveAwaitingApproval(supervisorEmail, supervisorData);
						}
					}
					session.setAttribute("success", message);
					response.sendRedirect("makeLeaveRequestWithPay.jsp");
				}
				else {
					if(ls.getCode().trim().equals("drafted")) {
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