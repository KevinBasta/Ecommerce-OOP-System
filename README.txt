Bonus Guide: 
{ECommerceUserInterface has three actions for the Bonus}
addRating - takes inputs of product id, customer id, and the rating and calls a method
printRatings - takes product id and calls a method
printProdsCategoryRating - takes category name and rating threshold and calls a method

{the methods these actions call are located in ECommerceSystem}
addProductRating - validates the product and customer ids and calls a method
printProductRating - validates product and loads its ratings into a map and prints them
printProdsCategoryWithRating - validates category and prints items of the category above the threshold

{these methods rely on things in the Product class which are}
[variables]
totalRatings - an integer incremented every time a rating is added
ratings - a map that holds all the ratings

[Mehods]
initRatings - adds the keys 1-5 in the ratings map with value of 0 
getRatings - returns the ratings map
addProductRating - adds a rating to the ratings map by incrementing the value of the key
getRatingAverage - returns the average rating of the product which is used for printProdsCategoryWithRating

