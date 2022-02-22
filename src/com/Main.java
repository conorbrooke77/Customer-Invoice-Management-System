package com;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;

import com.Driver.DatabaseConnector;
import com.Entities.*;
import com.GUI.*;

public class Main {
    private static Register register;

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

    public static void main(String[] args) throws SQLException {
        
        /*
        Login login = new Login();
        showOnScreen(1, login);
        

        login.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (login.getListenerComponent().equals("login"))
                    new ManagementSystem();
                else if (login.getListenerComponent().equals("register"))
                    register = new Register();
            }
        });
        */
        showOnScreen(1, new ManagementSystem());
    }
}
