package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/deletePayrollRole")
public class DeletePayrollRoleController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		HttpSession session = request.getSession();
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		PayrollRoleNameNotification prnn = new PayrollRoleNameNotification();
		PayrollRoleNameDao prnd = new PayrollRoleNameDao();
		int payrollRoleNameId = Integer.parseInt(request.getParameter("payrollRoleNameId"));
		PayrollRoleName prn = prnd.getPayrollRoleNameById(payrollRoleNameId);
		// delete data from database table
		int count = prnd.deletePayrollRoleName(prn);
		if(count >= 1) {
			session.setAttribute("success", prnn.getDeletePayrollRoleNameMessage(true));
		}
		else {
			session.setAttribute("error", prnn.getDeletePayrollRoleNameMessage(false));
		}
		response.sendRedirect("allPayrollRole.jsp");
		
	}
}
