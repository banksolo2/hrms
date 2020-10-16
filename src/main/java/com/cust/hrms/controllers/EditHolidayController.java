package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/editHoliday")
public class EditHolidayController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("editHoliday.jsp");
		int holidayId = Integer.parseInt(request.getParameter("holidayId"));
		String name = request.getParameter("name").trim();
		String dateAt = request.getParameter("dateAt");
		String description = request.getParameter("description").trim();
		int updatedBy = (int)session.getAttribute("employeeId");
		
		HolidayDao hd = new HolidayDao();
		Holiday h = new Holiday();
		h.setHolidayId(holidayId);
		h.setName(name);
		h.setDateAt(dateAt);
		h.setDescription(description);
		h.setUpdatedBy(updatedBy);
		//Check if holiday name already exist
		boolean isNameExist = hd.isNameExistOnUpdate(h);
		
		if(isNameExist == false) {
			//Check if date at already exist
			boolean isDateExist = hd.isDateAtExistOnUpdate(h);
			if(isDateExist == false) {
				//Update holiday table
				int count = hd.updateHoliday(h);
				if(count >= 1) {
					response.sendRedirect("allHolidays.jsp");
				}
				else {
					session.setAttribute("error", "Enable save holiday....");
					rd.forward(request, response);
				}
			}
			else {
				session.setAttribute("error", "Date already exist...");
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", "Name already exist..");
			rd.forward(request, response);
		}
	}
}
