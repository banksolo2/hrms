package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/deleteLeaveType")
public class DeleteLeaveTypeController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int leaveTypeId = Integer.parseInt(request.getParameter("leaveTypeId"));
		LeaveTypeDao ltd = new LeaveTypeDao();
		LeaveType lt = ltd.getLeaveTypeById(leaveTypeId);
		ltd.deleteLeaveType(lt);
		response.sendRedirect("allLeaveTypes.jsp");
	}
}
