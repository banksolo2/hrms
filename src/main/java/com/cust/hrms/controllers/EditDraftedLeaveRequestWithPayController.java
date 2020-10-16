package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import java.util.*;

@WebServlet("/editDraftedLeaveRequestWithPay")
public class EditDraftedLeaveRequestWithPayController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("editDraftedLeaveRequest.jsp");
		PrintWriter out = response.getWriter();
		int leaveId = Integer.parseInt(request.getParameter("leaveId"));
		LeaveDao led = new LeaveDao();
		Leave le = led.getLeaveById(leaveId);
		LeaveStatusDao lsd = new LeaveStatusDao();
		LeaveTypeDao ltd = new LeaveTypeDao();
		String leaveTypeCode = request.getParameter("leaveTypeId");
		LeaveType lt = ltd.getLeaveTypeByCode(leaveTypeCode);
		le.setLeaveTypeId(lt.getLeaveTypeId());
		DateDao dd = new DateDao();
		String dates = request.getParameter("dates");
		String dateArr[] = dates.split(" - ");
		String startDate = dd.convertDateFormat(dateArr[0], "/");
		le.setStartDate(startDate);
		String endDate = dd.convertDateFormat(dateArr[1], "/");
		le.setEndDate(endDate);
		String resumptionDate = dd.addDaysSkippingWeekends(endDate, 1);
		le.setResumptionDate(resumptionDate);
		int days = led.getNoOfDays(startDate, endDate);
		le.setNoOfDays(days);
		String inLineWithLeavePlan = request.getParameter("inLineWithLeavePlan");
		le.setInlineWithLeavePlan(inLineWithLeavePlan);
		le.setUpdatedBy(le.getEmployeeId());
		int primaryReliefOfficeId = Integer.parseInt(request.getParameter("primaryReliefOfficeId"));
		le.setPrimaryReliefOfficeId(primaryReliefOfficeId);
		int secondaryReliefOfficeId = Integer.parseInt(request.getParameter("secondaryReliefOfficeId"));
		le.setSecondaryReliefOfficeId(secondaryReliefOfficeId);
		int leaveStatusId = Integer.parseInt(request.getParameter("leaveStatusId"));
		le.setLeaveStatusId(leaveStatusId);
		
		//Check if leave request is in line with leave plan
		if(le.getInlineWithLeavePlan().equals("yes")) {
			LeavePlanDao lpd = new LeavePlanDao();
			LeavePlan lp = new LeavePlan();
			lp.setEmployeeId(le.getEmployeeId());
			lp.setStartDate(le.getStartDate());
			lp.setEndDate(le.getEndDate());
			boolean isLeaveRequestInLineWithLeavePlan = lpd.isLeaveRequestInLineWithLeavePlan(lp);
			if(isLeaveRequestInLineWithLeavePlan == false) {
				session.setAttribute("error", "Leave request is not in line with leave plan...");
				rd.forward(request, response);
			}
		}
		
		//Check if start date is greater than or equals to today date
		boolean isStartDateGreaterThanOrEqualsTodayDate = dd.isStartDateValid(le.getStartDate());
		if(isStartDateGreaterThanOrEqualsTodayDate == true) {
			String currentYear  = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			boolean isLeaveRequestNoOfDaysValid = led.isEmployeeLeavedaysValid(le.getEmployeeId(), le.getLeaveTypeId(), currentYear, le.getNoOfDays(), le.getWithPay());
			if(isLeaveRequestNoOfDaysValid == true) {
				//Check red zone
				RedZoneDao rzd = new RedZoneDao();
				RedZone rz = new RedZone();
				rz.setDepartmentId(le.getDepartmentId());
				rz.setDateFrom(le.getStartDate());
				rz.setDateTo(le.getEndDate());
				boolean isDatesInRedZone = rzd.isDateInRedZone(rz);
				if(isDatesInRedZone == false) {
					//Update database table
					int count = led.updateLeave(le);
					if(count >= 1) {
						String message = null;
						if(le.getLeaveStatusId() == lsd.getLeaveStatusId("sent_to_supervisor_for_approval"))
							message = "Leave request has been sent to supervisor for approval...";
						else
							message = "drafted leave request has been update...";
						session.setAttribute("success", message);
						response.sendRedirect("employeeDraftedLeaveRequestWithPay.jsp");
					}
					else {
						session.setAttribute("error", "Enable to update leave request...");
						rd.forward(request, response);
					}
				}
				else {
					session.setAttribute("error", "Dates in a red zone period...");
					rd.forward(request, response);
				}
			}
			else {
				session.setAttribute("error", "Leave request exceed number of leave days...");
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", "Start date is less than today date.....");
			rd.forward(request, response);
		}
	}
}
