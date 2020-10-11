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
	}
}
