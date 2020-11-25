package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/createLevelPayElement")
public class CreateLevelPayElementController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("createLevelPayElement.jsp");
		PrintWriter out = response.getWriter();
		int createdBy = 0;
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			createdBy = (int)session.getAttribute("employeeId");
		}
		LevelPayElementNotification lpen = new LevelPayElementNotification();
		LevelPayElementDao lped = new LevelPayElementDao();
		LevelPayElement lpe = new LevelPayElement();
		lpe.setCreatedBy(createdBy);
		int levelId = Integer.parseInt(request.getParameter("levelId"));
		lpe.setLevelId(levelId);
		String payElementsId[] = request.getParameterValues("payElementId");
		int inserted = 0;
		int notInserted = 0;
		for(String id : payElementsId) {
			lpe.setPayElementId(Integer.parseInt(id));
			boolean isLevelPayElementExist = lped.isLevelPayElementExist(lpe);
			if(isLevelPayElementExist == false) {
				inserted += lped.createLevelPayElement(lpe); 
			}
			else {
				notInserted += 1;
			}
		}
		if(inserted >= 1) {
			session.setAttribute("success", lpen.getCreateLevelPayElementMessage(true));
			response.sendRedirect("createLevelPayElement.jsp");
		}
		else if(notInserted >= 1) {
			session.setAttribute("error", lpen.getLevelPayElementAlreadyExistErrorMessage());
			rd.forward(request, response);
		}
		else {
			session.setAttribute("error", lpen.getCreateLevelPayElementMessage(false));
			rd.forward(request, response);
		}
		
	}
}
