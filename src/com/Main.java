package com;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Arrays;

import javax.swing.*;

import com.Driver.DatabaseConnector;
import com.Entities.*;
import com.GUI.*;
import com.Information.Address;
import com.Invoice.*;

public class Main {

    private static Admin[] admins;
    private static Customer[] customers;
    private static Address[] addresses;
    private static Invoice[] invoices;
    private static Product[] Products;


    public static void showOnScreen(int screen, JFrame frame ) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gd = ge.getScreenDevices();
        int width = 0, height = 0;
        if( screen > -1 && screen < gd.length ) {
            width = gd[screen].getDefaultConfiguration().getBounds().width;
            height = gd[screen].getDefaultConfiguration().getBounds().height;
            frame.setLocation(
                ((width / 2) - (frame.getSize().width / 2)) + gd[screen].getDefaultConfiguration().getBounds().x, 
                ((height / 2) - (frame.getSize().height / 2)) + gd[screen].getDefaultConfiguration().getBounds().y
            );
            frame.setVisible(true);
        }
    }


    public static void databaseInitializer() throws SQLException {
        DatabaseConnector.createConnection();
        
        customers = DatabaseConnector.retrieveCustomers();
    }

    public static void main(String[] args) throws SQLException {
    
        databaseInitializer();
        Register reg = new Register();
        showOnScreen(1, reg);

        reg.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                new ManagementSystem();
            }
        });
    }
}
