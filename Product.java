import java.util.HashMap;
import java.util.Map;

import javax.xml.catalog.Catalog;

/*
 * class Product defines a product for sale by the system. 
 * 
 * A product belongs to one of the 5 categories below. 
 * 
 * Some products also have various options (e.g. size, color, format, style, ...). The options can affect
 * the stock count(s). In this generic class Product, product options are not used in get/set/reduce stockCount() methods  
 * 
 * Some products
 */
public class Product
{
	public static enum Category {GENERAL, CLOTHING, SHOES, BOOKS, FURNITURE, COMPUTERS};
	
	private String name;
	private String id;
	private Category category;
	private double price;
	private int stockCount;
	private int numberOfOrders;
	private Map<Integer, Integer> ratings;
	private int totalRatings; // will help with averaging
	
	public Product()
	{
		this.name = "Product";
		this.id = "001";
		this.category = Category.GENERAL;
		this.stockCount = 0;
		this.numberOfOrders = 0;
		ratings = new HashMap<Integer,Integer>();
		initRatings();
		totalRatings = 0;
	}
	
	public Product(String id)
	{
		this("Product", id, 0, 0, Category.GENERAL);
		this.numberOfOrders = 0;
		ratings = new HashMap<Integer,Integer>();
		initRatings();
		totalRatings = 0;
	}

	public Product(String name, String id, double price, int stock, Category category)
	{
		this.name = name;
		this.id = id;
		this.price = price;
		this.stockCount = stock;
		this.category = category;
		this.numberOfOrders = 0;
		ratings = new HashMap<Integer,Integer>();
		initRatings();
		totalRatings = 0;
	}
	/*
	 * This method always returns true in class Product. In subclasses, this method will be overridden
	 * and will check to see if the options specified are valid for this product.
	 */
	public boolean validOptions(String productOptions)
	{
		if (productOptions.equals("") || productOptions == null) { 
			return false;
		} 
		return true;
	}

	// setting the amount of ratings 1-5 to 0
	private void initRatings() { 
		for (int i = 1; i < 6; i++) { 
			ratings.put(i, 0);
		}
	}

	// returing the ratings
	public Map<Integer, Integer> getRatings() { 
		return this.ratings;
	}

	// adding a rating to the map by increasing the value of the key
	public void addProductRating(Integer oneToFive) { 
		Integer numbOfRatings;
		if (!ratings.containsKey(oneToFive)) { 
			throw new RuntimeException("invalid rating");
		} else { 
			numbOfRatings = ratings.get(oneToFive);
		}
		ratings.put(oneToFive, numbOfRatings + 1);
		totalRatings += 1;
	}

	// calculating the rating average
	public Double getRatingAverage() { 
		if (totalRatings == 0) { 
			return 0.0;
		}
		
		Double ratingAverage = 0.0;
		for (Integer key : ratings.keySet()) {
			 ratingAverage += ((double) key * (double) ratings.get(key));
		}
		ratingAverage = ratingAverage / totalRatings;
		return ratingAverage;
	}
	
	public Category getCategory()
	{
		return category;
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public void setCategory(Category category) { 
		this.category = category;
	}

	public void setNumberOfOrders(int orderNumbers) { 
		this.numberOfOrders = orderNumbers;
	}

	public int getNumberOfOrders() { 
		return this.numberOfOrders;
	}
	/*
	 * Return the number of items currently in stock for this product
	 * Note: in this general class, the productOptions parameter is not used. It may be used
	 * in subclasses.
	 */
	public int getStockCount(String productOptions)
	{
		return stockCount;
	}
	/*
	 * Set (or replenish) the number of items currently in stock for this product
	 * Note: in this general class, the productOptions parameter is not used. It may be used
	 * in subclasses.
	 */
	public void setStockCount(int stockCount, String productOptions)
	{
		this.stockCount = stockCount;
	}
	/*
	 * Reduce the number of items currently in stock for this product by 1 (called when a product has
	 * been ordered by a customer)
	 * Note: in this general class, the productOptions parameter is not used. It may be used
	 * in subclasses.
	 */
	public void reduceStockCount(String productOIptions)
	{
		stockCount--;
	}
	
	public void print()
	{
		System.out.printf("\nId: %-5s Category: %-9s Name: %-20s Price: %7.1f", id, category, name, price);
	}

	public void printStats()
	{
		System.out.printf("\nName: %-20s Id: %-5s Number Of Orders: %7d", name, id, numberOfOrders);
	}
	
	/*
	 * Two products are equal if they have the same product Id.
	 * This method is inherited from superclass Object and overridden here
	 */
	public boolean equals(Object other)
	{
		Product otherP = (Product) other;
		return this.id.equals(otherP.id);
	}
	
	
}
