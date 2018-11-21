package com.apress.springboot2recipes.order;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Order {

	@Id
	private long id;

	private String number;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Order order = (Order) o;
		return id == order.id &&
						Objects.equals(number, order.number);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, number);
	}

	@Override
	public String toString() {
		return "Order [" +
						"id=" + id +
						", number='" + number + '\'' +
						']';
	}
}
