/* A book IS A product that has additional information - e.g. title, author

 	 A book also comes in different formats ("Paperback", "Hardcover", "EBook")
 	 
 	 The format is specified as a specific "stock type" in get/set/reduce stockCount methods.

*/
public class Book extends Product 
{
  private String author;
  private String title;
  
  // Stock related information NOTE: inherited stockCount variable is used for EBooks
  private int paperbackStock;
  private int hardcoverStock;
  private int year; 
  
  public Book(String name, String id, double price, int paperbackStock, int hardcoverStock, String title, String author)
  {
  	 // Make use of the constructor in the super class Product. Initialize additional Book instance variables. 
  	 // Set category to BOOKS 
     super(name, id, price, 100000, Product.Category.BOOKS);
     this.paperbackStock = paperbackStock; 
     this.hardcoverStock = hardcoverStock; 
     this.title = title; 
     this.author = author; 
     this.year = 0;
  }

  // Another constructor method that uses the year variable aswell
  public Book(String name, String id, double price, int paperbackStock, int hardcoverStock, String title, String author, int year)
  {
     super(name, id, price, 100000, Product.Category.BOOKS);
     this.paperbackStock = paperbackStock; 
     this.hardcoverStock = hardcoverStock; 
     this.title = title; 
     this.author = author; 
     this.year = year;
  }
    
  // Check if a valid format  
  public boolean validOptions(String productOptions)
  {
  	// check productOptions for "Paperback" or "Hardcover" or "EBook"
  	// if it is one of these, return true, else return false
    if (productOptions.equalsIgnoreCase("Paperback") || productOptions.equalsIgnoreCase("Hardcover") || productOptions.equalsIgnoreCase("EBook")) {
      return true;
    } else { 
      return false;
    }
  }
  
  // Override getStockCount() in super class.
  public int getStockCount(String productOptions)
	{
  	// Use the productOptions to check for (and return) the number of stock for "Paperback" etc
  	// Use the variables paperbackStock and hardcoverStock at the top. 
  	// For "EBook", use the inherited stockCount variable.
    if (productOptions.equalsIgnoreCase("Paperback")) {
      return this.paperbackStock;
    } else if (productOptions.equalsIgnoreCase("Hardcover")) { 
      return this.hardcoverStock;
    } else if (productOptions.equalsIgnoreCase("EBook")) {
      return super.getStockCount(productOptions);
    }
  	return 1;
	}
  
  public void setStockCount(int stockCount, String productOptions)
	{
    // Use the productOptions to check for (and set) the number of stock for "Paperback" etc
   	// Use the variables paperbackStock and hardcoverStock at the top. 
   	// For "EBook", set the inherited stockCount variable.
    if (productOptions.equalsIgnoreCase("Paperback")) {
      this.paperbackStock = stockCount;
    } else if (productOptions.equalsIgnoreCase("Hardcover")) { 
      this.hardcoverStock = stockCount;
    } else if (productOptions.equalsIgnoreCase("EBook")) {
      super.setStockCount(stockCount, productOptions);
    }
	}
  
  /*
   * When a book is ordered, reduce the stock count for the specific stock type
   */
  public void reduceStockCount(String productOptions)
	{
  	// Use the productOptions to check for (and reduce) the number of stock for "Paperback" etc
   	// Use the variables paperbackStock and hardcoverStock at the top. 
   	// For "EBook", set the inherited stockCount variable.
    if (productOptions.equalsIgnoreCase("Paperback")) {
      this.paperbackStock -= 1;
    } else if (productOptions.equalsIgnoreCase("Hardcover")) { 
      this.hardcoverStock -= 1;
    } else if (productOptions.equalsIgnoreCase("EBook")) {
      super.reduceStockCount(productOptions);
    }
	}

  public String getAuthor() { 
    return this.author;
  }

  public int getYear() { 
    return this.year;
  }

  /*
   * Print product information in super class and append Book specific information title and author
   */
  public void print()
  {
  	// Replace the line below.
  	// Make use of the super class print() method and append the title and author info. See the video
    super.print();
    if (this.year != 0) { 
      System.out.printf("  Title: %-30s Author: %-20s Year: %-4s", this.title, this.author, this.year);
    } else { 
      System.out.printf("  Title: %-30s Author: %-1s", this.title, this.author);
    }
    
  }
}
