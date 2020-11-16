package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet("/success")
public class SuccessController extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		HttpSession session = request.getSession();;
		String pageTo = (String) session.getAttribute("pageTo");
		session.setAttribute("pageTo", null);
		response.sendRedirect(pageTo);
	}
}
