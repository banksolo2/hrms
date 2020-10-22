package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.oreilly.servlet.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/createEmployeeRequisition")
public class CreateEmployeeRequisitionController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		PrintWriter out = response.getWriter();
		int maxFileSize = 500000 * 1024;
	    int maxMemSize = maxFileSize;
	    FileUploadDao fud = new FileUploadDao();
	    MultipartRequest mreq = new MultipartRequest(request, fud.getUrl()+fud.getRequisitionUrl(), maxFileSize);
	    HttpSession session = request.getSession();
	    RequestDispatcher rd = request.getRequestDispatcher("createEmployeeRequisition.jsp"); 
	    RequisitionStatusDao rsd = new RequisitionStatusDao();
	    RequisitionNotification rn = new RequisitionNotification();
	    RequisitionDao rqd = new RequisitionDao();
	    DateDao dd = new DateDao();
	    Requisition r = new Requisition();
	    r.setCreatedDate(dd.getTodayDate());
	    int requisitionTypeId = Integer.parseInt(mreq.getParameter("requisitionTypeId"));
	    r.setRequisitionTypeId(requisitionTypeId);
	    String subject = mreq.getParameter("subject");
	    r.setSubject(subject);
	    int employeeId = (int)session.getAttribute("employeeId");
	    r.setRequesterId(employeeId);
	    r.setCreatedBy(employeeId);
	    int supervisorId = Integer.parseInt(mreq.getParameter("supervisorId"));
	    r.setSupervisorId(supervisorId); 
	    String recipients[] = mreq.getParameterValues("recipients");
	    String result = "";
	    for(int i = 0; i < recipients.length; i++) {
	    	result += "'"+recipients[i]+"'";
	    	if(i != (recipients.length - 1)) {
	    		result += ":";
	    	}
	    }
	    r.setRecipients(result);
	    String fileName = mreq.getFilesystemName("file");
	    r.setFileUrl(fud.getRequisitionUrl()+fileName);
	    int requisitionStatusId = Integer.parseInt(mreq.getParameter("requisitionStatusId"));
	    r.setRequisitionStatusId(requisitionStatusId);
	    
	    //Save requisition in the database
	    RequisitionStatus reqs = rsd.getRequisitionStatusById(r.getRequisitionStatusId());
	    String message = "";
	    int count = rqd.createRequisition(r);
	    if(count >= 1) {
	    	File newfileloc = new File(fud.getUrl()+r.getFileUrl());
	    	Boolean uploadresult = mreq.getFile("file").renameTo(newfileloc);
	    	if(reqs.getCode().equals("drafted")) {
	    		message = rn.getDraftedRequisitionMessage(true);
	    	}
	    	else {
	    		message = rn.getSentToSupervisorForAuthorizationMessage(true);
	    	}
	    	session.setAttribute("success", message);
	    	response.sendRedirect("createEmployeeRequisition.jsp");
	    }
	    else {
	      if(reqs.getCode().equals("drafted")) {
	    	  message = rn.getDraftedRequisitionMessage(false);
	      }
	      else {
	    	  message = rn.getSentToSupervisorForAuthorizationMessage(false);
	      }
	      session.setAttribute("error", message);
	      rd.forward(request, response);
	    }
	    //out.println(r.toString());
	}
}
