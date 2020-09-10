package com.cust.hrms.controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import java.util.*;

@WebServlet("/editLeavePlan")
public class EditLeavePlanController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("editLeavePlan.jsp");
		PrintWriter out = response.getWriter();
		LeavePlanDao lpd = new LeavePlanDao();
		int leavePlanId = Integer.parseInt(request.getParameter("leavePlanId"));
		LeavePlan lp = lpd.getLeavePlan(leavePlanId);
		String dates = request.getParameter("dates");
		String date[] = dates.split(" - ");
		String startDate = date[0];
		DateDao dd = new DateDao();
		startDate = dd.convertDateFormat(startDate, "/");
		lp.setStartDate(startDate);
		String endDate = date[1];
		endDate = dd.convertDateFormat(endDate, "/");
		lp.setEndDate(endDate);
		int leavePlanStatusId = Integer.parseInt(request.getParameter("leavePlanStatusId"));
		lp.setLeavePlanStatusId(leavePlanStatusId);
		String currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		
		//Check if start date is valid
		boolean isStartDateValid = dd.isStartDateValid(startDate);
		if(isStartDateValid == true) {
			//check if start Date is before end date
			boolean isStartDateBeforeEndDate = dd.compareDate(startDate, endDate);
			if(isStartDateBeforeEndDate == true) {
				lp.setNoOfDays(lpd.getNoOfDays(startDate, endDate));
				// Check number of days
				boolean isNoOfDaysValid = lpd.isEmployeeLeavePlanRequestedDaysValid(lp.getEmployeeId(), currentYear, lp.getNoOfDays());
				if(isNoOfDaysValid == true) {
					//Check if date is in a red zone period
					RedZoneDao rzd = new RedZoneDao();
					RedZone rz = new RedZone();
					rz.setDepartmentId(lp.getDepartmentId());
					rz.setDateFrom(lp.getStartDate());
					rz.setDateTo(lp.getEndDate());
					boolean isRedZonePeriod = rzd.isDateInRedZone(rz);
					if(isRedZonePeriod == false) {
						int count = lpd.updateLeavePlan(lp);
						if(count >= 1) {
							LeavePlanStatusDao lpsd = new LeavePlanStatusDao();
							LeavePlanStatus lps = lpsd.getLeavePlanStatusById(lp.getLeavePlanStatusId());
							String message = null;
							if(lps.getCode().equals("drafted")) {
								message = "Drafted leave plan has been updated...";
							}
							else {
								message = "Leave plan has been sent for approval...";
							}
							session.setAttribute("success", message);
							response.sendRedirect("allDraftedLeavePlan.jsp");
						}
						else {
							session.setAttribute("error", "Enable to update drafted leave plan....");
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
