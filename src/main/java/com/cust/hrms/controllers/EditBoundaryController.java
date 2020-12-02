package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/editBoundary")
public class EditBoundaryController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = request.getRequestDispatcher("editBoundary.jsp");
		int updatedBy = 0;
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			updatedBy = (int)session.getAttribute("employeeId");
		}
		BoundaryDao bd = new BoundaryDao();
		BoundaryNotification bn = new BoundaryNotification();
		int boundaryId = Integer.parseInt(request.getParameter("boundaryId"));
		Boundary b = bd.getBoundaryById(boundaryId);
		b.setUpdatedBy(updatedBy);
		String amount = request.getParameter("highestAmount");
		//Check highest amount is a valid format
		if(bd.isAmountValid(amount)) {
			b.setHighestAmount(Double.parseDouble(amount));
			amount = request.getParameter("lowestAmount");
			// check lowest amount is a valid format
			if(bd.isAmountValid(amount)) {
				b.setLowestAmount(Double.parseDouble(amount));
				amount = request.getParameter("defaultAmount");
				//Check if default amount is valid
				if(bd.isAmountValid(amount)) {
					b.setDefaultAmount(Double.parseDouble(amount));
					//Check if lowest amount is less than the highest amount
					if(bd.isLowestAmountValid(b.getLowestAmount(), b.getHighestAmount())) {
						//Check if default amount is less than highest amount and not lower than the lowest amount
						if(bd.isDefaultAmountValid(b.getDefaultAmount(), b.getLowestAmount(), b.getHighestAmount())) {
							//Update boundary in the database table
							int count = bd.updateBoundary(b);
							if(count >= 1) {
								session.setAttribute("success", bn.updateBoundaryMessage(true));
								response.sendRedirect("allPaymentBoundariesReport.jsp");
							}
							else {
								session.setAttribute("error", bn.updateBoundaryMessage(false));
								rd.forward(request, response);
							}
						}
						else {
							session.setAttribute("error", bn.getDefaultGreaterThanHighestAndLessThanLowerAmountErrorMessage());
							rd.forward(request, response);
						}
					}
					else {
						session.setAttribute("error", bn.getLowestGreaterThanHighestAmountErrorMessage());
						rd.forward(request, response);
					}
				}
				else {
					session.setAttribute("error", bn.getAmountFormatErrorMessage("default amount"));
					rd.forward(request, response);
				}
			}
			else {
				session.setAttribute("error", bn.getAmountFormatErrorMessage("lowest amount"));
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", bn.getAmountFormatErrorMessage("highest amount"));
			rd.forward(request, response);
		}
	}

}
