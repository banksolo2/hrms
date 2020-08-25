package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/editRedZone")
public class EditRedZoneController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("editRedZone.jsp");
		int updatedBy = (int)session.getAttribute("employeeId");
		int departmentId = Integer.parseInt(request.getParameter("departmentId"));
		int redZoneId = Integer.parseInt(request.getParameter("redZoneId"));
		String dateFrom = request.getParameter("dateFrom");
		String dateTo = request.getParameter("dateTo");
		String description = request.getParameter("description");
		
		RedZoneDao rzd = new RedZoneDao();
		RedZone rz = new RedZone();
		rz.setRedZoneId(redZoneId);
		rz.setDepartmentId(departmentId);
		rz.setDateFrom(dateFrom);
		rz.setDateTo(dateTo);
		rz.setDescription(description);
		rz.setUpdatedBy(updatedBy);
		
		int count = rzd.updateRedZone(rz);
		
		if(count >= 1) {
			response.sendRedirect("allRedZone.jsp");
		}
		else {
			session.setAttribute("error", "Enable to edit red zone.....");
			rd.forward(request, response);
			
		}
	}
}
