package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/createPayrollRole")
public class CreatePayrollRoleController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = request.getRequestDispatcher("createPayrollRole.jsp");
		int createdBy = 0;
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			createdBy = (int)session.getAttribute("employeeId");
		}
		PayrollRoleNameNotification prnn = new PayrollRoleNameNotification();
		PayrollRoleNameDao prnd = new PayrollRoleNameDao();
		PayrollRoleName prn = new PayrollRoleName();
		prn.setCreatedBy(createdBy);
		String name = request.getParameter("name");
		prn.setName(name);
		
		//Check if name already exist
		boolean isNameExist = prnd.isNameExist(prn.getName());
		if(isNameExist == false) {
			// save data in the database table
			int count = prnd.createPayrollRoleName(prn);
			if(count >= 1) {
				session.setAttribute("success", prnn.getCreatePayrollRoleNameMessage(true));
				response.sendRedirect("createPayrollRole.jsp");
			}
			else {
				session.setAttribute("error", prnn.getCreatePayrollRoleNameMessage(false));
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", prnn.getNameAlreadyExistErrorMessage());
			rd.forward(request, response);
		}
	}
}
