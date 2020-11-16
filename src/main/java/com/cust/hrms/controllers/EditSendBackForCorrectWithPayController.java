package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.email.*;
import com.cust.hrms.notification.*;
import com.cust.hrms.email.message.*;

@WebServlet("/editSendBackForCorrectWithPay")
public class EditSendBackForCorrectWithPayController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("editLeaveRequestSentBackForCorrectionWithPay.jsp");
		PrintWriter out = response.getWriter();
		int updatedBy = 0;
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			updatedBy = (int)session.getAttribute("employeeId");
		}
		LeaveNotification ln = new LeaveNotification();
		HrmsEmail he = new HrmsEmail();
		EmployeeDao ed = new EmployeeDao();
		LeaveTypeDao ltd = new LeaveTypeDao();
		LeaveStatusDao lsd = new LeaveStatusDao();
		LeavePlanDao lpd = new LeavePlanDao();
		RedZoneDao rzd = new RedZoneDao();
		DateDao dd = new DateDao();
		LeaveDao ld = new LeaveDao();
		int leaveId = Integer.parseInt(request.getParameter("leaveId"));
		Leave l = ld.getLeaveById(leaveId);
		LeaveType lt = ltd.getLeaveTypeById(l.getLeaveTypeId());
		String dates[] = request.getParameter("dates").split(" - ");
		l.setStartDate(dd.convertDateFormat(dates[0], "/"));
		l.setEndDate(dd.convertDateFormat(dates[1], "/"));
		l.setResumptionDate(dd.addDaysSkippingWeekends(l.getEndDate(), 1));
		String date[] = l.getStartDate().split("-");
		String year = date[0];
		String inLineWithLeavePlan = request.getParameter("inLineWithLeavePlan");
		inLineWithLeavePlan = (inLineWithLeavePlan == null) ? "no" : inLineWithLeavePlan;
		l.setInlineWithLeavePlan(inLineWithLeavePlan);
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
		l.setLeaveStatusId(leaveStatusId);
		l.setUpdatedBy(updatedBy);
		boolean isLeaveRequestInLineWithLeavePlan = true;
		if(lt.getCode().equals("annual") && l.getInlineWithLeavePlan().equals("yes")) {
			LeavePlan lp = new LeavePlan();
			lp.setEmployeeId(l.getEmployeeId());
			lp.setStartDate(l.getStartDate());
			lp.setEndDate(l.getEndDate());
			isLeaveRequestInLineWithLeavePlan = lpd.isLeaveRequestInLineWithLeavePlan(lp);
		}
		
		if(isLeaveRequestInLineWithLeavePlan == true) {
			//Check if employee leave days is valid
			boolean isEmployeeLeaveDaysValid = ld.isEmployeeLeavedaysValid(l.getEmployeeId(), l.getLeaveTypeId(), year, l.getNoOfDays(), l.getWithPay());
			if(isEmployeeLeaveDaysValid) {
				//Check if leave request is in a red zone period
				RedZone rz = new RedZone();
				rz.setDateFrom(l.getStartDate());
				rz.setDateTo(l.getEndDate());
				rz.setDepartmentId(l.getDepartmentId());
				boolean isLeaveInRedZone = rzd.isDateInRedZone(rz);
				if(isLeaveInRedZone == false) {
					//Update leave request in the database
					int count = ld.updateLeave(l);
					if(count >= 1) {
						/* Sent email notification 
						 * 1) Employee
						 * 2) Supervisor*/
						if(he.isEmailEnable()) {
							LeaveEmailMessage lem = new LeaveEmailMessage();
							Employee emp = ed.getEmployeeById(l.getEmployeeId());
							String employeeFullName = emp.getFirstName()+" "+emp.getMiddleName()+" "+emp.getLastName();
							String employeeEmailAddress[] = { emp.getEmail()};
							Employee sup = ed.getEmployeeById(l.getSupervisorId());
							String supervisorEmailAddress[] = { sup.getEmail() };
							
							//1) Employee
							String employeeData[] = {
									emp.getNameInitials(),
									"with_pay"
							};
							lem.getEmployeeSendLeaveToSupervisorForApprovalMessage(employeeEmailAddress, employeeData);
							
							//2) Supervisor
							/*data value order {
							 *1) Name Initials, 
							 *2) days,
							 *3) leave type, 
							 *4) start date, 
							 *5) end date,
							 *6) resumption date,
							 *7) name of the employee
							 *8) staff ID 
							 *9) leave option  [with_pay or without_pay]
							 * }*/
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
						
						session.setAttribute("success", ln.getSentLeaveToSupervisorForApprovalMessage(true));
						session.setAttribute("pageTo", "employeeLeaveRequestSentBackForCorrectionWithPay.jsp");
						rd = request.getRequestDispatcher("success");
						rd.forward(request, response);
					}
					else {
						session.setAttribute("error", ln.getSentLeaveToSupervisorForApprovalMessage(false));
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
