package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.notification.*;

@WebServlet("/searchEmployeePayslip")
public class SearchEmployeePayslipController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("viewPayslip.jsp");
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		PayrollNotification pn = new PayrollNotification();
		String monthNo = request.getParameter("monthNo").trim();
		int employeeId = Integer.parseInt(request.getParameter("employeeId"));
		String year = request.getParameter("year").trim();
		int yearLength = year.length();
		if(yearLength == 4) {
			rd = request.getRequestDispatcher("payslip.jsp");
			rd.forward(request, response);
		}
		else {
			session.setAttribute("error", pn.getInvalidYearErrorMessage());
			rd.forward(request, response);
		}
	}

}
