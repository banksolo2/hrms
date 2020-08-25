package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/editLeaveStatus")
public class EditLeaveStatusController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("editLeaveStatus.jsp");
		//int updatedBy = (int)session.getAttribute("employeeId");
		int leaveStatusId = Integer.parseInt(request.getParameter("leaveStatusId"));
		String name = request.getParameter("name");
		
		LeaveStatusDao lsd = new LeaveStatusDao();
		LeaveStatus ls = new LeaveStatus();
		ls.setLeaveStatusId(leaveStatusId);
		ls.setName(name);
		
		//Check if name already exist
		boolean isNameExist = lsd.isNameExistOnUpdate(ls);
		if(isNameExist == false) {
			int count = lsd.updateLeaveStatus(ls);
			if(count >= 1) {
				response.sendRedirect("allLeaveStatues.jsp");
			}
			else {
				session.setAttribute("error", "Enable to update leave status...");
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", "Name already exist...");
			rd.forward(request, response);
		}
	}
}
