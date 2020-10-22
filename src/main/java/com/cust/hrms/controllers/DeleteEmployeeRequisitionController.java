package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/deleteEmployeeRequisition")
public class DeleteEmployeeRequisitionController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequisitionDao rd = new RequisitionDao();
		RequisitionNotification rn = new RequisitionNotification();
		int requisitionId = Integer.parseInt(request.getParameter("requisitionId"));
		Requisition r = rd.getRequisitionById(requisitionId);
		int count = rd.deleteRequisition(r);
		if(count >= 1) {
			session.setAttribute("success", rn.getDeletedRequisitionMessage(true));
			response.sendRedirect("allDraftedEmployeeRequisitionReport.jsp");
		}
		else {
			session.setAttribute("error", rn.getDeletedRequisitionMessage(false));
			response.sendRedirect("allDraftedEmployeeRequisitionReport.jsp");
		}
	}
}
