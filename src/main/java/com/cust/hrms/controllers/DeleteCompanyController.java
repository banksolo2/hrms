package com.cust.hrms.controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/deleteCompany")
public class DeleteCompanyController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int companyId = Integer.parseInt(request.getParameter("companyId"));
		
		//Get company details
		CompanyDao cd = new CompanyDao();
		Company c = cd.getCompanyById(companyId);
		
		//Delete company
		cd.deleteCompany(c);
		response.sendRedirect("allCompanies.jsp");
	}
}
