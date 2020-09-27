package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.models.*;
import com.cust.hrms.dao.*;

@WebServlet("/deleteDraftedLeaveRequest")
public class DeleteDraftedLeaveRequestController extends HttpServlet{

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		HttpSession session = request.getSession();
		int leaveId = Integer.parseInt(request.getParameter("leaveId"));
		LeaveDao ld = new LeaveDao();
		Leave l = ld.getLeaveById(leaveId);
		ld.deleteLeave(l);
		session.setAttribute("success", "Leave request has been deleted...");
		response.sendRedirect("employeeDraftedLeaveRequestWithPay.jsp");
	}
}
