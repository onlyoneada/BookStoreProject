package com.bookstore.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.bookstore.dao.BookDAO;
import com.bookstore.dao.CategoryDAO;
import com.bookstore.dao.ReviewDAO;
import com.bookstoredb.entity.Book;
import com.bookstoredb.entity.Category;
import com.bookstoredb.entity.Review;
import com.bookstoredb.entity.Users;

import jakarta.persistence.EntityManager;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

public class BookServices extends CommonUtility {
	private BookDAO bookDAO;
	private CategoryDAO categoryDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public BookServices(HttpServletRequest request, HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;
		bookDAO = new BookDAO();
		categoryDAO = new CategoryDAO();
	}

	public void listBooks() throws ServletException, IOException {
		listBooks(null);
	}

	public void listBooks(String message) throws ServletException, IOException {
		List<Book> listBooks = bookDAO.listAll();
		request.setAttribute("listBooks", listBooks);

		if (message != null) {
			request.setAttribute("message", message);
		}
		forwardToPage("book_list.jsp", request, response);
	}

	public void showBookNewForm() throws ServletException, IOException {
		List<Category> listCategory = categoryDAO.listAll();
		request.setAttribute("listCategory", listCategory);	
		forwardToPage("book_form.jsp", request, response);
	}

	public void createBook() throws ServletException, IOException {
		String title = request.getParameter("title");

		Book existBook = bookDAO.findByTitle(title);
		if (existBook != null) {
			String errorMessage = "A book with title " + title + " has been created already";		
			showMessageBackend(errorMessage, request, response);
		} else {
			Book newBook = new Book();
			readBookFields(newBook);
			bookDAO.create(newBook);
			listBooks("A new book has been created successfully.");
		}
	}

	public void deleteBook() throws ServletException, IOException {
		int bookId = Integer.parseInt(request.getParameter("id"));
		Book book = bookDAO.get(bookId);
		ReviewDAO reviewDAO = new ReviewDAO();
		List<Review> reviews = reviewDAO.listByBook(bookId);

		if (book == null) {
			String message = "Could not find book with ID " + bookId + ", or it might have been deleted by another admin";
            showMessageBackend(message, request, response);
		} else {
			if (reviews.isEmpty()) {
				bookDAO.delete(bookId);
				listBooks("The book with ID" + bookId + " has been deleted successfully");
			}else {
				showMessageBackend("Could not delete the book " + bookId + " because it contains some reviews", request, response);	
			}
			
		}
	}

	public void editBook() throws ServletException, IOException {
		int bookId = Integer.parseInt(request.getParameter("id"));
		Book book = bookDAO.get(bookId);
		List<Category> listCategory = categoryDAO.listAll();

		if (book == null) {
			String message = "Could not find book with ID " + bookId;
			showMessageBackend(message, request, response);
		} else {
			request.setAttribute("book", book);
			request.setAttribute("listCategory", listCategory);
			forwardToPage("book_form.jsp", request, response);
		}				
	}

	public void readBookFields(Book book) throws ServletException, IOException {
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String isbn = request.getParameter("isbn");
		float price = Float.parseFloat(request.getParameter("price"));
		String description = request.getParameter("description");

		Date publishDate = null;
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

		try {
			publishDate = dateFormat.parse(request.getParameter("publishDate"));
		} catch (ParseException ex) {
			ex.printStackTrace();
			throw new ServletException("Error parsing publish date (format is MM/dd/yyyy)");
		}

		Integer categoryId = Integer.parseInt(request.getParameter("category"));
		Category category = categoryDAO.get(categoryId);
		book.setCategory(category);

		book.setTitle(title);
		book.setAuthor(author);
		book.setIsbn(isbn);
		book.setPrice(price);
		book.setDescription(description);
		book.setPublishDate(publishDate);

		Part part = request.getPart("bookImage");
		if (part != null && part.getSize() > 0) {
			long size = part.getSize();
			byte[] imageBytes = new byte[(int) size];

			InputStream inputStream = part.getInputStream();
			inputStream.read(imageBytes);
			inputStream.close();

			book.setImage(imageBytes);
		}
	}

	public void updateBook() throws ServletException, IOException {
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		Book bookById = bookDAO.get(bookId);
		readBookFields(bookById);

		String bookTitle = request.getParameter("title");
		Book bookByTitle = bookDAO.findByTitle(bookTitle);

		if (bookByTitle != null && bookByTitle.getBookId() != bookId) {
			String message = "Could not update Book. Book with name " + bookTitle + " already exists";
			showMessageBackend(message, request, response);
		} else {
			bookById.setTitle(bookTitle);
			bookDAO.update(bookById);
			listBooks("Book has been updated successfully");
		}
	}

	public void listBooksByCategory() throws ServletException, IOException {
		int categoryId = Integer.parseInt(request.getParameter("id"));
		Category category = categoryDAO.get(categoryId);     
		
		if (category == null) {
			String errorMessage = "Sorry, the category ID " + categoryId + " is not available.";
			showMessageFrontend(errorMessage, request, response);
		} else {
			List<Book> listBooks = bookDAO.listByCategory(categoryId);
			request.setAttribute("listBooks", listBooks);
			request.setAttribute("category", category);
			forwardToPage("frontend/books_list_by_category.jsp", request, response);
		}
	}

	public void viewBookDetail() throws ServletException, IOException {
		int bookId = Integer.parseInt(request.getParameter("id"));
		Book book = bookDAO.get(bookId);	
		
		if (book==null) {
			String errorMessage = "Sorry, the book with ID " + bookId + " is not available.";
			showMessageFrontend(errorMessage, request, response);
		}else {
			request.setAttribute("book", book);		
			forwardToPage("frontend/book_detail.jsp", request, response);
		}
	}

	public void search() throws ServletException, IOException {
		String keyword = request.getParameter("keyword");
		
		List<Book> result = null;
		String listPage = "frontend/search_result.jsp";
		if (keyword == null) {
			result = bookDAO.listAll();
			listPage = "book_list.jsp";	
		}else {
			result = bookDAO.search(keyword);	
		}
		request.setAttribute("keyword", keyword);
		request.setAttribute("result", result);	
		forwardToPage(listPage, request, response);
	}
}