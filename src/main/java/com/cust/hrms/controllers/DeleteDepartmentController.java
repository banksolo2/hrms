package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/deleteDepartment")
public class DeleteDepartmentController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int departmentId = Integer.parseInt(request.getParameter("departmentId"));
		DepartmentDao dd = new DepartmentDao();
		Department d = dd.getDepartmentById(departmentId);
		dd.deleteDepartment(d);
		response.sendRedirect("allDepartments.jsp");
	}
}
