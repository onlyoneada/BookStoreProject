package com.bookstore.dao;

import java.util.List;

import com.bookstoredb.entity.Category;
import com.bookstoredb.entity.Users;

import jakarta.persistence.EntityManager;

public class CategoryDAO extends JpaDAO<Category> implements GenericDAO<Category> {

	public CategoryDAO() {
	}

	@Override
	public Category create(Category category) {
		return super.create(category);
	}

	@Override
	public Category update(Category category) {
		return super.update(category);
	}

	@Override
	public Category get(Object CategoryId) {
		return super.find(Category.class, CategoryId);
	}

	@Override
	public void delete(Object CategoryId) {
		super.delete(Category.class, CategoryId);
	}

	@Override
	public List<Category> listAll() {
		return super.findWithNamedQuery("Category.findAll");
	}

	@Override
	public long count() {
		return super.countWithNamedQuery("Category.countAll");
	}
	
	public Category findByName(String categoryName) {
		List<Category> listCategories = super.findWithNamedQuery("Category.findByName", "name", categoryName);
		if (listCategories !=null && listCategories.size()> 0) {
			return listCategories.get(0);
		}
		return null;
	}
	

}
