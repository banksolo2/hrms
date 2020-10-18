package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/editRequisitionStatus")
public class EditRequisitionStatusController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = request.getRequestDispatcher("editRequisitionStatus.jsp");
		HttpSession session = request.getSession();
		RequisitionStatusNotification rsn = new RequisitionStatusNotification();
		RequisitionStatusDao rsd = new RequisitionStatusDao();
		int requisitionStatusId = Integer.parseInt(request.getParameter("requisitionStatusId"));
		RequisitionStatus rs = rsd.getRequisitionStatusById(requisitionStatusId);
		String name = request.getParameter("name");
		rs.setName(name);
		int updatedBy = (int)session.getAttribute("employeeId");
		rs.setUpdatedBy(updatedBy);
		
		//Check if name already exist
		boolean isNameExist = rsd.isNameExistOnUpdate(rs);
		 
		if(isNameExist == false) {
			// save data in the database
			int count = rsd.updateRequisitionStatus(rs);
			if(count >= 1) {
				session.setAttribute("success", rsn.getUpdateRequisitionStatusMessage(true));
				response.sendRedirect("allRequisitionStatus.jsp");
			}
			else {
				session.setAttribute("error", rsn.getUpdateRequisitionStatusMessage(false));
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", rsn.getNameAlreadyExistMessage());
			rd.forward(request, response);
		}
	}
}
