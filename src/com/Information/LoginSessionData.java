package com.Information;

import com.Entities.*;

/*

    Class Purpose: The purpose of this class is to store information about the currently logged in User. The
                   class also creates the Java Objects needed for the user to function within the System.

*/
public class LoginSessionData {
    
    private static boolean isAdmin;
    private static Customer customer;
    private static Admin admin;
    private static int addressID;

    public static void createCustomer(String firstName, String lastName, String email, String password, String phoneNumber, int cusAddress) {
        isAdmin = false;
        addressID = cusAddress;

        customer = new Customer(firstName, lastName, email, password, phoneNumber);
    }

    public static void createAdmin(String firstName, String lastName, String email, String password, String phoneNumber) {
        isAdmin = true;
        admin = new Admin(firstName, lastName, email, password, phoneNumber);
    }

    public static Customer getCustomer() {
        return customer;
    }

    public static boolean getIsAdmin() {
        return isAdmin;
    }

    public static int getCustomerAddress() {
        return addressID;
    }

    public static Admin getAdmin() {
        return admin;
    }

}
