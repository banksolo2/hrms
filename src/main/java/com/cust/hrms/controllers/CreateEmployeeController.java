package com.cust.hrms.controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/createEmployee")
public class CreateEmployeeController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		final String DEFAULT_PASSWORD = "Lagos@123";
		int count = 0;
		String message = null;
		PrintWriter out = response.getWriter();
		String title = request.getParameter("title").trim();
		String firstName = request.getParameter("firstName").trim();
		String middleName = request.getParameter("middleName").trim();
		String lastName = request.getParameter("lastName").trim();
		String nameInitials = request.getParameter("nameInitials").toUpperCase().trim();
		String email = request.getParameter("email").trim();
		int employeeStatusId = Integer.parseInt(request.getParameter("employeeStatusId"));
		String dateOfEmployment = request.getParameter("dateOfEmployment").trim();
		String mobileNumber = request.getParameter("mobileNumber").trim();
		int stateId = Integer.parseInt(request.getParameter("stateId"));
		int departmentId = Integer.parseInt(request.getParameter("departmentId"));
		int levelId = Integer.parseInt(request.getParameter("levelId"));
		String staffId = request.getParameter("staffId").trim();
		int branchId = Integer.parseInt(request.getParameter("branchId"));
		int companyId = Integer.parseInt(request.getParameter("companyId"));
		int genderId = Integer.parseInt(request.getParameter("genderId"));
		int martialStatusId = Integer.parseInt(request.getParameter("martialStatusId"));
		String personalEmail = request.getParameter("personalEmail").trim();
		String currentAddress = request.getParameter("currentAddress").trim();
		String dateOfBirth = request.getParameter("dateOfBirth").trim();
		int leaveSupervisorId = Integer.parseInt(request.getParameter("leaveSupervisorId"));
		RequestDispatcher rd = request.getRequestDispatcher("createEmployee.jsp");
		
		//create a http session object
		HttpSession session = request.getSession();
		EmployeeDao ed = new EmployeeDao();
		boolean isPersonalProductionTargetValid = ed.isPersonalProductionTargetValid(request.getParameter("personalProductionTarget"));
		double personalProductionTarget;
		if(isPersonalProductionTargetValid == true) {
			personalProductionTarget = Double.parseDouble(request.getParameter("personalProductionTarget"));
		
			int createdBy = (int)session.getAttribute("employeeId");
			
			
			boolean isEmailExist = ed.isEmailExist(email);
			
			if(isEmailExist == false) {
				boolean isStaffIdExist = ed.isStaffIdExist(staffId);
				if(isStaffIdExist == false) {
					Employee e = new Employee();
					e.setTitle(title);
					e.setFirstName(firstName);
					e.setMiddleName(middleName);
					e.setLastName(lastName);
					e.setNameInitials(nameInitials);
					e.setEmail(email);
					e.setEmployeeStatusId(employeeStatusId);
					e.setDateOfEmployment(dateOfEmployment);
					e.setMobileNumber(mobileNumber);
					e.setStateId(stateId);
					e.setDepartmentId(departmentId);
					e.setLevelId(levelId);
					e.setStaffId(staffId);
					e.setBranchId(branchId);
					e.setCompanyId(companyId);
					e.setGenderId(genderId);
					e.setMartialStatusId(martialStatusId);
					e.setPersonalEmail(personalEmail);
					e.setCurrentAddress(currentAddress);
					e.setDateOfBirth(dateOfBirth);
					e.setPassword(DEFAULT_PASSWORD);
					e.setLeaveSupervisorId(leaveSupervisorId);
					e.setCreatedBy(createdBy);
					e.setPersonalProductionTarget(personalProductionTarget);
					count = ed.createEmployee(e);
					if(count >= 1) {
						session.setAttribute("success", "Employee has been created..");
						response.sendRedirect("createEmployee.jsp");
					}
					else {
						session.setAttribute("error", "Enable to create employee...");
						rd.forward(request, response);
					}
				}
				else {
					session.setAttribute("error", "Staff ID already exist....");
					rd.forward(request, response);
				}
				
			}
			else {
				session.setAttribute("error", "Email address already exist.");
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", "Invalid personal production target...");
			rd.forward(request, response);
		}
	}

}
