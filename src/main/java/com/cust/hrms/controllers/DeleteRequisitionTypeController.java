package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.notification.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/deleteRequisitionType")
public class DeleteRequisitionTypeController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		HttpSession session = request.getSession();
		RequisitionTypeNotification rtn = new RequisitionTypeNotification();
		RequisitionTypeDao rtd = new RequisitionTypeDao();
		int requisitionTypeId = Integer.parseInt(request.getParameter("requisitionTypeId"));
		RequisitionType rt = rtd.getRequisitionTypeById(requisitionTypeId);
		//delete requisition type from database
		int count = rtd.deleteRequisitionType(rt);
		if(count >= 1) {
			session.setAttribute("success", rtn.deleteRequisitionTypeMessage(true));
			response.sendRedirect("allRequisitionType.jsp");
		}
		else {
			session.setAttribute("error", rtn.deleteRequisitionTypeMessage(false));
			response.sendRedirect("allRequisitionType.jsp");
		}
	}
}
