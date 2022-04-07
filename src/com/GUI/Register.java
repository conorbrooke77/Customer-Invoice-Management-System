package com.GUI;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.*;

import com.Database.DatabaseConnector;
import com.Entities.*;
import com.Information.Address;

public class Register extends JFrame {

    private User user;
    private Address address;

    private JScrollPane scrollableForm;
    private JTextField[] fieldsArr = new JTextField[10];
    private JPanel formContainer, form, formHeader, btnContainer, fieldsContainer;
    private JLabel register;
    private JButton registerBtn;
    private Boolean isAdmin = false;

    
    public Register() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Customer Invoice Management System");
        setSize(1920, 1080);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().setBackground(new Color(83, 148, 244));

        createRegisterForm();

        setVisible(true);
    }

    public void createRegisterForm() {
        //Creating the Scrollpane
        scrollableForm = new JScrollPane();
        scrollableForm.setOpaque(false);

        //Creating the form Container
        formContainer = new JPanel(new BorderLayout());
        formContainer.setBackground(Color.white);
        formContainer.setPreferredSize(new Dimension(600, 1300));
        formContainer.setBorder(new EmptyBorder(60, 50, 80, 100));

        scrollableForm.setMaximumSize(new Dimension(620, 850));

        //The form
        createForm();

        formContainer.add(form, BorderLayout.CENTER);
        add(Box.createRigidArea(new Dimension(0, 80)));
        scrollableForm.setViewportView(formContainer);
        add(scrollableForm);
        add(Box.createRigidArea(new Dimension(0, 80)));
    }

    public void createForm() {

        String[] placholderText = {"Enter First Name", "Enter Last Name", "Enter Email Address", "Enter Password", "Confirm Password", "Enter Phonenumber", "Enter Street", "Enter Town", "Enter County", "Enter Eircode"};
        form = new JPanel(new BorderLayout());
        form.setBackground(Color.white);

        formHeader = new JPanel();
        formHeader.setLayout(new BoxLayout(formHeader, BoxLayout.Y_AXIS));
        formHeader.setPreferredSize(new Dimension(0, 80));
        formHeader.setOpaque(false);

        JLabel text = new JLabel("Please fill in this form to create an account.");
        text.setFont(new Font("Arial", Font.PLAIN, 18));
        text.setForeground(new Color(144, 144, 144));

        register = new JLabel("Register", SwingConstants.LEFT);
        register.setFont(new Font("Arial", Font.BOLD, 36));
        register.setForeground(new Color(63, 61, 61));

        formHeader.add(register);
        formHeader.add(Box.createVerticalGlue());
        formHeader.add(text);

        //Fields
        fieldsContainer = new JPanel();
        fieldsContainer.setLayout(new BoxLayout(fieldsContainer, BoxLayout.Y_AXIS));
        fieldsContainer.setBorder(new EmptyBorder(50, 0, 40, 0));
        fieldsContainer.setOpaque(false);

        
        for (int fields = 0; fields < 10; fields++) {
            //Sections
            if (fields == 0) {
                JLabel sectionTags = new JLabel("Personal Information");
                sectionTags.setFont(new Font("Arial", Font.BOLD, 21));
                sectionTags.setBorder(new EmptyBorder(0,0,40,0));
                sectionTags.setForeground(new Color(63, 61, 61));
                fieldsContainer.add(sectionTags);
            } else if (fields == 6) {
                JLabel addressInfoLabel = new JLabel("Address Information");
                addressInfoLabel.setFont(new Font("Arial", Font.BOLD, 21));
                addressInfoLabel.setBorder(new EmptyBorder(10,0,40,0));
                addressInfoLabel.setForeground(new Color(63, 61, 61));
                fieldsContainer.add(addressInfoLabel);
            }

            JTextField textField = new JTextField(placholderText[fields],35);
            textField.setBackground(new Color(239, 239, 239));
            textField.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
            textField.setForeground(new Color(147, 147, 147));
            textField.setFont(new Font("Arial", Font.PLAIN, 17));

            final int final_i = fields;
            textField.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (textField.getText().equals(placholderText[final_i])) {
                        textField.setText("");
                    }
                }
                @Override
                public void focusLost(FocusEvent e) {
                    if (textField.getText().equals("")) {
                        textField.setText(placholderText[final_i]);
                    }
                }
                });
            fieldsContainer.add(textField);
            fieldsArr[fields] = textField;
            fieldsContainer.add(Box.createVerticalStrut(30));
        }


        btnContainer = new JPanel(new BorderLayout());
        btnContainer.setOpaque(false);
        btnContainer.setPreferredSize(new Dimension(600,100));

        JCheckBox createAdmin = new JCheckBox("Click this box to create an Admin account.");
        createAdmin.setBorder(new EmptyBorder(0,0,35,0));
        createAdmin.setOpaque(false);
        createAdmin.setFont(new Font("Arial", Font.BOLD, 13));

        createAdmin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractButton absB = (AbstractButton) e.getSource();
                if (absB.getModel().isSelected()) {
                    isAdmin = true;

                    for (int fields = 6; fields < 10; fields++) {
                        fieldsArr[fields].setEditable(false);
                        fieldsArr[fields].setText("Not Required");
                    }
                } else {
                    for (int fields = 6; fields < 10; fields++) {
                        fieldsArr[fields].setEditable(true);
                        fieldsArr[fields].setText(placholderText[fields]);
                    }
                }
            }

        });

        registerBtn = new JButton("Register");
        registerBtn.setBackground(new Color(86, 152, 212));
        registerBtn.setAlignmentX(SwingConstants.LEFT);
        registerBtn.setPreferredSize(new Dimension(180, 0));
        registerBtn.setFont(new Font("Arial", Font.BOLD, 18));
        registerBtn.setForeground(Color.white);

        registerBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                register();
            }
        });
        btnContainer.add(createAdmin, BorderLayout.NORTH);
        btnContainer.add(registerBtn, BorderLayout.WEST);

        form.add(fieldsContainer, BorderLayout.CENTER);
        form.add(btnContainer, BorderLayout.SOUTH);
        form.add(formHeader, BorderLayout.NORTH);
        
    }

    public boolean register() {

        if (isAdmin)
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
            if (isAdmin) {
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

        dispose();
        new Login();
        return true;
    }
}
