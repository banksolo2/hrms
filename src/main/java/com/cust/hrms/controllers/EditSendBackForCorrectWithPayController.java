package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/editSendBackForCorrectWithPay")
public class EditSendBackForCorrectWithPayController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("editLeaveRequestSentBackForCorrectionWithPay.jsp");
		PrintWriter out = response.getWriter();
		int leaveId = Integer.parseInt(request.getParameter("leaveId"));
		LeaveDao led = new LeaveDao();
		DateDao dd = new DateDao();
		Leave le = led.getLeaveById(leaveId);
		String dates[] = request.getParameter("dates").split(" - ");
		String startDate = dd.convertDateFormat(dates[0], "/");
		le.setStartDate(startDate);
		String endDate = dd.convertDateFormat(dates[1], "/");
		le.setEndDate(endDate);
		String resumptionDate = dd.addDaysSkippingWeekends(endDate, 1);
		le.setResumptionDate(resumptionDate);
		int noOfDays = led.getNoOfDays(startDate, endDate);
		le.setNoOfDays(noOfDays);
		String inLineWithLeavePlan = request.getParameter("inLineWithLeavePlan");
		le.setInlineWithLeavePlan(inLineWithLeavePlan);
		int primaryReliefOfficeId = Integer.parseInt(request.getParameter("primaryReliefOfficeId"));
		le.setPrimaryReliefOfficeId(primaryReliefOfficeId);
		int secondaryReliefOfficeId = Integer.parseInt(request.getParameter("secondaryReliefOfficeId"));
		le.setSecondaryReliefOfficeId(secondaryReliefOfficeId);
		int leaveStatusId = Integer.parseInt(request.getParameter("leaveStatusId"));
		le.setLeaveStatusId(leaveStatusId);
		int updatedBy = (int)session.getAttribute("employeeId");
		le.setUpdatedBy(updatedBy);
		String comment = request.getParameter("comment");
		le.setComment(comment);
		
		//Check if leave request is in line with leave plan
		if(le.getInlineWithLeavePlan().equals("yes")) {
			LeavePlanDao lpd = new LeavePlanDao();
			LeavePlan lp = new LeavePlan();
			lp.setStartDate(le.getStartDate());
			lp.setEndDate(le.getEndDate());
			lp.setEmployeeId(le.getEmployeeId());
			boolean isLeaveRequestInLineWithLeavePlan = lpd.isLeaveRequestInLineWithLeavePlan(lp);
			if(isLeaveRequestInLineWithLeavePlan == false) {
				session.setAttribute("error", "Leave request not in line with leave Plan...");
				rd.forward(request, response);
			}
		}
		
		boolean isStartDateGreaterThanTodayDate = dd.isStartDateValid(startDate);
		if(isStartDateGreaterThanTodayDate == true) {
			String arr[] = le.getStartDate().split("-");
			String year = arr[0].trim();
			//out.println(year);
			boolean isEmployeeLeaveDaysValid = led.isEmployeeLeavedaysValid(le.getEmployeeId(), le.getLeaveTypeId(), year, le.getNoOfDays(), le.getWithPay());
			if(isEmployeeLeaveDaysValid == true) {
				RedZoneDao rzd = new RedZoneDao();
				RedZone rz = new RedZone();
				rz.setDepartmentId(le.getDepartmentId());
				rz.setDateFrom(le.getStartDate());
				rz.setDateTo(le.getEndDate());
				boolean isLeaveRequestInRedZone = rzd.isDateInRedZone(rz);
				if(isLeaveRequestInRedZone == false) {
					//send leave request back to supervisor for approval
					int count = led.updateLeave(le);
					if(count >= 1) {
						session.setAttribute("success", "Leave request has been sent back to supervisor for approval...");
						response.sendRedirect("employeeLeaveRequestSentBackForCorrectionWithPay.jsp");
					}
					else {
						session.setAttribute("error", "Enable to send leave request back to supervisor for approval...");
						rd.forward(request, response);
					}
				}
				else {
					session.setAttribute("error", "Leave request dates is in a red zone period....");
					rd.forward(request, response);
				}
			}
			else {
				session.setAttribute("error", "Leave request exceed number of leave days you have...");
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", "Start date can't be less than today date....");
			rd.forward(request, response);
		}
	}
}
