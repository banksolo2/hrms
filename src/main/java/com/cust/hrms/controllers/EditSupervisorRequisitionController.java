package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;
import com.cust.hrms.email.*;
import com.cust.hrms.email.message.*;

@WebServlet("/editSupervisorRequisition")
public class EditSupervisorRequisitionController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		RequestDispatcher red = request.getRequestDispatcher("editSupervisorPendingRequisition.jsp");
		RequisitionNotification rn = new RequisitionNotification();
		RequisitionStatusDao rsd = new RequisitionStatusDao();
		RequisitionDao rd = new RequisitionDao();
		EmployeeDao ed = new EmployeeDao();
		HrmsEmail he = new HrmsEmail();
		RequisitionEmailMessage rem = new RequisitionEmailMessage();
		int requisitionId = Integer.parseInt(request.getParameter("requisitionId"));
		Requisition r = rd.getRequisitionById(requisitionId);
		int requisitionStatusId = Integer.parseInt(request.getParameter("requisitionStatusId"));
		r.setRequisitionStatusId(requisitionStatusId);
		String comment = request.getParameter("comment");
		r.setComment(comment);
		int updatedBy = (int)session.getAttribute("employeeId");
		r.setUpdatedBy(updatedBy);
		RequisitionStatus rs = rsd.getRequisitionStatusById(requisitionStatusId);
		if(rs.getCode().trim().equals("declined")) {
			r.setDeclinedBy(updatedBy);
			if(comment.trim().length() == 0) {
				session.setAttribute("error", "Please provide a comment....");
				red.forward(request, response);
			}
		}	
		String message = null;
		int count = rd.updateRequisition(r);
		if(count >= 1) {
			if(rs.getCode().equals("declined")) {
				message = rn.getDeclinedRequisitionMessage(true);
				//Send email message
				if(he.isEmailEnable()) {
					Employee e = ed.getEmployeeById(r.getRequesterId());
					
					String emailAddress[] = {e.getEmail()};
					String data[] = {
							e.getNameInitials(),
							String.valueOf(r.getRequisitionId()),
							ed.getEmployeeName(r.getDeclinedBy()), 
							r.getComment()
					};
					rem.getRequisitionRequesterDeclinedMessage(emailAddress, data);
				}
			}
			else {
				message = rn.getSentToRecipientForApprovalMessage(true);
				//Send email message
				if(he.isEmailEnable()) {
				  int employeesId[] = ed.getEmployeesId(r.getRecipients());
				  String emailAddress[] = ed.getEmployeesEmail(employeesId);
				  String data[] = {String.valueOf(r.getRequesterId())};
				  rem.getRequisitionSentRecipientsForApprovalMessage(emailAddress, data);
				}
			}
			session.setAttribute("success", message);
			response.sendRedirect("allPendingSupervisorRequisitionReport.jsp");
		}
		else {
			if(rs.getCode().equals("declined")) {
				message = rn.getDeclinedRequisitionMessage(false);
			}
			else {
				message = rn.getSentToRecipientForApprovalMessage(false);
			}
			session.setAttribute("error", message);
			red.forward(request, response);
		}
	}
}
