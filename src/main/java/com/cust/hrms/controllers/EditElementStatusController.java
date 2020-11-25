package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/editElementStatus")
public class EditElementStatusController extends HttpServlet {

		protected void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
			HttpSession session = request.getSession();
			PrintWriter out = response.getWriter();
			RequestDispatcher rd = request.getRequestDispatcher("editElementStatus.jsp");
			if(session.getAttribute("email") == null) {
				response.sendRedirect("index.jsp");
			}
			ElementStatusNotification esn = new ElementStatusNotification();
			ElementStatusDao esd = new ElementStatusDao();
			int elementStatusId = Integer.parseInt(request.getParameter("elementStatusId"));
			ElementStatus es = esd.getElementStatusById(elementStatusId);
			String name = request.getParameter("name");
			es.setName(name);
			boolean isNameExist = esd.isNameExistOnUpdate(es);
			if(isNameExist == false) {
				//Update element status in database
				int count = esd.updateElementStatus(es);
				if(count >= 1) {
					session.setAttribute("success", esn.getUpdatedElementStatusMessage(true));
					response.sendRedirect("allElementStatues.jsp");
				}
				else {
					session.setAttribute("error", esn.getUpdatedElementStatusMessage(false));
					rd.forward(request, response);
				}
			}
			else {
				session.setAttribute("error", esn.getElementStatusAlreadyExistErrorMessage());
				rd.forward(request, response);
			}
		}
}
