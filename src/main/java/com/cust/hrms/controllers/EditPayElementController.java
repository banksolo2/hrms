package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/editPayElement")
public class EditPayElementController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = request.getRequestDispatcher("editPayElement.jsp");
		int updatedBy = 0;
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			updatedBy = (int)session.getAttribute("employeeId");
		}
		PayElementNotification pen = new PayElementNotification();
		PayElementDao ped = new PayElementDao();
		int payElementId = Integer.parseInt(request.getParameter("payElementId"));
		PayElement pe = ped.getPayElementById(payElementId);
		pe.setUpdatedBy(updatedBy); 
		String name = request.getParameter("name");
		pe.setName(name);
		int elementStatusId = Integer.parseInt(request.getParameter("elementStatusId"));
		pe.setElementStatusId(elementStatusId);
		String description = request.getParameter("description");
		pe.setDescription(description);
		
		//Check if name already exist
		boolean isNameExist = ped.isNameExistOnUpdate(pe);
		if(isNameExist == false) {
			//Update pay element database table
			int count = ped.updatePayElement(pe);
			if(count >= 1) {
				session.setAttribute("success", pen.getUpdatePayElementMessage(true));
				response.sendRedirect("allPayElementReport.jsp");
			}
			else {
				session.setAttribute("error", pen.getUpdatePayElementMessage(false));
				rd.forward(request, response); 
			}
		}
		else {
			session.setAttribute("error", pen.getNameAlreadyExistErrorMessage());
			rd.forward(request, response);
		}
	}
}
