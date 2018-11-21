package com.apress.springboot2recipes.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;

@SpringBootApplication
public class JdbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(JdbcApplication.class, args);
	}
}

@Component
class TableLister implements ApplicationRunner {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final JdbcTemplate jdbc;

	TableLister(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public void run(ApplicationArguments args) {

		jdbc.execute((ConnectionCallback<Object>) con -> {
			try (var rs = con.getMetaData().getTables(null, null, "%", null)) {
				while (rs.next()) {
					logger.info("{}", rs.getString(3));
				}
			}
			return null;
		});
	}
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
