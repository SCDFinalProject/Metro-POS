/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project;

/**
 *
 * @author fatimabintetariq
 */

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class AdminLogin extends JFrame {
    
    private JTextField usernameTF;
    private JPasswordField passwordTF;
    private JButton loginButton;
    private JPanel loginPanel;
    private JPanel actionPanel;

    public AdminLogin() {
        setTitle("Admin Login");
        setBounds(400, 200, 300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        loginPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        loginPanel.add(new JLabel("Username:"));
        usernameTF = new JTextField();
        loginPanel.add(usernameTF);
        
        loginPanel.add(new JLabel("Password:"));
        passwordTF = new JPasswordField();
        loginPanel.add(passwordTF);
        
        loginButton = new JButton("Login");
        loginPanel.add(new JLabel());
        loginPanel.add(loginButton);
        add(loginPanel, BorderLayout.CENTER);
        
        actionPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        JButton addBranchButton = new JButton("Add Branch");
        JButton addBranchManagerButton = new JButton("Add Branch Manager");
        JButton viewReportsButton = new JButton("View Reports");

        actionPanel.add(addBranchButton);
        actionPanel.add(addBranchManagerButton);
        actionPanel.add(viewReportsButton);
        actionPanel.setVisible(false); 
        add(actionPanel, BorderLayout.SOUTH);
        
        loginButton.addActionListener(e -> validateLogin());
        addBranchButton.addActionListener(e -> new Branch());
        addBranchManagerButton.addActionListener(e -> new BranchManager());
        viewReportsButton.addActionListener(e -> new ViewReports());

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
            
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                showAdminActions();
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect username or password.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }
    
    private void showAdminActions() {
        loginPanel.setVisible(false);
        actionPanel.setVisible(true);
        setSize(300, 300);
       // revalidate();
       // repaint();  
    }

    public static void main(String[] args) {
        new AdminLogin();
    }
}
