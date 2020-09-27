package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/editSupervisorPendingLeaveRequest")
public class EditSupervisorPendingLeaveRequestController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("editSupervisorLeavePendingApproval.jsp");
		PrintWriter out = response.getWriter();
		NotificationMessageDao nmd = new NotificationMessageDao();
		int leaveId = Integer.parseInt(request.getParameter("leaveId"));
		LeaveDao ld = new LeaveDao();
		Leave l = ld.getLeaveById(leaveId);
		LeaveStatusDao lsd = new LeaveStatusDao();
		int updatedBy = (int) session.getAttribute("employeeId");
		l.setUpdatedBy(updatedBy);
		int leaveStatusId = Integer.parseInt(request.getParameter("leaveStatusId"));
		l.setLeaveStatusId(leaveStatusId);
		String comment = request.getParameter("comment");
		l.setComment(comment);
		
		//Save to database
		int count = ld.updateLeave(l);
		if(count >= 1) {
			LeaveStatus ls = lsd.getLeaveStatusById(l.getLeaveStatusId());
			String message = null;
			if(ls.getCode().equals("approved")) {
				message = nmd.getLeaveApprovedMessage("success");
			}
			else if(ls.getCode().equals("declined")) {
				message = nmd.getLeaveDeclinedMessage("success");
			}
			else if(ls.getCode().equals("sent_back_for_correction")) {
				message = nmd.getLeaveSentBackForCorrectionMessage("success");
			}
			else if(ls.getCode().equals("sent_to_hr_for_approval")){
				message = nmd.getLeaveSendToHrForApprovalMessage("success");
			}
			else {
				message = "Leave request has been updated...";
			}
			session.setAttribute("success", message);
			response.sendRedirect("supervisorLeavePendingApprovalWithPay.jsp");
		}
		else {
			session.setAttribute("error", "Enable to update leave request....");
			rd.forward(request, response);
		}
	}

}
