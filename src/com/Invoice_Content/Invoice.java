package com.Invoice_Content;
import com.Entities.Customer;

public class Invoice {

    private float subTotal;
    private Customer customer;
    private int noOfProductsInInvoice;

    public void addCustomerToInvoice(Customer customer) {
        this.customer = customer;
    }
    

    public Customer getCustomer() {
        return customer;
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

    public void setNoOfProductsInInvoice(int noOfProductsInInvoice) {
        this.noOfProductsInInvoice = noOfProductsInInvoice;
    }
}
