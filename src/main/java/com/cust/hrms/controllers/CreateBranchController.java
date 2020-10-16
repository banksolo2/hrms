package com.cust.hrms.controllers;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/createBranch")
public class CreateBranchController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String name = request.getParameter("name");
		RequestDispatcher rd = request.getRequestDispatcher("createBranch.jsp");
		HttpSession session = request.getSession();
		
		int userId = (int)session.getAttribute("employeeId");
		BranchDao bd = new BranchDao();
		
		//check if state name already exist
		boolean isStateNameExist = bd.isBranchNameExist(name);
		
		if(isStateNameExist == false) {
			//create new branch
			Branch b = new Branch();
			b.setName(name);
			b.setCreatedBy(userId);
			int count = bd.createBranch(b);
			
			if(count >= 1) {
				session.setAttribute("success", "Branch created..");
				response.sendRedirect("createBranch.jsp");
			}
			else {
				session.setAttribute("error", "Enable to create branch..");
				rd.forward(request, response);
			}
			
		}
		else {
			session.setAttribute("error", "Branch name already exist..");
			rd.forward(request, response);
		}
		
	}
}
