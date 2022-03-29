package com.bookstore.service;

import java.io.IOException;
import java.util.List;

import com.bookstore.dao.UserDAO;
import com.bookstoredb.entity.Users;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserServices extends CommonUtility{
	private UserDAO userDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public UserServices(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		userDAO = new UserDAO();
	}

	public void listUser() throws ServletException, IOException {
		listUser(null);
	}

	public void listUser(String message) throws ServletException, IOException {
		List<Users> listUsers = userDAO.listAll();

		request.setAttribute("listUsers", listUsers);

		if (message != null) {
			request.setAttribute("message", message);
		}
        forwardToPage("user_list.jsp", request, response);
	}

	public void createUser() throws ServletException, IOException {
		String email = request.getParameter("email");
		String fullName = request.getParameter("fullname");
		String password = request.getParameter("password");

		Users existUser = userDAO.findByEmail(email);
		if (existUser != null) {
			String message = "Could not create user. A user with email " + email + " already exists";
			showMessageBackend(message, request, response);
		} else {
			Users newUser = new Users(email, fullName, password);
			userDAO.create(newUser);
			listUser("New user created successfully!");
		}
	}

	public void editUser() throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("id"));
		Users user = userDAO.get(userId);
		
		if (user == null) {
			String message = "Could not find user with ID " + userId;
			showMessageBackend(message, request, response);
		}else {		
	     	request.setAttribute("user", user);
	     	forwardToPage("user_form.jsp", request, response);
		}
	}

	public void updateUser() throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("userId"));
		String email = request.getParameter("email");
		String fullName = request.getParameter("fullname");
		String password = request.getParameter("password");
		
		Users userById = userDAO.get(userId);
		Users userByEmail = userDAO.findByEmail(email);
		
		if (userByEmail != null && userByEmail.getUserId() != userById.getUserId()) {
			String message = "Could not update user. User with email " + email + " already exists";
			showMessageBackend(message, request, response);	
		}else {
			Users user = new Users(userId, email, fullName, password);
			userDAO.update(user);
			
			String message = "User has been updated successfully";
			listUser(message);		
		}
	}
	
	public void deleteUser() throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("id"));
		
		Users user = userDAO.get(userId);
		String message = "User has been deleted successfully";
		
		if (userId == 1) {
			message = "The default admin user account cannot be deleted.";
			showMessageBackend(message, request, response);
			return;
		}
		
		if (user == null) {
			message = "Could not find user with ID " + userId
					+ ", or it might have been deleted by another admin";			
			showMessageBackend(message, request, response);		
		} else {
			userDAO.delete(userId);
			listUser(message);
		}		
	}

	public void login() throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		boolean loginResult = userDAO.checkLogin(email, password);
		if (loginResult) {	
			request.getSession().setAttribute("useremail", email);
			forwardToPage("/admin/", request, response);
		}else {
			String message = "Wrong email or password!";
			forwardToPage("login.jsp", message, request, response);						
		}
	}
}