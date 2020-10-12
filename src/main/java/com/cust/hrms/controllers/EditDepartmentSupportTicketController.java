package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.email.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/editDepartmentSupportTicket")
public class EditDepartmentSupportTicketController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("departmentEditSupportTicket.jsp");
		SupportTicketStatusDao stsd = new SupportTicketStatusDao();
		SupportTicketNotification stn = new SupportTicketNotification();
		SupportTicketDao std = new SupportTicketDao();
		int supportTicketId = Integer.parseInt(request.getParameter("supportTicketId"));
		SupportTicket st = std.getSupportTicketById(supportTicketId);
		int supportTicketStatusId = Integer.parseInt(request.getParameter("supportTicketStatusId"));
		st.setSupportTicketStatusId(supportTicketStatusId);
		String comment = request.getParameter("comment");
		st.setComment(comment);
		int updatedBy = (int)session.getAttribute("employeeId");
		st.setUpdatedBy(updatedBy);
		SupportTicketStatus sts = stsd.getSupportTicketStatusById(supportTicketStatusId);
		String message = null;
		
		//Update database table
		int count = std.updateSupportTicket(st);
		if(count >= 1) {
			if(sts.getCode().equals("closed")) {
				message = stn.getClosedSupportTicketMessage(true);
			}
			else {
				message = stn.getUnresolvedSupportTicketMessage(true);
			}
			session.setAttribute("success", message);
			response.sendRedirect("departmentResolveSupportTicketReport.jsp");
		}
		else {
			if(sts.getCode().equals("closed")) {
				message = stn.getClosedSupportTicketMessage(false);
			}
			else {
				message = stn.getUnresolvedSupportTicketMessage(false);
			}
			session.setAttribute("error", message);
			rd.forward(request, response);
		}
	}
}
