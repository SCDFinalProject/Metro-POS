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

public class Branch extends JFrame {

    JTextField branchCodeField, nameField, cityField, addressField, phoneField;
    JCheckBox isActiveCB;
    JButton saveButton;

    public Branch() {
        setTitle("Add Branch");
        setBounds(400, 200, 400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(7, 2, 10, 10));

        add(new JLabel("Branch Code:"));
        branchCodeField = new JTextField();
        add(branchCodeField);

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("City:"));
        cityField = new JTextField();
        add(cityField);

        add(new JLabel("Address:"));
        addressField = new JTextField();
        add(addressField);

        add(new JLabel("Phone:"));
        phoneField = new JTextField();
        add(phoneField);

        add(new JLabel("Active:"));
        isActiveCB = new JCheckBox();
        add(isActiveCB);

        saveButton = new JButton("Save");
        add(new JLabel());
        add(saveButton);

        saveButton.addActionListener(e -> saveBranch());
        setVisible(true);
    }

    private void saveBranch() {
        String branchCode = branchCodeField.getText();
        String name = nameField.getText();
        String city = cityField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        boolean isActive = isActiveCB.isSelected();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ProjectDB", "root", "12345678");
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Branch (branchCode, name, city, address, phone, isActive) VALUES (?, ?, ?, ?, ?, ?)")) {

            stmt.setString(1, branchCode);
            stmt.setString(2, name);
            stmt.setString(3, city);
            stmt.setString(4, address);
            stmt.setString(5, phone);
            stmt.setBoolean(6, isActive);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Branch added successfully!");
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
