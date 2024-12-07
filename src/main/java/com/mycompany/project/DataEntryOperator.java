/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project;

/**
 *
 * @author fatimabintetariq
 */

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

class VendorInformationPanel extends JFrame {
    private JTextField vendorIDField, vendorNameField, vendorContactField;
    private JButton saveButton, loadButton, addProductButton;
    
    public VendorInformationPanel() {
        setTitle("Vendor Information");
        setBounds(300, 100, 500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));
        
        add(new JLabel("Vendor ID:"));
        vendorIDField = new JTextField();
        add(vendorIDField);
        
        add(new JLabel("Vendor Name:"));
        vendorNameField = new JTextField();
        add(vendorNameField);
        
        add(new JLabel("Vendor Contact:"));
        vendorContactField = new JTextField();
        add(vendorContactField);
        
        loadButton = new JButton("Load Vendor");
        loadButton.addActionListener(e -> loadVendor());
        add(loadButton);
        
        addProductButton = new JButton("+ Add Product");
        addProductButton.addActionListener(e -> new ProductInformationPanel(Integer.parseInt(vendorIDField.getText())));
        add(addProductButton);
        
        saveButton = new JButton("Save Vendor");
        saveButton.addActionListener(e -> saveVendor());
        add(saveButton);
        
        setVisible(true);
    }
    
    private void loadVendor() {
        int vendorId = Integer.parseInt(vendorIDField.getText());
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ProjectDB", "root", "12345678");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Vendor WHERE vendorId = ?")) {
             
            stmt.setInt(1, vendorId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                vendorNameField.setText(rs.getString("vendorName"));
                vendorContactField.setText(rs.getString("vendorContact"));
            } else {
                JOptionPane.showMessageDialog(this, "Vendor not found.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
    
    private void saveVendor() {
        String vendorName = vendorNameField.getText();
        String vendorContact = vendorContactField.getText();
        
        if (vendorName.isEmpty() || vendorContact.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ProjectDB", "root", "12345678");
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Vendor (vendorName, vendorContact) VALUES (?, ?)")) {
             
            stmt.setString(1, vendorName);
            stmt.setString(2, vendorContact);
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(this, "Vendor added successfully.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
class ProductInformationPanel extends JFrame {
    private JTextField productNameField, categoryField, originalPriceField, salePriceField, pricePerUnitField, pricePerCartonField, quantityField;
    private JButton saveButton;
    
    public ProductInformationPanel(int vendorID) {
        setTitle("Product Information for Vendor ID: " + vendorID);
        setBounds(300, 100, 500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(7, 2, 10, 10));
        
        add(new JLabel("Product Name:"));
        productNameField = new JTextField();
        add(productNameField);
        
        add(new JLabel("Category:"));
        categoryField = new JTextField();
        add(categoryField);
        
        add(new JLabel("Original Price:"));
        originalPriceField = new JTextField();
        add(originalPriceField);
        
        add(new JLabel("Sale Price:"));
        salePriceField = new JTextField();
        add(salePriceField);
        
        add(new JLabel("Price Per Unit:"));
        pricePerUnitField = new JTextField();
        add(pricePerUnitField);
        
        add(new JLabel("Price Per Carton:"));
        pricePerCartonField = new JTextField();
        add(pricePerCartonField);
        
        add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        add(quantityField);
        
        saveButton = new JButton("Save Product");
        saveButton.addActionListener(e -> saveProduct(vendorID));
        add(saveButton);
        
        setVisible(true);
    }
    
    private void saveProduct(int vendorID) {
        String productName = productNameField.getText();
        String category = categoryField.getText();
        String originalPrice = originalPriceField.getText();
        String salePrice = salePriceField.getText();
        String pricePerUnit = pricePerUnitField.getText();
        String pricePerCarton = pricePerCartonField.getText();
        String quantity = quantityField.getText();
        
        if (productName.isEmpty() || category.isEmpty() || originalPrice.isEmpty() || salePrice.isEmpty() || pricePerUnit.isEmpty() || pricePerCarton.isEmpty() || quantity.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ProjectDB", "root", "12345678");
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Product (vendorId, productName, category, originalPrice, salePrice, pricePerUnit, pricePerCarton, quantity) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
             
            stmt.setInt(1, vendorID);
            stmt.setString(2, productName);
            stmt.setString(3, category);
            stmt.setDouble(4, Double.parseDouble(originalPrice));
            stmt.setDouble(5, Double.parseDouble(salePrice));
            stmt.setDouble(6, Double.parseDouble(pricePerUnit));
            stmt.setDouble(7, Double.parseDouble(pricePerCarton));
            stmt.setString(8, quantity);
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(this, "Product added successfully.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}

class DataEntryOperatorLogin extends JFrame {

    JTextField emailField;
    JPasswordField passwordField;
    JButton loginButton;
    private int vendorId;
    public DataEntryOperatorLogin() {
        setTitle("Data Entry Operator Login");
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
         PreparedStatement stmt = conn.prepareStatement("SELECT * FROM DataEntryOperator WHERE email = ? AND password = ?")) {

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
                promptForNewPassword(rs.getInt("id"));
            }
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect email or password.");
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
    }
}

private void promptForNewPassword(int userId) {
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
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM DataEntryOperator WHERE id = ? AND password = ?")) {

            stmt.setInt(1, userId);
            stmt.setString(2, currentPassword);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                try (PreparedStatement updateStmt = conn.prepareStatement("UPDATE DataEntryOperator SET password = ?, passwordChanged = TRUE WHERE id = ?")) {
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
        
        JButton vendorInfoButton = new JButton("Vendor Information");
        vendorInfoButton.addActionListener(e -> new VendorInformationPanel());
        frame.add(vendorInfoButton);
        
        frame.setVisible(true);
    }
}

public class DataEntryOperator extends JFrame {

    private JTextField nameField, empNoField, emailField, branchCodeField, salaryField;
    private JPasswordField passwordField;
    private JButton saveButton;
    private String branchCode;
    public static String url = "jdbc:mysql://localhost:3306/ProjectDB";
    public static String username = "root";
    public static String password = "12345678";

    public DataEntryOperator(String branchCode) {
        this.branchCode = branchCode;
        setTitle("Add Data Entry Operator");
        setBounds(400, 200, 400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Name:", JLabel.RIGHT));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Emp No:", JLabel.RIGHT));
        empNoField = new JTextField();
        inputPanel.add(empNoField);

        inputPanel.add(new JLabel("Email:", JLabel.RIGHT));
        emailField = new JTextField();
        inputPanel.add(emailField);

        inputPanel.add(new JLabel("Salary:", JLabel.RIGHT));
        salaryField = new JTextField();
        inputPanel.add(salaryField);

        add(inputPanel, BorderLayout.CENTER);

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

        if (name.isEmpty() || empNo.isEmpty() || email.isEmpty() || salaryText.isEmpty()) {
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
                 "INSERT INTO DataEntryOperator (name, empNo, email, password, branchCode, salary) VALUES (?, ?, ?, 'Password_123', ?, ?)")) {

            stmt.setString(1, name);
            stmt.setString(2, empNo);
            stmt.setString(3, email);
            stmt.setString(4, branchCode);  
            stmt.setDouble(5, salary);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data Entry Operator added successfully!");
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
