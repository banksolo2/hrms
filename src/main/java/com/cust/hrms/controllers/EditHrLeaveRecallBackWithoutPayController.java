package com.cust.hrms.controllers;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/editHrLeaveRecallBackWithoutPay")
public class EditHrLeaveRecallBackWithoutPayController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("hrEditPendingLeaveRecallBackWithoutPay.jsp");
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
				response.sendRedirect("hrLeaveRecallBackPendingApprovalWithoutPay.jsp");
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
					response.sendRedirect("hrLeaveRecallBackPendingApprovalWithoutPay.jsp");
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
						response.sendRedirect("hrLeaveRecallBackPendingApprovalWithoutPay.jsp");
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
