package com.cust.hrms.controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		session.removeAttribute("employeeId");
		session.removeAttribute("stuffId");
		session.removeAttribute("email");
		session.removeAttribute("firstName");
		session.removeAttribute("lastName");
		session.invalidate();
		response.sendRedirect("login.jsp");
	}

}
