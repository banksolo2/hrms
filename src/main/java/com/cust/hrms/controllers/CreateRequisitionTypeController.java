package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.notification.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/createRequisitionType")
public class CreateRequisitionTypeController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("createRequisitionType.jsp");
		RequisitionTypeDao rtd = new RequisitionTypeDao();
		RequisitionTypeNotification rtn = new RequisitionTypeNotification();
		RequisitionType rt = new RequisitionType();
		String name = request.getParameter("name");
		rt.setName(name);
		int createdBy = (int)session.getAttribute("employeeId");
		rt.setCreatedBy(createdBy);
		
		//Check if name already exist
		boolean isNameExist = rtd.isNameExist(name);
		if(isNameExist == false) {
			// insert into database values
			int count = rtd.createRequisitionType(rt);
			if(count >= 1) {
				session.setAttribute("success", rtn.createRequisitionTypeMessage(true));
				response.sendRedirect("createRequisitionType.jsp");
			}
			else {
				session.setAttribute("error", rtn.createRequisitionTypeMessage(false));
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", rtn.getNameAlreadyExistMessage());
			rd.forward(request, response);
		}
	} 
}
