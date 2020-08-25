package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/deleteMartialStatus")
public class DeleteMartialStatusController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int martialStatusId = Integer.parseInt(request.getParameter("martialStatusId"));
		MartialStatusDao msd = new MartialStatusDao();
		MartialStatus ms = msd.getMartialStatusById(martialStatusId);
		msd.deleteMartialStatus(ms);
		response.sendRedirect("allMartialStatus.jsp");
	}
}
