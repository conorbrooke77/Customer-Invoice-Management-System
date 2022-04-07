package com.GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Dashboard_Box extends JPanel {
    
    public Dashboard_Box(String label, String value, Color color) {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(150, 150));
        setBackground(color);

        JLabel boxName = new JLabel("Total " + label);

        boxName.setFont(new Font("Arial", Font.BOLD, 21));
        boxName.setBorder(new EmptyBorder(20, 0, 20, 0));
        boxName.setHorizontalAlignment(SwingConstants.CENTER);

        add(boxName, BorderLayout.NORTH);

        JLabel total = new JLabel(value);
        total.setFont(new Font("Arial", Font.PLAIN, 50));
        total.setBorder(new EmptyBorder(20, 0, 20, 0));
        total.setHorizontalAlignment(SwingConstants.CENTER);

        add(total, BorderLayout.CENTER);
    }
}
