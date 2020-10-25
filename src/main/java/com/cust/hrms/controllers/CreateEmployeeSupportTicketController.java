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
import com.oreilly.servlet.MultipartRequest;
import com.cust.hrms.dao.*;
import com.cust.hrms.notification.*;
import com.cust.hrms.email.message.*;
import com.cust.hrms.email.*;
/**
 * Servlet implementation class CreateEmployeeSupportTicketController
 */
@WebServlet("/createEmployeeSupportTicket")
public class CreateEmployeeSupportTicketController extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		//fix max file size 500 Mb
	    int maxFileSize = 500000 * 1024;
	    int maxMemSize = maxFileSize;
	    PrintWriter out = response.getWriter();
	    RequestDispatcher rd = request.getRequestDispatcher("createEmployeeSupportTicket.jsp");
	    HttpSession session = request.getSession();
	    FileUploadDao fud = new FileUploadDao();
	    SupportTicketStatusDao stsd = new SupportTicketStatusDao();
	    SupportTicketDao std = new SupportTicketDao();
	    IssueTypeDao itd = new IssueTypeDao();
	    EmployeeDao ed = new EmployeeDao();
	    DateDao dd = new DateDao();
	    SupportTicket st = new SupportTicket();
	    MultipartRequest mreq = new MultipartRequest(request, fud.getUrl()+fud.getSupportTicketurl(), maxFileSize);
	    st.setIssueReportDate(dd.getTodayDate());
	    int issueTypeId = Integer.parseInt(mreq.getParameter("issueTypeId"));
	    st.setIssueTypeId(issueTypeId);
	    String issueDescription = mreq.getParameter("issueDescription");
	    st.setIssueDescription(issueDescription);
	    String employees[] = mreq.getParameterValues("employees");
	    String result = "";
	    for(int i = 0; i < employees.length; i++) {
	    	result += "'"+employees[i]+"'";
	    	if(i != (employees.length - 1)) {
	    		result += ":";
	    	}
	    }
	    st.setEmployees(result);
	    int createdBy = (int)session.getAttribute("employeeId");
	    st.setIssueFor("employees");
	    st.setCreatedBy(createdBy);
	    st.setFileUrl("#");
	    st.setSupportTicketStatusId(stsd.getSupportTicketStatusId("pending"));
	    SupportTicketNotification stn = new SupportTicketNotification();
	    boolean isSupportTicketExist = std.isSupportTicketExist(st);
	    if(isSupportTicketExist == false) {
	    	String fileName = mreq.getFilesystemName("file");
	    	st.setFileUrl(fud.getSupportTicketurl()+fileName);
	    	int count = std.createSupportTicketByForEmployees(st);
	    	
	    	if(count >= 1) {
	    		File newfileloc = new File(fud.getUrl()+fud.getSupportTicketurl() + fileName);
		    	Boolean uploadresult = mreq.getFile("file").renameTo(newfileloc);
		    	HrmsEmail hr = new HrmsEmail();
		    	if(hr.isEmailEnable()) {
		    		int employeesId[] = std.getEmployeesId(st.getEmployees());
		    		String emailAddress[] = ed.getEmployeesEmail(employeesId);
		    		String data[] = {
		    				dd.changeFormatDate(st.getIssueReportDate()),
		    				ed.getEmployeeName(st.getCreatedBy()),
		    				itd.getIssueTypeName(st.getIssueTypeId()),
		    				st.getIssueDescription(),
		    		};
		    		SupportTicketEmailMessage stem = new SupportTicketEmailMessage();
		    		stem.getinitializeSupportTicketMessage(emailAddress, data);
		    	}
		    	session.setAttribute("success", stn.getPendingSupportTicketMessage(true));
		    	response.sendRedirect("createEmployeeSupportTicket.jsp");
	    	}
	    	else {
	    		session.setAttribute("error", stn.getPendingSupportTicketMessage(false));
	    		rd.forward(request, response);
	    	}
	    	
	    }
	    else {
	    	session.setAttribute("error", stn.getSupportTicketAlreadyExistMessage());
	    	rd.forward(request, response);
	    }
	    
	}
}
