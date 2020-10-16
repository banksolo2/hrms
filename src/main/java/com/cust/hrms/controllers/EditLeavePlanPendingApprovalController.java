package com.cust.hrms.controllers;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.models.*;
import com.cust.hrms.dao.*;

@WebServlet("/editLeavePlanPendingApproval")
public class EditLeavePlanPendingApprovalController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		int userId = (int)session.getAttribute("employeeId");
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = request.getRequestDispatcher("editLeavePlanSentForApproval.jsp");
		LeavePlanDao lpd = new LeavePlanDao();
		int leavePlanId = Integer.parseInt(request.getParameter("leavePlanId"));
		LeavePlan lp = lpd.getLeavePlan(leavePlanId);
		lp.setComment(request.getParameter("comment"));
		lp.setLeavePlanStatusId(Integer.parseInt(request.getParameter("leavePlanStatusId")));
		lp.setUpdatedBy(userId);
		
		//Update leave plan in database 
		int count = lpd.updateLeavePlan(lp);
		String message = null;
		if(count >= 1) {
			LeavePlanStatusDao lpsd = new LeavePlanStatusDao();
			if(lpsd.getLeavePlanStatusId("approved") == lp.getLeavePlanStatusId()) message = "Leave plan has been approved....";
			else if(lpsd.getLeavePlanStatusId("declined") == lp.getLeavePlanStatusId()) message = "Leave plan has been declined.....";
			else if(lpsd.getLeavePlanStatusId("sent_back_for_correction") == lp.getLeavePlanStatusId()) message = "Leave plan has been sent back for correction...";
			session.setAttribute("success", message);
			response.sendRedirect("hodLeavePlanSendForApproval.jsp");
		}
		else {
			session.setAttribute("error", "Enable to update leave Plan.......");
			rd.forward(request, response);
		}
		
	}
}
