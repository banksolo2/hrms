package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.email.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;
import com.cust.hrms.email.message.*;
import com.oreilly.servlet.*;

@WebServlet("/createDepartmentSupportTicket")
public class CreateDepartmentSupportTicketController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("createDepartmentSupportTicket.jsp");
		SupportTicketNotification stn = new SupportTicketNotification();
		SupportTicketStatusDao stsd = new SupportTicketStatusDao();
		IssueTypeDao itd = new IssueTypeDao();
		SupportTicketDao std = new SupportTicketDao();
		HrmsEmail he = new HrmsEmail();
		SupportTicketEmailMessage stem = new SupportTicketEmailMessage();
		DateDao dd = new DateDao();
		FileUploadDao fud = new FileUploadDao();
		SupportTicket st = new SupportTicket();
		//fix max file size 500 Mb
	    int maxFileSize = 500000 * 1024;
	    int maxMemSize = maxFileSize;
		MultipartRequest mreq = new MultipartRequest(request, fud.getSupportTicketurl(), maxFileSize);
		st.setIssueReportDate(dd.getTodayDate());
		int issueTypeId = Integer.parseInt(mreq.getParameter("issueTypeId"));
		st.setIssueTypeId(issueTypeId);
		String issueDescription = mreq.getParameter("issueDescription");
		st.setIssueDescription(issueDescription);
		st.setIssueFor("department");
		int departmentId = Integer.parseInt(mreq.getParameter("departmentId"));
		st.setDepartmentId(departmentId);
		st.setSupportTicketStatusId(stsd.getSupportTicketStatusId("pending"));
		int createdBy = (int)session.getAttribute("employeeId");
		st.setCreatedBy(createdBy);
		boolean isSupportTicketExist = std.isSupportTicketExist(st);
		if(isSupportTicketExist == false) {
			String fileName = mreq.getFilesystemName("file");
	    	st.setFileUrl("filesUpload/supportTickets/"+fileName);
	    	int count = std.createSupportTicketForDepartment(st);
	    	if(count >= 1) {
	    		File newfileloc = new File(fud.getSupportTicketurl() + fileName);
		    	Boolean uploadresult = mreq.getFile("file").renameTo(newfileloc);
		    	if(he.isEmailEnable()) {
		    		EmployeeDao ed = new EmployeeDao();
		    		String emailAddresses[] = ed.getDepartmentEmployeesEmail(st.getDepartmentId());
		    		String data[] = {
		    				dd.changeFormatDate(st.getIssueReportDate()),
		    				ed.getEmployeeName(st.getCreatedBy()),
		    				itd.getIssueTypeName(st.getIssueTypeId()),
		    				st.getIssueDescription(),
		    		};
		    		
		    		stem.getinitializeSupportTicketMessage(emailAddresses, data);
		    	}
		    	session.setAttribute("success", stn.getPendingSupportTicketMessage(true));
		    	response.sendRedirect("createDepartmentSupportTicket.jsp");
	    	}
	    	else {
	    		session.setAttribute("error", stn.getPendingSupportTicketMessage(false));
	    		rd.forward(request, response);
	    	}
			out.println(st.toString());
		}
		else {
			session.setAttribute("error", stn.getSupportTicketAlreadyExistMessage());
			rd.forward(request, response);
		}
		
	} 
}
