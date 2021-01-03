package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/deleteEmployeePayrollRole")
public class DeleteEmployeePayrollRoleController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
		HttpSession session = request.getSession();
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			EmployeePayrollRoleNotification eprn = new EmployeePayrollRoleNotification();
			EmployeePayrollRoleDao eprd = new EmployeePayrollRoleDao();
			int employeePayrollRoleId = Integer.parseInt(request.getParameter("employeePayrollRoleId"));
			EmployeePayrollRole epr = eprd.getEmployeePayrollRoleById(employeePayrollRoleId);
			// delete data from database table
			int count = eprd.deleteEmployeePayrollRole(epr);
			if(count >= 1) {
				session.setAttribute("success", eprn.getDeleteEmployeePayrollRoleMessage(true));
			}
			else {
				session.setAttribute("success", eprn.getDeleteEmployeePayrollRoleMessage(true));
			}
			response.sendRedirect("allEmployeePayrollRoles.jsp");
		}
	}
}
