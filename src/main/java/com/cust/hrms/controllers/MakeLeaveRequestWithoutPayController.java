package com.cust.hrms.controllers;

import java.io.*;
import java.util.Calendar;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/makeLeaveRequestWithoutPay")
public class MakeLeaveRequestWithoutPayController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("makeLeaveRequestWithoutPay.jsp");
		PrintWriter out = response.getWriter();
		LeaveDao ld = new LeaveDao();
		LeaveStatusDao lsd = new LeaveStatusDao();
		LeaveTypeDao ltd = new LeaveTypeDao();
		RedZoneDao rzd = new RedZoneDao();
		DateDao dd = new DateDao();
		NotificationMessageDao nmd = new NotificationMessageDao();
		Leave l = new Leave();
		int employeeId = (int)session.getAttribute("employeeId");
		l.setEmployeeId(employeeId);
		l.setCreatedBy(employeeId);
		int departmentId = Integer.parseInt(request.getParameter("departmentId"));
		l.setDepartmentId(departmentId);
		int supervisorId = Integer.parseInt(request.getParameter("supervisorId"));
		l.setSupervisorId(supervisorId);
		l.setWithPay("no");
		String leaveTypeCode = request.getParameter("leaveTypeId");
		int leaveTypeId = ltd.getLeaveTypeId(leaveTypeCode);
		l.setLeaveTypeId(leaveTypeId);
		String dates = request.getParameter("dates");
		String date[] = dates.split(" - ");
		String startDate = dd.convertDateFormat(date[0], "/");
		l.setStartDate(startDate);
		String endDate = dd.convertDateFormat(date[1], "/");
		l.setEndDate(endDate);
		String resumptionDate = dd.addDaysSkippingWeekends(endDate, 1);
		l.setResumptionDate(resumptionDate);
		int noOfDays = ld.getNoOfDays(startDate, endDate);
		l.setNoOfDays(noOfDays);
		int primaryReliefOfficeId = Integer.parseInt(request.getParameter("primaryReliefOfficeId"));
		l.setPrimaryReliefOfficeId(primaryReliefOfficeId);
		int secondaryReliefOfficeId = Integer.parseInt(request.getParameter("secondaryReliefOfficeId"));
		l.setSecondaryReliefOfficeId(secondaryReliefOfficeId);
		l.setInlineWithLeavePlan("no");
		int leaveStatusId = Integer.parseInt(request.getParameter("leaveStatusId"));
		l.setLeaveStatusId(leaveStatusId);
		String currentYear  = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		
		//Check if start date
		boolean isStartDateGreaterEqualToday = dd.isStartDateValid(startDate);
		if(isStartDateGreaterEqualToday == true) {
			boolean isEmployeeLeaveDaysValid = ld.isEmployeeLeavedaysValid(employeeId, leaveTypeId, currentYear, noOfDays, l.getWithPay());
			if(isEmployeeLeaveDaysValid == true) {
				//Check if leave request is in red zone
				RedZone rz = new RedZone();
				rz.setDepartmentId(departmentId);
				rz.setDateFrom(startDate);
				rz.setDateTo(endDate);
				boolean isLeaveRequestInRedZone = rzd.isDateInRedZone(rz);
				if(isLeaveRequestInRedZone == false) {
					// Check if leave request already exist between start date and end date
					boolean isStartDateAndEndDateAlreadyExist = ld.isStartAndEndDateExist(l);
					if(isStartDateAndEndDateAlreadyExist == false) {
						int count = ld.createLeave(l);
						LeaveStatus ls = lsd.getLeaveStatusById(leaveStatusId);
						String message = null;
						if(count >= 1) {
							if(ls.getCode().equals("sent_to_supervisor_for_approval")) {
								message = nmd.getEmployeeLeaveSentToSupervisorForApprovalMessage("success");
							}
							else {
								message = nmd.getEmployeeLeaveSaveAsDraftMessage("success");
							}
							session.setAttribute("success", message);
							response.sendRedirect("makeLeaveRequestWithoutPay.jsp");
						}
						else {
							if(ls.getCode().equals("sent_to_supervisor_for_approval")) {
								message = nmd.getEmployeeLeaveSentToSupervisorForApprovalMessage("error");
							}
							else {
								message = nmd.getEmployeeLeaveSaveAsDraftMessage("error");
							}
							session.setAttribute("error", message);
							rd.forward(request, response);
						}
					}
					else {
						session.setAttribute("error", nmd.getLeaveAlreadyExistErrorMessage());
						rd.forward(request, response);
					}
				}
				else {
					session.setAttribute("error", nmd.getRedZoneErrorMessage());
					rd.forward(request, response);
				}
			}
			else {
				session.setAttribute("error", nmd.getEmployeeLeaveDaysErrorMessage());
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", nmd.isStartDateEqualGreaterThanTodayErrorMessage());
			rd.forward(request, response);
		}
	}
}
