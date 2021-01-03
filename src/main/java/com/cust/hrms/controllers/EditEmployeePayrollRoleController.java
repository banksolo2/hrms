package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/editEmployeePayrollRole")
public class EditEmployeePayrollRoleController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("editEmployeePayrollRole.jsp");
		PrintWriter out = response.getWriter();
		int updatedBy = 0;
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			updatedBy = (int)session.getAttribute("employeeId");
		}
		EmployeePayrollRoleNotification eprn = new EmployeePayrollRoleNotification();
		EmployeePayrollRoleDao eprd = new EmployeePayrollRoleDao();
		int employeePayrollRoleId = Integer.parseInt(request.getParameter("employeePayrollRoleId"));
		EmployeePayrollRole epr = eprd.getEmployeePayrollRoleById(employeePayrollRoleId);
		epr.setUpdatedBy(updatedBy);
		int employeeId = Integer.parseInt(request.getParameter("employeeId"));
		epr.setEmployeeId(employeeId);
		int payrollRoleNameId = Integer.parseInt(request.getParameter("payrollRoleNameId"));
		epr.setPayrollRoleNameId(payrollRoleNameId);
		
		// Check if employee already has a role
		boolean isEmployeeExist = eprd.isEmployeeExistOnUpdate(epr);
		if(isEmployeeExist == false) {
			// update data in the database table
			int count = eprd.updateEmployeePayrollRole(epr);
			if(count >= 1) {
				session.setAttribute("success", eprn.getUpdateEmployeePayrollRoleMessage(true));
				response.sendRedirect("allEmployeePayrollRoles.jsp");
			}
			else {
				session.setAttribute("error", eprn.getUpdateEmployeePayrollRoleMessage(false));
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", eprn.getEmployeeAlreadyExistErrorMessage());
			rd.forward(request, response);
		}
	}
}
