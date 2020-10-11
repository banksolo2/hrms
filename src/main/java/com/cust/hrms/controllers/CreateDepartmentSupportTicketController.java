package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.email.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/createDepartmentSupportTicket")
public class CreateDepartmentSupportTicketController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("createDepartmentSupportTicket.jsp");
		SupportTicketNotification stn = new SupportTicketNotification();
		SupportTicketStatusDao stsd = new SupportTicketStatusDao();
		SupportTicketDao std = new SupportTicketDao();
		DateDao dd = new DateDao();
		SupportTicket st = new SupportTicket();
		String issueReportDate = request.getParameter("issueReportDate");
		st.setIssueReportDate(dd.convertDateFormat(issueReportDate, "/"));
		int issueTypeId = Integer.parseInt(request.getParameter("issueTypeId"));
		st.setIssueTypeId(issueTypeId);
		String issueDescription = request.getParameter("issueDescription");
		st.setIssueDescription(issueDescription);
		int departmentId = Integer.parseInt(request.getParameter("departmentId"));
		st.setDepartmentId(departmentId);
		st.setSupportTicketStatusId(stsd.getSupportTicketStatusId("pending"));
		int createdBy = (int)session.getAttribute("employeeId");
		st.setCreatedBy(createdBy);
		st.setFileUrl("#");
		
		//insert into database
		int count = std.createSupportTicketForDepartment(st);
		if(count >= 1) {
			session.setAttribute("success", stn.getPendingSupportTicketMessage(true));
			response.sendRedirect("createDepartmentSupportTicket.jsp");
		}
		else {
			session.setAttribute("error", stn.getPendingSupportTicketMessage(false));
			rd.forward(request, response);
		}
	} 
}
