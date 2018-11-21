package com.apress.springboot2recipes.jpa;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
class HibernateCustomerRepository implements CustomerRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private Session getSession() {
		return entityManager.unwrap(Session.class);
	}

  @Override
	public List<Customer> findAll() {
		return getSession().createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
	}

	@Override
	public Customer findById(long id) {
		return getSession().find(Customer.class, id);
	}

	@Override
	public Customer save(Customer customer) {
		getSession().persist(customer);
		return customer;
	}
}
