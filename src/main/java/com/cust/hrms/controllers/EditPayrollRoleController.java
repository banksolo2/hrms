package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/editPayrollRole")
public class EditPayrollRoleController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = request.getRequestDispatcher("editPayrollRole.jsp");
		int updatedBy = 0;
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			updatedBy = (int)session.getAttribute("employeeId");
		}
		PayrollRoleNameNotification prnn = new PayrollRoleNameNotification();
		PayrollRoleNameDao prnd = new PayrollRoleNameDao();
		int payrollRoleNameId = Integer.parseInt(request.getParameter("payrollRoleNameId"));
		PayrollRoleName prn = prnd.getPayrollRoleNameById(payrollRoleNameId);
		prn.setUpdatedBy(updatedBy);
		String name = request.getParameter("name");
		prn.setName(name);
		
		//Check if name already exist
		boolean isNameExist = prnd.isNameExistOnUpdate(prn);
		if(isNameExist == false) {
			// update data in the database table
			int count = prnd.updatePayrollRoleName(prn);
			if(count >= 1) {
				session.setAttribute("success", prnn.getUpdatePayrollRoleNameMessage(true));
				response.sendRedirect("allPayrollRole.jsp");
			}
			else {
				session.setAttribute("error", prnn.getUpdatePayrollRoleNameMessage(false));
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", prnn.getNameAlreadyExistErrorMessage());
			rd.forward(request, response);
		}
	}
}
