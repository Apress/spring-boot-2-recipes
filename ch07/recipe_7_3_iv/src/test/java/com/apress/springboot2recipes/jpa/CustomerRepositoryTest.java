package com.apress.springboot2recipes.jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(properties = "spring.flyway.enabled=false")
public class CustomerRepositoryTest {

  @Autowired
  private CustomerRepository repository;

  @Autowired
  private TestEntityManager testEntityManager;

  @Test
  public void insertNewCustomer() {
    assertThat(repository.findAll()).isEmpty();

    Customer customer = repository.save(new Customer("T. Testing", "t.testing@test123.tst"));

    assertThat(customer.getId()).isGreaterThan(-1L);
    assertThat(customer.getName()).isEqualTo("T. Testing");
    assertThat(customer.getEmail()).isEqualTo("t.testing@test123.tst");

    assertThat(repository.findById(customer.getId())).hasValue(customer);
  }

  @Test
  public void findAllCustomers() {
    assertThat(repository.findAll()).isEmpty();

    testEntityManager.persistAndFlush(new Customer("T. Testing1", "t.testing@test123.tst"));
    testEntityManager.persistAndFlush(new Customer("T. Testing2", "t.testing@test123.tst"));

    assertThat(repository.findAll()).hasSize(2);
  }


}
