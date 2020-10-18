package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/editRequisitionType")
public class EditRequisitionTypeController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("editRequisitionType.jsp");
		PrintWriter out = response.getWriter();
		RequisitionTypeNotification rtn = new RequisitionTypeNotification();
		RequisitionTypeDao rtd = new RequisitionTypeDao();
		int requisitionTypeId = Integer.parseInt(request.getParameter("requisitionTypeId"));
		RequisitionType rt = rtd.getRequisitionTypeById(requisitionTypeId);
		String name = request.getParameter("name");
		rt.setName(name);
		int updatedBy = (int)session.getAttribute("employeeId");
		rt.setUpdatedBy(updatedBy);
		
		//Check if name already exist
		boolean isNameExist = rtd.isNameExistOnUpdate(rt);
		if(isNameExist == false) {
			//Update database table requisition_types
			int count = rtd.updateRequisitionType(rt);
			if(count >= 1) {
				session.setAttribute("success", rtn.updateRequisitionTypeMessage(true));
				response.sendRedirect("allRequisitionType.jsp");
			}
			else {
				session.setAttribute("error", rtn.updateRequisitionTypeMessage(false));
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", rtn.getNameAlreadyExistMessage());
			rd.forward(request, response);
		}
	}
}
