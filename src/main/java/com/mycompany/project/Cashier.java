/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author fatimabintetariq
 */

class CashierLogin extends JFrame {

    public static String url = "jdbc:mysql://localhost:3306/ProjectDB";
    public static String username = "root"; 
    public static String password = "12345678"; 
    JTextField emailField;
    JPasswordField passwordField;
    JButton loginButton;
    private int vendorId;
    public CashierLogin() {
        setTitle("Cashier Login");
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
         PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Cashier WHERE email = ? AND password = ?")) {

        stmt.setString(1, email);
        stmt.setString(2, password);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            boolean passwordChanged = rs.getBoolean("passwordChanged");

            if (passwordChanged) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                dispose();
                showPostLoginOptions(vendorId);
            } else {
                JOptionPane.showMessageDialog(this, "Please change your password.");
                enterNewPassword(rs.getInt("id"));
            }
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect email or password.");
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
    }
}

private void enterNewPassword(int userId) {
    JFrame frame = new JFrame("Change Password");
    frame.setBounds(400, 200, 400, 200);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setLayout(new GridLayout(3, 2, 10, 10));

    JLabel currentPasswordLabel = new JLabel("Current Password:");
    JPasswordField currentPasswordField = new JPasswordField();
    JLabel newPasswordLabel = new JLabel("New Password:");
    JPasswordField newPasswordField = new JPasswordField();
    JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
    JPasswordField confirmPasswordField = new JPasswordField();

    frame.add(currentPasswordLabel);
    frame.add(currentPasswordField);
    frame.add(newPasswordLabel);
    frame.add(newPasswordField);
    frame.add(confirmPasswordLabel);
    frame.add(confirmPasswordField);

    JButton changePasswordButton = new JButton("Change Password");
    frame.add(new JLabel()); 
    frame.add(changePasswordButton);

    changePasswordButton.addActionListener(e -> {
        String currentPassword = new String(currentPasswordField.getPassword());
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(frame, "Passwords do not match!");
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ProjectDB", "root", "12345678");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Cashier WHERE id = ? AND password = ?")) {

            stmt.setInt(1, userId);
            stmt.setString(2, currentPassword);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                try (PreparedStatement updateStmt = conn.prepareStatement("UPDATE Cashier SET password = ?, passwordChanged = TRUE WHERE id = ?")) {
                    updateStmt.setString(1, newPassword);
                    updateStmt.setInt(2, userId);
                    updateStmt.executeUpdate();
                    JOptionPane.showMessageDialog(frame, "Password updated successfully!");
                    frame.dispose();
                    showPostLoginOptions(vendorId);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Current password is incorrect.");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Database error: " + ex.getMessage());
        }
    });

    frame.setVisible(true);
}
 
    private void showPostLoginOptions(int vendorID) 
    {
         JFrame frame = new JFrame("Post Login Options");
        frame.setBounds(400, 200, 400, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(2, 1, 10, 10));

        JButton enterDataButton = new JButton("Enter Data");
        enterDataButton.addActionListener(e -> openSaleEntry());

        frame.add(enterDataButton);

        frame.setVisible(true);
    }
    
    private void openSaleEntry() {
        JFrame frame = new JFrame("Enter Purchased Products");
        frame.setBounds(400, 200, 400, 350);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Product ID:", JLabel.RIGHT));
        JTextField productIdField = new JTextField();
        panel.add(productIdField);

        panel.add(new JLabel("Quantity Purchased:", JLabel.RIGHT));
        JTextField quantityField = new JTextField();
        panel.add(quantityField);

        panel.add(new JLabel("Price Per Unit:", JLabel.RIGHT));
        JTextField priceField = new JTextField();
        panel.add(priceField);

        frame.add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton okButton = new JButton("OK");
        buttonPanel.add(okButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        okButton.addActionListener(e -> {
            String productId = productIdField.getText().trim();
            String quantityText = quantityField.getText().trim();
            String priceText = priceField.getText().trim();

            if (productId.isEmpty() || quantityText.isEmpty() || priceText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields are required!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int quantity;
            double pricePerUnit;
            try {
                quantity = Integer.parseInt(quantityText);
                pricePerUnit = Double.parseDouble(priceText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid quantity or price!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double totalCost = quantity * pricePerUnit;
            String message = processSale(productId, quantity, pricePerUnit, totalCost);
            if (message.equals("Sale processed successfully!")) 
            {
                message += "\nTotal Cost: " + totalCost;
            }
            JOptionPane.showMessageDialog(frame, message);
        });

        frame.setVisible(true);
    }

    private String processSale(String productId, int quantity, double pricePerUnit, double totalCost) {
        String message = "";
        String url = "jdbc:mysql://localhost:3306/ProjectDB";
        String user = "root";
        String password = "12345678";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String checkQuantityQuery = "SELECT quantity FROM Product WHERE productId = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(checkQuantityQuery)) {
                pstmt.setString(1, productId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int currentQuantity = rs.getInt("quantity");
                        if (currentQuantity == 0) {
                            message = "Inventory empty.";
                        } else if (quantity > currentQuantity) {
                            message = "Not enough stock available. Available: " + currentQuantity;
                        } else {
                            String updateQuantityQuery = "UPDATE Product SET quantity = quantity - ? WHERE productId = ?";
                            try (PreparedStatement updateStmt = conn.prepareStatement(updateQuantityQuery)) {
                                updateStmt.setInt(1, quantity);
                                updateStmt.setString(2, productId);
                                updateStmt.executeUpdate();
                                message = "Sale processed successfully!";
                            }
                        }
                    } else {
                        message = "Product not found!";
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            message = "Error processing the sale.";
        }
        return message;
    }
}

public class Cashier extends JFrame {

    private JTextField nameField, empNoField, emailField, branchCodeField, salaryField;
    private JPasswordField passwordField;
    private JButton saveButton;
    private String branchCode;
    public static String url = "jdbc:mysql://localhost:3306/ProjectDB";
    public static String username = "root"; 
    public static String password = "12345678"; 

    public Cashier(String branchCode) {
        this.branchCode = branchCode;
        setTitle("Add Cashier");
        setBounds(400, 200, 400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Name:", JLabel.RIGHT));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Emp No:", JLabel.RIGHT));
        empNoField = new JTextField();
        panel.add(empNoField);

        panel.add(new JLabel("Email:", JLabel.RIGHT));
        emailField = new JTextField();
        panel.add(emailField);      

        panel.add(new JLabel("Salary:", JLabel.RIGHT));
        salaryField = new JTextField();
        panel.add(salaryField);

        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        saveButton = new JButton("Save");
        buttonPanel.add(saveButton);

        add(buttonPanel, BorderLayout.SOUTH);
        saveButton.addActionListener(e -> save());

        setVisible(true);
    }

    private void save() {
        String name = nameField.getText().trim();
        String empNo = empNoField.getText().trim();
        String email = emailField.getText().trim();
        String salaryText = salaryField.getText().trim();

        if (name.isEmpty() || empNo.isEmpty() || email.isEmpty() || branchCode.isEmpty() || salaryText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double salary;
        try {
            salary = Double.parseDouble(salaryText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Salary must be a valid number!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(
                 "INSERT INTO Cashier (name, empNo, email, password, branchCode, salary) VALUES (?, ?, ?, 'Password_123', ?, ?)")) {

            stmt.setString(1, name);
            stmt.setString(2, empNo);
            stmt.setString(3, email);
            stmt.setString(4, branchCode); 
            stmt.setDouble(5, salary);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Cashier added successfully!");
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
