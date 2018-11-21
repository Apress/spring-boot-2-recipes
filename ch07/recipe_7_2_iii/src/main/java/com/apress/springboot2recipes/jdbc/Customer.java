package com.apress.springboot2recipes.jdbc;

import java.util.Objects;

public class Customer {

	private final long id;
	private final String name;
	private final String email;

	Customer(long id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Customer customer = (Customer) o;
		return id == customer.id &&
						Objects.equals(name, customer.name) &&
						Objects.equals(email, customer.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, email);
	}

	@Override
	public String toString() {
		return "Customer [" +
						"id=" + id +
						", name='" + name + '\'' +
						", email='" + email + '\'' +
						']';
	}
}
