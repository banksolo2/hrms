package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/deletePayElement")
public class DeletePayElementController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		HttpSession session = request.getSession();
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		PayElementNotification pen = new PayElementNotification();
		PayElementDao ped = new PayElementDao();
		int payElementId = Integer.parseInt(request.getParameter("payElementId"));
		PayElement pe = ped.getPayElementById(payElementId);
		int count = ped.deletePayElement(pe);
		if(count >= 1) {
			session.setAttribute("success", pen.getDeletePayElementMessage(true));
		}
		else {
			session.setAttribute("error", pen.getDeletePayElementMessage(false));
		}
		response.sendRedirect("allPayElementReport.jsp");
	}
}
