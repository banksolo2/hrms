package com.cust.hrms.controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import java.io.*;
import java.util.*;

@WebServlet("/createLeavePlan")
public class CreateLeavePlanController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = request.getRequestDispatcher("createLeavePlan.jsp");
		HttpSession session = request.getSession();
		int employeeId = Integer.parseInt(request.getParameter("employeeId"));
		int departmentId = Integer.parseInt(request.getParameter("departmentId"));
		int departmentHeadId = Integer.parseInt(request.getParameter("departmentHeadId"));
		String dates = request.getParameter("dates");
		String date[] = dates.split(" - ");
		String startDate = date[0];
		String endDate = date[1];
		DateDao dd = new DateDao();
		startDate = dd.convertDateFormat(startDate, "/");
		endDate = dd.convertDateFormat(endDate, "/");
		int leavePlanStatusId = Integer.parseInt(request.getParameter("leavePlanStatusId"));
		String currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		
		// Validate if leave plan date is in a red zone date
		RedZoneDao rzd = new RedZoneDao();
		RedZone rz = new RedZone();
		rz.setDepartmentId(departmentId);
		rz.setDateFrom(startDate);
		rz.setDateTo(endDate);
		boolean isDatesInRedZone = rzd.isDateInRedZone(rz);
		
		if(isDatesInRedZone == false) {
			//Check if start date is a validate date.
			boolean isStartDateValid = dd.isStartDateValid(startDate);
			if(isStartDateValid == true) {
				LeavePlanDao lpd = new LeavePlanDao();
				int noOfDays = lpd.getNoOfDays(startDate, endDate);
				//out.println("Number Of Days: "+ noOfDays);
				//Check leave plan number of days
				boolean isNoOfDaysValid = lpd.isEmployeeLeavePlanRequestedDaysValid(employeeId, currentYear, noOfDays);
				if(isNoOfDaysValid == true) {
					//Save leave plan in the database
					LeavePlan lp = new LeavePlan();
					lp.setEmployeeId(employeeId);
					lp.setDepartmentId(departmentId);
					lp.setDepartmentHeadId(departmentHeadId);
					lp.setStartDate(startDate);
					lp.setEndDate(endDate);
					lp.setNoOfDays(noOfDays);
					lp.setLeavePlanStatusId(leavePlanStatusId);
					lp.setOnBehalf("no");
					lp.setCreatedBy(employeeId);
					int count = lpd.createLeavePlan(lp);
					if(count >= 1) {
						LeavePlanStatusDao lpsd = new LeavePlanStatusDao();
						LeavePlanStatus lps = lpsd.getLeavePlanStatusById(lp.getLeavePlanStatusId());
						String message = null;
						if(lps.getCode().equals("drafted")) {
							message = "Leave plan has been drafted....";
						}
						else {
							message ="Leave plan has been sent for approval...";
						}
						session.setAttribute("success", message);
						response.sendRedirect("createLeavePlan.jsp");
					}
					else {
						session.setAttribute("error", "Enable to create leave plan....");
						rd.forward(request, response);
					}
					
				}
				else {
					session.setAttribute("error", "Your leave plan number of days exceeded....");
					rd.forward(request, response);
				}
			}
			else {
				session.setAttribute("error", "Your start date is a back dated date....");
				rd.forward(request, response);
			}
			
		}else {
			session.setAttribute("error", "Your leave plan is in a red zone period....");
			rd.forward(request, response);
		}
		
	}
}
