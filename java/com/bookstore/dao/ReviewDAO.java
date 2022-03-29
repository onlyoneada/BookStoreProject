package com.bookstore.dao;

import java.util.Date;
import java.util.List;

import com.bookstoredb.entity.Book;
import com.bookstoredb.entity.Review;

public class ReviewDAO extends JpaDAO<Review> implements GenericDAO<Review> {
	
	public ReviewDAO(){		
	}

	@Override
	public Review create(Review review) {
		review.setReviewTime(new Date());
		return super.create(review);
	}

	@Override
	public Review update(Review review) {
		return super.update(review);
	}

	@Override
	public Review get(Object reviewId) {
		return super.find(Review.class, reviewId);
	}

	@Override
	public void delete(Object reviewId) {
		super.delete(Review.class, reviewId);
	}

	@Override
	public List<Review> listAll() {
		return super.findWithNamedQuery("Review.findAll");
	}

	@Override
	public long count() {
		return super.countWithNamedQuery("Review.countAll");
	}

	public List<Review> listByBook(int bookId) {
		return super.findWithNamedQuery("Review.findByBook", "bokId", bookId);
	}

	public List<Review> listByCustomer(int customerId) {
		return super.findWithNamedQuery("Review.findByCustomer", "custId", customerId);
	}
	
	public long countByCustomer(int customerId) {
		return super.countWithNamedQuery("Review.countByCustomer", "customerId", customerId);
	}
}
