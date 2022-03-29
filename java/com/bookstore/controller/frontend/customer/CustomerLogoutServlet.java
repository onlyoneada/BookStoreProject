package com.bookstore.controller.frontend.customer;

import jakarta.servlet.http.HttpServlet;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/customer_logout")
public class CustomerLogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public CustomerLogoutServlet() {      
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().removeAttribute("loginCustomer");	
		response.sendRedirect(request.getContextPath());
	}

}
