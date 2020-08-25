package com.cust.hrms.controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import com.cust.hrms.models.*;
import com.cust.hrms.dao.*;

@WebServlet("/createRole")
public class CreateRoleController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		PrintWriter out = response.getWriter();
		String name = request.getParameter("name").trim();
		HttpSession session = request.getSession();
		int createdBy = (int)session.getAttribute("employeeId");
		RequestDispatcher rd;
		
		//check if role name already exist
		RoleDao rod = new RoleDao();
		boolean isRoleNameExist = rod.isRoleNameExist(name);
		
		if(isRoleNameExist == false) {
			Role r = new Role();
			r.setName(name);
			r.setCreatedBy(createdBy);
			int count = rod.createRole(r);
			
			if(count >= 1) {
				session.setAttribute("success", "New role created...");
				response.sendRedirect("createRole.jsp");
			}
			else {
				session.setAttribute("error", "Enable to create new role...");
				rd = request.getRequestDispatcher("createRole.jsp");
				rd.forward(request, response);
			}
			
		}
		else {
			session.setAttribute("error", "Role name already exist...");
			rd = request.getRequestDispatcher("createRole.jsp");
			rd.forward(request, response);
		}
	}

}
