package com.Entities;

public class Admin extends User {

/*  
     Purpose: The Admin class creates a Admin user that has access to all areas within the system.
*/

    public Admin() {

    }
    
    public Admin(String firstName, String lastName, String email, String phoneNumber, String password) {
        super(firstName, lastName, email, password, phoneNumber);
    }

}
