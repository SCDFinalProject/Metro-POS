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

public class ViewReports extends JFrame {

    JTextField branchCodeField, startDateField, endDateField;
    JButton generateReportButton;

    public ViewReports() {
        setTitle("View Reports");
        setBounds(400, 200, 400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Branch Code:"));
        branchCodeField = new JTextField();
        add(branchCodeField);

        add(new JLabel("Start Date (YYYY-MM-DD):"));
        startDateField = new JTextField();
        add(startDateField);

        add(new JLabel("End Date (YYYY-MM-DD):"));
        endDateField = new JTextField();
        add(endDateField);

        generateReportButton = new JButton("Generate Report");
        add(new JLabel());
        add(generateReportButton);

        generateReportButton.addActionListener(e -> generateReport());
        setVisible(true);
    }

    private void generateReport() {
        String branchCode = branchCodeField.getText();
        String startDate = startDateField.getText();
        String endDate = endDateField.getText();

        // Fetch and display data (additional feature: integrate chart library for graphs)
        JOptionPane.showMessageDialog(this, "Reports generated for Branch: " + branchCode);
    }
}
