package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/deleteHoliday")
public class DeleteHolidayController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int holidayId = Integer.parseInt(request.getParameter("holidayId"));
		HolidayDao hd = new HolidayDao();
		Holiday h = hd.getHolidayById(holidayId);
		hd.deleteHoliay(h);
		response.sendRedirect("allHolidays.jsp");
	}
}
