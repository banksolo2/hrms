package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/editPayElementDeductionType")
public class EditPayElementDeductionTypeController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = request.getRequestDispatcher("editPayElementDeductionType.jsp");
		int updatedBy = 0;
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			updatedBy = (int)session.getAttribute("employeeId");
		}
		PayElementDeductionTypeNotification pedtn = new PayElementDeductionTypeNotification();
		PayElementDeductionTypeDao pedtd = new PayElementDeductionTypeDao();
		int payElementDeductionTypeId = Integer.parseInt(request.getParameter("payElementDeductionTypeId"));
		PayElementDeductionType pedt = pedtd.getPayElementDeductionTypeById(payElementDeductionTypeId);
		pedt.setUpdatedBy(updatedBy);
		String name = request.getParameter("name");
		pedt.setName(name);
		
		//Check if name already exist
		boolean isNameExist = pedtd.isNameExistOnUpdate(pedt);
		if(isNameExist == false) {
			// update data in the database table
			int count = pedtd.updatePayElementDeductionType(pedt);
			if(count >= 1) {
				session.setAttribute("success", pedtn.getUpdatePayElementDeductionTypeMessage(true));
				response.sendRedirect("allPayElementDeductionTypesReport.jsp");
			}
			else {
				session.setAttribute("error", pedtn.getUpdatePayElementDeductionTypeMessage(false));
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", pedtn.getNameAlreadyExistErrorMessage());
			rd.forward(request, response);
		}
	}
}
