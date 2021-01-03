package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/createEmployeeStatus")
public class CreateEmployeeStatusController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = request.getRequestDispatcher("createEmployeeStatus.jsp");
		int createdBy = 0;
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			createdBy = (int)session.getAttribute("employeeId");
		}
		EmployeeStatusNotification esn = new EmployeeStatusNotification();
		EmployeeStatusDao esd = new EmployeeStatusDao();
		EmployeeStatus es = new EmployeeStatus();
		es.setCreatedBy(createdBy);
		String name = request.getParameter("name");
		es.setName(name);
		
		// Check if name already exist
		boolean isNameExist = esd.isEmployeeStatusNameExist(es.getName());
		if(isNameExist == false) {
			// save data in the database table
			int count = esd.createEmployeeStatus(es);
			if(count >= 1) {
				session.setAttribute("success", esn.createEmployeeStatusMessage(true));
				response.sendRedirect("createEmployeeStatus.jsp");
			}
			else {
				session.setAttribute("error", esn.createEmployeeStatusMessage(false));
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", esn.getNameAlreadyExistErrorMessage());
			rd.forward(request, response);
		}
	}
}
