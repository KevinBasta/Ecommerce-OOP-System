public class CartItem {
    private Product cartProduct;
    private String cartProductOption; 

    // Initalizing a cartitem with a product and it's options
    public CartItem(Product product, String productOption) { 
        this.cartProduct = product; 
        this.cartProductOption = productOption;
    }

    // Method to return the product id of the cartitem 
    public String getProductId() { 
        return this.cartProduct.getId();
    }

    // Method to get the product option
    public String getProductOption() { 
        return cartProductOption;
    }

    // Method to print the product
    public void printProd() { 
        this.cartProduct.print(); // using the print of the product class
        System.out.printf(" Options:  %8s", this.cartProductOption); // adding another parameter for the full print
    }
}
