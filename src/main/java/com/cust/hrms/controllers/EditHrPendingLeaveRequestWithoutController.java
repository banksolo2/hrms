package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/editHrPendingLeaveRequestWithout")
public class EditHrPendingLeaveRequestWithoutController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("hrSupervisorLeavePendingApprovalWithoutPay.jsp");
		NotificationMessageDao nmd = new NotificationMessageDao();
		LeaveDao ld = new LeaveDao();
		LeaveStatusDao lsd = new LeaveStatusDao();
		int leaveId = Integer.parseInt(request.getParameter("leaveId"));
		Leave l = ld.getLeaveById(leaveId);
		int updatedBy = (int) session.getAttribute("employeeId");
		l.setUpdatedBy(updatedBy);
		int leaveStatusId = Integer.parseInt(request.getParameter("leaveStatusId"));
		l.setLeaveStatusId(leaveStatusId);
		String comment = request.getParameter("comment");
		l.setComment(comment);
		
		// Update leave table
		int count = ld.updateLeave(l);
		LeaveStatus ls = lsd.getLeaveStatusById(l.getLeaveStatusId());
		String message = null;
		if(count >= 1) {
			if(ls.getCode().equals("approved")) {
				message = nmd.getLeaveApprovedMessage("success");
			}
			else if(ls.getCode().equals("declined")) {
				message = nmd.getLeaveDeclinedMessage("success");
			}
			else if(ls.getCode().equals("sent_back_for_correction")) {
				message = nmd.getLeaveSentBackForCorrectionMessage("success");
			}
			else {
				message = "Leave request updated....";
			}
			session.setAttribute("success", message);
			response.sendRedirect("hrLeavePendingApprovalWithoutPay.jsp");
		}
		else {
			if(ls.getCode().equals("approved")) {
				message = nmd.getLeaveApprovedMessage("error");
			}
			else if(ls.getCode().equals("declined")) {
				message = nmd.getLeaveDeclinedMessage("error");
			}
			else if(ls.getCode().equals("sent_back_for_correction")) {
				message = nmd.getLeaveSentBackForCorrectionMessage("error");
			}
			else {
				message = "Enable to update leave...";
			}
			session.setAttribute("error", message);
			rd.forward(request, response);
		}
	}

}
