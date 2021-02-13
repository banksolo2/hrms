package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/updateBank")
public class UpdateBank extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = request.getRequestDispatcher("updateBank.jsp");
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			BankDao bd = new BankDao();
			BankNotification bn = new BankNotification();
			int bankId = Integer.parseInt(request.getParameter("bankId"));
			Bank b = bd.getBankById(bankId);
			String name = request.getParameter("name");
			b.setName(name);
			// Check Bank name already exist
			boolean isBankNameExist = bd.isBankExistOnUpdate(b);
			if(isBankNameExist == false) {
				//Update bank information
				int count = bd.updateBank(b);
				if(count >= 1) {
					session.setAttribute("success", bn.getUpdateBankMessage(true));
					response.sendRedirect("allBanks.jsp");
				}
				else {
					session.setAttribute("error", bn.getUpdateBankMessage(false));
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
