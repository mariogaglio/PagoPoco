package com.itisetorricelli.pagopoco.server;

//A Java program for a Server
import java.net.*;
import java.util.ArrayList;
import java.util.Random;

import com.itisetorricelli.pagopoco.model.Check;
import com.itisetorricelli.pagopoco.model.Product;

import java.io.*;

public class Server
{
	//initialize socket and input stream
	private Socket socket = null;
	private ServerSocket server = null;
	private ObjectOutputStream output = null;
	private ObjectInputStream input = null;
	private double tot = 0.00;

	// constructor with port
	public Server(int port)
	{
		// starts server and waits for a connection
		try
		{
			// Start to Server
			server = new ServerSocket(port);
			System.out.println("Server started");

			// Create product list into supermarket
			ArrayList<Product> list = generateListProducts();
			list.forEach((msg)-> System.out.println(msg.toString()));


			// Waiting to client connection
			System.out.println("Waiting for a client ...");

			// Accept connection to server
			socket = server.accept();
			System.out.println("Client accepted");

			/**
			 * Communication from server to client
			 */
			// Send list product to client
			output = new ObjectOutputStream(socket.getOutputStream());
			output.writeObject(list);

			/**
			 * Communication from client to server
			 */
			// takes input from the client socket
			input = new ObjectInputStream(socket.getInputStream());
			ArrayList<Product> cart = extracted(input);
			System.out.println("PRODOTTI ALL'INTERNO DEL CARRELLO");
			
			// Calculate total amount shopping cart
			cart.forEach((msg)-> {
				System.out.println(msg.toString());
				tot += msg.getPrice() * msg.getQuantity();
			});
			
			/**
			 * Communication from server to client
			 */
			// Send check to client
			Check check = new Check();
			check.setCart(cart);
			tot = Math.round(tot*100.0)/100.0;
			check.setAmount(tot);
			output.writeObject(check);

			System.out.println("Closing connection");

			// close connection
			socket.close();
			input.close();
		}
		catch(IOException i)
		{
			System.out.println(i);
		}
	}

	/**
	 * Generate product list
	 * 
	 * @return {ArrayList<Product>} object contains the list of product to supermarket
	 */
	public ArrayList<Product> generateListProducts() {

		// Initialize list product
		ArrayList<Product> glp = new ArrayList<Product>();

		String descProducts[]= {"Pasta", "Frutta", "Verdura", "Detersivo", "Carne", "Pesce", "Latte", "Bibita", "Salumi", "Pane"};

		// Create Random object
		Random random = new Random();

		// genera numero casuale tra 1 e 10
		int numberProducts = random.nextInt(10) + 1;

		int quantityProduct;

		for(int i = 0 ; i < numberProducts; i++) {

			quantityProduct = random.nextInt(100) + 1;
			Product p = new Product(generateAlphaStringCode().toString(), descProducts[i], quantityProduct, generateRandomPrice());
			glp.add(p);
		}

		return glp;
	}

	/**
	 * Generate alphanumeric string code
	 * 
	 * @return the string code
	 */
	private StringBuilder generateAlphaStringCode() {

		// Init range to generate alphanoumeric code
		int startRange = 65;
		int endRange = 90;
		int randomStrLen = 10;

		// Create Random object
		Random randomizer = new Random();

		// Create StringBuilder object
		StringBuilder bufferSpace = new StringBuilder(randomStrLen);

		for (int k = 0; k < randomStrLen; k++) {

			int stringRangeChecker = startRange + (int)(randomizer.nextFloat() * (endRange - startRange + 1));

			// Append to random character into StringBuilder object
			bufferSpace.append((char) stringRangeChecker);
		}

		return bufferSpace;
	}

	/**
	 * Generate a random price
	 * 
	 * @return double number
	 */
	public double generateRandomPrice() {

		// Create Random object
		final Random rnd = new Random();

		double leftLimit = 1D;
		double rightLimit = 10D;
		double generatedDouble = leftLimit + rnd.nextDouble() * (rightLimit - leftLimit);
		double around = Math.round(generatedDouble*100.0)/100.0;

		return around;

	}

	/**
	 * Extracted array list of product to Input Stream
	 * 
	 * @param is object Input Stream
	 * @return the {ArrayList} of product
	 */
	private ArrayList<Product> extracted(ObjectInputStream is) {
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

	public static void main(String args[])
	{
		Server server = new Server(5000);
	}
}
