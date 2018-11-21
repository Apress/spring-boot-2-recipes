package com.apress.springboot2recipes.jdbc;

import java.util.List;

public interface CustomerRepository {

	List<Customer> findAll();
	Customer findById(long id);
	Customer save(Customer customer);
}
