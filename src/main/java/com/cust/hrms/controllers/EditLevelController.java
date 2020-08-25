package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/editLevel")
public class EditLevelController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("editLevel.jsp");
		int updatedBy = (int)session.getAttribute("employeeId");
		int levelId = Integer.parseInt(request.getParameter("levelId"));
		String name = request.getParameter("name");
		int leaveDays = Integer.parseInt(request.getParameter("leaveDays"));
		
		LevelDao ld = new LevelDao();
		Level l = new Level();
		l.setLevelId(levelId);
		l.setName(name);
		l.setLeaveDays(leaveDays);
		l.setUpdatedBy(updatedBy);
		
		//Check if name already exist
		boolean isLevelNameExist = ld.isLevelNameExistByAnother(l);
		if(isLevelNameExist == false) {
			//Update database table levels
			int count = ld.updateLevel(l);
			if(count >= 1) {
				response.sendRedirect("allLevels.jsp");
			}
			else {
				session.setAttribute("error", "Enable to update level...");
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", "Level name already exist...");
			rd.forward(request, response);
		}
	}
}
