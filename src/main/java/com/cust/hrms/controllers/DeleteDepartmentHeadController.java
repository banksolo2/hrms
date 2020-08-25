package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import com.cust.hrms.models.*;
import com.cust.hrms.dao.*;

@WebServlet("/deleteDepartmentHead")
public class DeleteDepartmentHeadController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
		int departmentHeadId = Integer.parseInt(request.getParameter("departmentHeadId"));
		DepartmentHeadDao dhd = new DepartmentHeadDao();
		DepartmentHead dh = dhd.getDepartmentHeadById(departmentHeadId);
		dhd.deleteDepartmentHead(dh);
		response.sendRedirect("allDepartmentHeads.jsp");
	}
}
