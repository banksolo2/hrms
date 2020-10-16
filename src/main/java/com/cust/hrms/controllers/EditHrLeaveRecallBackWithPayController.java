package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.models.*;
import com.cust.hrms.dao.*;
import java.text.*;
import java.util.*;

@WebServlet("/editHrLeaveRecallBackWithPay")
public class EditHrLeaveRecallBackWithPayController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("hrEditPendingLeaveRecallBackWithPay.jsp");
		int leaveId = Integer.parseInt(request.getParameter("leaveId"));
		LeaveDao ld = new LeaveDao();
		Leave l = ld.getLeaveById(leaveId);
		LeaveStatusDao lsd = new LeaveStatusDao();
		int updatedBy = (int) session.getAttribute("employeeId");
		l.setUpdatedBy(updatedBy);
		String comment = request.getParameter("comment");
		l.setComment(comment);
		int leaveStatusId = Integer.parseInt(request.getParameter("leaveStatusId"));
		LeaveStatus ls = lsd.getLeaveStatusById(leaveStatusId);
		NotificationMessageDao nmd = new NotificationMessageDao();
		String message = null;
		int count = 0;
		if(ls.getCode().equals("decline_leave_recall_back")) {
			l.setLeaveStatusId(lsd.getLeaveStatusId("approved"));
			count = ld.updateLeave(l);
			if(count >= 1) {
				message = nmd.getDeclinedLeaveRecallBackMessage("success");
				session.setAttribute("success", message);
				response.sendRedirect("hrLeaveRecallBackPendingApproval.jsp");
			}
			else {
				message = nmd.getDeclinedLeaveRecallBackMessage("error");
				session.setAttribute("error", message);
				rd.forward(request, response);
			}
		}
		else {
			DateDao dd = new DateDao();
			boolean isStartDateBeforeToday = dd.isStartDateValid(l.getStartDate());
			boolean isEndDateBeforeToday = dd.isStartDateValid(l.getEndDate());
			
			if(isStartDateBeforeToday == true) {
				l.setLeaveStatusId(leaveStatusId);
				count = ld.updateLeave(l);
				if(count >= 1) {
					message = nmd.getApprovedLeaveRecallBackMessage("success");
					session.setAttribute("success", message);
					response.sendRedirect("hrLeaveRecallBackPendingApproval.jsp");
				}
				else {
					message = nmd.getApprovedLeaveRecallBackMessage("error");
					session.setAttribute("error", message);
					rd.forward(request, response);
				}
			}
			else if((isStartDateBeforeToday == false) && (isEndDateBeforeToday == true)) {
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String today = sdf.format(date);
				l.setLeaveStatusId(leaveStatusId);
				count = ld.updateLeave(l);
				if(count >= 1) {
					l.setLeaveId(0);
					l.setEndDate(today);
					l.setResumptionDate(dd.addDaysSkippingWeekends(today, 1));
					l.setNoOfDays(ld.getNoOfDays(l.getStartDate(), l.getEndDate()));
					l.setLeaveStatusId(lsd.getLeaveStatusId("approved"));
					count = ld.createLeave(l);
					if(count >= 1) {
						message = nmd.getApprovedLeaveRecallBackMessage("success");
						session.setAttribute("success", message);
						response.sendRedirect("hrLeaveRecallBackPendingApproval.jsp");
					}
					else {
						message = nmd.getApprovedLeaveRecallBackMessage("error");
						session.setAttribute("error", message);
						rd.forward(request, response);
					}
				}
				else {
					message = nmd.getApprovedLeaveRecallBackMessage("error");
					session.setAttribute("error", message);
					rd.forward(request, response);
				}
			}
			else {
				message = nmd.getApprovedLeaveRecallBackMessage("error");
				session.setAttribute("error", message);
				rd.forward(request, response);
			}
		}
	}
}
