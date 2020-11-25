package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/editLevelPayElement")
public class EditLevelPayElementController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = request.getRequestDispatcher("editLevelPayElement.jsp");
		int updatedBy = 0;
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			updatedBy = (int)session.getAttribute("employeeId");
		}
		LevelPayElementNotification lpen = new LevelPayElementNotification();
		LevelPayElementDao lped = new LevelPayElementDao();
		int levelPayElementId = Integer.parseInt(request.getParameter("levelPayElementId"));
		LevelPayElement lpe = lped.getLevelPayElementById(levelPayElementId);
		lpe.setUpdatedBy(updatedBy);
		int levelId = Integer.parseInt(request.getParameter("levelId"));
		lpe.setLevelId(levelId);
		int payElementId = Integer.parseInt(request.getParameter("payElementId"));
		lpe.setPayElementId(payElementId);
		
		//Check if level pay element already exist
		boolean isLevelPayElementExist = lped.isLevelPayElementExistOnUpdate(lpe);
		if(isLevelPayElementExist == false) {
			//update level pay element database table
			int count = lped.updateLevelPayElement(lpe);
			if(count >= 1) {
				session.setAttribute("success", lpen.getUpdateLevelPayElementMessage(true));
				response.sendRedirect("allLevelPayElementReport.jsp");
			}
			else {
				session.setAttribute("error", lpen.getUpdateLevelPayElementMessage(false));
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", lpen.getLevelPayElementAlreadyExistErrorMessage());
			rd.forward(request, response);
		}
	}
}
