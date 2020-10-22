package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/editSupervisorRequisition")
public class EditSupervisorRequisitionController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		RequestDispatcher red = request.getRequestDispatcher("editSupervisorPendingRequisition.jsp");
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
			}
			else {
				message = rn.getSentToRecipientForApprovalMessage(true);
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
