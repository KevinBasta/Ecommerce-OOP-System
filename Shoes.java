public class Shoes extends Product{
    
    // each shoe has two colour options (black and brown) and the size option for each colour that is (6, 7, 8, 9, 10)
    // two 2d arrays, one for each colour. Format is {{"size", "stockNumber"}}
    private int[][] blackSizes = {{6, 0}, {7, 0}, {8, 0}, {9, 0}, {10, 0}};
    private int[][] brownSizes = {{6, 0}, {7, 0}, {8, 0}, {9, 0}, {10, 0}};

    

    public Shoes(String name, String id, double price, int[] blackSizesArr, int[] brownSizesArr) {
        super(name, id, price, 0, Product.Category.SHOES);
        // the system doesn't pass the size number i.e. 6, 7, 8, 9, 10. They just pass the stock of each size in order
        // calling helper to set the stock
        initStock(blackSizesArr, "Black");
        initStock(brownSizesArr, "Brown");
    }

    // Method Initializes the stock arrays with the system input from the arrays
    private void initStock(int[] stockArr, String colour) {
        if (colour.equalsIgnoreCase("Black")) {
            for(int i = 0; i < this.blackSizes.length; i++) { 
                blackSizes[i][1] = stockArr[i];
            }
        } else if (colour.equalsIgnoreCase("Brown")) {
            for(int i = 0; i < this.brownSizes.length; i++) { 
                brownSizes[i][1] = stockArr[i];
            }
        }
    }

    // checking for valid colour and valid size
    // since the method in ecommercesystem has only one parameter I am using substrings to 
    // extract both the colour and size from a string
    public boolean validOptions(String productOptions) {
        // need to first check if the length of the options
        // is less than 6 because 6 characters is the minimum 
        // ammount of chars for the options string passed
        if (productOptions.length() < 6) { 
            return false;
        }
        String colour = productOptions.substring(0, 5);
        
        // this check is needed in the shoe class in the case that
        // a shoe is attempted to be ordered as a book
        char sizeCheck = (char) productOptions.charAt(productOptions.length() - 1);
        if (!Character.isDigit(sizeCheck)) {
            return false;
        }

        int size = Integer.parseInt(productOptions.substring(5, productOptions.length()));
        if ((colour.equalsIgnoreCase("Black") || colour.equalsIgnoreCase("Brown")) && size >= 6 && size <= 10) {
            return true;
        }
        return false;
    }
    
    // returning the stock count from the appropriate colour array with the correct size
    public int getStockCount(String productOptions) {
        String shoeColour = productOptions.substring(0, 5);
        int shoeSizeOption = Integer.parseInt(productOptions.substring(5, productOptions.length()));

        if (shoeColour.equalsIgnoreCase("Black")) {
            for (int i = 0; i < blackSizes.length; i++) {
                if (blackSizes[i][0] == shoeSizeOption) {
                    return blackSizes[i][1];
                }
            }
        } else if (shoeColour.equalsIgnoreCase("Brown")) {
            for (int i = 0; i < brownSizes.length; i++) {
                if (brownSizes[i][0] == shoeSizeOption) {
                    return brownSizes[i][1];
                }
            }
        }
        return 0;
    }


    // changing the stock count of a certain colour and size
    public void setStockCount(int stockCount, String productOptions) {
        String shoeColour = productOptions.substring(0, 5);
        int shoeSizeOption = Integer.parseInt(productOptions.substring(5, productOptions.length()));

        if (shoeColour.equalsIgnoreCase("Black")) {
            for (int i = 0; i < blackSizes.length; i++) {
                if (blackSizes[i][0] == shoeSizeOption) {
                    blackSizes[i][1] = stockCount;
                }
            }
        } else if (shoeColour.equalsIgnoreCase("Brown")) {
            for (int i = 0; i < brownSizes.length; i++) {
                if (brownSizes[i][0] == shoeSizeOption) {
                    brownSizes[i][1] = stockCount;
                }
            }
        }
    }


    // taking away from the stock count
    public void reduceStockCount(String productOptions) {
        String shoeColour = productOptions.substring(0, 5);
        int shoeSizeOption = Integer.parseInt(productOptions.substring(5, productOptions.length()));

        if (shoeColour.equalsIgnoreCase("Black")) {
            for (int i = 0; i < blackSizes.length; i++) {
                if (blackSizes[i][0] == shoeSizeOption) {
                    blackSizes[i][1] -= 1;
                }
            }
        } else if (shoeColour.equalsIgnoreCase("Brown")) {
            for (int i = 0; i < brownSizes.length; i++) {
                if (brownSizes[i][0] == shoeSizeOption) {
                    brownSizes[i][1] -= 1;
                }
            }
        }
    }

    
    public void print()
    {
      super.print();
      //System.out.printf("  colour: %-10s size: %-1s", this.colour, this.size);
    }
}
