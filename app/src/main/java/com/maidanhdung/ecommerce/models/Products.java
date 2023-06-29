package com.maidanhdung.ecommerce.models;

import java.io.Serializable;

public class Products implements Serializable {
    String ProductName, ImageProduct, Description, ImageDetail1,ImageDetail2,ImageDetail3,ImageDetail4;
    int Price;
    public Products() {
    }

    public Products(String productName, String imageProduct, String description, String imageDetail1, String imageDetail2, String imageDetail3, String imageDetail4, int price) {
        ProductName = productName;
        ImageProduct = imageProduct;
        Description = description;
        ImageDetail1 = imageDetail1;
        ImageDetail2 = imageDetail2;
        ImageDetail3 = imageDetail3;
        ImageDetail4 = imageDetail4;
        Price = price;
    }
    public String getImageDetail1() {
        return ImageDetail1;
    }

    public void setImageDetail1(String imageDetail1) {
        ImageDetail1 = imageDetail1;
    }

    public String getImageDetail2() {
        return ImageDetail2;
    }

    public void setImageDetail2(String imageDetail2) {
        ImageDetail2 = imageDetail2;
    }

    public String getImageDetail3() {
        return ImageDetail3;
    }

    public void setImageDetail3(String imageDetail3) {
        ImageDetail3 = imageDetail3;
    }

    public String getImageDetail4() {
        return ImageDetail4;
    }

    public void setImageDetail4(String imageDetail4) {
        ImageDetail4 = imageDetail4;
    }
    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getImageProduct() {
        return ImageProduct;
    }

    public void setImageProduct(String imageProduct) {
        ImageProduct = imageProduct;
    }


}
