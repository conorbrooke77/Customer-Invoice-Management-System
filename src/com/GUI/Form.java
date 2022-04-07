package com.GUI;

import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.Database.DatabaseConnector;
import com.Entities.*;
import com.Information.Address;
import com.Information.LoginSessionData;
import com.Invoice_Content.Invoice;
import com.Invoice_Content.Product;

public class Form extends JPanel {

    private User user;
    private int newTotal;
    private Address address;
    private JTextField[] fieldsArr;
    private String formType;
    private JLabel formHeader, total;
    private JButton createProductBtn;
    private JPanel form, fieldsContainer, inputContainer, btnContainer, invoiceContentContainer;
    private Product product;
    private String[] inputs;
    private String[][] data;
    private ArrayList<Float> productTotal;
    
    public Form(String name, String[] inputs) {

        this.formType = name;
        this.inputs = inputs;

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(80, 120, 50, 50));
        setBackground(Color.WHITE);
        addHeader();

        if (name != "Invoice") {
            addForm();
        } else {
            createInvoice();
        }
    }

    public void addHeader() {
        formHeader = new JLabel( formType + " Form");
        formHeader.setFont(new Font("Arial", Font.BOLD, 32));
        formHeader.setForeground(new Color(82, 82, 82));

        add(formHeader, BorderLayout.NORTH);
    }

    public void addForm() {

        form = new JPanel(new BorderLayout());
        form.setBackground(Color.white);

        //Fields
        fieldsContainer = new JPanel();
        fieldsContainer.setLayout(new BoxLayout(fieldsContainer, BoxLayout.Y_AXIS));
        fieldsContainer.setOpaque(false);
        fieldsContainer.setBorder(new EmptyBorder(30,0,70,0));
        //fieldsContainer.setBorder(BorderFactory.createLineBorder(Color.black));

        
        fieldsArr = new JTextField[inputs.length];

        inputContainer = new JPanel();
        inputContainer.setLayout(new BoxLayout(inputContainer, BoxLayout.Y_AXIS));
        inputContainer.setPreferredSize(new Dimension(400,700));

        if (formType == "Customer") {
            inputContainer.setPreferredSize(new Dimension(400,950));
        }

        inputContainer.setBorder(new EmptyBorder(0,0,0,900));
        inputContainer.setOpaque(false);

        for (int fields = 0; fields < inputs.length; fields++) {
            //Sections
            if (fields == 0) {
                JLabel sectionTags = new JLabel("To create a " + formType + " fill in the form below.", SwingConstants.LEFT);
                sectionTags.setFont(new Font("Arial", Font.PLAIN, 18));
                sectionTags.setBorder(new EmptyBorder(0,0,70,0));
                sectionTags.setForeground(new Color(63, 61, 61));
                inputContainer.add(sectionTags);
            }
          
            JTextField textField = new JTextField(inputs[fields],35);
            textField.setBackground(new Color(239, 239, 239));
            textField.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
            textField.setForeground(new Color(147, 147, 147));
            textField.setFont(new Font("Arial", Font.PLAIN, 17));

            final int final_i = fields;
            textField.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (textField.getText().equals(inputs[final_i])) {
                        textField.setText("");
                    }
                }
                @Override
                public void focusLost(FocusEvent e) {
                    if (textField.getText().equals("")) {
                        textField.setText(inputs[final_i]);
                    }
                }
                });
            inputContainer.add(textField);

            fieldsArr[fields] = textField;

            inputContainer.add(Box.createVerticalStrut(30));

            fieldsContainer.add(inputContainer);
        }


        btnContainer = new JPanel(new BorderLayout());
        btnContainer.setOpaque(false);


        createProductBtn = new JButton("Create " + formType);
        createProductBtn.setBackground(new Color(86, 152, 212));
        createProductBtn.setAlignmentX(SwingConstants.LEFT);
        createProductBtn.setPreferredSize(new Dimension(165, 70));
        createProductBtn.setFont(new Font("Arial", Font.BOLD, 17));
        createProductBtn.setForeground(Color.white);

        createProductBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                if (formType=="System User" || formType=="Customer") {
                    register();
                } else {
                    createProduct();
                }
            }
        });
        btnContainer.add(createProductBtn, BorderLayout.WEST);

        form.add(fieldsContainer, BorderLayout.CENTER);
        form.add(btnContainer, BorderLayout.SOUTH);

        add(form, BorderLayout.SOUTH);
    }

    public boolean createProduct() {
        product = new Product();
        try {  
                product.setProductPrice(Float.parseFloat(fieldsArr[1].getText()));
                product.setQuantity(Integer.parseInt(fieldsArr[4].getText()));
                product.setRating(Integer.parseInt(fieldsArr[5].getText()));
                product.setWeight(Float.parseFloat(fieldsArr[6].getText()));

          } catch(NumberFormatException e) {  
            JOptionPane.showMessageDialog(null, "Number inputs aren't valid");
            return false;  
        }  

        
        product.setProductName(fieldsArr[0].getText());
        product.setDecription(fieldsArr[2].getText());
        product.setBrand(fieldsArr[3].getText());

        try {
            DatabaseConnector.createConnection();

            DatabaseConnector.insertProduct(product);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public boolean register() {

        if (formType=="System User")
            user = new Admin();
        else
            user = new Customer();

        if (!fieldsArr[3].getText().equals(fieldsArr[4].getText())) {
            JOptionPane.showMessageDialog(null, "Passwords don't match!");
            
            fieldsArr[3].setBorder(new LineBorder(Color.RED, 2));
            fieldsArr[4].setBorder(new LineBorder(Color.RED, 2));

            return false;
        }

        if (!user.getAccount().setEmail(fieldsArr[2].getText())) {
            JOptionPane.showMessageDialog(null, "Email isn't valid");
            
            fieldsArr[2].setBorder(new LineBorder(Color.RED, 2));

            return false;
        }

        if (!user.setPhoneNumber(fieldsArr[5].getText())) {
            JOptionPane.showMessageDialog(null, "Phone Number isn't valid");
            
            fieldsArr[5].setBorder(new LineBorder(Color.RED, 2));

            return false;
        }

        user.setFirstName(fieldsArr[0].getText());
        user.setLastName(fieldsArr[1].getText());
        user.getAccount().setPassword(fieldsArr[4].getText());

        try {
            DatabaseConnector.createConnection();
            if (formType=="System User") {
                DatabaseConnector.insertAdmin((Admin) user);
            }
            else {
                address = new Address();
                address.setStreet(fieldsArr[6].getText());
                address.setTown(fieldsArr[7].getText());
                address.setCounty(fieldsArr[8].getText());
                address.setEircode(fieldsArr[9].getText());

                ((Customer) user).addCustomerAddress(address);
                DatabaseConnector.insertCustomer((Customer) user);
                DatabaseConnector.insertAddress(address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public void createInvoice() {
        productTotal = new ArrayList<>();
        String[] invoiceColumns = {"ProductId","Product Name","Product Description","Product Brand","Product Rating","Product Price",};

        invoiceContentContainer = new JPanel(new BorderLayout());
        invoiceContentContainer.setBorder(new EmptyBorder(60, 0, 0, 0));
        invoiceContentContainer.setOpaque(false);
        invoiceContentContainer.setPreferredSize(new Dimension(0, 400));

        try {
            DatabaseConnector.createConnection();
            data = DatabaseConnector.retrieveProduct();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String[] products = new String[data.length];

        for (int i = 0; i < products.length; i++) {
            products[i] = data[i][0] + " " + data[i][1];
        }
        
        JComboBox<String> productList = new JComboBox<>(products);
        JPanel productContent = new JPanel(new BorderLayout());
        productContent.setOpaque(false);

        JLabel product = new JLabel("Products Available   ", SwingConstants.LEFT);
        product.setFont(new Font("Arial", Font.PLAIN, 22));
        product.setForeground(new Color(63, 61, 61));

        productContent.add(product, BorderLayout.WEST);
        productContent.add(productList);

        JPanel productListContainer = new JPanel(new BorderLayout());
        productListContainer.setOpaque(false);
        productListContainer.setBorder(new EmptyBorder(0, 0, 50, 0));
        productListContainer.add(productContent, BorderLayout.WEST);

        DefaultTableModel model = new DefaultTableModel(null, invoiceColumns);

        JTable table = new JTable();
        table.setModel(model);
        table.getTableHeader().setPreferredSize(new Dimension(100, 40));
        table.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(table);
        
        productList.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

            String name = (String) productList.getSelectedItem();
            int id = Character.getNumericValue(name.charAt(0));

            try {
                DatabaseConnector.createConnection();
                data = DatabaseConnector.retrieveProduct();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            for (int i = 0; i < data.length; i++) {
                if (Integer.parseInt(data[i][0]) == id) {
                    model.addRow(new Object[]{data[i][0],data[i][1],data[i][3],data[i][4],data[i][6],data[i][2]});
                    productTotal.add(Float.parseFloat(data[i][2]));

                    findInvoiceTotal();
                }
            }
            
            revalidate();
            }
        });

        invoiceContentContainer.add(scrollPane, BorderLayout.CENTER);
        invoiceContentContainer.add(productListContainer, BorderLayout.NORTH);

        JPanel subTotalContainer = new JPanel(new BorderLayout());
        subTotalContainer.setOpaque(false);
        subTotalContainer.setBorder(new EmptyBorder(60, 0, 0, 60));

        JPanel subTotalPanel = new JPanel(new GridLayout(2,1));
        subTotalPanel.setOpaque(false);

        JLabel customerName = new JLabel("Customer: " + LoginSessionData.getCustomer().getFirstName() + " " + LoginSessionData.getCustomer().getLastName(), SwingConstants.LEFT);
        customerName.setFont(new Font("Arial", Font.PLAIN, 20));
        customerName.setForeground(new Color(63, 61, 61));

        total = new JLabel("Total of invoice: 0" , SwingConstants.LEFT);
        total.setFont(new Font("Arial", Font.PLAIN, 20));
        total.setForeground(new Color(63, 61, 61));

        subTotalPanel.add(customerName);
        subTotalPanel.add(total);

        subTotalContainer.add(subTotalPanel, BorderLayout.EAST);

        JPanel createInvoiceContainer = new JPanel(new BorderLayout());
        createInvoiceContainer.setPreferredSize(new Dimension(0, 100));
        createInvoiceContainer.setBorder(new EmptyBorder(40, 0, 0, 0));
        createInvoiceContainer.setOpaque(false);
        
        JButton createInvoice = new JButton("Create Invoice");
        createInvoice.setBackground(new Color(86, 152, 212));
        createInvoice.setAlignmentX(SwingConstants.LEFT);
        createInvoice.setPreferredSize(new Dimension(180, 0));
        createInvoice.setFont(new Font("Arial", Font.BOLD, 18));
        createInvoice.setForeground(Color.white);

        createInvoice.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Invoice invoice = new Invoice();
                invoice.addCustomerToInvoice(LoginSessionData.getCustomer());
                invoice.setSubTotal(newTotal);
                invoice.setNoOfProductsInInvoice(productTotal.size());

                System.out.print(invoice.getCustomer().getFirstName());
                try {
                    DatabaseConnector.createConnection();
                    DatabaseConnector.insertInvoice(invoice);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            
        });

        createInvoiceContainer.add(createInvoice, BorderLayout.WEST);

        subTotalContainer.add(createInvoiceContainer, BorderLayout.SOUTH);

        add(subTotalContainer, BorderLayout.SOUTH);
        add(invoiceContentContainer, BorderLayout.CENTER);
    }

    public void findInvoiceTotal() {
        newTotal = 0;
        for (int i = 0; i < productTotal.size(); i++) {
            newTotal += productTotal.get(i);
        }
        
        total.setText(String.valueOf("Total of invoice: " + newTotal));
    }
}
