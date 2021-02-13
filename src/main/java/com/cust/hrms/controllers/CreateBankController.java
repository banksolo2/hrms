package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/createBank")
public class CreateBankController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = request.getRequestDispatcher("createBank.jsp");
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			BankNotification bn = new BankNotification();
			BankDao bd = new BankDao();
			Bank b = new Bank();
			String name = request.getParameter("name");
			b.setName(name);
			//Check if bank name already exist
			boolean isBankNameExist = bd.isBankExist(name);
			if(isBankNameExist == false) {
				//Save bank information in the database
				int count = bd.createBank(b);
				if(count >= 1) {
					session.setAttribute("success", bn.getCreateBankMessage(true));
					response.sendRedirect("createBank.jsp");
				}
				else {
					session.setAttribute("error", bn.getCreateBankMessage(false));
					rd.forward(request, response);
				}
			}
			else {
				session.setAttribute("error", bn.getBankNameAlreadyExist());
				rd.forward(request, response);
			}
		}
	}

}
