package com.bookstore.service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.bookstore.dao.BookDAO;
import com.bookstore.dao.CategoryDAO;
import com.bookstore.dao.UserDAO;
import com.bookstoredb.entity.Book;
import com.bookstoredb.entity.Category;
import com.bookstoredb.entity.Users;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CategoryServices extends CommonUtility {

	private CategoryDAO categoryDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public CategoryServices(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		categoryDAO = new CategoryDAO();
	}

	public void listCategory() throws ServletException, IOException {
		listCategory(null);
	}

	public void listCategory(String message) throws ServletException, IOException {
		List<Category> listCategory = categoryDAO.listAll();
		request.setAttribute("listCategory", listCategory);

		if (message != null) {
			request.setAttribute("message", message);
		}
		forwardToPage("category_list.jsp", request, response);
	}

	public void createCategory() throws ServletException, IOException {
		String name = request.getParameter("name");
		Category existCategory = categoryDAO.findByName(name);

		if (existCategory != null) {
			String message = "Could not create category. A category with name " + name + " already exists";
			showMessageBackend(message, request, response);
		} else {
			Category newCategory = new Category(name);
			categoryDAO.create(newCategory);
			listCategory("New category created successfully!");
		}
	}

	public void editCategory() throws ServletException, IOException {
		int categoryId = Integer.parseInt(request.getParameter("id"));
		Category category = categoryDAO.get(categoryId);

		if (category == null) {
			String message = "Could not find category with ID " + categoryId;
			showMessageBackend(message, request, response);
		} else {
			request.setAttribute("category", category);
			forwardToPage("category_form.jsp", request, response);
		}
	}

	public void updateCategory() throws ServletException, IOException {
		int categoryId = Integer.parseInt(request.getParameter("categoryId"));
		Category categoryById = categoryDAO.get(categoryId);

		String categoryName = request.getParameter("name");
		Category categoryByName = categoryDAO.findByName(categoryName);

		if (categoryByName != null && categoryByName.getCategoryId() != categoryId) {
			String message = "Could not update category. Category with name " + categoryName + " already exists";
			showMessageBackend(message, request, response);
		} else {
			categoryById.setName(categoryName);
			categoryDAO.update(categoryById);
			String message = "Category has been updated successfully";
			listCategory(message);
		}
	}

	public void deleteCategory() throws ServletException, IOException {
		int categoryId = Integer.parseInt(request.getParameter("id"));
		Category category = categoryDAO.get(categoryId);
		BookDAO bookDAO = new BookDAO();
		List<Book> books = bookDAO.listByCategory(categoryId);

		if (category == null) {
			String message = "Could not find category with ID " + categoryId
					+ ", or it might have been deleted by another admin";
			showMessageBackend(message, request, response);
		} else{
			if (books.isEmpty()) {
				categoryDAO.delete(categoryId);
				listCategory("The category with ID " + categoryId + " has been deleted successfully");
			}else {
				showMessageBackend("Could not delete the category " + categoryId + " because it contains some books", request, response);			
			}
				
		}
	}
}