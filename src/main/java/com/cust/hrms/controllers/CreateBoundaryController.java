package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/createBoundary")
public class CreateBoundaryController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = request.getRequestDispatcher("createBoundary.jsp");
		int createdBy = 0;
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			createdBy = (int)session.getAttribute("employeeId");
		}
		BoundaryNotification bn = new BoundaryNotification();
		BoundaryDao bd = new BoundaryDao();
		Boundary b = new Boundary();
		b.setCreatedBy(createdBy);
		int levelPayElementId = Integer.parseInt(request.getParameter("levelPayElementId"));
		b.setLevelPayElementId(levelPayElementId);
		int levelId = Integer.parseInt(request.getParameter("levelId"));
		b.setLevelId(levelId);
		int payElementId = Integer.parseInt(request.getParameter("payElementId"));
		b.setPayElementId(payElementId);
		String amount = request.getParameter("highestAmount");
		//Check if highest amount is a valid format
		if(bd.isAmountValid(amount)) {
			b.setHighestAmount(Double.parseDouble(amount));
			amount = request.getParameter("lowestAmount");
			//Check if lowest amount is a valid format
			if(bd.isAmountValid(amount)) {
				b.setLowestAmount(Double.parseDouble(amount));
				amount = request.getParameter("defaultAmount");
				//Check if default amount is a valid format
				if(bd.isAmountValid(amount)) {
					b.setDefaultAmount(Double.parseDouble(amount));
					//Check highest amount value
					if(b.getHighestAmount() >= 1) {
						//Check lowest amount value
						if(b.getLowestAmount() >= 1) {
							//Check default amount value
							if(b.getDefaultAmount() >= 1) {
								// Check if highest amount is greater than lowest amount
								if(bd.isLowestAmountValid(b.getLowestAmount(), b.getHighestAmount())) {
									//Check if default amount is less than highest amount and greater than lowest amount
									if(bd.isDefaultAmountValid(b.getDefaultAmount(), b.getLowestAmount(), b.getHighestAmount())){
										//check if boundary already exist
										boolean isBoundaryExist = bd.isBoundaryExist(b.getLevelPayElementId());
										if(isBoundaryExist == false) {
											// Save boundary in the database table
											int count = bd.createBoundary(b);
											if(count >= 1) {
												session.setAttribute("success", bn.createBoundaryMessage(true));
												response.sendRedirect("uncreatedPaymentBoundary.jsp");
											}
											else {
												session.setAttribute("error", bn.createBoundaryMessage(false));
												rd.forward(request, response);
											}
										}
										else {
											session.setAttribute("error", bn.getBoundaryAlreadyExistErrorMessage());
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
								session.setAttribute("error", "Please provide default amount...");
								rd.forward(request, response);
							}
						}
						else {
							session.setAttribute("error", "Please provide lowest amount...");
							rd.forward(request, response);
						}
					}
					else {
						session.setAttribute("error", "Please provide highest amount...");
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
