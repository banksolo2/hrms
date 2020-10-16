package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/deleteLevel")
public class DeleteLevelController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
		int levelId = Integer.parseInt(request.getParameter("levelId"));
		LevelDao ld = new LevelDao();
		Level l = ld.getLevelById(levelId);
		ld.deleteLevel(l);
		response.sendRedirect("allLevels.jsp");
	}

}
