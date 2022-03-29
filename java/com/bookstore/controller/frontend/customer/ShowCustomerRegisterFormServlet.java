package com.bookstore.controller.frontend.customer;

import jakarta.servlet.http.HttpServlet;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class ShowCustomerRegisterFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public ShowCustomerRegisterFormServlet() {
        super();    
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("frontend/register_form.jsp").forward(request, response);
	}

}
