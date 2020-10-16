package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/deleteRedZone")
public class DeleteRedZoneController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		int redZoneId = Integer.parseInt(request.getParameter("redZoneId"));
		RedZoneDao rzd = new RedZoneDao();
		RedZone rz = rzd.getRedZoneById(redZoneId);
		rzd.deleteRedZone(rz);
		response.sendRedirect("allRedZone.jsp");
	}
}
