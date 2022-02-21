package com.Invoice;

public class Product {
    
    private String name;
    private int price;
    /*
    private String decription;
    private String brand;
    private int quantity;
    private int rating;
    private int weight;
    
    */
    public Product(String name, int price) {
        setProductName(name);
    }

    public void setProductName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setProductPrice(int price) {
        this.price = price;
    }

    public String toString() {
        return "\nProduct name: " + name + "\nProduct Price" + price;
    }
}
