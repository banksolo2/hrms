package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/deleteLevelPayElement")
public class DeleteLevelPayElementController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		LevelPayElementNotification lpen = new LevelPayElementNotification();
		LevelPayElementDao lped = new LevelPayElementDao();
		int levelPayElementId = Integer.parseInt(request.getParameter("levelPayElementId"));
		LevelPayElement lpe = lped.getLevelPayElementById(levelPayElementId);
		// Delete Level pay element from database table
		int count = lped.deleteLevelPayElement(lpe);
		if(count >= 1) {
			session.setAttribute("success", lpen.getDeleteLevelPayElementMessage(true));
		}
		else {
			session.setAttribute("error", lpen.getDeleteLevelPayElementMessage(false));
		}
		response.sendRedirect("allLevelPayElementReport.jsp");
	}
}
