package com.cust.hrms.controllers;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/createState")
public class CreateStateController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		PrintWriter out = response.getWriter();
		String name = request.getParameter("name").trim();
		HttpSession session = request.getSession();
		//String message = null;
		RequestDispatcher rd = request.getRequestDispatcher("createState.jsp");
		int createdBy = (int)session.getAttribute("employeeId");
		
		//check if name already exits
		StateDao sd = new StateDao();
		boolean isNameExist = sd.isStateNameExist(name);
		
		if(isNameExist == false) {
			State s = new State();
			s.setName(name);
			s.setCreatedBy(createdBy);
			int count = sd.createState(s);
			if(count >= 1) {
				session.setAttribute("success", "State created...");
				response.sendRedirect("createState.jsp");
			}
			else {
				session.setAttribute("error", "Enable to create state...");
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", "State name already exist..");
			rd.forward(request, response);
		}
	}

}
