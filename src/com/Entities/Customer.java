package com.Entities;

import com.Information.*;

/*  
    Purpose: The customer class has access to some areas within the system it also includes an Address class.
*/

// Each customer is part of the CustomerManager.
//Each customer is connected to the Database interface.

public class Customer extends User {

    private Address address;// Address set to customer (basic aggregation)
    
    public Customer(String firstName, String lastName, String email, String password, String phoneNumber) {
        super(firstName, lastName, email, password, phoneNumber);
    }

    public Customer() {
        
    }
    // Getters and Setters for the Customer class;

    public void addCustomerAddress(Address address) {
        this.address = address;
    }

    public Address getCustomerAddress() {
        return address;
    }

    public String toString() {
        return super.toString() + "Address: " + address;
    }
}
