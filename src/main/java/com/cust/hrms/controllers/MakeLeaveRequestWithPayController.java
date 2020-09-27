package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import java.util.*;
@WebServlet("/makeLeaveRequestWithPay")
public class MakeLeaveRequestWithPayController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = request.getRequestDispatcher("makeLeaveRequestWithPay.jsp");
		EmployeeDao ed = new EmployeeDao();
		RedZoneDao rzd = new RedZoneDao();
		LevelDao lvd = new LevelDao();
		LeaveDao ld = new LeaveDao();
		LeaveTypeDao ltd = new LeaveTypeDao();
		LeaveStatusDao lsd = new LeaveStatusDao();
		LeavePlanDao lpd = new LeavePlanDao();
		Leave l = new Leave();
		int employeeId = Integer.parseInt(request.getParameter("employeeId"));
		l.setEmployeeId(employeeId);
		int departmentId = Integer.parseInt(request.getParameter("departmentId"));
		l.setDepartmentId(departmentId);
		int supervisorId = Integer.parseInt(request.getParameter("supervisorId"));
		l.setSupervisorId(supervisorId);
		String currentYear  = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		String leaveTypeCode = request.getParameter("leaveTypeId");
		int leaveTypeId = ltd.getLeaveTypeId(leaveTypeCode);
		l.setLeaveTypeId(leaveTypeId);
		String dates = request.getParameter("dates");
		String date[] = dates.split(" - ");
		DateDao dd = new DateDao();
		String startDate = dd.convertDateFormat(date[0], "/");
		l.setStartDate(startDate.trim());
		String endDate = dd.convertDateFormat(date[1], "/");
		l.setEndDate(endDate.trim());
		String resumptionDate = dd.addDaysSkippingWeekends(endDate.trim(), 1);
		l.setResumptionDate(resumptionDate);
		int noOfDays = ld.getNoOfDays(startDate, endDate);
		l.setNoOfDays(noOfDays);
		int primaryReliefOfficeId = Integer.parseInt(request.getParameter("primaryReliefOfficeId"));
		l.setPrimaryReliefOfficeId(primaryReliefOfficeId);
		int secondaryReliefOfficeId = Integer.parseInt(request.getParameter("secondaryReliefOfficeId"));
		l.setSecondaryReliefOfficeId(secondaryReliefOfficeId);
		String inLineWithLeavePlan = request.getParameter("inLineWithLeavePlan");
		l.setInlineWithLeavePlan(inLineWithLeavePlan);
		String withPay = request.getParameter("withPay");
		l.setWithPay(withPay);
		int leaveStatusId = Integer.parseInt(request.getParameter("leaveStatusId"));
		l.setLeaveStatusId(leaveStatusId);
		l.setCreatedBy(employeeId);
		
		/*Check Start Date */
		boolean isStartDateValid = dd.isStartDateValid(startDate);
		if(isStartDateValid == true) {
			//Check employee leave days
			boolean isEmployeeLeaveDaysValid = ld.isEmployeeLeavedaysValid(employeeId, leaveTypeId, currentYear, noOfDays, withPay);
			if(isEmployeeLeaveDaysValid == true) {
				//Check if leave request is in red zone
				RedZone rz = new RedZone();
				rz.setDepartmentId(departmentId);
				rz.setDateFrom(startDate);
				rz.setDateTo(endDate);
				boolean isLeaveRequestInRedZone = rzd.isDateInRedZone(rz);
				if(isLeaveRequestInRedZone == false) {
					//Check if leave request is in line with Leave Plan
					if(l.getInlineWithLeavePlan().toLowerCase().equals("yes") && leaveTypeId == ltd.getLeaveTypeId("annual")) {
						LeavePlan lp = new LeavePlan();
						lp.setEmployeeId(employeeId);
						lp.setStartDate(startDate);
						lp.setEndDate(endDate);
						boolean isLeaveRequestInLineWithLeavePlan = lpd.isLeaveRequestInLineWithLeavePlan(lp);
						if(isLeaveRequestInLineWithLeavePlan == false) {
							session.setAttribute("error", "Leave request is not in line with leave plan...");
							rd.forward(request, response);
						}
					}
					// Check if leave request already exist between start date and end date
					boolean isStartDateAndEndDateAlreadyExist = ld.isStartAndEndDateExist(l);
					if(isStartDateAndEndDateAlreadyExist == false) {
						//create leave request in the database
						int count = ld.createLeave(l);
						if(count >= 1) {
							String message = null;
							if(l.getLeaveStatusId() == lsd.getLeaveStatusId("drafted")) message = "Leave request has been drafted...";
							else message = "Leave request has been sent to supervisor for approval...";
							session.setAttribute("success", message);
							response.sendRedirect("makeLeaveRequestWithPay.jsp");
						}
						else {
							session.setAttribute("error", "enable to create leave request...");
							rd.forward(request, response);
						}
					
					}
					else {
						session.setAttribute("error", "Leave request already exist between the start date and end date...");
						rd.forward(request, response);
					}
				}
				else {
					session.setAttribute("error", "Leave request is in a red zone period....");
					rd.forward(request, response);
				}
			}
			else {
				session.setAttribute("error", "You have exceeded the number of your leave days.....");
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", "Start Date is out dated date....");
			rd.forward(request, response);
		}
	}
}
