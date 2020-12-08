package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/deletePayElementDeductionType")
public class DeletePayElementDeductionTypeController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			int payElementDeductionTypeId = Integer.parseInt(request.getParameter("payElementDeductionTypeId"));
			PayElementDeductionTypeDao pedtd = new PayElementDeductionTypeDao();
			PayElementDeductionTypeNotification pedtn = new PayElementDeductionTypeNotification();
			PayElementDeductionType pedt = pedtd.getPayElementDeductionTypeById(payElementDeductionTypeId);
			// Delete data from the database table
			int count = pedtd.deletePayElementDeductionType(pedt);
			if(count >= 1) {
				session.setAttribute("success", pedtn.getDeletePayElementDeductionTypeMessage(true));
			}
			else {
				session.setAttribute("error", pedtn.getDeletePayElementDeductionTypeMessage(false));
			}
			response.sendRedirect("allPayElementDeductionTypesReport.jsp");
		}
	}
}
