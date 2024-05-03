package com.itisetorricelli.pagopoco.model;

import java.io.Serializable;

public class Product implements Serializable {
	
	private String code;
	private String description;
	private int quantity;
	private double price;
	private double amount = 0.00;
	
	public Product(String code, String description, int quantity, double d) {
		super();
		this.code = code;
		this.description = description;
		this.quantity = quantity;
		this.price = d;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Product [code=" + code + ", description=" + description + ", quantity=" + quantity + ", price=" + price
				+ "]";
	}
	
	

}
