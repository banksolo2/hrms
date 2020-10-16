package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/createDepartment")
public class CreateDepartmentController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("createDepartment.jsp");
		String name = request.getParameter("name");
		int createdBy = (int)session.getAttribute("employeeId");
		
		DepartmentDao dd = new DepartmentDao();
		Department d = new Department();
		d.setName(name);
		d.setCreatedBy(createdBy);
		
		//Check if department name exist
		boolean isNameExist = dd.isDepartmentNameExist(name);
		if(isNameExist == false) {
			int count = dd.createDepartment(d);
			if(count >= 1) {
				session.setAttribute("success", "Department created...");
				response.sendRedirect("createDepartment.jsp");
			}
			else {
				session.setAttribute("error", "Enable create department...");
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", "Name already exist..");
			rd.forward(request, response);
		}
	}
}
