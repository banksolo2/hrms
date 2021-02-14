package com.cust.hrms.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.cust.hrms.dao.*;
import com.cust.hrms.models.*;
import com.cust.hrms.notification.*;

@WebServlet("/deleteBank")
public class DeleteBankController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		if(session.getAttribute("email") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
			BankDao bd = new BankDao();
			BankNotification bn = new BankNotification();
			int bankId = Integer.parseInt(request.getParameter("bankId"));
			Bank b = bd.getBankById(bankId);
			int count = bd.deleteBank(b);
			if(count >= 1) {
				session.setAttribute("success", bn.getDeleteBankMessage(true));
			}
			else {
				session.setAttribute("error", bn.getDeleteBankMessage(false));
			}
			response.sendRedirect("allBanks.jsp");
		}
	}
}
