package com.Entities;

public class Admin extends User {

    public Admin() {

    }
    
    public Admin(String firstName, String lastName, String email, String phoneNumber, String password) {
        super(firstName, lastName, email, password, phoneNumber);
    }

}
