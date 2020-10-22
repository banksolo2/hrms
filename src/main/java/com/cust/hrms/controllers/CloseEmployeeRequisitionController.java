package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/closeEmployeeRequisition")
public class CloseEmployeeRequisitionController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		RequestDispatcher rqd = request.getRequestDispatcher("editEmployeeApprovedRequisition.jsp");
		RequisitionNotification rn = new RequisitionNotification();
		RequisitionStatusDao rsd = new RequisitionStatusDao();
		RequisitionDao rd = new RequisitionDao();
		int requisitionId = Integer.parseInt(request.getParameter("requisitionId"));
		Requisition r = rd.getRequisitionById(requisitionId);
		r.setRequisitionStatusId(rsd.getRequisitionStatusId("closed"));
		int updatedBy = (int)session.getAttribute("employeeId");
		r.setUpdatedBy(updatedBy);
		int count = rd.updateRequisition(r);
		if(count >= 1) {
			session.setAttribute("success", rn.getClosedRequisitionMessage(true));
			response.sendRedirect("allApprovedEmployeeRequisitionReport.jsp");
		}
		else {
			session.setAttribute("error", rn.getClosedRequisitionMessage(false));
			rqd.forward(request, response);
		}
	}
}
