package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/editRecipientsRequisition")
public class EditRecipientsRequisitionController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		RequestDispatcher red = request.getRequestDispatcher("editRecipientsPendingRequisition.jsp");
		RequisitionNotification rn = new RequisitionNotification();
		RequisitionStatusDao rsd = new RequisitionStatusDao();
		RequisitionDao rd = new RequisitionDao();
		int requisitionId = Integer.parseInt(request.getParameter("requisitionId"));
		Requisition r = rd.getRequisitionById(requisitionId);
		int requisitionStatusId = Integer.parseInt(request.getParameter("requisitionStatusId"));
		r.setRequisitionStatusId(requisitionStatusId);
		String comment = request.getParameter("comment");
		r.setComment(comment);
		int updatedBy = (int)session.getAttribute("employeeId");
		r.setUpdatedBy(updatedBy);
		RequisitionStatus rs = rsd.getRequisitionStatusById(requisitionStatusId);
		if(rs.getCode().equals("declined")) {
			r.setDeclinedBy(updatedBy);
			if(comment.trim().length() == 0) {
				session.setAttribute("error", "Please provide a comment...");
				red.forward(request, response);
			}
		}
		else {
			r.setApprovedBy(updatedBy);
		}
		
		//Update data in the database
		String message = null;
		int count = rd.updateRequisition(r);
		if(count >= 1) {
			if(rs.getCode().equals("declined")) {
				message = rn.getDeclinedRequisitionMessage(true);
			}
			else {
				message = rn.getApprovedRequisitionMessage(true);
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
