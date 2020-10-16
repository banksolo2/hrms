package com.cust.hrms.controllers;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;
import com.cust.hrms.models.*;
import com.cust.hrms.dao.*;

@WebServlet("/deleteState")
public class DeleteStateController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String name = request.getParameter("name").trim();
		
		//get state details
		StateDao sd = new StateDao();
		State s = sd.getState(name);
		//delete state
		sd.deleteState(s);
		
		response.sendRedirect("allStates.jsp");
		
	}
}
