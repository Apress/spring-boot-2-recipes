package com.apress.springbootrecipes.order;

import java.math.BigDecimal;
import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Order order = (Order) o;
    return Objects.equals(id, order.id) &&
            Objects.equals(amount, order.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, amount);
  }
}
