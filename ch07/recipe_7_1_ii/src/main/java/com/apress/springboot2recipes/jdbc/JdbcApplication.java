package com.apress.springboot2recipes.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@SpringBootApplication
public class JdbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(JdbcApplication.class, args);
	}
}

@Component
class TableLister implements ApplicationRunner {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final DataSource dataSource;

	TableLister(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		try (var con = dataSource.getConnection();
				 var rs = con.getMetaData().getTables(null, null, "%", null)) {
			while (rs.next()) {
				logger.info("{}", rs.getString(3));
			}
		}
	}
}
