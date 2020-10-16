package com.cust.hrms.controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import com.cust.hrms.models.*;
import com.cust.hrms.dao.*;

@WebServlet("/editGender")
public class EditGenderController extends HttpServlet {

		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			int genderId = Integer.parseInt(request.getParameter("genderId"));
			String name = request.getParameter("name").trim();
			HttpSession session = request.getSession();
			RequestDispatcher rd = request.getRequestDispatcher("editGender.jsp");
			int updatedBy = (int)session.getAttribute("employeeId");
			
			GenderDao gd = new GenderDao();
			Gender g = new Gender();
			g.setGenderId(genderId);
			g.setName(name);
			g.setUpdatedBy(updatedBy);
			
			//Check if gender name already exist
			boolean isGenderNameExist = gd.isGenderNameExistByAnother(g);
			
			if(isGenderNameExist == false) {
				int count = gd.updateGender(g);
				if(count >= 1) {
					response.sendRedirect("allGenders.jsp");
				}
				else {
					session.setAttribute("error", "Enable to update gender...");
					rd.forward(request, response);
				}
			}
			else {
				session.setAttribute("error", "Gender name already exist...");
				rd.forward(request, response);
			}
			
			
		}
}
