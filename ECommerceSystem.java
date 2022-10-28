import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import javax.management.RuntimeErrorException;
import javax.xml.transform.Templates;

/*
 * Models a simple ECommerce system. Keeps track of products for sale, registered customers, product orders and
 * orders that have been shipped to a customer
 */
public class ECommerceSystem {
  // Creating two hashmaps. One for the products and one for the product stats
  private Map<String, Product> products = new HashMap<String, Product>();
  private Map<String, Integer> productOrderStats = new HashMap<String, Integer>();
  private ArrayList<Customer> customers = new ArrayList<Customer>();

  private ArrayList<ProductOrder> orders = new ArrayList<ProductOrder>();
  private ArrayList<ProductOrder> shippedOrders = new ArrayList<ProductOrder>();

  // These variables are used to generate order numbers, customer id's, product
  // id's
  private int orderNumber = 500;
  private int customerId = 900;
  private int productId = 700;

  // Random number generator
  Random random = new Random();

  public ECommerceSystem() {

    // Create some products. Notice how generateProductId() method is used
    try {
      // using a method to read a file for the products
      products = initProds();
    } catch (IOException e) {
      System.out.println(e.getMessage());
      System.exit(1);
    }

    // Create some customers. Notice how generateCustomerId() method is used
    customers.add(new Customer(generateCustomerId(), "Inigo Montoya", "1 SwordMaker Lane, Florin"));
    customers.add(new Customer(generateCustomerId(), "Prince Humperdinck", "The Castle, Florin"));
    customers.add(new Customer(generateCustomerId(), "Andy Dufresne", "Shawshank Prison, Maine"));
    customers.add(new Customer(generateCustomerId(), "Ferris Bueller", "4160 Country Club Drive, Long Beach"));
  }

  // Reading products into a map from a text file
  private Map<String, Product> initProds() throws IOException {
    Map<String, Product> prods = new HashMap<String, Product>();
    File myObj = new File("products.txt");
    Scanner in = new Scanner(myObj);

    int lineCounter = 1;
    String line;
    String productCategory = "";
    String productName = "";
    double productPrice = 0.0;
    ArrayList<String> stockCount = new ArrayList<String>();
    ArrayList<String> additionalInfo = new ArrayList<String>();

    // while there are still more products in the text file, continue reading the
    // lines
    while (in.hasNextLine() || lineCounter >= 5) {

      // Creating the products and putting them in the temp map
      if (lineCounter == 6) {
        String prodId = generateProductId();

        if (productCategory.equalsIgnoreCase("BOOKS")) {
          prods.put(prodId,
              new Book(productName, prodId, productPrice, Integer.parseInt(stockCount.get(0)),
                  Integer.parseInt(stockCount.get(1)), additionalInfo.get(0), additionalInfo.get(1),
                  Integer.parseInt(additionalInfo.get(2))));
        } else if (productCategory.equalsIgnoreCase("SHOES")) {
          // prods.put(prodId, new Shoes(productName, prodId, productPrice,
          // stockCount.get(0), stockCount.get(0)));
        } else {
          Product.Category cat = Product.Category.GENERAL;
          if (productCategory.equalsIgnoreCase("COMPUTERS")) {
            cat = Product.Category.COMPUTERS;
          } else if (productCategory.equalsIgnoreCase("FURNITURE")) {
            cat = Product.Category.FURNITURE;
          } else if (productCategory.equalsIgnoreCase("CLOTHING")) {
            cat = Product.Category.CLOTHING;
          } else if (productCategory.equalsIgnoreCase("GENERAL")) {
            cat = Product.Category.GENERAL;
          } else if (productCategory.equalsIgnoreCase("SHOES")) {
            cat = Product.Category.SHOES;
          }

          prods.put(prodId, new Product(productName, prodId, productPrice, Integer.parseInt(stockCount.get(0)), cat));
        }

        stockCount.clear();
        additionalInfo.clear();
        lineCounter = 1;
        if (!in.hasNextLine()) {
          break;
        }
      }

      // getting the next line and setting it to the appropriate parameter based on a
      // counter variable
      line = in.nextLine();

      if (lineCounter == 1) {
        productCategory = line;
      } else if (lineCounter == 2) {
        productName = line;
      } else if (lineCounter == 3) {
        productPrice = Double.parseDouble(line);
      } else if (lineCounter == 4) {
        String[] tempList = line.split(" ");
        for (String t : tempList) {
          stockCount.add(t);
        }
      } else if (lineCounter == 5) {
        String[] tempList = line.split(":");
        for (String t : tempList) {
          additionalInfo.add(t);
        }
      }

      lineCounter++;
    }
    return prods;
  }

  private String generateOrderNumber() {
    return "" + orderNumber++;
  }

  private String generateCustomerId() {
    return "" + customerId++;
  }

  private String generateProductId() {
    return "" + productId++;
  }

  public String getErrorMessage() {
    return null;
  }

  public void printAllProducts() {
    // Iterating though the keyset because of map
    for (String key : products.keySet())
      products.get(key).print();
  }

  public Product.Category getProductCategory(String id) {
    // Need to check if the product is there before trying to get it's catagory
    if (!products.containsKey(id)) {
      throw new unknownProductException("Product " + id + " Not Found");
    }
    return products.get(id).getCategory();
  }

  public void printAllBooks() {
    // Iterating though the keyset because of map
    for (String key : products.keySet()) {
      if (products.get(key).getCategory() == Product.Category.BOOKS) {
        products.get(key).print();
      }
    }
  }

  public void printAllShoes() {
    // Iterating though the keyset because of map
    for (String key : products.keySet()) {
      if (products.get(key).getCategory() == Product.Category.SHOES) {
        products.get(key).print();
      }
    }
  }

  public void printAllOrders() {
    for (ProductOrder o : orders) {
      o.print();
    }
  }

  public void printAllShippedOrders() {
    for (ProductOrder SO : shippedOrders) {
      SO.print();
    }
  }

  public void printCustomers() {
    for (int i = 0; i < this.customers.size(); i++) {
      System.out.printf("%-29s", "Name: " + this.customers.get(i).getName());
      System.out.printf("%-10s", "ID: " + this.customers.get(i).getId());
      System.out.println("Address: " + this.customers.get(i).getShippingAddress());
    }
  }

  /*
   * Given a customer id, print all the current orders and shipped orders for them
   * (if any)
   */
  public void printOrderHistory(String customerId) {
    int custCheck = customers.indexOf(new Customer(customerId));
    if (custCheck == -1) {
      throw new unknownCustomerException("Customer " + customerId + " Not Found");
    }

    // Current orders
    System.out.println("Current Orders of Customer " + customerId);
    for (ProductOrder order : orders) {
      if (order.getCustomer().getId().equals(customerId)) {
        order.print();
      }
    }

    // Shipped orders
    System.out.println("\nShipped Orders of Customer " + customerId);
    for (ProductOrder shipped : shippedOrders) {
      if (shipped.getCustomer().getId().equals(customerId)) {
        shipped.print();
      }
    }
  }

  // get books by author
  public void getBooksByAuthor(String authorName) {
    // array to keep the books by the author in
    ArrayList<Book> booksByTheAuthor = new ArrayList<Book>();

    // loops over products, finds books, checks if written by author
    for (String key : products.keySet()) {
      if (products.get(key).getCategory() == Product.Category.BOOKS) {
        Book bookCast = (Book) products.get(key);
        if (bookCast.getAuthor().equalsIgnoreCase(authorName)) {
          booksByTheAuthor.add(bookCast);
        }
      }
    }

    // if there are no books by the author inputted set error and return false
    if (booksByTheAuthor.isEmpty()) {
      throw new unknownProductException("No books written by " + authorName + ".");
    }

    // sorting the books array using interface
    Collections.sort(booksByTheAuthor, new bookYearComparator());

    // printing the books
    for (Book b : booksByTheAuthor) {
      b.print();
    }
  }

  class bookYearComparator implements Comparator<Book> {
    // Class used for just comparing the year number of the books
    public int compare(Book bookObjectOne, Book bookObjectTwo) {
      if (bookObjectOne.getYear() > bookObjectTwo.getYear())
        return 1;
      else if (bookObjectOne.getYear() < bookObjectTwo.getYear())
        return -1;
      return 0;
    }
  }

  public String orderProduct(String productId, String customerId, String productOptions) {
    // Using my helper methods to get the customer id and product id
    // exceptions thrown inside the helper method
    Customer customer = getCustomer(customerId);
    Product prod = getProduct(productId);

    if (!prod.validOptions(productOptions)) {
      throw new invalidProductOptionsException(
          "Product " + prod.getCategory() + " ProductId " + productId + " Invalid Options: " + productOptions);
    }

    if (prod.getStockCount(productOptions) == 0) {
      throw new productOutOfStockException("No Stock Avaliable For " + productId + ": " + productOptions);
    }

    String orderNumb = generateOrderNumber();
    ProductOrder newOrder = new ProductOrder(orderNumb, prod, customer, productOptions);
    prod.reduceStockCount(productOptions);
    orders.add(newOrder);

    // Product Order Stats
    // Getting the number of orders of a product id
    // if it is not in the map then put it as 1 in the map
    // otherwise add 1 to it's exsisting number in the map
    Integer numberOfOrders = productOrderStats.get(productId);
    if (numberOfOrders == null) {
      productOrderStats.put(productId, 1);
    } else {
      productOrderStats.put(productId, numberOfOrders + 1);
    }
    prod.setNumberOfOrders(productOrderStats.get(productId));

    return orderNumb;
  }

  /*
   * Create a new Customer object and add it to the list of customers
   */
  public void createCustomer(String name, String address) {
    if (name == null || name.equals("")) {
      throw new invalidCustomerNameException("Invalid Customer Name " + name);
    }
    if (address == null || address.equals("")) {
      throw new invalidCustomerAddressException("Invalid Customer Address " + address);
    }

    // Create a Customer object and add to array list
    Customer customer = new Customer(generateCustomerId(), name, address);
    customers.add(customer);
  }

  public ProductOrder shipOrder(String orderNumber) {
    int index = orders.indexOf(new ProductOrder(orderNumber, null, null, ""));
    if (index == -1) {
      throw new invalidOrderNumberException("Order " + orderNumber + " Not Found");
    }

    ProductOrder order = orders.get(index);
    orders.remove(index);
    shippedOrders.add(order);

    return order;
  }

  /*
   * Cancel a specific order based on order number
   */
  public void cancelOrder(String orderNumber) {
    int index = orders.indexOf(new ProductOrder(orderNumber, null, null, ""));
    if (index == -1) {
      throw new invalidOrderNumberException("Order " + orderNumber + " Not Found");
    }

    // ProductOrder order = orders.get(index);
    orders.remove(index);
  }

  // A2 Extra Methods
  public void addProductToCart(String customerId, String productId, String productOptions) {
    // adding the product to cart
    // using helper methods to get the cust and prod (thowing exceptions there)
    Customer cust = getCustomer(customerId);
    Product prod = getProduct(productId);
    Cart custCart = cust.getCart();

    // adding item to the cart of an individual
    custCart.addCartItem(prod, productOptions);
  }

  public void removeProductFromCart(String customerId, String productId) {
    // removing a product from a custs cart
    // using helper method to get the cust (throwing exception there)
    Customer cust = getCustomer(customerId);
    Cart custCart = cust.getCart();

    // removing the item from the cart using product id
    custCart.removeCartItem(productId);
  }

  public void printCustomerCart(String customerId) {
    // printing the cart of a cust
    // using helper moethod to get the cust and throw exception there
    Customer cust = getCustomer(customerId);
    Cart custCart = cust.getCart();

    // loading the cart items into an arraylist
    ArrayList<CartItem> cartItems = custCart.getCartItems();

    // looping over the cart items to print them
    System.out.println(cust.getName() + "'s cart:");
    for (CartItem CI : cartItems) {
      CI.printProd();
    }
  }

  public void orderCustomerCart(String customerId) {
    // ordering all the items in a cust's cart
    // using a helper method to get the cust and throw exception there
    Customer cust = getCustomer(customerId);
    Cart custCart = cust.getCart();

    // Ordering cart items by loading them into an arraylist
    // looping through the items and trying to call the orderproduct
    // method, if exception is thrown in there, then the ordering will stop
    ArrayList<CartItem> cartItems = custCart.getCartItems();
    for (CartItem CI : cartItems) {
      String prodId = CI.getProductId();
      orderProduct(prodId, customerId, CI.getProductOption());
    }

    // Clearing cust cart after order
    custCart.clearCart();
  }

  public void printProductStats() {
    // printing the stats of how many times each item has been ordered
    // loading the map of all products into an array list so that they can
    // be sorted from most ordered to least ordered
    ArrayList<String> prodStats = new ArrayList<>(products.keySet());
    Collections.sort(prodStats, new productStatsComparator());
    for (String p : prodStats) {
      // USING THE MAP OF PRODUCTS to get the products by their keys 
      // this results in a sorted console printing because the prodstats
      // arraylist sorts the keys of the map
      // then that sorted list is used to get the products associated with those keys in from the map
      Product currentProd = products.get(p);
      currentProd.printStats();
    }
  }

  class productStatsComparator implements Comparator<String> {
    // sorting the products based on how many times they were ordered
    public int compare(String firstProductId, String secondProductId) {
      // getting the nubmer of times ordered from a variable inside each product
      // object
      // the two number of orders variables are what's being compared
      int productOneOrders = products.get(firstProductId).getNumberOfOrders();
      int productTwoOrders = products.get(secondProductId).getNumberOfOrders();
      if (productOneOrders > productTwoOrders)
        return -1;
      else if (productOneOrders < productTwoOrders)
        return 1;
      return 0;
    }
  }

  public void addProductRating(String customerId, String productId, Integer oneToFive) {
    // adding a rating to a product
    // using the get cust helper to check if it's a real cust
    // then getting the product and adding the 1-5 rating to it
    Product prod = getProduct(productId);
    Customer cust = getCustomer(customerId);

    prod.addProductRating(oneToFive);
  }

  public void printProductRating(String productId) {
    // getting the product and the ratings map
    Product prod = getProduct(productId);
    Map<Integer, Integer> ratings = prod.getRatings();

    // printing the ratings
    System.out.println(prod.getName() + "'s ratings: ");
    for (Integer key : ratings.keySet()) {
      System.out.println(key + ": " + ratings.get(key));
    }
  }

  public void printProdsCategoryWithRating(String category, Integer ratingThreshhold) {
    // checking if the category is real
    Product.Category cat = Product.Category.GENERAL;
    if (category.equalsIgnoreCase("COMPUTERS")) {
      cat = Product.Category.COMPUTERS;
    } else if (category.equalsIgnoreCase("FURNITURE")) {
      cat = Product.Category.FURNITURE;
    } else if (category.equalsIgnoreCase("CLOTHING")) {
      cat = Product.Category.CLOTHING;
    } else if (category.equalsIgnoreCase("GENERAL")) {
      cat = Product.Category.GENERAL;
    } else if (category.equalsIgnoreCase("SHOES")) {
      cat = Product.Category.SHOES;
    } else if (category.equalsIgnoreCase("BOOKS")) { 
      cat = Product.Category.BOOKS;
    } else { 
      throw new RuntimeException("The category " + category + " doesn't exist");
    }

    // getting only the products in the category and above the ratings 
    Map<String, Product> prodsOfCat = new HashMap<String, Product>();
    for (String key : products.keySet()) { 
      Product tempProd = products.get(key);
      if ((tempProd.getCategory().equals(cat)) && (tempProd.getRatingAverage() >= (double) ratingThreshhold)) { 
        prodsOfCat.put(key, tempProd);
        tempProd.print();
        System.out.print("    average rating: " + tempProd.getRatingAverage());
      }
    }
  }

  // helper methods
  public Customer getCustomer(String custId) {
    // helper method to get the customer from the customer array
    // if the customer is not there then an appropriate exception is thrown
    int custCheck = customers.indexOf(new Customer(custId));
    if (custCheck == -1) {
      throw new unknownCustomerException("Customer " + custId + " Not Found");
    }
    // if an exception is not thrown the customer is fetched and returned
    Customer customer = customers.get(custCheck);
    return customer;
  }

  public Product getProduct(String prodId) {
    // helper method to get the product from the products map
    // if the product is not in the map then an appropriate exception is thrown
    if (!products.containsKey(prodId)) {
      throw new unknownProductException("Product " + prodId + " Not Found");
    }
    // if no exception is thrown then the product is returned
    return products.get(prodId);
  }

  public ArrayList<Product> convertMapToArr(Map<String, Product> productsMap) {
    // helper method to convert the map used from products into an array
    // used for sorting methods below
    ArrayList<Product> productsArr = new ArrayList<Product>();
    for (String key : productsMap.keySet()) {
      Product prod = productsMap.get(key);
      productsArr.add(prod);
    }
    return productsArr;
  }

  // Sorting Methods
  public void sortByPrice() {
    // Sort products by increasing price
    // using helper method to convert the map to array
    ArrayList<Product> productsArr = convertMapToArr(products);
    Collections.sort(productsArr, new productPriceComparator());
    for (Product p : productsArr) {
      p.print();
    }
  }

  class productPriceComparator implements Comparator<Product> {
    // Class used for just comparing the product prices with the compare method
    public int compare(Product firstProduct, Product secondProduct) {
      if (firstProduct.getPrice() > secondProduct.getPrice())
        return 1;
      else if (firstProduct.getPrice() < secondProduct.getPrice())
        return -1;
      return 0;
    }
  }

  public void sortByName() {
    // Sort products alphabetically by product name
    // using helper method to convert the map to array
    ArrayList<Product> productsArr = convertMapToArr(products);
    Collections.sort(productsArr, new productNameComparator());
    for (Product p : productsArr) {
      p.print();
    }
  }

  class productNameComparator implements Comparator<Product> {
    // Class used for just comparing the product names with the compare method
    public int compare(Product firstProduct, Product secondProduct) {
      return (firstProduct.getName().compareTo(secondProduct.getName()));
    }
  }

  public void sortCustomersByName() {
    // Sort custs alphabetically by custs name
    Collections.sort(customers);
  }
}

// Exception classes
class unknownCustomerException extends RuntimeException {
  public unknownCustomerException() {
  }

  public unknownCustomerException(String message) {
    super(message);
  }
}

class unknownProductException extends RuntimeException {
  public unknownProductException() {
  }

  public unknownProductException(String message) {
    super(message);
  }
}

class invalidProductOptionsException extends RuntimeException {
  public invalidProductOptionsException() {
  }

  public invalidProductOptionsException(String message) {
    super(message);
  }
}

class productOutOfStockException extends RuntimeException {
  public productOutOfStockException() {
  }

  public productOutOfStockException(String message) {
    super(message);
  }
}

class invalidCustomerNameException extends RuntimeException {
  public invalidCustomerNameException() {
  }

  public invalidCustomerNameException(String message) {
    super(message);
  }
}

class invalidCustomerAddressException extends RuntimeException {
  public invalidCustomerAddressException() {
  }

  public invalidCustomerAddressException(String message) {
    super(message);
  }
}

class invalidOrderNumberException extends RuntimeException {
  public invalidOrderNumberException() {
  }

  public invalidOrderNumberException(String message) {
    super(message);
  }
}

class productNotInCartException extends RuntimeException {
  public productNotInCartException() {
  }

  public productNotInCartException(String message) {
    super(message);
  }
}