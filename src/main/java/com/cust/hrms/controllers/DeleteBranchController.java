package com.cust.hrms.controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import com.cust.hrms.models.*;
import com.cust.hrms.dao.*;

@WebServlet("/deleteBranch")
public class DeleteBranchController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int branchId = Integer.parseInt(request.getParameter("branchId"));
		
		//get branch details
		BranchDao bd = new BranchDao();
		Branch b = bd.getBranchById(branchId);
		int count = bd.deleteBranch(b);
		response.sendRedirect("allBranches.jsp");
	}
}
