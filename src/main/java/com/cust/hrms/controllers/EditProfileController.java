package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/editProfile")
public class EditProfileController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		PrintWriter out = response.getWriter();
		int employeeId =Integer.parseInt(request.getParameter("employeeId"));
		String title = request.getParameter("title").trim();
		String firstName = request.getParameter("firstName").trim();
		String middleName = request.getParameter("middleName").trim();
		String lastName = request.getParameter("lastName").trim();
		String nameInitials = request.getParameter("nameInitials").trim();
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
		String accountNo = request.getParameter("accountNo").trim();
		int bankId = Integer.parseInt(request.getParameter("bankId"));
		RequestDispatcher rd = request.getRequestDispatcher("editProfile.jsp");
		HttpSession session = request.getSession();
		
		EmployeeDao ed = new EmployeeDao();
		Employee e = new Employee();
		e.setEmployeeId(employeeId);
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
		e.setAccountNo(accountNo);
		e.setBankId(bankId);
		
		int count = ed.updateEmployee(e);
		
		if(count >= 1) {
			session.setAttribute("success", "Profile has been updated...");
			response.sendRedirect("editProfile.jsp");
		}
		else {
			session.setAttribute("error", "Enable to update profile...");
			rd.forward(request, response);
		}
	}
}