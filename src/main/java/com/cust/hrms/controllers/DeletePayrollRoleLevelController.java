package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/deletePayrollRoleLevel")
public class DeletePayrollRoleLevelController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			PayrollRoleLevelNotification prln = new PayrollRoleLevelNotification();
			PayrollRoleLevelDao prld = new PayrollRoleLevelDao();
			int payrollRoleLevelId = Integer.parseInt(request.getParameter("payrollRoleLevelId"));
			PayrollRoleLevel prl = prld.getPayrollRoleLevelById(payrollRoleLevelId);
			session.setAttribute("payrollRoleNameId", prl.getPayrollRoleNameId());
			int count = prld.deletePayrollRoleLevel(prl);
			if(count >= 1) {
				session.setAttribute("success", prln.getDeletePayrollRoleLevelMessage(true));
			}
			else {
				session.setAttribute("error", prln.getDeletePayrollRoleLevelMessage(false));
			}
			response.sendRedirect("viewPayrollRoleLevel.jsp");
		}
	}
}
