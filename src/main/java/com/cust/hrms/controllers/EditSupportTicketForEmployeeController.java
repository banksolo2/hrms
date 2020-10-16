package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.notification.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.email.*;
import com.cust.hrms.email.message.*;

@WebServlet("/editSupportTicketForEmployee")
public class EditSupportTicketForEmployeeController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("EditSupportTicketForEmployee.jsp");
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
		
		//Update database table
		int count = std.updateSupportTicket(st);
		if(count >= 1) {
			HrmsEmail he = new HrmsEmail();
			if(he.isEmailEnable()) {
				SupportTicketEmailMessage stem = new SupportTicketEmailMessage();
				EmployeeDao ed = new EmployeeDao();
				Employee e = ed.getEmployeeById(st.getCreatedBy());
				String emailAddress[] = {e.getEmail()};
				String data[] = {
						st.getIssueFor(),
						e.getNameInitials().toUpperCase(),
						String.valueOf(st.getSupportTicketId())
				};
				stem.getResolveSupportTicketMessage(emailAddress, data);
			}
			session.setAttribute("success", stn.getResolvedSupportTicketMessage(true));
			response.sendRedirect("allPendingSupportTicketReportForEmployee.jsp");
		}
		else {
			session.setAttribute("error", stn.getResolvedSupportTicketMessage(false));
			rd.forward(request, response);
		}
		
	}
}
