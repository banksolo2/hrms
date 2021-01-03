package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/deleteEmployeeStatus")
public class DeleteEmployeeStatusController extends HttpServlet{
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			EmployeeStatusNotification esn = new EmployeeStatusNotification();
			EmployeeStatusDao esd = new EmployeeStatusDao();
			int employeeStatusId = Integer.parseInt(request.getParameter("employeeStatusId"));
			EmployeeStatus es = esd.getEmployeeStatusById(employeeStatusId);
			int count = esd.deletedEmployeeStatus(es);
			if(count >= 1) {
				session.setAttribute("success", esn.deleteEmployeeStatusMessage(true));
			}
			else {
				session.setAttribute("error", esn.deleteEmployeeStatusMessage(false));
			}
			response.sendRedirect("allEmployeeStatusReport.jsp");
		}
	}

}
