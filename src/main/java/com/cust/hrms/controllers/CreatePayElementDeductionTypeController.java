package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/createPayElementDeductionType")
public class CreatePayElementDeductionTypeController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = request.getRequestDispatcher("createPayElementDeductionType.jsp");
		int createdBy = 0;
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			createdBy = (int)session.getAttribute("employeeId");
		} 
		PayElementDeductionTypeNotification pedtn = new PayElementDeductionTypeNotification();
		PayElementDeductionTypeDao pedtd = new PayElementDeductionTypeDao();
		PayElementDeductionType pedt = new PayElementDeductionType();
		pedt.setCreatedBy(createdBy);
		String name = request.getParameter("name");
		pedt.setName(name);
		
		//Check if name already exist
		boolean isNameExist = pedtd.isNameExist(pedt.getName());
		if(isNameExist == false) {
			// save data in the database
			int count = pedtd.createPayElementDeductionTypeDao(pedt);
			if(count >= 1) {
				session.setAttribute("success", pedtn.getCreatePayElementDeductionTypeMessage(true));
				response.sendRedirect("createPayElementDeductionType.jsp");
			}
			else {
				session.setAttribute("error", pedtn.getCreatePayElementDeductionTypeMessage(false));
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", pedtn.getNameAlreadyExistErrorMessage());
			rd.forward(request, response);
		}
	}

}
