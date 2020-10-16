package com.cust.hrms.controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/editCompany")
public class EditCompanyController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int companyId = Integer.parseInt(request.getParameter("companyId"));
		String name = request.getParameter("name").trim();
		String codeName = request.getParameter("codeName").toUpperCase().trim();
		RequestDispatcher rd = request.getRequestDispatcher("editCompany.jsp");
		HttpSession session = request.getSession();
		int userId = (int)session.getAttribute("employeeId");
		
		CompanyDao cd = new CompanyDao();
		Company c = new Company();
		c.setCompanyId(companyId);
		c.setName(name);
		c.setCodeName(codeName);
		c.setUpdatedBy(userId);
		
		//check if company name already exist
		boolean isCompanyNameExist = cd.isCompanyNameExistByanother(c);
		
		if(isCompanyNameExist == false) {
			int count = cd.updateCompany(c);
			if(count >= 1) {
				response.sendRedirect("allCompanies.jsp");
			}
			else {
				session.setAttribute("error", "Enable to update company...");
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", "Company Name already exist..");
			rd.forward(request, response);
		}
	}
}
