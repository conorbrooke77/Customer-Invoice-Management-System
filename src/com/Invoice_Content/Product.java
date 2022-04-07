package com.Invoice_Content;

public class Product {
    
    private String name;
    private float price;
    private String decription;
    private String brand;
    private int quantity;
    private int rating;
    private float weight;

    public Product() {

    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float f) {
        this.weight = f;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public void setProductName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setProductPrice(float f) {
        this.price = f;
    }

    public float getProductPrice() {
        return price;
    }

    public String toString() {
        return "\nProduct name: " + name + "\nProduct Price" + price;
    }
}
