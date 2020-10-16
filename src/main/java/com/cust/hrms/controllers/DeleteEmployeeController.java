package com.cust.hrms.controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/deleteEmployee")
public class DeleteEmployeeController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String email = request.getParameter("email").trim();
		EmployeeDao ed = new EmployeeDao();
		Employee e = ed.getEmployee(email);
		ed.deleteEmployee(e);
		
		response.sendRedirect("allEmployees.jsp");
	}
}
