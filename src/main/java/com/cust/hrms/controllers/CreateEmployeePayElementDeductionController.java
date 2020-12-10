package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/createEmployeePayElementDeduction")
public class CreateEmployeePayElementDeductionController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = request.getRequestDispatcher("createEmployeePayElementDeduction.jsp");
		int createdBy = 0;
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			createdBy = (int)session.getAttribute("employeeId");
		}
		EmployeePayElementDeductionNotification epedn = new EmployeePayElementDeductionNotification();
		EmployeePayElementDeductionDao epedd = new EmployeePayElementDeductionDao();
		EmployeePayElementDeduction eped = new EmployeePayElementDeduction();
		eped.setCreatedBy(createdBy);
		int employeeId = Integer.parseInt(request.getParameter("employeeId"));
		eped.setEmployeeId(employeeId);
		int payElementDeductionTypeId = Integer.parseInt(request.getParameter("payElementDeductionTypeId"));
		eped.setPayElementDeductionTypeId(payElementDeductionTypeId);
		String value = request.getParameter("amount");
		String month = request.getParameter("month");
		eped.setMonthNo(month);
		String year = request.getParameter("year");
		eped.setYear(year);
		// Check amount 
		if(value.equals("0.0") || value.equals("0")) {
			session.setAttribute("error", "Please provide amount");
			rd.forward(request, response);
		}
		else {
			// validate amount
			if(epedd.isAmountValid(value)) {
				eped.setAmount(Double.valueOf(value));
				// validate year
				if(epedd.isYearValid(eped.getYear())) {
					// save data in the database table
					int count = epedd.createEmployeePayElementDeduction(eped);
					if(count >= 1) {
						session.setAttribute("success", epedn.getCreateEmployeePayElementDeductionMessage(true));
						response.sendRedirect("createEmployeePayElementDeduction.jsp");
					}
					else {
						session.setAttribute("error", epedn.getCreateEmployeePayElementDeductionMessage(false));
						rd.forward(request, response);
					}
				}
				else {
					session.setAttribute("error", epedn.getInvalidYearFormatErrorMessage());
					rd.forward(request, response);
				}
			}
			else {
				session.setAttribute("error", epedn.getInvalidAmountFormatErrorMessage());
				rd.forward(request, response);
			}
		}
	}
}
