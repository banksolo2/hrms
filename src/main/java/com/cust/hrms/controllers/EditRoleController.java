package com.cust.hrms.controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/editRole")
public class EditRoleController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int roleId = Integer.parseInt(request.getParameter("roleId"));
		String name = request.getParameter("name").trim();
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("editRole.jsp");
		int updatedBy = (int)session.getAttribute("employeeId");
		
		//check if name already exist
		RoleDao rod = new RoleDao();
		Role r = new Role();
		r.setRoleId(roleId);
		r.setName(name);
		r.setUpdatedBy(updatedBy);
		boolean isRoleNameExist = rod.isRoleNameExistByAnother(r);
		
		if(isRoleNameExist == false) {
			int count = rod.updateRole(r);
			if(count >= 1) {
				response.sendRedirect("allRoles.jsp");
			}
			else {
				session.setAttribute("error", "Enable to update role...");
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", "Role name already exist...");
			rd.forward(request, response);
		}
	}

}
