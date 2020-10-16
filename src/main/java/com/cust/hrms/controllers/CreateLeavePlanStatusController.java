package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/createLeavePlanStatus")
public class CreateLeavePlanStatusController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("createLeavePlanStatus.jsp");
		String name = request.getParameter("name");
		
		LeavePlanStatusDao lpsd = new LeavePlanStatusDao();
		
		//Check if name already exist
		boolean isNameExist = lpsd.isNameExist(name);
		
		if(isNameExist == false) {
			//create new leave plan status
			LeavePlanStatus lps = new LeavePlanStatus();
			lps.setName(name);
			int count = lpsd.createLeavePlanStatus(lps);
			if(count >= 1) {
				session.setAttribute("success", "Leave plan status created...");
				response.sendRedirect("createLeavePlanStatus.jsp");
			}
			else {
				session.setAttribute("error", "Enable to create leave plan status...");
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", "Name already exist...");
			rd.forward(request, response);
		}
	}
}
