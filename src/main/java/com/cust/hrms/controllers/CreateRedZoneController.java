package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/createRedZone")
public class CreateRedZoneController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("createRedZone.jsp");
		int departmentId = Integer.parseInt(request.getParameter("departmentId"));
		String dateFrom = request.getParameter("dateFrom");
		String dateTo = request.getParameter("dateTo");
		String description = request.getParameter("description");
		int createdBy = (int)session.getAttribute("employeeId");
		
		RedZoneDao rzd = new RedZoneDao();
		RedZone rz = new RedZone();
		rz.setDepartmentId(departmentId);
		rz.setDateFrom(dateFrom);
		rz.setDateTo(dateTo);
		rz.setDescription(description);
		rz.setCreatedBy(createdBy);
		
		int count = rzd.createRedZone(rz);
		if(count >= 1) {
			session.setAttribute("success", "Red zone created...");
			response.sendRedirect("createRedZone.jsp");
		}
		else {
			session.setAttribute("error", "Enable to create red zone...");
			rd.forward(request, response);
		}
	}
}
