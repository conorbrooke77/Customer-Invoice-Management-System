package com.Driver;

import java.sql.*;
import java.util.ArrayList;

import com.Entities.*;
import com.Information.Address;

public class DatabaseConnector {

    private static final String USER = "root";
    private static final String PASSWORD = "1108cb";
    private static final String URL = "jdbc:mysql://localhost:3306/Customer-Invoice-System";
    private static final String DRIVER_CONNECTION = "com.mysql.cj.jdbc.Driver";

    private static Connection con;

    public static void createConnection() throws SQLException {
        try {

            Class.forName(DRIVER_CONNECTION); //Load the JDBC Driver to connect to the MYSQL server;
            con = DriverManager.getConnection(URL, USER, PASSWORD); // Details needed to connect to my server on my local machine;

        } catch (ClassNotFoundException e) {
        } 
    }

    public static void insertCustomer(Customer customer) throws SQLException {
        
        PreparedStatement statement = con.prepareStatement("INSERT INTO Customer (firstName, lastName, email, password, phoneNumber, addressId) VALUES ( ?, ?, ?, ?, ?, ?)");

        statement.setString(1, customer.getFirstName());
        statement.setString(2, customer.getLastName());
        statement.setString(3, customer.getAccount().getEmail());
        statement.setString(4, customer.getAccount().getPassword());
        statement.setString(5, customer.getPhoneNumber());
        statement.setInt(6, customer.getCustomerAddress().getAddressId());
        
        int insert = statement.executeUpdate();
        System.out.println(insert + " customer successfully added to the table");
    } 
    
    public static void insertAdmin(Admin admin) throws SQLException {
        
        PreparedStatement statement = con.prepareStatement("INSERT INTO Admin (firstName, lastName, email, password, phoneNumber) VALUES ( ?, ?, ?, ?, ?)");

        statement.setString(1, admin.getFirstName());
        statement.setString(2, admin.getLastName());
        statement.setString(3, admin.getAccount().getEmail());
        statement.setString(4, admin.getAccount().getPassword());
        statement.setString(5, admin.getPhoneNumber());
        
        int insert = statement.executeUpdate();
        System.out.println(insert + " record successfully added to the table");
    }

    public static void insertAddress(Address address) throws SQLException {
        
        PreparedStatement statement = con.prepareStatement("INSERT INTO Address (addressId, street, town, county, eircode) VALUES ( ?, ?, ?, ?, ?)");

        statement.setInt(1, address.getAddressId());
        statement.setString(2, address.getStreet());
        statement.setString(3, address.getTown());
        statement.setString(4, address.getCounty());
        statement.setString(5, address.getEircode());
        
        int insert = statement.executeUpdate();
        System.out.println(insert + " address successfully added to the table");
    }

    public static ArrayList<Address> retrieveAddresses() throws  SQLException {
        Statement statement = con.createStatement();

        ArrayList<Address> collection = new ArrayList<Address>();

        ResultSet set = statement.executeQuery("SELECT * FROM Address");

        while (set.next()) {
            collection.add(new Address(set.getString("street"), set.getString("town"), set.getString("county"), set.getString("eircode")));
        }

        return collection;
    }

    public static ArrayList<Customer> retrieveCustomers(ArrayList<Address> addresses) throws SQLException {
        Statement statement = con.createStatement();

        ArrayList<Customer> collection = new ArrayList<Customer>();

        ResultSet set = statement.executeQuery("SELECT * FROM Customer");

        int i = 0;
        while (set.next()) {
            collection.add(new Customer(set.getString("firstName"), set.getString("lastName"), set.getString("email"), set.getString("password"), set.getString("phoneNumber")));

            if(addresses.get(i).getAddressId() == set.getInt("addressID"))  {
                collection.get(i).addCustomerAddress((addresses.get(i)));
            }

            i++;
        }

        return collection;
    }

    public static ArrayList<Admin> retrieveAdmins() throws  SQLException {
        Statement statement = con.createStatement();

        ArrayList<Admin> collection = new ArrayList<Admin>();

        ResultSet set = statement.executeQuery("SELECT * FROM Admin");

        while (set.next()) {
            collection.add(new Admin(set.getString("firstName"), set.getString("lastName"), set.getString("email"), set.getString("phoneNumber"), set.getString("password")));
        }

        return collection;
    }
}
