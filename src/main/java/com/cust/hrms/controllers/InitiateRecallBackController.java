package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/initiateRecallBack")
public class InitiateRecallBackController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		NotificationMessageDao nmd = new NotificationMessageDao();
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("initiateLeaveCallBackWithPay.jsp");
		int leaveId = Integer.parseInt(request.getParameter("leaveId"));
		LeaveDao ld = new LeaveDao();
		Leave le = ld.getLeaveById(leaveId);
		String comment = request.getParameter("comment");
		le.setComment(comment);
		LeaveStatusDao lsd = new LeaveStatusDao();
		int leaveStatusId = lsd.getLeaveStatusId("initiate_recall_back");
		le.setLeaveStatusId(leaveStatusId);
		int updatedBy = (int) session.getAttribute("employeeId");
		le.setUpdatedBy(updatedBy);
		
		int count = ld.updateLeave(le);
		if(count >= 1) {
			session.setAttribute("success", nmd.getInitiateLeaveRecallBackMessage("success"));
			response.sendRedirect("supervisorApprovedLeaveRequestReportWithPay.jsp");
		}
		else {
			session.setAttribute("error", nmd.getInitiateLeaveRecallBackMessage("error"));
			rd.forward(request, response);
		}
	}

}
