package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/createMartialStatus")
public class CreateMartialStatusController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String name = request.getParameter("name").trim();
		MartialStatusDao msd = new MartialStatusDao();
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("createMartialStatus.jsp");
		int createdBy = (int)session.getAttribute("employeeId");
		
		//Check if martial status already exist
		boolean isMartialStatusExist = msd.isMartialStatusExist(name);
		
		if(isMartialStatusExist == false) {
			MartialStatus ms = new MartialStatus();
			ms.setName(name);
			ms.setCreatedBy(createdBy);
			int count = msd.createMartialStatus(ms);
			if(count >= 1) {
				session.setAttribute("success", "Martial status created...");
				response.sendRedirect("createMartialStatus.jsp");
			}
			else {
				session.setAttribute("error", "Enable to create martial status...");
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", "Martial status already exist...");
			rd.forward(request, response);
		}
	}
}
