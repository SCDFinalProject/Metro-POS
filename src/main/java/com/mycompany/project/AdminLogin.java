/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project;

/**
 *
 * @author fatimabintetariq
 */

import com.mycompany.project.Project;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class AdminLogin extends JFrame {
    
    JTextField usernameTF;
    JPasswordField passwordTF;
    JButton loginButton;
    
    public AdminLogin() {
        setTitle("Admin Login");
        setBounds(400, 200, 300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10));
        
        add(new JLabel("Username:"));
        usernameTF = new JTextField();
        add(usernameTF);
        
        add(new JLabel("Password:"));
        passwordTF = new JPasswordField();
        add(passwordTF);
        
        loginButton = new JButton("Login");
        add(new JLabel());
        add(loginButton);
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                validateLogin();
            }
        });
        
        setVisible(true);
    }
    
    private void validateLogin() {
        String username = usernameTF.getText();
        String password = new String(passwordTF.getPassword());
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ProjectDB", "root", "12345678");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Admin WHERE username = ? AND password = ?")) {
             
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) 
            {
                JOptionPane.showMessageDialog(this, "Login successful!");
                dispose();
            } 
            else {
                JOptionPane.showMessageDialog(this, "Incorrect username or password.");
            }
        } 
        catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }
}
