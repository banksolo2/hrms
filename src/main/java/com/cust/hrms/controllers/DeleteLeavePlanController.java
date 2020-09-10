package com.cust.hrms.controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/deleteLeavePlan")
public class DeleteLeavePlanController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		PrintWriter out = response.getWriter();
		int leavePlanId = Integer.parseInt(request.getParameter("leavePlanId"));
		LeavePlanDao lpd = new LeavePlanDao();
		LeavePlan lp = lpd.getLeavePlan(leavePlanId);
		lpd.deleteLeavePlan(lp);
		response.sendRedirect("allDraftedLeavePlan.jsp");
	}
}
