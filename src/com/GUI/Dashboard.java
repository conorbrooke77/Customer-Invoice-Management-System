package com.GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.Database.DatabaseConnector;

public class Dashboard extends JPanel{

    private String headerName;

    public Dashboard(String headerName) {
        this.headerName = headerName;
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(60, 90, 80, 90));
        setBackground(Color.WHITE);

        createDashboardHeader();
        createContent();
    }

    public void createDashboardHeader() {
        
        JLabel header = new JLabel(headerName);
        header.setFont(new Font("Arial", Font.BOLD, 32));
        header.setBorder(new EmptyBorder(0, 0, 50, 0));
        header.setForeground(new Color(82, 82, 82));

        add(header, BorderLayout.NORTH);
    }

    public void createContent() {
        String[][] data = new String[1][1];

        String totalProducts = "";
        String totalInvoices = "";
        String totalCustomers = "";
        String totalSystemUsers = "";
        String totalSales = "";

        try {
            DatabaseConnector.createConnection();

            data = DatabaseConnector.retrieveProduct();

            
            totalProducts = data[data.length-1][0];

            data = DatabaseConnector.retrieveInvoice();

            totalInvoices = data[data.length-1][0];

            data = DatabaseConnector.retrieveCustomers();
         
            totalCustomers = data[data.length-1][0];

            data = DatabaseConnector.retrieveAdmins();

            totalSystemUsers = data[data.length-1][0];

            data = DatabaseConnector.retrieveInvoice();

            float sales = 0;
            for (int i = 0; i < data.length; i++) {
                sales += Float.parseFloat(data[i][3]);
            }

            totalSales = String.valueOf(sales);

        } catch (Exception e) {
        }

        GridLayout layout = new GridLayout(2, 3);
        layout.setHgap(80);
        layout.setVgap(50); 

        JPanel boxContainer = new JPanel(layout);
        boxContainer.setOpaque(false);
        Dashboard_Box box1 = new Dashboard_Box("Products", totalProducts, new Color(129,245, 125));
        boxContainer.add(box1);
        Dashboard_Box box2 = new Dashboard_Box("Invoices", totalInvoices, new Color(247,173, 198));
        boxContainer.add(box2);
        Dashboard_Box box3 = new Dashboard_Box("Customers", totalCustomers, new Color(182,232, 250));
        boxContainer.add(box3);
        Dashboard_Box box4 = new Dashboard_Box("System Users", totalSystemUsers, new Color(255,211, 153));
        boxContainer.add(box4);
        Dashboard_Box box5 = new Dashboard_Box("Sales", totalSales, new Color(230,184, 255));
        boxContainer.add(box5);
        
        add(boxContainer, BorderLayout.CENTER);
    }
}
