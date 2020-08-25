package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/createRedZoneAllDepartment")
public class CreateRedZoneAllDepartmentController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("createRedZoneAllDepartment.jsp");
		int createdBy = (int)session.getAttribute("employeeId");
		String dateFrom = request.getParameter("dateFrom");
		String dateTo = request.getParameter("dateTo");
		String description = request.getParameter("description");
		
		RedZoneDao rzd = new RedZoneDao();
		RedZone rz = new RedZone();
		rz.setDescription(description);
		rz.setCreatedBy(createdBy);
		rz.setDateFrom(dateFrom);
		rz.setDateTo(dateTo);
		int count = rzd.createRedZoneAllDepartment(rz);
		if(count >= 1) {
			session.setAttribute("success", "Red zone for all department");
			response.sendRedirect("createRedZoneAllDepartment.jsp");
		}
		else {
			session.setAttribute("error", "Enable to create red zone for all department...");
			rd.forward(request, response);
		}
		
	}
}
