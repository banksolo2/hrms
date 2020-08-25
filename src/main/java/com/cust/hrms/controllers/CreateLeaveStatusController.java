package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/createLeaveStatus")
public class CreateLeaveStatusController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("createLeaveStatus.jsp");
		String name = request.getParameter("name");
		
		LeaveStatusDao lsd = new LeaveStatusDao();
		
		//Check if name already exist
		boolean isNameExist = lsd.isNameExist(name);
		if(isNameExist == false) {
			LeaveStatus ls = new LeaveStatus();
			ls.setName(name);
			int count = lsd.createLeaveStatus(ls);
			if(count >= 1) {
				session.setAttribute("success", "Leave status created...");
				response.sendRedirect("createLeaveStatus.jsp");
			}
			else {
				session.setAttribute("error", "Enable create leave status");
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", "Name already exist...");
			rd.forward(request, response);
		}
	}
}
