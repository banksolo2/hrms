package com.cust.hrms.controllers;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/createCompany")
public class CreateCompanyController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String name = request.getParameter("name").trim();
		String codeName = request.getParameter("codeName").toUpperCase().trim();
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("createCompany.jsp");
		int userId = (int)session.getAttribute("employeeId");
		
		if(name.length() > 2 && codeName.length() > 1) {
			CompanyDao cd = new CompanyDao();
			//check if company name already exist
			boolean isCompanyNameExist = cd.isCompanyNameExist(name);
			if(isCompanyNameExist == false) {
				//create new company details
				Company c = new Company();
				c.setName(name);
				c.setCodeName(codeName);
				c.setCreatedBy(userId);
				
				int count = cd.createCompany(c);
				if(count >= 1) {
					session.setAttribute("success", "Company created...");
					response.sendRedirect("createCompany.jsp");
				}
				else {
					session.setAttribute("error", "Enable to create company...");
					rd.forward(request, response);
				}
			}
			else {
				session.setAttribute("error", "Company name already exist...");
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", "All field required...");
			rd.forward(request, response);
		}
	}
	
}
