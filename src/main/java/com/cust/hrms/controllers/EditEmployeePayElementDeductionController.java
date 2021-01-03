package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/editEmployeePayElementDeduction")
public class EditEmployeePayElementDeductionController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = request.getRequestDispatcher("editEmployeePayElementDeduction.jsp");
		int updatedBy = 0;
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			updatedBy = (int)session.getAttribute("employeeId");
		}
		EmployeePayElementDeductionNotification epedn = new EmployeePayElementDeductionNotification();
		DateDao dd = new DateDao();
		EmployeePayElementDeductionDao epedd = new EmployeePayElementDeductionDao();
		int employeePayElementDeductionId = Integer.parseInt(request.getParameter("employeePayElementDeductionId"));
		EmployeePayElementDeduction eped = epedd.getEmployeePayElementDeductionById(employeePayElementDeductionId);
		eped.setUpdatedBy(updatedBy);
		int employeeId = Integer.parseInt(request.getParameter("employeeId"));
		eped.setEmployeeId(employeeId);
		int payElementDeductionTypeId = Integer.parseInt(request.getParameter("payElementDeductionTypeId"));
		eped.setPayElementDeductionTypeId(payElementDeductionTypeId);
		String value = request.getParameter("amount");
		String dates[] = request.getParameter("dates").split(" - ");
		eped.setStartDate(dd.convertDateFormat(dates[0], "/"));
		eped.setEndDate(dd.convertDateFormat(dates[1], "/"));
		// Check amount 
		if(value.equals("0.0") || value.equals("0")) {
			session.setAttribute("error", "Please provide amount");
			rd.forward(request, response);
		}
		else {
			//Valid amount 
			boolean isAmountValid = epedd.isAmountValid(value);
			if(isAmountValid) {
				//update data in the database table
				int count = epedd.updateEmployeePayElementDeduction(eped);
				if(count >= 1) {
					session.setAttribute("success", epedn.getUpdateEmployeePayElementDeductionMessage(true));
					response.sendRedirect("allEmployeesPayElementDeductionReport.jsp");
				}
				else {
					session.setAttribute("error", epedn.getUpdateEmployeePayElementDeductionMessage(false));
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
