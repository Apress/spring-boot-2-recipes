package com.apress.springbootrecipes.demo;

public class OrderConfirmation {

	private String orderId;

	public OrderConfirmation() {}

	public OrderConfirmation(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		return String.format("OrderConfirmation [orderId='%s']", orderId);
	}
}
