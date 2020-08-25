package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/deleteGender")
public class DeleteGenderController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int genderId = Integer.parseInt(request.getParameter("genderId"));
		
		Gender g = new Gender();
		g.setGenderId(genderId);
		GenderDao gd = new GenderDao();
		gd.deleteGender(g);
		response.sendRedirect("allGenders.jsp");
	}
}
