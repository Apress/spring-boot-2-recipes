package com.apress.springboot2recipes.jpa;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
class HibernateCustomerRepository implements CustomerRepository {

  private final SessionFactory sf;

  HibernateCustomerRepository(SessionFactory sf) {
    this.sf = sf;
  }

  private Session getSession() {
		return sf.getCurrentSession();
	}

  @Override
	public List<Customer> findAll() {
    String hql = "SELECT c FROM Customer c";
    var query = getSession().createQuery(hql, Customer.class);
    return query.getResultList();
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
