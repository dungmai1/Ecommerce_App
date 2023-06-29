package com.maidanhdung.ecommerce.models;

public class Order {
    String Product, Status, OrderPlace, Address, ImageProduct, PaymentMethod, DeliveryAddress;
    int Price,Quality,Total,NumberProduct;
    public Order() {
    }

    public Order(String product, String status, String orderPlace, String address, String imageProduct, String paymentMethod, String deliveryAddress, int price, int quality, int total, int numberProduct) {
        Product = product;
        Status = status;
        OrderPlace = orderPlace;
        Address = address;
        ImageProduct = imageProduct;
        PaymentMethod = paymentMethod;
        DeliveryAddress = deliveryAddress;
        Price = price;
        Quality = quality;
        Total = total;
        NumberProduct = numberProduct;
    }

    public String getDeliveryAddress() {
        return DeliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        DeliveryAddress = deliveryAddress;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public int getNumberProduct() {
        return NumberProduct;
    }

    public void setNumberProduct(int numberProduct) {
        NumberProduct = numberProduct;
    }
    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getOrderPlace() {
        return OrderPlace;
    }

    public void setOrderPlace(String orderPlace) {
        OrderPlace = orderPlace;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getImageProduct() {
        return ImageProduct;
    }

    public void setImageProduct(String imageProduct) {
        ImageProduct = imageProduct;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getQuality() {
        return Quality;
    }

    public void setQuality(int quality) {
        Quality = quality;
    }

}
