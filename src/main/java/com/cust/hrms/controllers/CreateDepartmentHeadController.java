package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/createDepartmentHead")
public class CreateDepartmentHeadController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("createDepartmentHead.jsp");
		int createdBy = (int)session.getAttribute("employeeId");
		int employeeId = Integer.parseInt(request.getParameter("employeeId"));
		int departmentId = Integer.parseInt(request.getParameter("departmentId"));
		
		DepartmentHeadDao dhd = new DepartmentHeadDao();
		DepartmentHead dh = new DepartmentHead();
		
		//Check if department head exist
		boolean isDepartmentHeadExist = dhd.isDepartmentHeadExistByDepartment(departmentId);
		if(isDepartmentHeadExist == false) {
			dh.setEmployeeId(employeeId);
			dh.setDepartmentId(departmentId);
			dh.setCreatedBy(createdBy);
			boolean isEmployeeInDepartment = dhd.isEmployeeExistInDepartment(dh);
			if(isEmployeeInDepartment == true) {
				int count = dhd.createDepartmentHead(dh);
				if(count >= 1) {
					session.setAttribute("success", "Department head has been created...");
					response.sendRedirect("createDepartmentHead.jsp");
				}
				else {
					session.setAttribute("error", "Enable to create department head.....");
					rd.forward(request, response);
				}
			}
			else {
				session.setAttribute("error", "Employee does not belong to this department....");
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", "This department already has a department head...");
			rd.forward(request, response);
		}
	}
}
