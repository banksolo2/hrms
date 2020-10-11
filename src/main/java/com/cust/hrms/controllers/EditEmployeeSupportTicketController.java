package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.notification.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/editEmployeeSupportTicket")
public class EditEmployeeSupportTicketController extends HttpServlet {

		protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
			PrintWriter out = response.getWriter();
			HttpSession session = request.getSession();
			RequestDispatcher rd = request.getRequestDispatcher("employeeEditSupportTicket.jsp");
			SupportTicketStatusDao stsd = new SupportTicketStatusDao();
			SupportTicketDao std = new SupportTicketDao();
			SupportTicketNotification stn = new SupportTicketNotification();
			int supportTicketId = Integer.parseInt(request.getParameter("supportTicketId"));
			SupportTicket st = std.getSupportTicketById(supportTicketId);
			int supportTicketStatusId = Integer.parseInt(request.getParameter("supportTicketStatusId"));
			st.setSupportTicketStatusId(supportTicketStatusId);
			String comment = request.getParameter("comment");
			st.setComment(comment);
			int updatedBy = (int)session.getAttribute("employeeId");
			st.setUpdatedBy(updatedBy);
			
			//Update support ticket in the database
			int count = std.updateSupportTicket(st);
			SupportTicketStatus sts = stsd.getSupportTicketStatusById(supportTicketStatusId);
			String message = null;
			if(count >= 1) {
				if(sts.getCode().equals("closed")) {
					message = stn.getClosedSupportTicketMessage(true);
				}
				else {
					message = stn.getUnresolvedSupportTicketMessage(true);
				}
				session.setAttribute("success", message);
				response.sendRedirect("employeeResolveSupportTicketReport.jsp");
			}
			else {
				if(sts.getCode().equals("closed")) {
					message = stn.getClosedSupportTicketMessage(false);
				}
				else {
					message = stn.getUnresolvedSupportTicketMessage(false);
				}
				session.setAttribute("error", message);
				rd.forward(request, response);
			}
		} 
}
