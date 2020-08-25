package com.cust.hrms.controllers;

import javax.servlet.*;
import java.io.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/createEmployeeRole")
public class CreateEmployeeRoleController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int employeeId = Integer.parseInt(request.getParameter("employeeId"));
		int roleId = Integer.parseInt(request.getParameter("roleId"));
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("createEmployeeRole.jsp");
		int createdBy = (int)session.getAttribute("employeeId");
		
		EmployeeRoleDao erd = new EmployeeRoleDao();
		EmployeeRole er = new EmployeeRole();
		er.setEmployeeId(employeeId);
		er.setRoleId(roleId);
		er.setCreatedBy(createdBy);
		
		//check if employee role already exist
		boolean isEmployeeRoleExist = erd.isEmployeeRoleExist(er);
		
		if(isEmployeeRoleExist == false) {
			int count = erd.createEmployeeRole(er);
			if(count >= 1) {
				session.setAttribute("success", "Employee role created...");
				response.sendRedirect("createEmployeeRole.jsp");
			}
			else {
				session.setAttribute("error", "Enable to create employee...");
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", "Employee role already exist...");
			rd.forward(request, response);
		}
	}
}
