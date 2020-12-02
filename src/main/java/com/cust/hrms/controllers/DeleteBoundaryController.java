package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/deleteBoundary")
public class DeleteBoundaryController extends HttpServlet  {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		BoundaryNotification bn = new BoundaryNotification();
		BoundaryDao bd = new BoundaryDao();
		int boundaryId = Integer.parseInt(request.getParameter("boundaryId"));
		Boundary b = bd.getBoundaryById(boundaryId);
		int count = bd.deleteBoundary(b);
		if(count >= 1) {
			session.setAttribute("success", bn.deleteBoundaryMessage(true));
		}
		else {
			session.setAttribute("error", bn.deleteBoundaryMessage(false));
		}
		response.sendRedirect("allPaymentBoundariesReport.jsp");
	}
}
