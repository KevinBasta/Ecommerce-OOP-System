import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

// Simulation of a Simple E-Commerce System (like Amazon)

public class ECommerceUserInterface {

	// Class with methods to avoid code copying for the order book and shoes product options
	static class getOptions {
		// method to get the options for a book product
		public static String getBookOption(Scanner scanner) {
			String options = "";

			System.out.print("\nFormat [Paperback Hardcover EBook]: ");
			if (scanner.hasNextLine())
				options = scanner.nextLine();

			return options;
		}

		// method to get the options for a shoe product
		private static String getShoeOption(Scanner scanner) {
			String size = "";
			String colour = "";
			String options = "";

			System.out.print("\nColor: \"Black\" \"Brown\": ");
			if (scanner.hasNextLine())
				colour = scanner.nextLine();

			System.out.print("\nSize: \"6\" \"7\" \"8\" \"9\" \"10\": ");
			if (scanner.hasNextLine())
				size = scanner.nextLine();

			options = colour + size;

			return options;
		}
	}

	public static void main(String[] args) {

		// Create the system
		ECommerceSystem amazon = new ECommerceSystem();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");


		// Process keyboard actions
		while (scanner.hasNextLine()) {
			try {
				String action = scanner.nextLine();

				if (action == null || action.equals("")) {
					System.out.print("\n>");
					continue;
				} else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
					return;

				// Small actions
				else if (action.equalsIgnoreCase("PRODS")) // List all products for sale
				{
					amazon.printAllProducts();
				} else if (action.equalsIgnoreCase("BOOKS")) // List all books for sale
				{
					amazon.printAllBooks();
				} else if (action.equalsIgnoreCase("SHOES")) // List all shoes for sale
				{
					amazon.printAllShoes();
				} else if (action.equalsIgnoreCase("CUSTS")) // List all registered customers
				{
					amazon.printCustomers();
				} else if (action.equalsIgnoreCase("ORDERS")) // List all current product orders
				{
					amazon.printAllOrders();
				} else if (action.equalsIgnoreCase("SHIPPED")) // List all orders that have been shipped
				{
					amazon.printAllShippedOrders();
				} else if (action.equalsIgnoreCase("PRINTBYPRICE")) // sort products by price
				{
					amazon.sortByPrice();
				} else if (action.equalsIgnoreCase("PRINTBYNAME")) // sort products by name (alphabetic)
				{
					amazon.sortByName();
				} else if (action.equalsIgnoreCase("SORTCUSTS")) // sort products by name (alphabetic)
				{
					amazon.sortCustomersByName();
				} 
				
				

				// Long Actions
				else if (action.equalsIgnoreCase("NEWCUST")) {
					// Creates a new registered customer
					String name = "";
					String address = "";

					System.out.print("Name: ");
					if (scanner.hasNextLine())
						name = scanner.nextLine();

					System.out.print("\nAddress: ");
					if (scanner.hasNextLine())
						address = scanner.nextLine();

					amazon.createCustomer(name, address);
				} 

				else if (action.equalsIgnoreCase("SHIP")) {
					// ship an order to a customer
					String orderNumber = "";

					System.out.print("Order Number: ");
					if (scanner.hasNextLine())
						orderNumber = scanner.nextLine();

					ProductOrder success = amazon.shipOrder(orderNumber);
					if (success == null) {
						System.out.println(amazon.getErrorMessage());
					} else {
						success.print();
					}
				} 

				else if (action.equalsIgnoreCase("CUSTORDERS")) {
					// List all the current orders and shipped orders for this customer id
					String customerId = "";

					System.out.print("Customer Id: ");
					if (scanner.hasNextLine())
						customerId = scanner.nextLine();

					amazon.printOrderHistory(customerId);
				} 
				
				else if (action.equalsIgnoreCase("ORDER")) {
					// order a product for a certain customer
					String productId = "";
					String customerId = "";

					System.out.print("Product Id: ");
					if (scanner.hasNextLine())
						productId = scanner.nextLine();

					System.out.print("\nCustomer Id: ");
					if (scanner.hasNextLine())
						customerId = scanner.nextLine();

					String orderNumber = amazon.orderProduct(productId, customerId, "default");
					System.out.println("Order #" + orderNumber);
				} 
				
				else if (action.equalsIgnoreCase("ORDERBOOK")) {
					// order a book for a customer, provide a format (Paperback, Hardcover or EBook)
					String productId = "";
					String customerId = "";

					System.out.print("Product Id: ");
					if (scanner.hasNextLine())
						productId = scanner.nextLine();

					System.out.print("\nCustomer Id: ");
					if (scanner.hasNextLine())
						customerId = scanner.nextLine();

					// custom class to get bookoption. passing scanner obj
					String options = getOptions.getBookOption(scanner);

					String orderNumber = amazon.orderProduct(productId, customerId, options);
					System.out.println("Order #" + orderNumber);
				} 
				
				else if (action.equalsIgnoreCase("ORDERSHOES")) {
					// order shoes for a customer, provide size and color
					String productId = "";
					String customerId = "";

					System.out.print("Product Id: ");
					if (scanner.hasNextLine())
						productId = scanner.nextLine();

					System.out.print("\nCustomer Id: ");
					if (scanner.hasNextLine())
						customerId = scanner.nextLine();

					// custom class to get shoeoption. passing scanner obj
					String options = getOptions.getShoeOption(scanner);

					String orderNumber = amazon.orderProduct(productId, customerId, options);
					System.out.println("Order #" + orderNumber);
				}

				else if (action.equalsIgnoreCase("CANCEL")) {
					// Cancel an existing order
					String orderNumber = "";

					System.out.print("Order Number: ");
					if (scanner.hasNextLine())
						orderNumber = scanner.nextLine();

					amazon.cancelOrder(orderNumber);
				}  
				
				else if (action.equalsIgnoreCase("BooksByAuthor")) {
					String authorName = "";

					System.out.print("Author Name: ");
					if (scanner.hasNextLine())
						authorName = scanner.nextLine();

					amazon.getBooksByAuthor(authorName);
				} 
				



				// New A2 Actions
				else if (action.equalsIgnoreCase("ADDTOCART")) {
					// action to add a product to customer's cart
					String productId = "";
					Product.Category productType;
					String customerId = "";
					String options;

					// getting the product id and cust id
					System.out.print("Product Id: ");
					if (scanner.hasNextLine())
						productId = scanner.nextLine();

					System.out.print("\nCustomer Id: ");
					if (scanner.hasNextLine())
						customerId = scanner.nextLine();

					// getting the product type to know which options to ask for
					productType = amazon.getProductCategory(productId);

					// asking for the appropriate options by using another class
					if (productType == Product.Category.BOOKS) { 
						options = getOptions.getBookOption(scanner);
					} else if (productType == Product.Category.SHOES) { 
						options = getOptions.getShoeOption(scanner);
					} else { 
						options = "defult";
					}

					// adding the product to cart
					amazon.addProductToCart(customerId, productId, options);
				} 
				
				else if (action.equalsIgnoreCase("REMCARTITEM")) {
					// action to remove item from cart
					String productId = "";
					String customerId = "";

					// getting the product id and cust id
					System.out.print("\nCustomer Id: ");
					if (scanner.hasNextLine())
						customerId = scanner.nextLine();

					System.out.print("Product Id: ");
					if (scanner.hasNextLine())
						productId = scanner.nextLine();
					
					// using the ecommercesystem method to try to remove the product from a custs cart
					amazon.removeProductFromCart(customerId, productId);
				} 
				
				else if (action.equalsIgnoreCase("PRINTCART")) {
					// action to print the cart of a cust
					String customerId = "";

					// getting the cust id
					System.out.print("\nCustomer Id: ");
					if (scanner.hasNextLine())
						customerId = scanner.nextLine();

					// using ecommercesystem method to print the cart
					amazon.printCustomerCart(customerId);
				} 
				
				else if (action.equalsIgnoreCase("ORDERITEMS")) {
					// action to order all the items in a custs cart
					String customerId = "";

					// getting cust id
					System.out.print("\nCustomer Id: ");
					if (scanner.hasNextLine())
						customerId = scanner.nextLine();

					// using ecommercesystem method to order all the items in a custs cart
					amazon.orderCustomerCart(customerId);
				}

				else if (action.equalsIgnoreCase("STATS")) {
					// action to print the number of orders of each product
					amazon.printProductStats();
				} 

				else if (action.equalsIgnoreCase("addRating")) {
					// Action to add a rating to a product
					String productId = "";
					String customerId = "";
					Integer rating = 0;
					
					// getting the product id and cust id
					System.out.print("Product Id: ");
					if (scanner.hasNextLine())
						productId = scanner.nextLine();

					System.out.print("\nCustomer Id: ");
					if (scanner.hasNextLine())
						customerId = scanner.nextLine();

					System.out.print("\nRating (1-5): ");
					if (scanner.hasNextLine())
						rating = Integer.parseInt(scanner.nextLine());

					amazon.addProductRating(customerId, productId, rating);
				} 

				else if (action.equalsIgnoreCase("printRatings")) {
					// Action to print the ratings of a product
					String productId = "";

					// getting the product id and cust id
					System.out.print("Product Id: ");
					if (scanner.hasNextLine())
						productId = scanner.nextLine();
					
					amazon.printProductRating(productId);
				}

				else if (action.equalsIgnoreCase("printProdsCategoryRating")) {
					// Action to print the ratings of a product
					String category = "";
					Integer ratingThreshold = 0;

					// Getting the category
					System.out.print("Category (general, clothing, shoes, books, furniture, computers): ");
					if (scanner.hasNextLine())
						category = scanner.nextLine();

					// Getting the threshhold
					System.out.print("Above what rating (1, 2, 3, 4, 5): ");
					if (scanner.hasNextLine())
						ratingThreshold = Integer.parseInt(scanner.nextLine());

					amazon.printProdsCategoryWithRating(category, ratingThreshold);
				}


			} catch (RuntimeException e){ 
				// if any of the runtime exceptions in ecommercesystem are thrown
				// they will be caught here and their message would be printed out
				System.out.println(e.getMessage());
			}

			System.out.print("\n>");
		}

	}
}
