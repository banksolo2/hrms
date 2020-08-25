package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/editLeaveType")
public class EditLeaveTypeController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("editLeaveType.jsp");
		int leaveTypeId = Integer.parseInt(request.getParameter("leaveTypeId"));
		String name = request.getParameter("name").trim();
		int days = Integer.parseInt(request.getParameter("days"));
		int updatedBy = (int)session.getAttribute("employeeId");
		
		LeaveTypeDao ltd = new LeaveTypeDao();
		LeaveType lt = new LeaveType();
		lt.setLeaveTypeId(leaveTypeId);
		lt.setName(name);
		lt.setDays(days);
		lt.setUpdatedBy(updatedBy);
		
		//Check if name already exist
		boolean isNameExist = ltd.isNameExistOnUpdate(lt);
		if(isNameExist == false) {
			//update leave type
			int count = ltd.updateLeaveType(lt);
			if(count >= 1) {
				response.sendRedirect("allLeaveTypes.jsp");
			}
			else {
				session.setAttribute("error", "Enable edit leave type....");
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", "Name provide already exist.....");
			rd.forward(request, response);
		}
	}
}
