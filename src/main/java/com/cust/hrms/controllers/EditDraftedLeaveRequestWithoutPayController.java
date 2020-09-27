package com.cust.hrms.controllers;

import java.io.*;
import java.util.Calendar;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/editDraftedLeaveRequestWithoutPay")
public class EditDraftedLeaveRequestWithoutPayController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("editDraftedLeaveRequestWithoutPay.jsp");
		LeaveDao ld = new LeaveDao();
		LeaveTypeDao ltd = new LeaveTypeDao();
		LeaveStatusDao lsd = new LeaveStatusDao();
		RedZoneDao rzd = new RedZoneDao();
		DateDao dd = new DateDao();
		NotificationMessageDao nmd = new NotificationMessageDao();
		int leaveId = Integer.parseInt(request.getParameter("leaveId"));
		Leave l = ld.getLeaveById(leaveId);
		String leaveTypeCode = request.getParameter("leaveTypeId");
		int leaveTypeId = ltd.getLeaveTypeId(leaveTypeCode);
		l.setLeaveTypeId(leaveTypeId);
		String dates[] = request.getParameter("dates").split(" - ");
		String startDate = dd.convertDateFormat(dates[0], "/");
		l.setStartDate(startDate);
		String endDate = dd.convertDateFormat(dates[1], "/");
		l.setEndDate(endDate);
		String resumptionDate = dd.addDaysSkippingWeekends(endDate, 1);
		l.setResumptionDate(resumptionDate);
		int noOfDays = ld.getNoOfDays(startDate, endDate);
		l.setNoOfDays(noOfDays);
		int leaveStatusId = Integer.parseInt(request.getParameter("leaveStatusId"));
		l.setLeaveStatusId(leaveStatusId);
		int primaryReliefOfficeId = Integer.parseInt(request.getParameter("primaryReliefOfficeId"));
		l.setPrimaryReliefOfficeId(primaryReliefOfficeId);
		int secondaryReliefOfficeId = Integer.parseInt(request.getParameter("secondaryReliefOfficeId"));
		l.setSecondaryReliefOfficeId(secondaryReliefOfficeId);
		int updatedBy = (int)session.getAttribute("employeeId");
		l.setUpdatedBy(updatedBy);
		String currentYear  = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		
		//Check if start date
				boolean isStartDateGreaterEqualToday = dd.isStartDateValid(l.getStartDate());
				if(isStartDateGreaterEqualToday == true) {
					boolean isEmployeeLeaveDaysValid = ld.isEmployeeLeavedaysValid(l.getEmployeeId(), l.getLeaveTypeId(), currentYear, l.getNoOfDays(), l.getWithPay());
					if(isEmployeeLeaveDaysValid == true) {
						//Check if leave request is in red zone
						RedZone rz = new RedZone();
						rz.setDepartmentId(l.getDepartmentId());
						rz.setDateFrom(l.getStartDate());
						rz.setDateTo(l.getEndDate());
						boolean isLeaveRequestInRedZone = rzd.isDateInRedZone(rz);
						if(isLeaveRequestInRedZone == false) {
								int count = ld.updateLeave(l);
								LeaveStatus ls = lsd.getLeaveStatusById(l.getLeaveStatusId());
								String message = null;
								if(count >= 1) {
									if(ls.getCode().equals("sent_to_supervisor_for_approval")) {
										message = nmd.getEmployeeLeaveSentToSupervisorForApprovalMessage("success");
									}
									else {
										message = nmd.getEmployeeLeaveSaveAsDraftMessage("success");
									}
									session.setAttribute("success", message);
									response.sendRedirect("employeeDraftedLeaveRequestWithoutPay.jsp");
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
