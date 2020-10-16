package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/createLevel")
public class CreateLevelController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("createLevel.jsp");
		int createdBy = (int)session.getAttribute("employeeId");
		String name = request.getParameter("name");
		int leaveDays = Integer.parseInt(request.getParameter("leaveDays"));
		
		LevelDao ld = new LevelDao();
		
		//Check if level name already exist
		boolean isLevelNameExist = ld.isLevelNameExist(name);
		if(isLevelNameExist == false) {
			//Save information to database
			Level l = new Level();
			l.setName(name);
			l.setLeaveDays(leaveDays);
			l.setCreatedBy(createdBy);
			
			int count = ld.createLevel(l);
			
			if(count >= 1) {
				session.setAttribute("success", "Level created....");
				response.sendRedirect("createLevel.jsp");
			}
			else {
				session.setAttribute("error", "Enable to create level...");
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", "Level name already exist....");
			rd.forward(request, response);
		}
	}
}
