package com.cust.hrms.controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;

@WebServlet("/editEmployee")
public class EditEmployeeController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		EmployeeDao ed = new EmployeeDao();
		RequestDispatcher rd = request.getRequestDispatcher("editEmployee.jsp");
		int employeeId = Integer.parseInt(request.getParameter("employeeId"));
		String title = request.getParameter("title");
		String firstName = request.getParameter("firstName");
		String middleName = request.getParameter("middleName");
		String lastName = request.getParameter("lastName");
		String nameInitials = request.getParameter("nameInitials").toUpperCase();
		String email = request.getParameter("email");
		int employeeStatusId = Integer.parseInt(request.getParameter("employeeStatusId"));
		String dateOfEmployment = request.getParameter("dateOfEmployment");
		String mobileNumber = request.getParameter("mobileNumber");
		int stateId = Integer.parseInt(request.getParameter("stateId"));
		int departmentId = Integer.parseInt(request.getParameter("departmentId"));
		int levelId = Integer.parseInt(request.getParameter("levelId"));
		String staffId = request.getParameter("staffId");
		int branchId = Integer.parseInt(request.getParameter("branchId"));
		int companyId = Integer.parseInt(request.getParameter("companyId"));
		int genderId = Integer.parseInt(request.getParameter("genderId"));
		int martialStatusId = Integer.parseInt(request.getParameter("martialStatusId"));
		String personalEmail = request.getParameter("personalEmail");
		String leaveSupervisor = request.getParameter("leaveSupervisorId");
		if(leaveSupervisor == null) leaveSupervisor = "0";
		int leaveSupervisorId = Integer.parseInt(leaveSupervisor);
		String dateOfBirth = request.getParameter("dateOfBirth");
		String currentAddress = request.getParameter("currentAddress");
		String personalProduction = request.getParameter("personalProductionTarget");
		if(personalProduction == null) personalProduction = "0";
		int updatedBy = (int)session.getAttribute("employeeId");
		boolean isPersonalProductionTarget = ed.isPersonalProductionTargetValid(personalProduction);
		
		if(isPersonalProductionTarget == true) {
			double personalProductionTarget = Double.parseDouble(personalProduction);
			Employee e = new Employee();
			e.setTitle(title);
			e.setEmployeeId(employeeId);
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
			e.setLeaveSupervisorId(leaveSupervisorId);
			e.setDateOfBirth(dateOfBirth);
			e.setCurrentAddress(currentAddress);
			e.setPersonalProductionTarget(personalProductionTarget);
			e.setUpdatedBy(updatedBy);
			
			//check if email already exist
			boolean isEmailExist = ed.isEmailExistByAnotherEmployee(e);
			
			if(isEmailExist == false) {
				//check if staff ID already exist..
				boolean isStaffIdExist = ed.isStaffIdExistOnUpdate(employeeId, staffId);
				if(isStaffIdExist == false) {
					int count = ed.updateEmployee(e);
					if(count >= 1) {
						response.sendRedirect("allEmployees.jsp");
					}
					else {
						session.setAttribute("error", "Enable to update employee...");
						rd.forward(request, response);
					}
				}
				else {
					session.setAttribute("error", "Staff ID already exist...");
					rd.forward(request, response);
				}
			}
			else {
				session.setAttribute("error", "Email address already exist...");
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", "Please provide a valid personal production target value..");
			rd.forward(request, response);
		}
	}
}
