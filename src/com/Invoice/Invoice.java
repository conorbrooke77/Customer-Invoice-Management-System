package com.Invoice;
import com.Entities.Customer;

public class Invoice implements InvoiceCreator{

    private float subTotal;
    private Customer customer;
    private Product[] products = new Product[10];
    private int noOfProductsInInvoice;

    public void addCustomerToInvoice(Customer customer) {
        this.customer = customer;
    }
    

    public void addProductToInvoice(Product product) {
        try {
            products[noOfProductsInInvoice] = product;
            noOfProductsInInvoice++;
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }

    public Customer getCustomer() {
        return customer;
    }

    public Product[] getProducts() {
        return products;
    }

    public float getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(float subTotal) {
        this.subTotal = subTotal;
    }

    public void recivePayment(float amount) {
        Boolean isvalid = subTotal - amount >= 0 ;
        
        if (isvalid)
            subTotal -= amount;
    }

    public int getNoOfProductsInInvoice() {
        return noOfProductsInInvoice;
    }
}
