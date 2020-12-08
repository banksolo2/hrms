package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/editEmployeePayElement")
public class EditEmployeePayElementController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = request.getRequestDispatcher("editEmployeePayElement.jsp");
		int updatedBy = 0;
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			updatedBy = (int)session.getAttribute("employeeId");
		}
		EmployeePayElementDao eped = new EmployeePayElementDao();
		BoundaryDao bd = new BoundaryDao();
		BoundaryNotification bn = new BoundaryNotification();
		int employeePayElementId = Integer.parseInt(request.getParameter("employeePayElementId"));
		EmployeePayElement epe = eped.getEmployeePayElementById(employeePayElementId);
		epe.setUpdatedBy(updatedBy);
		Boundary b = bd.getBoundaryById(epe.getBoundaryId());
		String amount = request.getParameter("amount");
		//Check if amount is a valid format
		if(bd.isAmountValid(amount)) {
			epe.setAmount(Double.parseDouble(amount));
			//Check if amount is less than highest amount or greater than equals to the lowest amount
			if(epe.getAmount() <= b.getHighestAmount()) {
				if(epe.getAmount() >= b.getLowestAmount()) {
					//Update employee pay element in database table
					int count = eped.updateEmployeePayElement(epe);
					if(count >= 1) {
						session.setAttribute("success", "Employee pay element updated....");
						response.sendRedirect("viewEmployeePayElement.jsp");
					}
					else {
						session.setAttribute("error", "Enable to update employee pay element....");
						rd.forward(request, response);
					}
				}
				else {
					session.setAttribute("error", "Amount can't be less than "+b.getLowestAmount());
					rd.forward(request, response);
				}
			}
			else {
				session.setAttribute("error", "Amount can't be greater than "+b.getHighestAmount());
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", bn.getAmountFormatErrorMessage("amount"));
			rd.forward(request, response);
		}
	}
}
