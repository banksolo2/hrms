package com.cust.hrms.controllers;

import java.io.*;
import java.sql.Date;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/createHoliday")
public class CreateHolidayController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("createHoliday.jsp");
		String name = request.getParameter("name").trim();
		String dateAt = request.getParameter("dateAt");
		String description = request.getParameter("description").trim();
		int createdBy = (int)session.getAttribute("employeeId");
		HolidayDao hd = new HolidayDao();
		Holiday h = new Holiday();
		
		//Check if name already exist
		boolean isNameExist = hd.isNameExist(name);
		if(isNameExist == false) {
			//check if date already exist
			Date date = Date.valueOf(dateAt);
			boolean isDateExist = hd.isDateAtExist(date);
			if(isDateExist == false) {
				// save holiday to database
				h.setName(name);
				h.setDateAt(dateAt);
				h.setDescription(description);
				h.setCreatedBy(createdBy);
				int count = hd.createHoliday(h);
				if(count >= 1) {
					session.setAttribute("success", "Holiday has been created...");
					response.sendRedirect("createHoliday.jsp");
				}
				else {
					session.setAttribute("error", "Enable to create holiday...");
					rd.forward(request, response);
				}
				
			}
			else {
				session.setAttribute("error", "Holiday date already exist...");
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", "Holiday name already exist....");
			rd.forward(request, response);
		}
	}
}
