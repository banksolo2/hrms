package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/editMartialStatus")
public class EditMartialStatusController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int martialStatusId = Integer.parseInt(request.getParameter("martialStatusId"));
		String name = request.getParameter("name").trim();
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("editMartialStatus.jsp");
		int updatedBy = (int)session.getAttribute("employeeId");
		
		MartialStatusDao msd = new MartialStatusDao();
		MartialStatus ms = new MartialStatus();
		ms.setMartialStatusId(martialStatusId);
		ms.setName(name);
		ms.setUpdatedBy(updatedBy);
		
		//Check if name martial status name already exist
		boolean isMartialStatusExist = msd.isMartialStatusExistByAnother(ms);
		
		if(isMartialStatusExist == false) {
			int count = msd.updateMartialStatus(ms);
			if(count >= 1) {
				response.sendRedirect("allMartialStatus.jsp");
			}
			else {
				session.setAttribute("error", "Enable to update martial status...");
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", "Martial status already exist...");
			rd.forward(request, response);
		}
	}
}
