import java.util.ArrayList;

public class Cart {
    // Array list that holds CartItem objects
    private ArrayList<CartItem> itemsInCart = new ArrayList<CartItem>();

    // Method to return the items in the cart arraylist
    public ArrayList<CartItem> getCartItems() { 
        return itemsInCart; 
    }

    // Method to delete everything in the cart
    public void clearCart() { 
        itemsInCart.clear();
    }

    // Method to add a product with it's options to the cart
    public void addCartItem(Product newCartProduct, String productOption) { 
        CartItem newCartItem = new CartItem(newCartProduct, productOption);
        this.itemsInCart.add(newCartItem);
    }

    // Method to remove a product from the cart arraylist
    public void removeCartItem(String productId) { 
        // getting id of the product inside cartitem and checking
        // if it's the same the same as the product id given
        for (CartItem prod : itemsInCart) { 
            if (prod.getProductId().equals(productId)) { 
                itemsInCart.remove(prod);
                break;
            }
        }
    }

}
