package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/deleteIssueType")
public class DeleteIssueTypeController extends HttpServlet {

		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			PrintWriter out = response.getWriter();
			HttpSession session = request.getSession();
			RequestDispatcher rd = request.getRequestDispatcher("allIssueTypes.jsp");
			int issueTypeId = Integer.parseInt(request.getParameter("issueTypeId"));
			IssueTypeDao itd = new IssueTypeDao();
			IssueType it = itd.getIssueTypeById(issueTypeId);
			int count = itd.deleteIssueType(it);
			if(count >= 1) {
				session.setAttribute("success", "Issue type has been deleted...");
				response.sendRedirect("allIssueTypes.jsp");
			}
			else {
				session.setAttribute("error", "Enable to delete issue type.....");
				rd.forward(request, response);
			}
		}
}
