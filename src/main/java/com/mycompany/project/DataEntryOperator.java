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

class DataEntryOperatorLogin extends JFrame {

    JTextField emailField;
    JPasswordField passwordField;
    JButton loginButton;

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
                JOptionPane.showMessageDialog(this, "Login successful!");
                dispose();
             //   postLoginButtons(email);
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect email or password.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

}

public class DataEntryOperator extends JFrame {

    private JTextField nameField, empNoField, emailField, branchCodeField, salaryField;
    private JPasswordField passwordField;
    private JButton saveButton;
    private String branchCode;

    public DataEntryOperator() {
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

        inputPanel.add(new JLabel("Branch Code:", JLabel.RIGHT));
        branchCodeField = new JTextField();
        inputPanel.add(branchCodeField);

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
        String branchCode = branchCodeField.getText().trim();
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

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ProjectDB", "root", "12345678");
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
    
    public static void main(String[] args) {
        new DataEntryOperator();
    }
}
