package com.cust.hrms.controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/createIssueType")
public class CreateIssueTypeController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("createIssueType.jsp");
		IssueTypeDao itd = new IssueTypeDao();
		IssueType it = new IssueType();
		String name = request.getParameter("name");
		it.setName(name);
		int createdBy = (int)session.getAttribute("employeeId");
		it.setCreatedBy(createdBy);
		
		//Check if name already exist
		boolean isNameExist = itd.isNameExist(name);
		if(isNameExist == false) {
			int count = itd.createIssueType(it);
			if(count >= 1) {
				session.setAttribute("success", "Issue type has been created...");
				response.sendRedirect("createIssueType.jsp");
			}
			else {
				session.setAttribute("error", "Enable to create issue type.....");
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", "Name already exist......");
			rd.forward(request, response);
		}
	}
}
