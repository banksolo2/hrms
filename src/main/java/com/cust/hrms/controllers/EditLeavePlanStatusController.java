package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/editLeavePlanStatus")
public class EditLeavePlanStatusController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("editLeavePlanStatus.jsp");
		int leavePlanStatusId = Integer.parseInt(request.getParameter("leavePlanStatusId"));
		String name = request.getParameter("name");
		
		LeavePlanStatusDao lpsd = new LeavePlanStatusDao();
		LeavePlanStatus lps = new LeavePlanStatus();
		lps.setLeavePlanStatusId(leavePlanStatusId);
		lps.setName(name);
		
		//check if name already exist
		boolean isNameExist = lpsd.isNameExistOnUpdate(lps);
		if(isNameExist == false) {
			int count = lpsd.updateLeavePlanStatus(lps);
			if(count >= 1) {
				response.sendRedirect("allLeavePlanStatues.jsp");
			}
			else {
				session.setAttribute("error", "Enable to save leave plan status...");
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", "Name already exist....");
			rd.forward(request, response);
		}
	}
}
