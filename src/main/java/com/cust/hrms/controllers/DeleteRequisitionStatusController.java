package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/deleteRequisitionStatus")
public class DeleteRequisitionStatusController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		//RequestDispatcher rd = request.getRequestDispatcher("allRequisitionStatus.jsp");
		RequisitionStatusNotification rsn = new RequisitionStatusNotification();
		RequisitionStatusDao rsd = new RequisitionStatusDao();
		int requisitionStatusId = Integer.parseInt(request.getParameter("requisitionStatusId"));
		RequisitionStatus rs = rsd.getRequisitionStatusById(requisitionStatusId);
		int count = rsd.deleteRequisitionStatus(rs);
		if(count >= 1) {
			session.setAttribute("success", rsn.getDeleteRequisitionStatusMessage(true));
			response.sendRedirect("allRequisitionStatus.jsp");
		}
		else {
			session.setAttribute("error", rsn.getDeleteRequisitionStatusMessage(false));
			response.sendRedirect("allRequisitionStatus.jsp");
		}
	}
}
