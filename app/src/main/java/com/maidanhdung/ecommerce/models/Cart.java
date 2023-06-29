package com.maidanhdung.ecommerce.models;

public class Cart {
    String productName, imageURL;
    int price, Quality;

    public Cart() {}

    public Cart(String productName, String imageURL, int price, int quality) {
        this.productName = productName;
        this.imageURL = imageURL;
        this.price = price;
        Quality = quality;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuality() {
        return Quality;
    }

    public void setQuality(int quality) {
        Quality = quality;
    }
}
