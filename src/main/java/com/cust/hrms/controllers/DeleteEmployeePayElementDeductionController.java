package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/deleteEmployeePayElementDeduction")
public class DeleteEmployeePayElementDeductionController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			EmployeePayElementDeductionNotification epedn = new EmployeePayElementDeductionNotification();
			EmployeePayElementDeductionDao epedd = new EmployeePayElementDeductionDao();
			int employeePayElementDeductionId = Integer.parseInt(request.getParameter("employeePayElementDeductionId"));
			EmployeePayElementDeduction eped = epedd.getEmployeePayElementDeductionById(employeePayElementDeductionId);
			//delete data from the database table
			int count = epedd.deleteEmployeePayElementDeduction(eped);
			if(count >= 1) {
				session.setAttribute("success", epedn.getDeleteEmployeePayElementDeductionMessage(true));
			}
			else {
				session.setAttribute("error", epedn.getDeleteEmployeePayElementDeductionMessage(false));
			}
			response.sendRedirect("allEmployeesPayElementDeductionReport.jsp");
		}
	}
}
