package com.GUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import com.Information.LoginSessionData;

public class ManagementSystem extends JFrame {

    private JPanel header, nav, navigationPanel, selected;
    private JLabel profileName, logo, navigation, exit;
    private JPanel[] navagationPanels = new JPanel[5];
    private final String[] imageURLs = {"src/com/Resources/img/dashboard.png","src/com/Resources/img/Products.png","src/com/Resources/img/Invoices.png","src/com/Resources/img/customers.png","src/com/Resources/img/System-users.png"};

    public ManagementSystem() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Customer Invoice Management System");
        setSize(1920, 1080);
        setLayout(new BorderLayout());

        createHeader();
        createNavBar();
        createNavigationPanel();
        add(new Dashboard("Dashboard"), BorderLayout.CENTER);

        setVisible(true);
    }

    public void createHeader(){
        header = new JPanel(new BorderLayout());

        header.setBackground(new Color(9, 131, 184));
        header.setPreferredSize(new Dimension(1920, 82));

        //Creating Logo
        logo = new JLabel("Invoice Management", SwingConstants.CENTER);

        logo.setFont(new Font("Arial", Font.BOLD, 21));
        logo.setForeground(Color.white);
        logo.setPreferredSize(new Dimension(300, 82));
        

        header.add(logo, BorderLayout.WEST);

        //Creating Profile Section
        createProfile();

        super.add(header, BorderLayout.NORTH);
    }

    public void createProfile() {
        JPanel profileContainer = new JPanel(new BorderLayout());
        profileContainer.setPreferredSize(new Dimension(290,0));
        profileContainer.setOpaque(false);

        JLabel profilePic = new JLabel(new ImageIcon("src/com/Resources/img/pic.png"));
        profilePic.setBorder(new EmptyBorder(0,0,0,40));

        if (LoginSessionData.getIsAdmin()) {
            profileName = new JLabel(LoginSessionData.getAdmin().getFirstName() + " " + LoginSessionData.getAdmin().getLastName());
        } else {
            profileName = new JLabel(LoginSessionData.getCustomer().getFirstName() + " " + LoginSessionData.getCustomer().getLastName());
        }

        profileName.setFont(new Font("Arial", Font.PLAIN, 17));
        profileName.setForeground(Color.WHITE);

        JLabel arrow = new JLabel(new ImageIcon("src/com/Resources/img/Webp.net-resizeimage (7).png"));
        arrow.setBorder(new EmptyBorder(0,0,0,60));
        
        profileContainer.add(profilePic, BorderLayout.WEST);
        profileContainer.add(profileName, BorderLayout.CENTER);
        profileContainer.add(arrow, BorderLayout.EAST);

        header.add(profileContainer, BorderLayout.EAST);
    }

    public void createNavBar() {
        //Create the Nav-bar
        nav = new JPanel(new BorderLayout());

        nav.setBackground(new Color(70, 72, 72));
        nav.setPreferredSize(new Dimension(300, 995));

        //The Label for Navation
        navigation = new JLabel("Navagation", SwingConstants.LEFT);
        navigation.setPreferredSize(new Dimension(300, 130));
        navigation.setBorder(new EmptyBorder(0, 40, 0, 0));
        navigation.setFont(new Font("Arial", Font.BOLD, 16));
        navigation.setForeground(new Color(148, 148, 148));

        nav.add(navigation, BorderLayout.NORTH);

        //The Label for the exit Label text
        exit = new JLabel(new ImageIcon("src/com/Resources/img/exit-system.png"), SwingConstants.LEFT);
        exit.setBorder(new EmptyBorder(0,50,0,0));
        exit.setPreferredSize(new Dimension(300, 200));

        exit.addMouseListener(new MouseAdapter() { 
            @Override
            public void mouseEntered(java.awt.event.MouseEvent event) {
                exit.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseClicked(java.awt.event.MouseEvent event) {
                dispose();
            }
          });

        nav.add(exit, BorderLayout.SOUTH);
        super.add(nav, BorderLayout.WEST);
    }

    public void createNavigationPanel() {

        final int totalNavagationPanels = 5;
        navigationPanel = new JPanel(new FlowLayout());
        navigationPanel.setOpaque(false);

        for (int panel = 0; panel < totalNavagationPanels; panel++) {
            //Top level panel
            JPanel fullWidthPanel = new JPanel(new BorderLayout());

            fullWidthPanel.setBackground(new Color(92, 92, 92));
            fullWidthPanel.setPreferredSize(new Dimension(300, 70));
            fullWidthPanel.setOpaque(false);

            //Highlighted indicator
            JPanel highlighted = new JPanel();
            highlighted.setBackground(new Color(8, 128, 180));
            highlighted.setOpaque(false);

            fullWidthPanel.add(highlighted, BorderLayout.WEST);

            //Icon and Label
            JLabel test = new JLabel(new ImageIcon(imageURLs[panel]));
            test.setBorder(new EmptyBorder(0,0,0,70));
            
            fullWidthPanel.add(test, BorderLayout.CENTER);

            //First panel auto select;
            if (panel == 0) {
                fullWidthPanel.setOpaque(true);
                highlighted.setOpaque(true);
            }
            
            fullWidthPanel.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseEntered(java.awt.event.MouseEvent event) {
                    if (!isClicked(event)) {
                        fullWidthPanel.setOpaque(true);
                        highlighted.setOpaque(true);
                        repaint();
                    }
                }
                @Override
                public void mouseExited(java.awt.event.MouseEvent event) {
                    if (!isClicked(event)) {
                        fullWidthPanel.setOpaque(false);
                        highlighted.setOpaque(false);
                        repaint();
                    }
                }
                @Override
                public void mouseClicked(java.awt.event.MouseEvent event) {
                    BorderLayout layout = (BorderLayout) getContentPane().getLayout();
                    remove(layout.getLayoutComponent(BorderLayout.CENTER));

                    selected = setClicked(event, selected, highlighted);
                    revalidate();
                    repaint();
                }
            });

            navigationPanel.add(fullWidthPanel);
            navagationPanels[panel] = fullWidthPanel;
            if (panel == 0) {
                selected = navagationPanels[0];
            }
        }
        nav.add(navigationPanel, BorderLayout.CENTER);
    }

    public JPanel setClicked(java.awt.event.MouseEvent e, JPanel selected, JPanel highlighted) {
        
        String[] productInputs = {"Product Name ", "Product Price", "Product Description", "Product Brand", "Product Quantity", "Product Rating", "Product Weight"};
        String[] systemUsersInputs = {"Enter First Name", "Enter Last Name", "Enter Email Address", "Enter Password", "Confirm Password", "Enter Phonenumber"};
        String[] customerInputs = {"Enter First Name", "Enter Last Name", "Enter Email Address", "Enter Password", "Confirm Password", "Enter Phonenumber", "Enter Street", "Enter Town", "Enter County", "Enter Eircode"};

        if (!e.getSource().equals(selected) ) {
            
            selected.setFocusable(true);
            selected.setOpaque(false);
            ((JComponent) selected.getComponent(0)).setOpaque(false);
            
            repaint();

            if (LoginSessionData.getIsAdmin()){
                if (e.getSource().equals(navagationPanels[0])) {
                    add(new Dashboard("Dashboard"), BorderLayout.CENTER);
                    navagationPanels[0].setFocusable(false);
                    return navagationPanels[0];
                } else if (e.getSource().equals(navagationPanels[1])) {
                    add(new Template("Product", productInputs), BorderLayout.CENTER);
                    navagationPanels[1].setFocusable(false);
                    return navagationPanels[1];
                } else if (e.getSource().equals(navagationPanels[3])) {
                    add(new Template("Customer", customerInputs), BorderLayout.CENTER);
                    navagationPanels[3].setFocusable(false);
                    return navagationPanels[3];
                } else if (e.getSource().equals(navagationPanels[4])) {
                    add(new Template("System User", systemUsersInputs), BorderLayout.CENTER);
                    navagationPanels[4].setFocusable(false);
                    return navagationPanels[4];
                } else {
                    add(new JPanel(), BorderLayout.CENTER );
                }
            } else {
                if (e.getSource().equals(navagationPanels[0])) {
                    add(new Dashboard("Dashboard"), BorderLayout.CENTER);
                    navagationPanels[0].setFocusable(false);
                    return navagationPanels[0];
                } else if (e.getSource().equals(navagationPanels[2])) {
                    add(new Template("Invoice", productInputs), BorderLayout.CENTER);
                    navagationPanels[2].setFocusable(false);
                    return navagationPanels[2];
                } else {
                    add(new JPanel(), BorderLayout.CENTER );
                }
            }
            
        }
        return selected;
    }

    public boolean isClicked(java.awt.event.MouseEvent event) {
        if (event.getSource().equals(selected))
            return true;
        else
            return false;
    }
}
