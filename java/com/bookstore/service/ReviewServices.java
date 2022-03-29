package com.bookstore.service;

import java.io.IOException;
import java.util.List;

import com.bookstore.dao.BookDAO;
import com.bookstore.dao.CustomerDAO;
import com.bookstore.dao.ReviewDAO;
import com.bookstoredb.entity.Book;
import com.bookstoredb.entity.Category;
import com.bookstoredb.entity.Customer;
import com.bookstoredb.entity.Review;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ReviewServices extends CommonUtility {
	private ReviewDAO reviewDAO;
	private BookDAO bookDAO;
	private CustomerDAO customerDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public ReviewServices(HttpServletRequest request, HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;
		reviewDAO = new ReviewDAO();
		bookDAO = new BookDAO();
		customerDAO = new CustomerDAO();
	}
	
	public void listReviews() throws ServletException, IOException {
		listReviews(null);
	}
	
	public void listReviews(String message) throws ServletException, IOException {
		List<Review> listReviews = reviewDAO.listAll();
		request.setAttribute("listReviews", listReviews);
		
		if (message != null) {
			request.setAttribute("message", message);
		}
		forwardToPage("review_list.jsp", request, response);
	}

	public void editReview() throws ServletException, IOException {	
		int reviewId = Integer.parseInt(request.getParameter("id"));
		Review review = reviewDAO.get(reviewId);
		
		if (review == null) {
			String message = "Could not find review with ID " + reviewId;
			showMessageBackend(message, request, response);
		}else {
			request.setAttribute("review", review);
			forwardToPage("review_form.jsp", request, response);
		}		
	}

	public void updateReview() throws ServletException, IOException {	
		int reviewId = Integer.parseInt(request.getParameter("reviewId"));
		Review review = reviewDAO.get(reviewId);
		
		//Integer bookId = Integer.parseInt(request.getParameter("book"));
		//Book belongBook = bookDAO.get(bookId);
		//Integer customerId = Integer.parseInt(request.getParameter("customer"));
		//Customer belongCustomer = customerDAO.get(customerId);
		//int rating = Integer.parseInt(request.getParameter("rating"));
		String headline = request.getParameter("headline");
		String comment = request.getParameter("comment");
		
		/*
		if (belongBook.getTitle() != null && !belongBook.getTitle().equals("")) {
			review.setBook(belongBook);
		}
		if (belongCustomer.getFullName() != null && !belongCustomer.getFullName().equals("")) {
			review.setCustomer(belongCustomer);
		}
		if (request.getParameter("rating") != null && !request.getParameter("rating").equals("")) {
			review.setRating(rating);		
		}
		*/
		review.setHeadline(headline);
		review.setComment(comment);	
		reviewDAO.update(review);
		listReviews("Updated review successfully!");	
	}

	
	public void deleteReview() throws ServletException, IOException {
		int reviewId = Integer.parseInt(request.getParameter("id"));	
		Review review = reviewDAO.get(reviewId);
		
		if (review == null) {
			String message = "Could not find review with ID " + reviewId + ", or it might have been deleted by another admin";
            showMessageBackend(message, request, response);
		}else {
			reviewDAO.delete(reviewId);
			listReviews("Deleted!");
		}
	}

	public void writeReview() {
		
		
	}
}
