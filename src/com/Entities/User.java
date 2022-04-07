package com.Entities;

import java.util.regex.*;

/*  
    Purpose: The User class is the super class to all entities within the system.
*/

public abstract class User {

    private String firstName;
    private String lastName;
    private String phoneNumber;

    private Account account;

    // This is the super class of Admin and Customer (the two entities in the system);
    public User() {
        createAccount("","");
    }

    public User(String firstName, String lastName, String email, String password, String phoneNumber) {

        setFirstName(firstName);
        setLastName(lastName);
        setPhoneNumber(phoneNumber);

        createAccount(email, password); // Creates a User account object;
    }

    /* Sets the basic information for a User in the system */

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean validatePhonenumber(String phoneNumber) {
        Matcher matcher = Pattern.compile("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}").matcher(phoneNumber);

        if (matcher.matches())
            return true;
        return false;
    }

    public boolean setPhoneNumber(String phoneNumber) {
        if (validatePhonenumber(phoneNumber)) {
            this.phoneNumber = phoneNumber;
            return true;
        }
        return false;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    /* The creation and validation of the Account class which has a composite relationship with each User */

    private void createAccount(String email, String password) { // Users can't create multiple accounts hence private;
        account = new Account(email, password);

    }

    public Account getAccount() {
        return account;
    }

    public String toString() {
        return "\nFirstname: " + firstName + "\nLastname: " + lastName + "\nPhone Number: " + phoneNumber + "\nAccount Details: " + account.toString();
    }
}
