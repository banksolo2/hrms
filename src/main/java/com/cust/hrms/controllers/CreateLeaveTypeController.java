package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/createLeaveType")
public class CreateLeaveTypeController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("createLeaveType.jsp");
		String name = request.getParameter("name");
		int days = Integer.parseInt(request.getParameter("days"));
		int createdBy = (int)session.getAttribute("employeeId");
		LeaveTypeDao ltd = new LeaveTypeDao();
		
		//Check if name already exist
		boolean isNameExist = ltd.isNameExist(name);
		
		if(isNameExist == false) {
			//Save new leave type
			LeaveType lt = new LeaveType();
			lt.setName(name);
			lt.setDays(days);
			lt.setCreatedBy(createdBy);
			int count = ltd.createLeaveType(lt);
			if(count >= 1) {
				session.setAttribute("success", "Leave type created...");
				response.sendRedirect("createLeaveType.jsp");
			}
			else {
				session.setAttribute("error", "Enable to create leave type...");
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", "Name provided already exist....");
			rd.forward(request, response);
		}
	}
}
