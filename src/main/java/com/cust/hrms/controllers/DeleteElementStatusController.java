package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/deleteElementStatus")
public class DeleteElementStatusController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		ElementStatusDao esd = new ElementStatusDao();
		int elementStatusId = Integer.parseInt(request.getParameter("elementStatusId"));
		ElementStatus es = esd.getElementStatusById(elementStatusId);
		ElementStatusNotification esn = new ElementStatusNotification();
		int count = esd.deleteElementStatus(es);
		if(count >= 1) {
			session.setAttribute("success", esn.getDeletedElementStatusMessage(true));
			response.sendRedirect("allElementStatues.jsp");
		}
		else {
			session.setAttribute("error", esn.getDeletedElementStatusMessage(false));
			response.sendRedirect("allElementStatues.jsp");
		}
	}
}
