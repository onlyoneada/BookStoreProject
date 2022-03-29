package com.bookstore.controller.frontend;

import java.io.IOException;
import java.util.List;

import com.bookstore.dao.BookDAO;
import com.bookstore.dao.CategoryDAO;
import com.bookstore.service.BookServices;
import com.bookstoredb.entity.Book;
import com.bookstoredb.entity.Category;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public HomeServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		BookDAO bookDAO = new BookDAO();
		List<Book> listNewBooks = bookDAO.listNewBooks();
		request.setAttribute("listNewBooks", listNewBooks);
		
		String homepage = "frontend/index.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		dispatcher.forward(request, response);
		
		
	}


}
