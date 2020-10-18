package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/createRequisitionStatus")
public class CreateRequisitionStatusController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("createRequisitionStatus.jsp");
		RequisitionStatusNotification rsn = new RequisitionStatusNotification();
		RequisitionStatusDao rsd = new RequisitionStatusDao();
		RequisitionStatus rs = new RequisitionStatus();
		String name = request.getParameter("name");
		rs.setName(name);
		int createdBy = (int)session.getAttribute("employeeId");
		rs.setCreatedBy(createdBy);
		
		//Check if name already exist
		boolean isNameExist = rsd.isNameExist(name);
		if(isNameExist == false) {
			//Save data in the database
			int count = rsd.createRequisitionStatus(rs);
			if(count >= 1) {
				session.setAttribute("success", rsn.getCreateRequisitionStatusMessage(true));
				response.sendRedirect("createRequisitionStatus.jsp");
			}
			else {
				session.setAttribute("error", rsn.getCreateRequisitionStatusMessage(false));
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", rsn.getNameAlreadyExistMessage());
			rd.forward(request, response);
		}
	}
}
