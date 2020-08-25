package com.cust.hrms.controllers;

import javax.servlet.annotation.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import java.io.*;

@WebServlet("/editState")
public class EditStateController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int stateId = Integer.parseInt(request.getParameter("stateId"));
		String name = request.getParameter("name").trim(); 
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("editState.jsp");
		int updatedBy = (int)session.getAttribute("employeeId");
		
		
		//Check if name exist
		StateDao sd = new StateDao();
		State s = new State();
		s.setStateId(stateId);
		s.setName(name);
		s.setUpdatedBy(updatedBy);
		boolean isNameExist = sd.isStateNameExistByAnother(s);
		
		if(isNameExist == false) {
			int count = sd.updateState(s);
			if(count >= 1)
			{
				response.sendRedirect("allStates.jsp");
			}
			else {
				session.setAttribute("error", "Enable to update state");
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", "State name already exist");
			rd.forward(request, response);
		}
	}
}
