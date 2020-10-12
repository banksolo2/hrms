package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/editSupportTicketForDepartment")
public class EditSupportTicketForDepartmentController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("editSupportTicketForDepartment.jsp");
		SupportTicketNotification stn = new SupportTicketNotification();
		SupportTicketStatusDao stsd = new SupportTicketStatusDao();
		SupportTicketDao std = new SupportTicketDao();
		int supportTicketId = Integer.parseInt(request.getParameter("supportTicketId"));
		SupportTicket st = std.getSupportTicketById(supportTicketId);
		String comment = request.getParameter("comment");
		st.setComment(comment);
		st.setSupportTicketStatusId(stsd.getSupportTicketStatusId("resolved"));
		int employeeId = (int)session.getAttribute("employeeId");
		st.setUpdatedBy(employeeId);
		st.setResolvedBy(employeeId);
		
		//Update database
		int count = std.updateSupportTicket(st);
		if(count >= 1) {
			session.setAttribute("success", stn.getResolvedSupportTicketMessage(true));
			response.sendRedirect("allPendingSupportTicketReportForDepartment.jsp");
		}
		else {
			session.setAttribute("error", stn.getResolvedSupportTicketMessage(false));
			rd.forward(request, response);
		}
	}
}
