package com.Information;

import com.Entities.*;

public class LoginSessionData {
    
    private static Customer customer;
    private static Admin admin;
    private static int addressID;

    public static void createCustomer(String firstName, String lastName, String email, String password, String phoneNumber, int cusAddress) {
        addressID = cusAddress;

        customer = new Customer(firstName, lastName, email, password, phoneNumber);
    }

    public static void createAdmin(String firstName, String lastName, String email, String password, String phoneNumber) {
        admin = new Admin(firstName, lastName, email, password, phoneNumber);
    }

    public static Customer getCustomer() {
        return customer;
    }

    public static int getCustomerAddress() {
        return addressID;
    }

    public static Admin getAdmin() {
        return admin;
    }

}
