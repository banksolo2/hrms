package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/editIssueType")
public class EditIssueTypeController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("editIssueType.jsp");
		int issueTypeId = Integer.parseInt(request.getParameter("issueTypeId"));
		IssueTypeDao itd = new IssueTypeDao();
		IssueType it = itd.getIssueTypeById(issueTypeId);
		String name = request.getParameter("name");
		//String employee[] = request.getParameterValues(name)
		it.setName(name);
		int updatedBy = (int)session.getAttribute("employeeId");
		it.setUpdatedBy(updatedBy);
		
		boolean isNameAlreadyExist = itd.isNameExistOnUpdate(it);
		if(isNameAlreadyExist == false) {
			int count = itd.updateIssueType(it);
			if(count >= 1) {
				session.setAttribute("success", "Issue type has been updated....");
				response.sendRedirect("allIssueTypes.jsp");
			}
			else {
				session.setAttribute("error", "Enable to update issue type....");
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", "Name already exist........");
			rd.forward(request, response);
		}
	}
}
