package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/changePassword")
public class ChangePasswordController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int employeeId = Integer.parseInt(request.getParameter("employeeId"));
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		HttpSession session = request.getSession();
		//RequestDispatcher rd = request.getRequestDispatcher("changePassword.jsp");
		
		
		if(confirmPassword.length() >= 1 && password.length() >= 1) {
		
			if(password.equals(confirmPassword)) {
				EmployeeDao ed = new EmployeeDao();
				Employee e = new Employee();
				e.setEmployeeId(employeeId);
				e.setPassword(password);
				int count = ed.updatePassword(e);
				
				if(count >= 1) {
					session.setAttribute("success", "Password updated...");
					response.sendRedirect("changePassword.jsp");
				}
				else {
					session.setAttribute("error", "Enable to update password...");
					response.sendRedirect("changePassword.jsp");
				}
			}
			else {
				session.setAttribute("error", "Password and confirm password does not match...");
				response.sendRedirect("changePassword.jsp");
			}
		}
		else {
			session.setAttribute("error", "All fields required...");
			response.sendRedirect("changePassword.jsp");
		}
	} 
}
