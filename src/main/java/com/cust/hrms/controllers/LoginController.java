package com.cust.hrms.controllers;

import javax.servlet.annotation.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import java.io.*;


@WebServlet("/login")
public class LoginController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		RequestDispatcher rd;
		
		//Check if employee email exist
		EmployeeDao ed = new EmployeeDao();
		DepartmentHeadDao dhd = new DepartmentHeadDao();
		EmployeeRoleDao erd = new EmployeeRoleDao();
		RoleDao rod = new RoleDao();
		boolean isValidEmail = ed.isEmailExist(email);
		
		if(isValidEmail == true) {
			//Validate employee login details
			boolean isValidEmployee = ed.login(email, password);
			
			if(isValidEmployee == true) {
				Employee e = ed.getEmployee(email);
				session.setAttribute("employeeId", e.getEmployeeId());
				session.setAttribute("email", e.getEmail());
				session.setAttribute("firstName", e.getFirstName());
				session.setAttribute("lastName", e.getLastName());
				session.setAttribute("staffId", e.getStaffId());
				session.setAttribute("isDepartmentHead", dhd.isEmployeeDepartmentHead(e.getEmployeeId()));
				session.setAttribute("isSupervisor", erd.isEmployeeRoleExistByParam(e.getEmployeeId(), rod.getRoleIdBycode("supervisor")));
				session.setAttribute("isHrAdmin", erd.isEmployeeRoleExistByParam(e.getEmployeeId(), rod.getRoleIdBycode("hr_admin")));
				session.setAttribute("isSuperAdmin", erd.isEmployeeRoleExistByParam(e.getEmployeeId(), rod.getRoleIdBycode("super_admin")));
				response.sendRedirect("index.jsp");
			}
			else {
				session.setAttribute("message", "Invalid password..");
				rd = request.getRequestDispatcher("login.jsp");
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("message", "Email address does not exist..");
			rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
		}
		
	}

}
