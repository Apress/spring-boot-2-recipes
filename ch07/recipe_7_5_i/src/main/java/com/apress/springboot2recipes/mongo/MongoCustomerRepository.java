package com.apress.springboot2recipes.mongo;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
class MongoCustomerRepository implements CustomerRepository {

	private final MongoTemplate mongoTemplate;

	MongoCustomerRepository(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public List<Customer> findAll() {
		return mongoTemplate.findAll(Customer.class);
	}

	@Override
	public Customer findById(long id) {
		return mongoTemplate.findById(id, Customer.class);
	}

	@Override
	public Customer save(Customer customer) {
		mongoTemplate.save(customer);
		return customer;
	}
}
