package com.apress.springbootrecipes.demo;

import java.math.BigDecimal;

public class Order {

	private String id;
	private BigDecimal amount;

	public Order() {
	}

	public Order(String id, BigDecimal amount) {
		this.id=id;
		this.amount = amount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return String.format("Order [id='%s', amount=%4.2f]", id, amount);
	}
}
