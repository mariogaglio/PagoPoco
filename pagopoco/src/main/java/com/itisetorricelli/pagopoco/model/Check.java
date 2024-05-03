package com.itisetorricelli.pagopoco.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Check implements Serializable {

	private ArrayList<Product> cart;
	private double amount = 0.00;
	
	public ArrayList<Product> getCart() {
		return cart;
	}
	
	public void setCart(ArrayList<Product> cart) {
		this.cart = cart;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Check [cart=" + cart + ", amount=" + amount + "]";
	}
}
