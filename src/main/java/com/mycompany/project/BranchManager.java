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
import javax.swing.*;
import java.sql.*;

class BranchManagerLogin extends JFrame {

    JTextField emailField;
    JPasswordField passwordField;
    JButton loginButton;

    public BranchManagerLogin() {
        setTitle("Branch Manager Login");
        setBounds(400, 200, 400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        loginButton = new JButton("Login");
        add(new JLabel());
        add(loginButton);

        loginButton.addActionListener(e -> validateLogin());
        setVisible(true);
    }

    private void validateLogin() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ProjectDB", "root", "12345678");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM BranchManager WHERE email = ? AND password = ?")) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String branchCode = rs.getString("branchCode");
                JOptionPane.showMessageDialog(this, "Login successful!");
                dispose();
                postLoginButtons(email,branchCode);
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect email or password.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }
    
    private void postLoginButtons(String email,String branchCode) 
    {
        JFrame frame = new JFrame("Branch Manager");
        frame.setBounds(400, 200, 400, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 1, 10, 10));

        JButton dataEntryButton = new JButton("Add Data Entry Operator");
        JButton cashierButton = new JButton("Add Cashier");
        JButton updateProfileButton = new JButton("Update Profile");

        frame.add(dataEntryButton);
        frame.add(cashierButton);
        frame.add(updateProfileButton);

        dataEntryButton.addActionListener(e -> new DataEntryOperator(branchCode));
        cashierButton.addActionListener(e -> new Cashier(branchCode)); 
        updateProfileButton.addActionListener(e -> new BranchManagerProfile(email));

        frame.setVisible(true);
    }

}

public class BranchManager extends JFrame {

    JButton loginButton, addManagerButton;

    public BranchManager() {
        setTitle("Branch Manager");
        setBounds(400, 200, 400, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(2, 1, 10, 10));

        addManagerButton = new JButton("Add Branch Manager");
        add(addManagerButton);
        addManagerButton.addActionListener(e -> new AddBranchManager());
        
        setVisible(true);
    }
}

class AddBranchManager extends JFrame {

    JTextField nameField, emailField, branchCodeField, salaryField;
    JButton saveButton;

    public AddBranchManager() {
        setTitle("Add Branch Manager");
        setBounds(400, 200, 400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("Manager Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Branch Code:"));
        branchCodeField = new JTextField();
        add(branchCodeField);

        add(new JLabel("Salary:"));
        salaryField = new JTextField();
        add(salaryField);

        saveButton = new JButton("Save");
        add(new JLabel());
        add(saveButton);

        saveButton.addActionListener(e -> save());
        setVisible(true);
    }

    private void save() {
        String name = nameField.getText();
        String email = emailField.getText();
        String branchCode = branchCodeField.getText();
        String salaryText = salaryField.getText();

        double salary;
        try {
            salary = Double.parseDouble(salaryText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid salary input! Please enter a valid number.");
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ProjectDB", "root", "12345678");
             PreparedStatement stmt = conn.prepareStatement(
                 "INSERT INTO BranchManager (name, email, password, branchCode, salary) VALUES (?, ?, 'Password_123', ?, ?)")) {

            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, branchCode);
            stmt.setDouble(4, salary);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Branch Manager added successfully!");
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}

class BranchManagerProfile extends JFrame {

    JTextField nameField, emailField, branchCodeField, salaryField;
    JPasswordField passwordField, confirmPasswordField;
    JButton updateButton;

    public BranchManagerProfile(String email) {
        setTitle("Branch Manager Profile");
        setBounds(400, 200, 400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(7, 2, 10, 10));

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Email:"));
        emailField = new JTextField(email);
        emailField.setEditable(false);
        add(emailField);

        add(new JLabel("Branch Code:"));
        branchCodeField = new JTextField();
        branchCodeField.setEditable(false);
        add(branchCodeField);

        add(new JLabel("Salary:"));
        salaryField = new JTextField();
        salaryField.setEditable(false);
        add(salaryField);

        add(new JLabel("New Password:"));
        passwordField = new JPasswordField();
        add(passwordField);
        
        add(new JLabel("Confirm Password:"));
        confirmPasswordField = new JPasswordField();
        add(confirmPasswordField);

        updateButton = new JButton("Update Profile");
        add(new JLabel());
        add(updateButton);

        load(email);

        updateButton.addActionListener(e -> update(email));
        setVisible(true);
    }

    private void load(String email) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ProjectDB", "root", "12345678");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM BranchManager WHERE email = ?")) {

            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) 
            {
                nameField.setText(rs.getString("name"));
                branchCodeField.setText(rs.getString("branchCode"));
                salaryField.setText(String.valueOf(rs.getDouble("salary")));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading profile: " + ex.getMessage());
        }
    }

    private void update(String email) {
    String newPassword = new String(passwordField.getPassword());
    String confirmPassword = new String(confirmPasswordField.getPassword()); 
    String currPassword = null;
    if (!newPassword.equals(confirmPassword)) {
        JOptionPane.showMessageDialog(this, "Passwords do not match!");
        return;
    }
     try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ProjectDB", "root", "12345678")) {  
        try (PreparedStatement fetchStmt = conn.prepareStatement("SELECT password FROM BranchManager WHERE email = ?")) {
            fetchStmt.setString(1, email);
            ResultSet rs = fetchStmt.executeQuery();
            if (rs.next()) {
                currPassword = rs.getString("password");
            }
        }

        if (newPassword.equals(currPassword)) {
            JOptionPane.showMessageDialog(this, "New password cannot be the same as the current password!");
            return;
        }

        try (PreparedStatement updateStmt = conn.prepareStatement("UPDATE BranchManager SET password = ? WHERE email = ?")) {
            updateStmt.setString(1, newPassword);
            updateStmt.setString(2, email);
            updateStmt.executeUpdate();
        }

        JOptionPane.showMessageDialog(this, "Profile updated successfully!");
        dispose();
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error updating profile: " + ex.getMessage());
    }
}

}
