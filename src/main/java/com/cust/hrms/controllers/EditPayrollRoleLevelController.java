package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/editPayrollRoleLevel")
public class EditPayrollRoleLevelController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = request.getRequestDispatcher("editPayrollRoleLevel.jsp");
		int updatedBy = 0;
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			updatedBy = (int)session.getAttribute("employeeId");
			
		}
		PayrollRoleLevelNotification prln = new PayrollRoleLevelNotification();
		PayrollRoleLevelDao prld = new PayrollRoleLevelDao();
		int payrollRoleLevelId = Integer.parseInt(request.getParameter("payrollRoleLevelId"));
		PayrollRoleLevel prl = prld.getPayrollRoleLevelById(payrollRoleLevelId);
		session.setAttribute("payrollRoleLevelId", prl.getPayrollRoleLevelId());
		session.setAttribute("payrollRoleNameId", prl.getPayrollRoleNameId());
		prl.setUpdatedBy(updatedBy);
		int levelId = Integer.parseInt(request.getParameter("levelId"));
		prl.setLevelId(levelId);
		// check if payroll role level already exist
		boolean isPayrollRoleLevelExist = prld.isPayrollRoleLevelExistOnUpdate(prl);
		if(isPayrollRoleLevelExist == false) {
			int count = prld.updatePayrollRoleLevel(prl);
			if(count >= 1) {
				session.setAttribute("success", prln.getUpdatePayrollRoleLevelMessage(true));
				response.sendRedirect("viewPayrollRoleLevel.jsp");
			}
			else {
				session.setAttribute("error", prln.getUpdatePayrollRoleLevelMessage(false));
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", prln.getPayrollRoleLevelAlreadyExistErrorMessage());
			rd.forward(request, response);
		}
	}
}
