package com.maidanhdung.ecommerce.models;

public class Category {
    String ImageCategory;

    public Category(String imageCategory) {
        ImageCategory = imageCategory;
    }

    public String getImageCategory() {
        return ImageCategory;
    }

    public void setImageCategory(String imageCategory) {
        ImageCategory = imageCategory;
    }
}
