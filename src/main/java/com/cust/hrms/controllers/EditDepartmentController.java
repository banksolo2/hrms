package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/editDepartment")
public class EditDepartmentController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("editDepartment.jsp");
		int updatedBy = (int)session.getAttribute("employeeId");
		int departmentId = Integer.parseInt(request.getParameter("departmentId"));
		String name = request.getParameter("name");
		DepartmentDao dd = new DepartmentDao();
		Department d = new Department();
		d.setDepartmentId(departmentId);
		d.setName(name);
		d.setUpdatedBy(updatedBy);
		
		//Check if name exist
		boolean isNameExist = dd.isNameExistByAnotherDepartment(d);
		if(isNameExist == false) {
			//Updated department
			int count = dd.updateDepartment(d);
			if(count >= 1) {
				response.sendRedirect("allDepartments.jsp");
			}
			else {
				session.setAttribute("error", "Enable to update department...");
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", "Department name already exist..");
			rd.forward(request, response);
		}
	}
}
