package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.oreilly.servlet.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;
import com.cust.hrms.email.*;
import com.cust.hrms.email.message.*;

@WebServlet("/editEmployeeDraftRequisition")
public class EditEmployeeDraftRequisitionController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		RequestDispatcher rqd = request.getRequestDispatcher("/editEmployeeDraftRequisition.jsp");
		int maxFileSize = 500000 * 1024;
	    int maxMemSize = maxFileSize;
	    FileUploadDao fud = new FileUploadDao();
	    MultipartRequest mreq = new MultipartRequest(request, fud.getUrl()+fud.getRequisitionUrl(), maxFileSize);
	    RequisitionStatusDao rsd = new RequisitionStatusDao();
	    RequisitionDao rd = new RequisitionDao();
	    HrmsEmail he = new HrmsEmail();
	    EmployeeDao ed = new EmployeeDao();
	    RequisitionEmailMessage rem = new RequisitionEmailMessage();
	    RequisitionNotification rn = new RequisitionNotification();
	    int requisitionId = Integer.parseInt(mreq.getParameter("requisitionId"));
	    Requisition r = rd.getRequisitionById(requisitionId);
	    int requisitionTypeId = Integer.parseInt(mreq.getParameter("requisitionTypeId"));
	    r.setRequisitionTypeId(requisitionTypeId);
	    String subject = mreq.getParameter("subject");
	    r.setSubject(subject);
	    String recipients[] = mreq.getParameterValues("recipients");
	    String result = "";
	    for(int i = 0; i < recipients.length; i++) {
	    	result += "'"+recipients[i]+"'";
	    	if(i != (recipients.length - 1)) {
	    		result += ":";
	    	}
	    }
	    r.setRecipients(result);
	    r.setRequisitionStatusId(rsd.getRequisitionStatusId("sent_to_supervisor_for_authorization"));
	    int updatedBy = (int)session.getAttribute("employeeId");
	    r.setUpdatedBy(updatedBy);
	    String fileName = mreq.getFilesystemName("file");
	    String oldFileUrl = null;
	    if(fileName != null) {
	    	oldFileUrl = r.getFileUrl();
	    	r.setFileUrl(fud.getRequisitionUrl()+fileName);
	    }
	    int count = rd.updateRequisition(r);
	    if(count >= 1) {
	    	if(fileName != null) {
	    		File oldFile = new File(fud.getUrl()+oldFileUrl);
	    		oldFile.delete();
	    		File newfileloc = new File(fud.getUrl()+r.getFileUrl());
		    	Boolean uploadresult = mreq.getFile("file").renameTo(newfileloc);
	    	}
	    	
	    	if(he.isEmailEnable()) {
	    		Employee e = ed.getEmployeeById(r.getSupervisorId());
	    		System.out.println(e.toString());
	    		String emailAddress[] = {e.getEmail()};
	    		String data[] = {e.getNameInitials()};
	    		rem.getRequisitionForSupervisorAuthorizationMessage(emailAddress, data);
	    	}
	    	session.setAttribute("success", rn.getSentToSupervisorForAuthorizationMessage(true));
	    	response.sendRedirect("allDraftedEmployeeRequisitionReport.jsp");
	    }
	    else {
	    	session.setAttribute("error", rn.getSentToSupervisorForAuthorizationMessage(false));
	    	rqd.forward(request, response);
	    }
	    out.println(r.toString());
	}
}
