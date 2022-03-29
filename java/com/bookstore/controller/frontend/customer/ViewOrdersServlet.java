package com.bookstore.controller.frontend.customer;

import jakarta.servlet.http.HttpServlet;
import java.io.IOException;

import com.bookstore.service.CustomerServices;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/view_orders")
public class ViewOrdersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public ViewOrdersServlet() {
        super();     
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CustomerServices customerServices = new CustomerServices(request, response);
		customerServices.viewOrders();
	}

}
