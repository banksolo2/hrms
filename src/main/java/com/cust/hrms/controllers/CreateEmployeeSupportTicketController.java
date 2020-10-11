package com.cust.hrms.controllers;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;
import com.cust.hrms.models.*;
import com.cust.hrms.dao.*;
/**
 * Servlet implementation class CreateEmployeeSupportTicketController
 */
@WebServlet("/createEmployeeSupportTicket")
public class CreateEmployeeSupportTicketController extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = request.getRequestDispatcher("createEmployeeSupportTicket.jsp");
		//ServletFileUpload sfu = new ServletFileUpload(new DiskFileItemFactory());
		SupportTicketDao std = new SupportTicketDao();
		SupportTicketStatusDao stsd = new SupportTicketStatusDao();
		FileUploadDao fud = new FileUploadDao();
		SupportTicket st = new SupportTicket();
		DateDao dd = new DateDao();
		String issueReportDate = request.getParameter("issueReportDate");
		issueReportDate = dd.convertDateFormat(issueReportDate, "/");
		st.setFileUrl(fud.getSupportTicketurl()+"HRMS MANUAL.pdf");
		st.setIssueReportDate(issueReportDate);
		int issueTypeId = Integer.parseInt(request.getParameter("issueTypeId"));
		st.setIssueTypeId(issueTypeId);
		String issueDescription = request.getParameter("issueDescription");
		st.setIssueDescription(issueDescription);
		String employees[] = request.getParameterValues("employees");
		String result = "";
		for(int i = 0; i < employees.length; i++) {
			result += "'"+employees[i]+"'";
			if(i != (employees.length - 1)) {
				result +=":";
			}
		}
		st.setEmployees(result);
		int createdBy = (int)session.getAttribute("employeeId");
		st.setCreatedBy(createdBy);
		st.setSupportTicketStatusId(stsd.getSupportTicketStatusId("pending"));
		
		int count = std.createSupportTicketByForEmployees(st);
		if(count >= 1) {
			session.setAttribute("success", "Support ticket has been created...");
			response.sendRedirect("createEmployeeSupportTicket.jsp");
		}
		else {
			session.setAttribute("error", "enable to create support ticket....");
			rd.forward(request, response);
		}
		
		/*if(request.getParameter("issueDescription") != null)
		{
		  out.println(request.getParameter("action"));
		}
		String fileUrl = null;
		try {
			List<FileItem> file = sfu.parseRequest(request);
			
			for(FileItem x : file) {
				fileUrl = fud.getSupportTicketurl()+x.getName();
				x.write(new File(fileUrl));
			}
			
			
		}
		catch(Exception ex) {
			System.out.println(ex.fillInStackTrace());
		}*/
	}
}
