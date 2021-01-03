package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/editEmployeeStatus")
public class EditEmployeeStatusController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = request.getRequestDispatcher("editEmployeeStatus.jsp");
		int updatedBy = 0;
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			updatedBy = (int)session.getAttribute("employeeId");
		}
		EmployeeStatusNotification esn = new EmployeeStatusNotification();
		EmployeeStatusDao esd = new EmployeeStatusDao();
		int employeeStatusId = Integer.parseInt(request.getParameter("employeeStatusId"));
		EmployeeStatus es = esd.getEmployeeStatusById(employeeStatusId);
		es.setUpdatedBy(updatedBy);
		String name = request.getParameter("name");
		es.setName(name);
		// Check if name already exist
		boolean isNameExist = esd.isNameExistOnUpdate(es);
		if(isNameExist == false) {
			//Update data in the database table
			int count = esd.updateEmployeeStatus(es);
			if(count >= 1) {
				session.setAttribute("success", esn.updateEmployeeStatusMessage(true));
				response.sendRedirect("allEmployeeStatusReport.jsp");
			}
			else {
				session.setAttribute("error", esn.updateEmployeeStatusMessage(false));
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", esn.getNameAlreadyExistErrorMessage());
			rd.forward(request, response);
		}
	}
}
