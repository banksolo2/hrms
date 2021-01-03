package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/createPayrollRoleLevel")
public class CreatePayrollRoleLevelController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = request.getRequestDispatcher("createPayrollRoleLevel.jsp");
		int createdBy = 0;
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			createdBy = (int)session.getAttribute("employeeId");
		}
		PayrollRoleLevelNotification prln = new PayrollRoleLevelNotification();
		PayrollRoleLevelDao prld = new PayrollRoleLevelDao();
		PayrollRoleLevel prl = new PayrollRoleLevel();
		prl.setCreatedBy(createdBy);
		int payrollRoleNameId = Integer.parseInt(request.getParameter("payrollRoleNameId"));
		session.setAttribute("payrollRoleNameId", payrollRoleNameId);
		prl.setPayrollRoleNameId(payrollRoleNameId);
		String levelId[] = request.getParameterValues("levelId");
		int existCount = 0;
		int count = 0;
		boolean isPayrollRollLevelExist = false;
		for(String x : levelId) {
			prl.setLevelId(Integer.parseInt(x));
			isPayrollRollLevelExist = prld.isPayrollRoleLevelExist(prl);
			if(isPayrollRollLevelExist == false) {
				count += prld.createPayrollRoleLevel(prl); 
			}
			else {
				existCount++;
			}
		}
		if(count >= 1) {
			session.setAttribute("success", prln.getCreatePayrollRoleLevelMessage(true));
			response.sendRedirect("viewPayrollRoleLevel.jsp");
		}
		else if(existCount >= 1) {
			session.setAttribute("error", prln.getPayrollRoleLevelAlreadyExistErrorMessage());
			rd.forward(request, response);
		}
		else {
			session.setAttribute("error", prln.getCreatePayrollRoleLevelMessage(false));
			rd.forward(request, response);
		}
	}

}
