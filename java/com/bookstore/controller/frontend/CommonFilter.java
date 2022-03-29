package com.bookstore.controller.frontend;

import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.List;

import com.bookstore.dao.CategoryDAO;
import com.bookstoredb.entity.Category;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

@WebFilter("/*")
public class CommonFilter extends HttpFilter {
    private final CategoryDAO categoryDAO;   
    
    public CommonFilter() {
        categoryDAO = new CategoryDAO();
    }

	public void destroy() {
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {	
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
		
		if (!path.startsWith("/admin/")) {
			List<Category> listCategory = categoryDAO.listAll();
		    request.setAttribute("listCategory", listCategory);
		}
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {		
	}
}
