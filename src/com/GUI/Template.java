package com.GUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;

public class Template extends JPanel{
    private String headerName;
    private JLabel headerLabel;
    private JButton createBtn, manageBtn;
    private JScrollPane scrollPanel;
    private JPanel conentPanelContainer, btnPanel, contentPanel, headerPanel;


    public Template(String headerName) {
        this.headerName = headerName;
        
        setLayout(new BorderLayout());
        setBackground(new Color(246,246,246));
        setBorder(new EmptyBorder(0,60,100,60));
        addHeaderPanel();
        contentContainer();
        clickCreateContent();

        setVisible(true);
    }

    public void addHeaderPanel() {
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setPreferredSize(new Dimension(0, 150));

        headerLabel = new JLabel(headerName + "s", SwingConstants.LEFT);
        headerLabel.setFont(new Font("Arial", Font.PLAIN, 38));
        headerLabel.setForeground(new Color(65, 65, 65));

        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);
    }

    public void contentContainer() {
        conentPanelContainer = new JPanel(new BorderLayout());
        conentPanelContainer.setBackground(Color.WHITE);

        btnPanel = new JPanel(new BorderLayout());
        btnPanel.setBackground(new Color(246,246,246));

        JPanel tempPanel = new JPanel();
        tempPanel.setOpaque(false);
        tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.X_AXIS));

        createBtn = new JButton("Create " + headerName);
        createBtn.setBorder(new EmptyBorder(30,30,30,30));
        createBtn.setFocusPainted(false);
        createBtn.setFont(new Font("Arial", Font.BOLD, 18));

        createBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                clickCreateContent();
                createContent();

                revalidate();
                repaint();
            }
        });
        
        manageBtn = new JButton("Manage " + headerName + "s");
        manageBtn.setFocusPainted(false);
        manageBtn.setBorder(new EmptyBorder(30,30,30,30));
        manageBtn.setFont(new Font("Arial", Font.BOLD, 18));

        manageBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                clickManageContent();
                manageContent();

                revalidate();
                repaint();
            }
        });

        tempPanel.add(createBtn);
        tempPanel.add(manageBtn);
        btnPanel.add(tempPanel, BorderLayout.WEST);
        
        scrollPanel = new JScrollPane();
        scrollPanel.setOpaque(false);

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.white);
        contentPanel.setPreferredSize(new Dimension(600, 1300));

        scrollPanel.setMaximumSize(new Dimension(620, 850));

        JPanel color = new JPanel();
        color.setBackground(Color.BLACK);

        contentPanel.add(color, BorderLayout.CENTER);
        
        scrollPanel.setViewportView(contentPanel);
        conentPanelContainer.add(btnPanel, BorderLayout.NORTH);
        conentPanelContainer.add(scrollPanel, BorderLayout.CENTER);

        add(conentPanelContainer, BorderLayout.CENTER);
    }

    public void clickCreateContent() {
        manageBtn.setBackground(new Color(227, 245, 255));
        manageBtn.setForeground(new Color(105, 105, 105));
        createBtn.setBackground(new Color(86, 152, 212));
        createBtn.setForeground(Color.white);
    }

    public void clickManageContent() {
        createBtn.setBackground(new Color(227, 245, 255));
        createBtn.setForeground(new Color(105, 105, 105));
        manageBtn.setBackground(new Color(86, 152, 212));
        manageBtn.setForeground(Color.white);
    }

    public void createContent() {
        BorderLayout layout = (BorderLayout)contentPanel.getLayout();
        contentPanel.remove(layout.getLayoutComponent(BorderLayout.CENTER));

        JPanel color = new JPanel();
        color.setBackground(Color.BLACK);

        contentPanel.add(color, BorderLayout.CENTER);
    }

    public void manageContent() {
        BorderLayout layout = (BorderLayout)contentPanel.getLayout();
        contentPanel.remove(layout.getLayoutComponent(BorderLayout.CENTER));

        contentPanel.add(new ManageMenu(), BorderLayout.CENTER);
    }
}