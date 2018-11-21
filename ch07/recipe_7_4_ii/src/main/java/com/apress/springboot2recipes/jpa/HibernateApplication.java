package com.apress.springboot2recipes.jpa;

import static org.hibernate.cfg.AvailableSettings.*;
import static org.hibernate.cfg.AvailableSettings.DIALECT;

import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AliasFor;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@SpringBootApplication
public class HibernateApplication {

	public static void main(String[] args) {
		SpringApplication.run(HibernateApplication.class, args);
	}

	@Bean
  public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {

	  Properties properties = new Properties();
	  properties.setProperty(DIALECT, "org.hibernate.dialect.PostgreSQL95Dialect");
	  properties.setProperty(NON_CONTEXTUAL_LOB_CREATION, "true");
    properties.setProperty(CONNECTION_PROVIDER_DISABLES_AUTOCOMMIT, "true");

	  LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
	  sessionFactoryBean.setDataSource(dataSource);
	  sessionFactoryBean.setPackagesToScan("com.apress.springboot2recipes.jpa");
	  sessionFactoryBean.setHibernateProperties(properties);
	  return sessionFactoryBean;
  }

//  @Bean
//  public PlatformTransactionManager transactionManager(SessionFactory sf) {
//	  return new HibernateTransactionManager(sf);
//  }
}

@Component
class CustomerLister implements ApplicationRunner {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final CustomerRepository customers;

	CustomerLister(CustomerRepository customers) {
		this.customers = customers;
	}

	@Override
	public void run(ApplicationArguments args) {

		customers.findAll().forEach( customer -> logger.info("{}", customer));
	}
}
