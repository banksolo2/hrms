package com.cust.hrms.controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/editBranch")
public class EditBranchController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int branchId = Integer.parseInt(request.getParameter("branchId"));
		String name = request.getParameter("name");
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("editBranch.jsp");
		int userId = (int)session.getAttribute("employeeId");
		
		BranchDao bd = new BranchDao();
		Branch b = new Branch();
		b.setBranchId(branchId);
		b.setName(name);
		b.setUpdatedBy(userId);
		
		//Check if branch already exist
		boolean isBranchExist = bd.isBranchNameExistByAnother(b);
		
		if(isBranchExist == false) {
			int count = bd.updateBranch(b);
			if(count >= 1) {
				response.sendRedirect("allBranches.jsp");
			}
			else {
				session.setAttribute("error", "Enable to upate branch...");
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", "Branch already exist..");
			rd.forward(request, response);
		}
	}

}
