package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.notification.*;

@WebServlet("/searchEmployeePayroll")
public class SearchEmployeePayrollController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("searchEmployeePayroll.jsp");
		PrintWriter out = response.getWriter();
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			PayrollDao pd = new PayrollDao();
			PayrollNotification pn = new PayrollNotification();
			int employeeId = Integer.parseInt(request.getParameter("employeeId"));
			//session.setAttribute("otherEmployeeId", employeeId);
			String monthNo = request.getParameter("monthNo");
			//.setAttribute("monthNo", monthNo);
			String year = request.getParameter("year").trim();
			int yearLength = year.length();
			if(yearLength == 4) {
				rd = request.getRequestDispatcher("veiwEmployeePayroll.jsp");
				rd.forward(request, response);
			}
			else {
				session.setAttribute("error", pn.getInvalidYearErrorMessage());
				rd.forward(request, response);
			}
		}
	}

}
