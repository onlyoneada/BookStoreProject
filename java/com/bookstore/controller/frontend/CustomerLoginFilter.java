package com.bookstore.controller.frontend;

import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import com.bookstoredb.entity.Category;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

@WebFilter("/*")
public class CustomerLoginFilter extends HttpFilter {
	private static final String[] loginRequiedURLs = {
		"/view_profile",
		"/edit_profile", 
		"/update_profile", 
		"/view_orders",
		"/view_cart"
	};
	
	public CustomerLoginFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession(false);
		boolean loggedIn = session != null && session.getAttribute("loginCustomer") != null;

		// if comes to backend, continue to do filter
		String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
		if (path.startsWith("/admin/")) {
			chain.doFilter(request, response);
			return;
		}

		// not login, forward request to login page
		String requestURL = httpRequest.getRequestURI().toString();
		if (!loggedIn && isLoginRequired(requestURL)) {
			httpRequest.getRequestDispatcher("frontend/login_form.jsp").forward(request, response);
		} else {
			chain.doFilter(request, response);
		}
	}
	
	private boolean isLoginRequired(String requestURL) {
		for (String loginRequestURL: loginRequiedURLs) {
			if (requestURL.contains(loginRequestURL)) {
				return true;
			}
		}
		return false;
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
