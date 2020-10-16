package com.cust.hrms.controllers;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/editLeavePlanSentForCorrection")
public class EditLeavePlanSentCorrectionController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("editLeavePlanSentForCorrection.jsp");
		int leavePlanId = Integer.parseInt(request.getParameter("leavePlanId"));
		LeavePlanDao lpd = new LeavePlanDao();
		LeavePlan lp = lpd.getLeavePlan(leavePlanId);
		String dates = request.getParameter("dates");
		String date[] = dates.split(" - ");
		String startDate = date[0];
		DateDao dd = new DateDao();
		startDate = dd.convertDateFormat(startDate, "/");
		lp.setStartDate(startDate);
		String endDate = date[1];
		endDate = dd.convertDateFormat(endDate, "/");
		//out.println(endDate);
		lp.setEndDate(endDate);
		LeavePlanStatusDao lpsd = new LeavePlanStatusDao();
		int leavePlanStatusId = lpsd.getLeavePlanStatusId("send_for_approval");
		lp.setLeavePlanStatusId(leavePlanStatusId);
		String currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		int noOfDays = lpd.getNoOfDays(lp.getStartDate(), lp.getEndDate());
		lp.setNoOfDays(noOfDays);
		String comment = request.getParameter("comment");
		lp.setComment(comment);
		
		//Check if start date is valid date
		boolean isStartDateValid = dd.isStartDateValid(lp.getStartDate());
		if(isStartDateValid == true) {
			//Check if start date is before end date
			boolean isStartDateBeforeEndDate = dd.compareDate(lp.getStartDate(), lp.getEndDate());
			if(isStartDateBeforeEndDate == true) {
				// Check number of days
				boolean isNoOfDaysValid = lpd.isEmployeeLeavePlanRequestedDaysValid(lp.getEmployeeId(), currentYear, lp.getNoOfDays());
				if(isNoOfDaysValid == true) {
					//Check if dates is in red zone
					RedZoneDao rzd =new RedZoneDao();
					RedZone rz = new RedZone();
					rz.setDepartmentId(lp.getDepartmentId());
					rz.setDateFrom(lp.getStartDate());
					rz.setDateTo(lp.getEndDate());
					boolean isRedZonePeriod = rzd.isDateInRedZone(rz);
					if(isRedZonePeriod == false) {
						int count = lpd.updateLeavePlan(lp);
						if(count >= 1) {
							session.setAttribute("success", "Leave plan has been sent for approval....");
							response.sendRedirect("allLeavePlanSentForCorrection.jsp");
						}
						else {
							session.setAttribute("error", "Enable to send leave plan for approval........");
							rd.forward(request, response);
						}
					}
					else {
						session.setAttribute("error", "Your leave plan is in a red zone period....");
						rd.forward(request, response);
					}
				}
				else {
					session.setAttribute("error", "Your leave plan number of days exceeded....");
					rd.forward(request, response);
				}
			}
			else {
				session.setAttribute("error", "End Date can't be before start date.....");
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", "Start date is invalid because it is a back dated date....");
			rd.forward(request, response);
		}
	}
}
