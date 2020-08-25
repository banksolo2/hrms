package com.cust.hrms.controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/deleteRole")
public class DeleteRoleController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String name = request.getParameter("name");
		RoleDao rd = new RoleDao();
		Role r = rd.getRole(name);
		rd.deleteRole(r);
		response.sendRedirect("allRoles.jsp");
	}

}
