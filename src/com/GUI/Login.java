package com.GUI;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.*;

import com.Driver.DatabaseConnector;

public class Login extends JFrame {

    private JTextField[] fieldsArr = new JTextField[2];
    private JPanel formContainer, form, formHeader, btnContainer, contentContainer, fieldContainer, otherContentContainer;
    private JLabel login;
    private JButton loginBtn;
    private String listenerComponent;
    
    public Login() {

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Customer Invoice Management System");
        setSize(1920, 1080);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().setBackground(new Color(83, 148, 244));

        createLoginForm();

        setVisible(true);
    }

    public String getListenerComponent() {
        return listenerComponent;
    }

    public void createLoginForm() {

        //Creating the form Container
        formContainer = new JPanel(new BorderLayout());
        formContainer.setBackground(Color.white);
        formContainer.setMaximumSize(new Dimension(600, 850));
        formContainer.setBorder(new EmptyBorder(90, 50, 160, 50));

        //The form
        createForm();

        formContainer.add(form, BorderLayout.CENTER);
        add(Box.createRigidArea(new Dimension(0, 80)));
        add(formContainer);
        add(Box.createRigidArea(new Dimension(0, 80)));
    }

    public void createForm() {

        form = new JPanel(new BorderLayout());
        form.setBackground(Color.white);

        formHeader = new JPanel(new BorderLayout());
        formHeader.setPreferredSize(new Dimension(0, 80));
        formHeader.setOpaque(false);

        login = new JLabel("Login", SwingConstants.CENTER);
        login.setFont(new Font("Arial", Font.BOLD, 42));
        login.setForeground(new Color(63, 61, 61));

        formHeader.add(login, BorderLayout.CENTER);

        //Fields
        contentContainer = new JPanel(new BorderLayout());
        contentContainer.setPreferredSize(new Dimension(500, 600));
        contentContainer.setBorder(new EmptyBorder(50, 0, 40, 0));
        contentContainer.setOpaque(false);

        fieldContainer = new JPanel();
        fieldContainer.setBorder(new EmptyBorder(30, 60, 20, 60));
        fieldContainer.setPreferredSize(new Dimension(0, 250));
        fieldContainer.setLayout(new BoxLayout(fieldContainer, BoxLayout.Y_AXIS));
        fieldContainer.setOpaque(false);

        String[] placholderText = {"Email", "Password"};

        for (int fields = 0; fields < 2; fields++) {
            JTextField textField = new JTextField(placholderText[fields],35);
            textField.setBackground(new Color(239, 239, 239));
            textField.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
            textField.setForeground(new Color(147, 147, 147));
            textField.setFont(new Font("Arial", Font.PLAIN, 18));

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

            fieldContainer.add(textField);
            fieldsArr[fields] = textField;
            fieldContainer.add(Box.createVerticalStrut(40));
        }


        btnContainer = new JPanel(new GridBagLayout());
        btnContainer.setOpaque(false);
        btnContainer.setPreferredSize(new Dimension(0,70));

        loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(86, 152, 212));
        loginBtn.setPreferredSize(new Dimension(180, 60));
        loginBtn.setFont(new Font("Arial", Font.BOLD, 19));
        loginBtn.setForeground(Color.white);
        

        loginBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                login();
            }

            private void login() {
                try {
                    DatabaseConnector.createConnection();
                    if (DatabaseConnector.retrieveLoginData(fieldsArr[0].getText(), fieldsArr[1].getText())) {
                        listenerComponent = "login";
                        dispose();
                    } else {
                        fieldsArr[0].setBorder(new LineBorder(Color.RED, 2));
                        fieldsArr[1].setBorder(new LineBorder(Color.RED, 2));
                    }
                            
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    
        btnContainer.add(loginBtn, new GridBagConstraints());

        otherContentContainer = new JPanel(new BorderLayout());
        otherContentContainer.setBorder(new EmptyBorder(30,100,0,0));
        otherContentContainer.setOpaque(false);
        otherContentContainer.setPreferredSize(new Dimension(0, 140));


        JCheckBox forgotPassword = new JCheckBox("Click here if you Forgot Password?");
        forgotPassword.setBorder(new EmptyBorder(0,0,35,0));
        forgotPassword.setOpaque(false);
        forgotPassword.setFont(new Font("Arial", Font.BOLD, 16));

        forgotPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractButton absB = (AbstractButton) e.getSource();
                if (absB.getModel().isSelected()) {
                    listenerComponent = "register";
                    dispose();
                }
            }
        });
        
        JCheckBox signUp = new JCheckBox("Click here to Register an account.");
        signUp.setBorder(new EmptyBorder(0,0,35,0));
        signUp.setOpaque(false);
        signUp.setFont(new Font("Arial", Font.BOLD, 16));

        signUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractButton absB = (AbstractButton) e.getSource();
                if (absB.getModel().isSelected()) {
                    listenerComponent = "register";
                    dispose();
                }
            }
        });

        otherContentContainer.add(forgotPassword, BorderLayout.NORTH);
        otherContentContainer.add(signUp, BorderLayout.SOUTH);

        contentContainer.add(fieldContainer, BorderLayout.NORTH);
        contentContainer.add(btnContainer, BorderLayout.SOUTH);
        
        form.add(contentContainer, BorderLayout.CENTER);
        form.add(formHeader, BorderLayout.NORTH); 
        form.add(otherContentContainer, BorderLayout.SOUTH);
    }
}