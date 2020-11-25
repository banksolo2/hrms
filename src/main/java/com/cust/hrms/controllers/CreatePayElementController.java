package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/createPayElement")
public class CreatePayElementController extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = request.getRequestDispatcher("createPayElement.jsp");
		int createdBy = 0;
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			createdBy = (int)session.getAttribute("employeeId");
		}
		PayElementNotification pen = new PayElementNotification();
		PayElementDao ped = new PayElementDao();
		PayElement pe = new PayElement();
		pe.setCreatedBy(createdBy);
		String name = request.getParameter("name");
		pe.setName(name);
		int elementStatusId = Integer.parseInt(request.getParameter("elementStatusId"));
		pe.setElementStatusId(elementStatusId);
		String description = request.getParameter("description");
		pe.setDescription(description);
		//Check if name already exist
		boolean isNameExist = ped.isNameExist(name);
		if(isNameExist == false) {
			//Save pay element in the database table
			int count = ped.createPayElement(pe);
			if(count >= 1) {
				session.setAttribute("success", pen.getCreatePayElementMessage(true));
				response.sendRedirect("createPayElement.jsp");
			}
			else {
				session.setAttribute("error", pen.getCreatePayElementMessage(false));
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", pen.getNameAlreadyExistErrorMessage());
			rd.forward(request, response);
		}
	} 
}
