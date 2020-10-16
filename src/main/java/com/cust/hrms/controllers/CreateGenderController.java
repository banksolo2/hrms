package com.cust.hrms.controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import com.cust.hrms.models.*;
import com.cust.hrms.dao.*;

@WebServlet("/createGender")
public class CreateGenderController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String name = request.getParameter("name");
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("createGender.jsp");
		int createdBy = (int)session.getAttribute("employeeId");
		
		GenderDao gd = new GenderDao();
		
		//Check if gender name exist
		boolean isGenderNameExist = gd.isGenderNameExist(name);
		
		if(isGenderNameExist == false) {
			Gender g = new Gender();
			g.setName(name);
			g.setCreatedBy(createdBy);
			
			int count = gd.createGender(g);
			if(count >= 1) {
				session.setAttribute("success", "Gender created..");
				response.sendRedirect("createGender.jsp");
			}
			else {
				session.setAttribute("error", "Enable to create gender..");
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", "Gender name already exist..");
			rd.forward(request, response);
		}
	}
}
