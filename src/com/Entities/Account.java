package com.Entities;

import java.util.regex.*;

public class Account {

    //private static int accountID;

    private String email;
    private String password;

    public Account(String email, String password) {
        setEmail(email);
        setPassword(password);
    }
    
    /* Initialise Account information */

    //Uses regex to identify a valid email address;
    private boolean emailValidator(String email) {
        Matcher matcher = Pattern.compile("^(.+)@(.+)$").matcher(email);

        if (matcher.matches())
            return true;
        return false;
    }

    public boolean setEmail(String email) {
        if(emailValidator(email)) {
            this.email = email;
            return true;
        }
        else 
            return false;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    /* Change Email or Password */

    public boolean changePassword(String email, String newPassword) {
        if(authenticateEmail(email))
            setPassword(newPassword);
        else    
            return false;

        return true;
    }

    public boolean changeUsername(String newUsername, String password) {
        if(authenticatePassword(password))
            setEmail(newUsername);
        else    
            return false;

        return true;
    }

    // Authentication
    public boolean authenticateEmail(String email) {
        return this.email.equals(email);
    }

    public boolean authenticatePassword(String password) {
        return this.password.equals(password);
    }

    public boolean isDetailsValid(String email, String password) {
        if (authenticatePassword(email) && authenticatePassword(password))
            return true;
        else
            return false;
    }
    
    public String toString() {
        return "\nEmail: " + email + "\nPassword: " + password + "\n\n" ;
    }

}
