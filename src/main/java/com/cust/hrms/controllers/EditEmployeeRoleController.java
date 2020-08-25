package com.cust.hrms.controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/editEmployeeRole")
public class EditEmployeeRoleController extends HttpServlet {

		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			PrintWriter out = response.getWriter();
			int employeeRoleId = Integer.parseInt(request.getParameter("employeeRoleId"));
			int employeeId = Integer.parseInt(request.getParameter("employeeId"));
			int roleId = Integer.parseInt(request.getParameter("roleId"));
			HttpSession session = request.getSession();
			RequestDispatcher rd = request.getRequestDispatcher("editEmployeeRole.jsp");
			int updatedBy = (int)session.getAttribute("employeeId");
			
			
			EmployeeRoleDao erd = new EmployeeRoleDao();
			EmployeeRole er = new EmployeeRole();
			er.setEmployeeRoleId(employeeRoleId);
			er.setEmployeeId(employeeId);
			er.setRoleId(roleId);
			er.setUpdatedBy(updatedBy);
			
			//check if employee role exist
			boolean isEmployeeRoleExist = erd.isEmployeeRoleExistOnUpdate(er);
			
			if(isEmployeeRoleExist == false) {
				int count = erd.updateEmployeeRole(er);
				if(count >= 1) {
					response.sendRedirect("allEmployeeRoles.jsp");
				}
				else {
					session.setAttribute("error", "Enable to update employee role...");
					rd.forward(request, response);
				}
			}
			else {
				session.setAttribute("error", "Employee role already exist...");
				rd.forward(request, response);
			}
		}
}
