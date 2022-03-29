package com.bookstore.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.bookstore.dao.CustomerDAO;
import com.bookstore.dao.ReviewDAO;
import com.bookstoredb.entity.Book;
import com.bookstoredb.entity.Category;
import com.bookstoredb.entity.Customer;
import com.bookstoredb.entity.Review;
import com.bookstoredb.entity.Users;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomerServices extends CommonUtility {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private CustomerDAO customerDAO;

	public CustomerServices(HttpServletRequest request, HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;
		customerDAO = new CustomerDAO();
	}

	public void listCustomer() throws ServletException, IOException {
		listCustomer(null);
	}

	public void listCustomer(String message) throws ServletException, IOException {
		List<Customer> listCustomers = customerDAO.listAll();

		request.setAttribute("listCustomers", listCustomers);

		if (message != null) {
			request.setAttribute("message", message);
		}
		forwardToPage("customer_list.jsp", request, response);
	}

	private void updateCustomerFieldsFromForm(Customer customer) {	
		String email = request.getParameter("email");
		String fullName = request.getParameter("fullName");
		String address = request.getParameter("address");
		String city = request.getParameter("city");
		String country = request.getParameter("country");
		String phone = request.getParameter("phone");
		String zipCode = request.getParameter("zipCode");
		String password = request.getParameter("password");
		
		if (email != null && !email.equals("")) {
			customer.setEmail(email);
		}
		customer.setFullName(fullName);
		customer.setAddress(address);
		customer.setCity(city);
		customer.setCountry(country);
		customer.setPhone(phone);
		customer.setZipCode(zipCode);
		
		if (password != null && !password.equals("")) {
			customer.setPassword(password);
		}
	}
	
	public void createCustomer() throws ServletException, IOException {
		String email = request.getParameter("email");
		Customer existCustomer = customerDAO.findByEmail(email);

		if (existCustomer != null) {
			showMessageBackend("Customer email " + email + " already exists!", request, response);
		} else {
			Customer customer1 = new Customer();
			updateCustomerFieldsFromForm(customer1);
			customerDAO.create(customer1);
			listCustomer("New customer created successfully!");
		}
	}
	
	public void registerCustomer() throws ServletException, IOException {
		String email = request.getParameter("email");
		Customer existCustomer = customerDAO.findByEmail(email);

		if (existCustomer != null) {
			showMessageFrontend("Customer email " + email + " already registers!", request, response);
		} else {
			Customer customer1 = new Customer();
			updateCustomerFieldsFromForm(customer1);
			customerDAO.create(customer1);
			showSuccessfulMessageFrontend("You are registered successfully! Thank you!" +
			"<a href='login'>Click here</a> to login", request, response);
		}
	}

	public void editCustomer() throws ServletException, IOException {
		int customerId = Integer.parseInt(request.getParameter("id"));
		Customer customer = customerDAO.get(customerId);

		if (customer == null) {
			String message = "Could not find customer with ID " + customerId;
			showMessageBackend(message, request, response);
		} else {
			request.setAttribute("customer", customer);
			forwardToPage("customer_form.jsp", request, response);
		}
	}

	public void updateCustomer() throws ServletException, IOException {
		int customerId = Integer.parseInt(request.getParameter("customerId"));
	
		String email = request.getParameter("email");
		Customer alreadyExistByEmail = customerDAO.findByEmail(email);
		
		if (alreadyExistByEmail != null && alreadyExistByEmail.getCustomerId() != customerId) {
			String message = "Could not update customer. Customer with email " + email + " already exists";
			showMessageBackend(message, request, response);
		}else {					
			Customer customerById = customerDAO.get(customerId);
			updateCustomerFieldsFromForm(customerById);
			customerDAO.update(customerById);
			String message = "Customer has been updated successfully";
			listCustomer(message);
		}
	}

	public void deleteCustomer() throws ServletException, IOException {
		int customerId = Integer.parseInt(request.getParameter("id"));
		Customer customer = customerDAO.get(customerId);
		//ReviewDAO reviewDAO = new ReviewDAO();
		//List<Review> reviews = reviewDAO.listByCustomer(customerId);
		
		if (customer == null) {
			String message = "Could not find customer with ID " + customerId + 
					", or it might have been deleted by another admin";
            showMessageBackend(message, request, response);
		}else {
			ReviewDAO reviewDAO = new ReviewDAO();
			long reviewCount = reviewDAO.countByCustomer(customerId);
			
			if (reviewCount == 0) {
				customerDAO.delete(customerId);
				listCustomer("Customer has been deleted successfully");
			}else {
				showMessageBackend("Could not delete the customer " + customerId + " because she/he has made some reviews", request, response);	

			}
		}		
	}

	public void loginCustomer() throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		Customer loginCustomer = customerDAO.checkLogin(email, password);
		if (loginCustomer != null) {
			request.getSession().setAttribute("loginCustomer",loginCustomer);
			forwardToPage("frontend/customer_profile.jsp", request, response);
		}else {
			String message = "Wrong email or password!";
			forwardToPage("frontend/login_form.jsp", message, request, response);						
		}
	}

	public void viewProfile() throws ServletException, IOException {
		forwardToPage("frontend/customer_profile.jsp", request, response);	
	}
	
	public void viewCart() throws ServletException, IOException {
		forwardToPage("frontend/cart.jsp", request, response);
	}
	
	public void viewOrders() throws ServletException, IOException {
		forwardToPage("frontend/orders.jsp", request, response);
	}

	public void editProfile() throws ServletException, IOException {
		Customer customer = (Customer) request.getSession().getAttribute("loginCustomer");		
		request.setAttribute("loginCustomer", customer);
		forwardToPage("frontend/profile_form.jsp", request, response);		
	}

	public void updateProfile() throws ServletException, IOException {
		Customer customer = (Customer) request.getSession().getAttribute("loginCustomer");
		updateCustomerFieldsFromForm(customer);
		customerDAO.update(customer);
		viewProfile();			
	}

}
