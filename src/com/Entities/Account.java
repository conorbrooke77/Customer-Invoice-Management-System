package com.Entities;

import java.util.regex.*;

/*  
    Purpose: The purpose of the Account class is to store information related to the Login details of the User.
             The class also includes account verification and recovery.
*/
public class Account {

    private String email;
    private String password;

    public Account(String email, String password) {
        setEmail(email);
        setPassword(password);
    }
    
    /* Initialise Account information */

    //Uses regex to identify a valid email address;
    
    /*** 
     * Validates the email inserted and returns a boolean.
     * @param email
     * @return Uses regex to verify email address
     */
    private boolean emailValidator(String email) {
        Matcher matcher = Pattern.compile("^(.+)@(.+)$").matcher(email);

        if (matcher.matches())
            return true;
        return false;
    }

    /**
     * Set email for Account returns a boolean if email is valid.
     * @param email
     * @return 
     */
    public boolean setEmail(String email) {
        if(emailValidator(email)) {
            this.email = email;
            return true;
        }
        else 
            return false;
    }

    
    /** 
     * Gets Email String.
     * @return String
     */
    public String getEmail() {
        return email;
    }

    
    /** 
     * Sets password.
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    
    /** 
     * Gets Password String.
     * @return String
     */
    public String getPassword() {
        return password;
    }

    
    /** 
     * Changes password if email is authenticated.
     * @param email
     * @param newPassword
     * @return boolean
     */
    /* Change Email or Password */

    public boolean changePassword(String email, String newPassword) {
        if(authenticateEmail(email))
            setPassword(newPassword);
        else    
            return false;

        return true;
    }

    
    /** 
     * Changes username if password is authenticated.
     * @param newUsername
     * @param password
     * @return boolean
     */
    public boolean changeUsername(String newUsername, String password) {
        if(authenticatePassword(password))
            setEmail(newUsername);
        else    
            return false;

        return true;
    }

    
    /** 
     * Authenticates email
     * @param email
     * @return boolean
     */
    // Authentication
    public boolean authenticateEmail(String email) {
        return this.email.equals(email);
    }

    
    /** 
     * Authenticates Password
     * @param password
     * @return boolean
     */
    public boolean authenticatePassword(String password) {
        return this.password.equals(password);
    }

    
    /** 
     * Checks details for login
     * @param email
     * @param password
     * @return boolean
     */
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
