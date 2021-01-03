package com.cust.hrms.controllers;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/generatePayroll")
public class GeneratePayrollController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = request.getRequestDispatcher("generatePayroll.jsp");
		int createdBy = 0;
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			createdBy = (int)session.getAttribute("employeeId");
		}
		PayrollNotification pn = new PayrollNotification();
		EmployeeDao ed = new EmployeeDao();
		PayrollDao pd = new PayrollDao();
		ResultSet rs;
		int insertedCount = 0;
		int alreadyExistCount = 0;
		int notInsertedCount = 0;
		int count = 0;
		EmployeePayElementDao eped = new EmployeePayElementDao();
		String monthNo = request.getParameter("monthNo");
		String year = request.getParameter("year").trim();
		int yearLength = year.length();
		if(yearLength == 4) {
			try{
				EmployeeStatusDao esd = new EmployeeStatusDao();
				int employeeStatusId = esd.getEmployeeStatusIdByCode("active");
				int employeesId[] = ed.getStatusTypeEmployeesId(employeeStatusId);
				for(int x : employeesId) {
					rs = eped.getEmployeePayElementByEmployee(x, year+"-"+monthNo+"-10");
					while(rs.next()) {
						Payroll p = new Payroll();
						p.setEmployeePayElementId(rs.getInt("pay_element_id"));
						p.setEmployeeId(rs.getInt("employee_id"));
						p.setLevelId(rs.getInt("level_id"));
						p.setPayElementid(rs.getInt("pay_element_id"));
						p.setAmount(rs.getDouble("amount"));
						p.setMonthNo(monthNo);
						p.setYear(year);
						p.setCreatedBy(createdBy);
						//check if payroll already exist
						boolean isPayrollExist = pd.isPayrollExist(p);
						if(isPayrollExist == false) {
							//save data in the database table
							count = pd.createPayroll(p);
							if(count >= 1) {
								insertedCount++;
							}
							else {
								notInsertedCount++;
							}
						}
						else {
							alreadyExistCount++;
						}
					}
				}
			}
			catch(SQLException ex) {
				System.out.println(ex.fillInStackTrace());
			}
			if(insertedCount >= 1) {
				session.setAttribute("success", pn.getCreatePayrollMessage(true));
				response.sendRedirect("generatePayroll.jsp");
			}
			else if(alreadyExistCount >= 1) {
				session.setAttribute("error", pn.getPayrollAlreadyExistErrorMessage());
				rd.forward(request, response);
			}
			else if(notInsertedCount >= 1) {
				session.setAttribute("error", pn.getCreatePayrollMessage(false));
				rd.forward(request, response);
			}
		}
		else {
			session.setAttribute("error", pn.getInvalidYearErrorMessage());
			rd.forward(request, response);
		}
		
	}
}
