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
		RequestDispatcher rd = request.getRequestDispatcher("makeLeaveRequestWithPay.jsp");
		int createdBy = 0;
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			createdBy = (int)session.getAttribute("employeeId");
		}
		PrintWriter out = response.getWriter();
		LeaveNotification ln = new LeaveNotification();
		HrmsEmail he = new HrmsEmail();
		DateDao dd = new DateDao();
		RedZoneDao rzd = new RedZoneDao();
		LeavePlanDao lpd = new LeavePlanDao();
		LeaveTypeDao ltd = new LeaveTypeDao();
		LeaveStatusDao lsd = new LeaveStatusDao();
		EmployeeDao ed = new EmployeeDao();
		LeaveDao ld = new LeaveDao();
		Leave l = new Leave();
		l.setCreatedBy(createdBy);
		int employeeId = Integer.parseInt(request.getParameter("employeeId"));
		l.setEmployeeId(employeeId);
		int supervisorId = Integer.parseInt(request.getParameter("supervisorId"));
		l.setSupervisorId(supervisorId);
		int departmentId = Integer.parseInt(request.getParameter("departmentId"));
		l.setDepartmentId(departmentId);
		String year = request.getParameter("year");
		String leaveTypeCode = request.getParameter("leaveTypeId");
		LeaveType lt = ltd.getLeaveTypeByCode(leaveTypeCode);
		l.setLeaveTypeId(lt.getLeaveTypeId());
		String dates[] = request.getParameter("dates").split(" - ");
		l.setStartDate(dd.convertDateFormat(dates[0], "/"));
		l.setEndDate(dd.convertDateFormat(dates[1], "/"));
		l.setResumptionDate(dd.addDaysSkippingWeekends(l.getEndDate(), 1));
		l.setNoOfDays(ld.getNoOfDays(l.getStartDate(), l.getEndDate()));
		String inLineWithLeavePlan = request.getParameter("inLineWithLeavePlan");
		if(lt.getCode().equals("annual")) {
			l.setInlineWithLeavePlan(inLineWithLeavePlan);
		}
		else {
			l.setInlineWithLeavePlan("no");
		}
		l.setWithPay("yes");
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
		String secondaryReliefOffice = request.getParameter("secondaryReliefOfficeId").trim();
		int secondaryReliefOfficeLength = secondaryReliefOffice.length();
		int secondaryReliefOfficeId = (secondaryReliefOfficeLength < 1) ? 0 : Integer.parseInt(secondaryReliefOffice);
		l.setSecondaryReliefOfficeId(secondaryReliefOfficeId);
		int leaveStatusId = Integer.parseInt(request.getParameter("leaveStatusId"));
		LeaveStatus ls = lsd.getLeaveStatusById(leaveStatusId);
		l.setLeaveStatusId(leaveStatusId);
		boolean isInLineWithLeavePlan = true;
		if(lt.getCode().equals("annual") && l.getInlineWithLeavePlan().equals("yes")) {
			LeavePlan lp = new LeavePlan();
			lp.setEmployeeId(l.getEmployeeId());
			lp.setStartDate(l.getStartDate());
			lp.setEndDate(l.getEndDate());
			isInLineWithLeavePlan = lpd.isLeaveRequestInLineWithLeavePlan(lp);
		}
		if(isInLineWithLeavePlan) {
			//Check if employee leave days valid
			boolean isEmployeeLeaveDaysValid = ld.isEmployeeLeavedaysValid(l.getEmployeeId(), lt.getLeaveTypeId(), year, l.getNoOfDays(), l.getWithPay());
			if(isEmployeeLeaveDaysValid) {
				//Check if leave request is in a red zone period
				RedZone rz = new RedZone();
				rz.setDepartmentId(l.getDepartmentId());
				rz.setDateFrom(l.getStartDate());
				rz.setDateTo(l.getEndDate());
			
				boolean isRedZonePeriod = rzd.isDateInRedZone(rz);
				if(isRedZonePeriod == false) {
					//save information in the database table
					int count = ld.createLeave(l);
					String message = null;
					if(count >= 1) {
						if(ls.getCode().equals("drafted")) {
							message = ln.getDraftedLeaveMessage(true);
						}
						else {
							message = ln.getSentLeaveToSupervisorForApprovalMessage(true);
							/* Send email notification
							 * 1) Employee
							 * 2) Supervisor*/
							if(he.isEmailEnable()) {
								LeaveEmailMessage lem = new LeaveEmailMessage(); 
								Employee emp = ed.getEmployeeById(l.getEmployeeId());
								Employee sup = ed.getEmployeeById(l.getSupervisorId());
								String employeeFullName = emp.getFirstName()+" "+emp.getMiddleName()+" "+emp.getLastName();
								String supervisorFullName = sup.getFirstName()+" "+sup.getMiddleName()+" "+sup.getLastName();
								String employeeEmailAddress[] = { emp.getEmail() };
								String supervisorEmailAddress[] = { sup.getEmail() };
								
								//1) Employee
								String employeeData[] = {
										emp.getNameInitials(),
										"with_pay"
								};
								lem.getEmployeeSendLeaveToSupervisorForApprovalMessage(employeeEmailAddress, employeeData);
								
								//2) Supervisor
								String supervisorData[] = {
										sup.getNameInitials(),
										String.valueOf(l.getNoOfDays()),
										lt.getName(),
										dd.changeFormatDate(l.getStartDate()),
										dd.changeFormatDate(l.getEndDate()),
										dd.changeFormatDate(l.getResumptionDate()),
										employeeFullName,
										emp.getStaffId(),
										"with_pay"
								};
								lem.getSupervisorLeaveAwaitingApproval(supervisorEmailAddress, supervisorData);
							}
						}
						session.setAttribute("success", message);
						session.setAttribute("pageTo", "makeLeaveRequestWithPay.jsp");
						rd = request.getRequestDispatcher("success");
						rd.forward(request, response);
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
		else {
			session.setAttribute("error", ln.getInlineWithLeavePlanErrorMessage());
			rd.forward(request, response);
		}
		
	}
}