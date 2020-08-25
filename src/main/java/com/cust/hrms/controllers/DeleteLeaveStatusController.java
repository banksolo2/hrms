package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/deleteLeaveStatus")
public class DeleteLeaveStatusController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int leaveStatusId = Integer.parseInt(request.getParameter("leaveStatusId"));
		LeaveStatusDao lsd = new LeaveStatusDao();
		LeaveStatus ls = lsd.getLeaveStatusById(leaveStatusId);
		lsd.deleteLeaveStatus(ls);
		response.sendRedirect("allLeaveStatues.jsp");
	}
}
