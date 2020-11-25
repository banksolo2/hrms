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

@WebServlet("/editRecipientsRequisition")
public class EditRecipientsRequisitionController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		RequestDispatcher red = request.getRequestDispatcher("editRecipientsPendingRequisition.jsp");
		int updatedBy = 0;
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			updatedBy = (int)session.getAttribute("employeeId");
		}
		EmployeeDao ed = new EmployeeDao();
		RequisitionNotification rn = new RequisitionNotification();
		RequisitionStatusDao rsd = new RequisitionStatusDao();
		RequisitionDao rd = new RequisitionDao();
		HrmsEmail he = new HrmsEmail();
		RequisitionEmailMessage rem = new RequisitionEmailMessage();
		int requisitionId = Integer.parseInt(request.getParameter("requisitionId"));
		Requisition r = rd.getRequisitionById(requisitionId);
		int requisitionStatusId = Integer.parseInt(request.getParameter("requisitionStatusId"));
		r.setRequisitionStatusId(requisitionStatusId);
		String comment = request.getParameter("comment").trim();
		r.setComment(comment);
		r.setUpdatedBy(updatedBy);
		RequisitionStatus rs = rsd.getRequisitionStatusById(requisitionStatusId);
		if(rs.getCode().equals("declined") && comment.length() < 1) {
			session.setAttribute("error", "Please provide a comment....");
			red.forward(request, response);
		}
		else {
			if(rs.getCode().equals("approved")) {
				r.setApprovedBy(updatedBy);
			}
			else if(rs.getCode().equals("declined")) {
				r.setDeclinedBy(updatedBy);
			}
			//Update data in the database
			String message = null;
			int count = rd.updateRequisition(r);
			if(count >= 1) {
				if(rs.getCode().equals("declined")) {
					message = rn.getDeclinedRequisitionMessage(true);
					//Send email notification
					if(he.isEmailEnable()) {
						Employee e = ed.getEmployeeById(r.getRequesterId());
						/*Send email Notification
						 * 1. To Requester
						 * 2. To Recipients*/
						
						// 1. To Requester
						String requesterEmail[] = {e.getEmail()};
						String requesterData[] = {
								e.getNameInitials(),
								String.valueOf(r.getRequisitionId()),
								ed.getEmployeeName(r.getDeclinedBy()),
								r.getComment()
						};
						rem.getRequisitionRequesterDeclinedMessage(requesterEmail, requesterData);
						
						//2. To Recipients
						int recipientsId [] = ed.getEmployeesId(r.getRecipients());
						String recipientsEmail[] = ed.getEmployeesEmail(recipientsId);
						String recipientData[] = {
								String.valueOf(r.getRequisitionId()),
								ed.getEmployeeName(r.getDeclinedBy())
						};
						rem.getRequisitionRecipientsDeclinedMessage(recipientsEmail, recipientData);
					}
				}
				else {
					message = rn.getApprovedRequisitionMessage(true);
					if(he.isEmailEnable()) {
						/*Send email Notification
						 * 1. To Requester
						 * 2. To Recipients*/
						
						//1. To Requester
						Employee e = ed.getEmployeeById(r.getRequesterId());
						String requesterEmail[] = {e.getEmail()};
						String requesterData[] = {
								e.getNameInitials(),
								String.valueOf(r.getRequisitionId()),
								ed.getEmployeeName(r.getApprovedBy())
						};
						rem.getRequisitionRequesterApprovedMessage(requesterEmail, requesterData);
						
						//2. To Recipients
						int recipientsId[] = ed.getEmployeesId(r.getRecipients());
						String recipientsEmail[] = ed.getEmployeesEmail(recipientsId);
						String recipientData[] = {
								String.valueOf(r.getRequisitionId()),
								ed.getEmployeeName(r.getApprovedBy())
						};
						rem.getRequisitionRecipientsApprovedMessage(recipientsEmail, recipientData);
					}
				}
				session.setAttribute("success", message);
				response.sendRedirect("allPendingRecipientRequisitionReport.jsp");
			}
			else {
				if(rs.getCode().equals("declined")) {
					message = rn.getDeclinedRequisitionMessage(false);
				}
				else {
					message = rn.getApprovedRequisitionMessage(false);
				}
				session.setAttribute("error", message);
				red.forward(request, response);
			}
		}
	}

}
