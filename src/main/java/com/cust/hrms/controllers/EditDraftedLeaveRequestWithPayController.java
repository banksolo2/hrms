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
		RequestDispatcher rd = request.getRequestDispatcher("editDraftedLeaveRequest.jsp");
		PrintWriter out = response.getWriter();
		int updatedBy = 0;
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			updatedBy = (int)session.getAttribute("employeeId");
		}
		HrmsEmail he = new HrmsEmail();
		LeaveNotification ln = new LeaveNotification();
		LeavePlanDao lpd = new LeavePlanDao();
		RedZoneDao rzd = new RedZoneDao();
		LeaveTypeDao ltd = new LeaveTypeDao();
		LeaveStatusDao lsd = new LeaveStatusDao();
		LeaveDao ld = new LeaveDao();
		EmployeeDao ed = new EmployeeDao();
		DateDao dd = new DateDao();
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
		String inLineWithLeavePlan = request.getParameter("inLineWithLeavePlan");
		if(lt.getCode().equals("annual")) {
			l.setInlineWithLeavePlan(inLineWithLeavePlan);
		}
		else {
			l.setInlineWithLeavePlan("no");
		}
		String employeesId[] = request.getParameterValues("staffToNotify");
		String staffToNotify = ed.getEmployeesSpecialFormat(employeesId);
		l.setStaffToNotify(staffToNotify);
		int primaryReliefOfficeId = Integer.parseInt(request.getParameter("primaryReliefOfficeId"));
		l.setPrimaryReliefOfficeId(primaryReliefOfficeId);
		String secondaryReliefOffice = request.getParameter("secondaryReliefOfficeId").trim();
		int secondaryReliefOfficeLength = secondaryReliefOffice.length();
		int secondaryReliefOfficeId = (secondaryReliefOfficeLength < 1) ? 0 : Integer.parseInt(secondaryReliefOffice);
		l.setSecondaryReliefOfficeId(secondaryReliefOfficeId);
		int leaveStatusId = Integer.parseInt(request.getParameter("leaveStatusId"));
		LeaveStatus ls = lsd.getLeaveStatusById(leaveStatusId);
		l.setLeaveStatusId(ls.getLeaveStatusId());
		boolean isLeaveRequestInLineWithLeavePlan = true;
		if(lt.getCode().equals("annual") && l.getInlineWithLeavePlan().equals("yes")) {
			LeavePlan lp = new LeavePlan();
			lp.setEmployeeId(l.getEmployeeId());
			lp.setStartDate(l.getStartDate());
			lp.setEndDate(l.getEndDate());
			isLeaveRequestInLineWithLeavePlan = lpd.isLeaveRequestInLineWithLeavePlan(lp);
		}
		if(isLeaveRequestInLineWithLeavePlan) {
			String year  = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			//Check employee leave days is valid
			boolean isEmployeeLeaveDaysValid = ld.isEmployeeLeavedaysValid(l.getEmployeeId(), l.getLeaveTypeId(), year, l.getNoOfDays(), l.getWithPay());
			if(isEmployeeLeaveDaysValid) {
				//Check if leave request is in a red zone period
				RedZone rz = new RedZone();
				rz.setDepartmentId(l.getDepartmentId());
				rz.setDateFrom(l.getStartDate());
				rz.setDateTo(l.getEndDate());
				boolean isLeaveRequestInRedZone = rzd.isDateInRedZone(rz);
				if(isLeaveRequestInRedZone == false) {
					// Update data in the database table
					int count = ld.updateLeave(l);
					String message = null;
					if(count >= 1) {
						if(ls.getCode().equals("drafted")) {
							message = ln.getDraftedLeaveMessage(true);
						}
						else {
							message = ln.getSentLeaveToSupervisorForApprovalMessage(true);
							/*Sent Email Notification 
							 * 1) Employee 
							 * 2) Supervisor */
							if(he.isEmailEnable()) {
								LeaveEmailMessage lem = new LeaveEmailMessage();
								Employee emp = ed.getEmployeeById(l.getEmployeeId());
								Employee sup = ed.getEmployeeById(l.getSupervisorId());
								String employeeFullName = emp.getFirstName()+" "+emp.getMiddleName()+" "+emp.getLastName();
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
						session.setAttribute("pageTo", "employeeDraftedLeaveRequestWithPay.jsp");
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