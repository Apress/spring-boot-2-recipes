package com.apress.springboot2recipes.jpa;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.List;

//@Repository
@Transactional
class JpaCustomerRepository implements CustomerRepository {

	@PersistenceContext
	private EntityManager em;

  @Override
	public List<Customer> findAll() {
		return em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
	}

	@Override
	public Customer findById(long id) {
		return em.find(Customer.class, id);
	}

	@Override
	public Customer save(Customer customer) {
		em.persist(customer);
		return customer;
	}
}
