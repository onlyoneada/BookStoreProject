package com.bookstore.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bookstoredb.entity.Book;
import com.bookstoredb.entity.Customer;
import com.bookstoredb.entity.Users;

public class CustomerDAO extends JpaDAO<Customer> implements GenericDAO<Customer> {
	
	public CustomerDAO() {		
	}

	@Override
	public Customer create(Customer customer) {
		customer.setRegisterDate(new Date());
		return super.create(customer);
	}

	@Override
	public Customer update(Customer customer) {
		customer.setRegisterDate(new Date());
		return super.update(customer);
	}

	@Override
	public Customer get(Object id) {
		return super.find(Customer.class, id);
	}

	@Override
	public void delete(Object id) {
		super.delete(Customer.class, id);
	}

	@Override
	public List<Customer> listAll() {
		return super.findWithNamedQuery("Customer.findAll");
	}

	@Override
	public long count() {
		return super.countWithNamedQuery("Customer.countAll");
	}
	
	public Customer findByEmail(String email) {
		List<Customer> customers = super.findWithNamedQuery("Customer.findByEmail", "email", email);
		if (customers !=null && customers.size()> 0) {
			return customers.get(0);
		}
		return null;
	}
	
	public Customer checkLogin(String email, String password) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("email", email);
		parameters.put("password", password);
		List<Customer> customers = super.findWithNamedQuery("Customer.checkLogin", parameters);
		
		if (customers.size()==1) {
			return customers.get(0);
		}
		return null;
	}
}
