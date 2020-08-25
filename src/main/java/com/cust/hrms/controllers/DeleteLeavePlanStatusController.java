package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/deleteLeavePlanStatus")
public class DeleteLeavePlanStatusController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int leavePlanStatusId = Integer.parseInt(request.getParameter("leavePlanStatusId"));
		LeavePlanStatusDao lpsd = new LeavePlanStatusDao();
		LeavePlanStatus lps = lpsd.getLeavePlanStatusById(leavePlanStatusId);
		lpsd.deleteLeavePlanStatus(lps);
		response.sendRedirect("allLeavePlanStatues.jsp");
	}
}
