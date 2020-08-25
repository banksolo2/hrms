package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/editDepartmentHead")
public class EditDepartmentHeadController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("editDepartmentHead.jsp");
		int updatedBy = (int)session.getAttribute("employeeId");
		int departmentHeadId = Integer.parseInt(request.getParameter("departmentHeadId"));
		int employeeId = Integer.parseInt(request.getParameter("employeeId"));
		int departmentId = Integer.parseInt(request.getParameter("departmentId"));
		
		DepartmentHeadDao dhd = new DepartmentHeadDao();
		DepartmentHead dh = new DepartmentHead();
		dh.setDepartmentHeadId(departmentHeadId);
		dh.setEmployeeId(employeeId);
		dh.setDepartmentId(departmentId);
		dh.setUpdatedBy(updatedBy);
		
		//Check if department already has a head department
		boolean isDepartmentHeadExist = dhd.isDepartmentHeadExistByDepartmentOnUpdate(dh);
		if(isDepartmentHeadExist == false) {
			//Check if employee belong to the department
			boolean isEmployeeExistInDepartment = dhd.isEmployeeExistInDepartment(dh);
			if(isEmployeeExistInDepartment == true) {
				int count = dhd.updateDepartmentHead(dh);
				if(count >= 1) {
					response.sendRedirect("allDepartmentHeads.jsp");
				}
				else {
					session.setAttribute("error", "Enable to update department head.....");
					rd.forward(request, response);
				}
			}
			else {
				session.setAttribute("error", "Employee does not belong to this department....");
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", "This department already has a department head...");
			rd.forward(request, response);
		}
	}
}
