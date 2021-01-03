package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/createEmployeePayrollRole")
public class CreateEmployeePayrollRoleController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = request.getRequestDispatcher("createEmployeePayrollRole.jsp");
		int createdBy = 0;
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			createdBy = (int)session.getAttribute("employeeId");
		}
		EmployeePayrollRoleNotification eprn = new EmployeePayrollRoleNotification();
		EmployeePayrollRoleDao eprd = new EmployeePayrollRoleDao();
		EmployeePayrollRole epr = new EmployeePayrollRole();
		epr.setCreatedBy(createdBy);
		int employeeId = Integer.parseInt(request.getParameter("employeeId"));
		epr.setEmployeeId(employeeId);
		int payrollRoleNameId = Integer.parseInt(request.getParameter("payrollRoleNameId"));
		epr.setPayrollRoleNameId(payrollRoleNameId);
		
		//Check if employee already has a role
		boolean isEmployeeExist = eprd.isEmployeeExist(epr.getEmployeeId());
		if(isEmployeeExist == false) {
			// Save data in the database table
			int count = eprd.createEmployeePayrollRole(epr);
			if(count >= 1) {
				session.setAttribute("success", eprn.getCreateEmployeePayrollRoleMessage(true));
				response.sendRedirect("createEmployeePayrollRole.jsp");
			}
			else {
				session.setAttribute("error", eprn.getCreateEmployeePayrollRoleMessage(false));
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", eprn.getEmployeeAlreadyExistErrorMessage());
			rd.forward(request, response);
			
		}
	}
}
