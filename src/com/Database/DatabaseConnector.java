package com.Database;

import java.sql.*;
import java.util.ArrayList;

import com.Entities.*;
import com.Information.Address;
import com.Information.LoginSessionData;
import com.Invoice_Content.Invoice;
import com.Invoice_Content.Product;

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
        
        int addressId = 0;

        Statement query = con.createStatement();

        ResultSet set = query.executeQuery("SELECT addressId FROM Address WHERE addressId=(SELECT MAX(addressId) FROM Address)");

        if (set.next()) {
            addressId = set.getInt("addressId") + 1;
            System.out.println(addressId);
        }
        PreparedStatement statement = con.prepareStatement("INSERT INTO Customer (firstName, lastName, email, password, phoneNumber, addressId) VALUES ( ?, ?, ?, ?, ?, ?)");

        statement.setString(1, customer.getFirstName());
        statement.setString(2, customer.getLastName());
        statement.setString(3, customer.getAccount().getEmail());
        statement.setString(4, customer.getAccount().getPassword());
        statement.setString(5, customer.getPhoneNumber());
        statement.setInt(6, addressId);
        
        int insert = statement.executeUpdate();
        System.out.println(insert + " customer successfully added to the table");
    } 
    
    public static void deleteCustomer(int id) throws SQLException {
        
        Statement stmt = con.createStatement();
        String statement = "DELETE FROM Customer WHERE customerID=" + id;

        stmt.executeUpdate(statement);

    }

    public static void updateCustomer(String[] data) throws SQLException {
        
        
        Statement stmt = con.createStatement();
        
        String statement = "Update Customer Set customerID='" + Integer.parseInt(data[0]) + "', firstName='" + data[1] + "', lastName='" + data[2] + "', email='" + data[3] + "', password='" + data[4] + "', phoneNumber='" + data[5] +"' WHERE customerID=" + Integer.parseInt(data[0]);

        stmt.executeUpdate(statement);

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

    public static void deleteAdmin(int id) throws SQLException {
        
        Statement stmt = con.createStatement();
        String statement = "DELETE FROM Admin WHERE idAdmin=" + id;

        stmt.executeUpdate(statement);

    }

    public static void updateAdmin(String[] data) throws SQLException {
        
        
        Statement stmt = con.createStatement();
        
        String statement = "Update Admin Set idAdmin='" + Integer.parseInt(data[0]) + "', firstName='" + data[1] + "', lastName='" + data[2] + "', email='" + data[3] + "', phoneNumber='" + data[4] + "', password='" + data[5] +"' WHERE idAdmin=" + Integer.parseInt(data[0]);

        stmt.executeUpdate(statement);

    }

    public static void insertProduct(Product product) throws SQLException {
        
        PreparedStatement statement = con.prepareStatement("INSERT INTO Product (product_Name, product_Price, product_Desc, product_Brand, product_Quantity, product_Rating, product_Weight) VALUES ( ?, ?, ?, ?, ?, ?, ?)");

        statement.setString(1, product.getName());
        statement.setFloat(2, product.getProductPrice());
        statement.setString(3, product.getDecription());
        statement.setString(4, product.getBrand());
        statement.setInt(5, product.getQuantity());
        statement.setInt(6, product.getRating());
        statement.setFloat(7, product.getWeight());
        
        int insert = statement.executeUpdate();
        System.out.println(insert + " record successfully added to the table");
    }

    public static void insertInvoice(Invoice invoice) throws SQLException {
        
        PreparedStatement statement = con.prepareStatement("INSERT INTO Invoice (customer_Name, noOfProducts, total, paid) VALUES (?, ?, ?, ?)");

        statement.setString(1, invoice.getCustomer().getFirstName() + " " +  invoice.getCustomer().getLastName());
        statement.setInt(2, invoice.getNoOfProductsInInvoice());
        statement.setFloat(3, invoice.getSubTotal());
        statement.setInt(4, 0);
        
        int insert = statement.executeUpdate();
        System.out.println(insert + " record successfully added to the table");
    }

    public static String[][] retrieveInvoice() throws SQLException {
        
        int maxId = 0;
        
        Statement statement = con.createStatement();

        ResultSet id = statement.executeQuery("SELECT invoiceId FROM Invoice WHERE invoiceId=(SELECT MAX(invoiceId) FROM Invoice)");

        if (id.next()) {
            maxId = id.getInt("invoiceId");
        }

        String[][] collection = new String[maxId][8];

        ResultSet set = statement.executeQuery("SELECT * FROM Invoice WHERE paid=0");

        int i = 0;

        while (set.next()) {

            String[] row = {String.valueOf(set.getInt("invoiceId")),set.getString("customer_Name"), String.valueOf(set.getInt("noOfProducts")), String.valueOf(set.getFloat("total"))};

            collection[i] = row;
            i++;
        }

        return collection;
    }

    public static void updateInvoice(int id) throws SQLException {
        
        
        Statement stmt = con.createStatement();
        
        String statement = "Update Invoice Set paid='"+ 1 + "' WHERE invoiceId="+id;

        stmt.executeUpdate(statement);

    }

    public static String[][] retrieveProduct() throws SQLException {
        
        int maxId = 0;
        
        Statement statement = con.createStatement();

        ResultSet id = statement.executeQuery("SELECT product_Id FROM Product WHERE product_Id=(SELECT MAX(product_Id) FROM Product)");

        if (id.next()) {
            maxId = id.getInt("product_Id");
        }

        String[][] collection = new String[maxId][8];

        ResultSet set = statement.executeQuery("SELECT * FROM Product");

        int i = 0;

        while (set.next()) {

            String[] row = {String.valueOf(set.getInt("product_Id")),set.getString("product_Name"), String.valueOf(set.getFloat("product_Price")), set.getString("product_Desc"), set.getString("product_Brand"), String.valueOf(set.getInt("product_Quantity")), String.valueOf(set.getInt("product_Rating")), String.valueOf(set.getFloat("product_Weight"))};

            collection[i] = row;
            i++;
        }

        return collection;
    }

    public static void deleteProduct(int id) throws SQLException {
        
        Statement stmt = con.createStatement();
        String statement = "DELETE FROM Product WHERE product_Id=" + id;

        stmt.executeUpdate(statement);

    }

    public static void updateProduct(String[] data) throws SQLException {
        
        
        Statement stmt = con.createStatement();
        
        String statement = "Update Product Set product_Id='" + Integer.parseInt(data[0]) + "', product_Name='" + data[1] + "', product_Price='" + Float.parseFloat(data[2]) + "', product_Desc='" + data[3] + "', product_Brand='" + data[4] + "', product_Quantity='" + Integer.parseInt(data[5]) + "', product_Rating='" + Integer.parseInt(data[6]) + "', product_Weight='" + Float.parseFloat(data[7]) + "' WHERE product_Id=" + Integer.parseInt(data[0]);

        stmt.executeUpdate(statement);

    }

    public static void insertAddress(Address address) throws SQLException {
        
        Statement query = con.createStatement();

        ResultSet set = query.executeQuery("SELECT addressId FROM Customer WHERE customerID=(SELECT MAX(customerID) FROM Customer)");
        int addressId = 0;
        if (set.next()) {
            addressId = set.getInt("addressId");
            System.out.println(addressId);
        }

        PreparedStatement statement = con.prepareStatement("INSERT INTO Address (addressId, street, town, county, eircode) VALUES ( ?, ?, ?, ?, ?)");

        statement.setInt(1, addressId);
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

    public static String[][] retrieveCustomers() throws SQLException {
        Statement statement = con.createStatement();

        int maxId = 0;
        ResultSet id = statement.executeQuery("SELECT customerID FROM Customer WHERE customerID=(SELECT MAX(customerID) FROM Customer)");

        if (id.next()) {
            maxId = id.getInt("customerID");
        }

        String[][] data = new String[maxId][6];

        ResultSet set = statement.executeQuery("SELECT * FROM Customer");

        int i = 0;

        while (set.next()) {
            String[] row = {String.valueOf(set.getInt("customerID")), set.getString("firstName"), set.getString("lastName"), set.getString("email"), set.getString("password"), set.getString("phoneNumber")};
                
            data[i] = row;

            i++;
        }

        return data;
    }

    public static String[][] retrieveAdmins() throws  SQLException {
        Statement statement = con.createStatement();

        int maxId = 0;
        ResultSet id = statement.executeQuery("SELECT idAdmin FROM Admin WHERE idAdmin=(SELECT MAX(idAdmin) FROM Admin)");

        if (id.next()) {
            maxId = id.getInt("idAdmin");
        }

        String[][] data = new String[maxId][6];


        ResultSet set = statement.executeQuery("SELECT * FROM Admin");
        int i = 0;
        while (set.next()) {
            String[] row = {String.valueOf(set.getInt("idAdmin")),set.getString("firstName"), set.getString("lastName"), set.getString("email"), set.getString("phoneNumber"), set.getString("password")};
            
            data[i] = row;

            i++;
        }

        return data;
    }

    public static boolean retrieveLoginData(String email, String password) throws  SQLException {
        Statement statement = con.createStatement();

        ResultSet admin = statement.executeQuery("SELECT * FROM Admin WHERE email='" + email+ "' AND password='"+password+"'");
        
        if (admin.next()) {
            LoginSessionData.createAdmin(admin.getString("firstName"), admin.getString("lastName"), admin.getString("email"), admin.getString("phoneNumber"), admin.getString("password"));
            return true;
        } else {
            ResultSet customer = statement.executeQuery("SELECT * FROM Customer WHERE email='" + email+ "' AND password='"+password+"'");
            if (customer.next()) 
                LoginSessionData.createCustomer(customer.getString("firstName"), customer.getString("lastName"), customer.getString("email"), customer.getString("phoneNumber"), customer.getString("password"), customer.getInt("addressId"));
            else
                return false;

            return true;
        }
    }
}
