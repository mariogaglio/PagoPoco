package com.itisetorricelli.pagopoco.client;

//A Java program for a Client
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

import com.itisetorricelli.pagopoco.model.Check;
import com.itisetorricelli.pagopoco.model.Product;

public class Client {
	// initialize socket and input output streams
	private Socket socket = null;
	private ObjectOutputStream output = null;
	private ObjectInputStream input = null;

	// constructor to put ip address and port
	public Client(String address, int port)
	{
		// establish a connection
		try {
			socket = new Socket(address, port);
			System.out.println("Connected");

			/**
			 * Read product list to server
			 */
			input = new ObjectInputStream(socket.getInputStream());
			ArrayList<Product> list = extractedProductList(input);
			System.out.println("PRODOTTI ALL'INTERNO DEL SUPERMERCATO");
			list.forEach((msg)-> System.out.println(msg.toString()));

			/**
			 * Send data object to server
			 */
			// Add product to cart
			ArrayList<Product> cart = shoppingCart(list);
			output = new ObjectOutputStream(socket.getOutputStream());
			output.writeObject(cart);

			/**
			 * Read product list to server
			 */
			//			inputClient = new ObjectInputStream(socket.getInputStream());
			Check check = extractedCheck(input);
			System.out.printf("Il totale da pagare è: " + check.getAmount());
		}
		catch (IOException ioe) {
			System.out.println(ioe);
			ioe.printStackTrace();
			return;
		}

		// string to read message from input
		String line = "";

		// close the connection
		try {
			//			input.close();
			output.close();
			socket.close();
		}
		catch (IOException ioe) {
			System.out.println(ioe);
		}
	}

	/**
	 * Extract product list to Input Stream
	 * 
	 * @param is object Input Streame in entry
	 * @return list of products
	 */
	private ArrayList<Product> extractedProductList(ObjectInputStream is) {
		try {
			return (ArrayList<Product>) is.readObject();
		} catch (ClassNotFoundException cnfe) {
			System.out.println(cnfe);
			return null;
		} catch (IOException ioe) {
			System.out.println(ioe);
			return null;
		}
	}

	/**
	 * Extract data to execute 
	 * 
	 * @param is
	 * @return
	 */
	private Check extractedCheck(ObjectInputStream is) {
		try {
			return (Check) is.readObject();
		} catch (ClassNotFoundException cnfe) {
			System.out.println(cnfe);
			return null;
		} catch (IOException ioe) {
			System.out.println(ioe);
			return null;
		}
	}

	/**
	 * Shopping cart to buy
	 * 
	 * @param list of product to choose
	 * @return 
	 */
	private ArrayList<Product> shoppingCart(ArrayList<Product> list) {

		Scanner scanner = new Scanner(System.in);
		ArrayList<Product> cart = new ArrayList<Product>();

		System.out.println("Inserisci il codice del prodotto da acquistare (Exit per terminare lo shopping): ");

		// Read inputCode
		String inputCode = "";
		inputCode = scanner.next();
		while (!inputCode.equalsIgnoreCase("Exit")) {
			for (Product product : list) {
				if (product.getCode().equalsIgnoreCase(inputCode)) {
					System.out.println("Inserisci la quantità da acquistsare: ");
					int q = scanner.nextInt();
					product.setQuantity(q);
					cart.add(product);
				}
			}
			System.out.println("Inserisci il codice del prodotto da acquistare (Exit per terminare lo shopping): ");
			inputCode = scanner.next();
		}
		scanner.close();
		return cart;
	}

	public static void main(String args[])
	{
		Client client = new Client("127.0.0.1", 5000);
	}
}