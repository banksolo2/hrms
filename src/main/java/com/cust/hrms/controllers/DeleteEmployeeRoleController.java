package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/deleteEmployeeRole")
public class DeleteEmployeeRoleController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int employeeRoleId = Integer.parseInt(request.getParameter("employeeRoleId"));
		EmployeeRoleDao erd = new EmployeeRoleDao();
		EmployeeRole er = erd.getEmployeeRoleById(employeeRoleId);
		erd.deleteEmployeeRole(er);
		response.sendRedirect("allEmployeeRoles.jsp");
	}
}
