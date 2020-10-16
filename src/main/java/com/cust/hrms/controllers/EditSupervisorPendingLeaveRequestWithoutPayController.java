package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/editSupervisorPendingLeaveRequestWithoutPay")
public class EditSupervisorPendingLeaveRequestWithoutPayController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("editSupervisorLeavePendingApprovalWithoutPay.jsp");
		LeaveDao ld = new LeaveDao();
		LeaveStatusDao lsd = new LeaveStatusDao();
		NotificationMessageDao nmd = new NotificationMessageDao();
		int leaveId = Integer.parseInt(request.getParameter("leaveId"));
		Leave l = ld.getLeaveById(leaveId);
		int leaveStatusId = Integer.parseInt(request.getParameter("leaveStatusId"));
		l.setLeaveStatusId(leaveStatusId);
		String comment = request.getParameter("comment");
		l.setComment(comment);
		int updatedBy = (int)session.getAttribute("employeeId");
		l.setUpdatedBy(updatedBy);
		
		int count = ld.updateLeave(l);
		LeaveStatus ls = lsd.getLeaveStatusById(leaveStatusId);
		String message = null;
		if(count >= 1) {
			if(ls.getCode().equals("declined")) {
				message = nmd.getLeaveDeclinedMessage("success");
			}
			else if(ls.getCode().equals("sent_back_for_correction")) {
				message = nmd.getLeaveSentBackForCorrectionMessage("success");
			}
			else if(ls.getCode().equals("sent_to_hr_for_approval")) {
				message = nmd.getLeaveSendToHrForApprovalMessage("success");
			}
			else {
				message = "Leave request has been updated....";
			}
			session.setAttribute("success", message);
			response.sendRedirect("supervisorLeavePendingApprovalWithoutPay.jsp");
		}
		else {
			if(ls.getCode().equals("declined")) {
				message = nmd.getLeaveDeclinedMessage("error");
			}
			else if(ls.getCode().equals("sent_back_for_correction")) {
				message = nmd.getLeaveSentBackForCorrectionMessage("error");
			}
			else if(ls.getCode().equals("sent_to_hr_for_approval")) {
				message = nmd.getLeaveSendToHrForApprovalMessage("error");
			}
			else {
				message = "Enable to update leave request...";
			}
			session.setAttribute("error", message);
			rd.forward(request, response);
		}
		
	}
}
