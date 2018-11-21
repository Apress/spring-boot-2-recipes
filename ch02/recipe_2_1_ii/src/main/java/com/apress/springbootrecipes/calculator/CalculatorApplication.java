package com.apress.springbootrecipes.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Collection;

@SpringBootApplication
public class CalculatorApplication {

	public static void main(String[] args) {

		var ctx = SpringApplication.run(CalculatorApplication.class, args);

		var calculator = ctx.getBean(Calculator.class);
		calculator.calculate(137, 21, '+');
		calculator.calculate(137, 21, '*');

		// This will throw an exception as there is no suitable operation
		calculator.calculate(137, 21, '-');
	}

	@Bean
	public Calculator calculator(Collection<Operation> operations) {
		return new Calculator(operations);
	}
}
