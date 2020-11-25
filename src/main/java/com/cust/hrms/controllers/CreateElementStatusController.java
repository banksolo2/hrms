package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;
import com.cust.hrms.email.*;
import com.cust.hrms.email.message.*;

@WebServlet("/createElementStatus")
public class CreateElementStatusController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("createElementStatus.jsp");
		PrintWriter out = response.getWriter();
		int createdBy = 0;
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		ElementStatusDao esd = new ElementStatusDao();
		ElementStatusNotification esn = new ElementStatusNotification();
		ElementStatus es = new ElementStatus();
		String name = request.getParameter("name");
		es.setName(name);
		boolean isNameExist = esd.isNameExist(name);
		if(isNameExist == false) {
			int count = esd.createElementStatus(es);
			if(count >= 1) {
				session.setAttribute("success", esn.getCreateElementStatusMessage(true));
				response.sendRedirect("createElementStatus.jsp");
			}
			else {
				session.setAttribute("error", esn.getCreateElementStatusMessage(false));
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", esn.getElementStatusAlreadyExistErrorMessage());
			rd.forward(request, response);
		}
	}
}
