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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectRole extends JFrame {

    public SelectRole() {
        setTitle("Select Role");
        setBounds(300, 200, 400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 10, 10));

        JButton superAdminButton = new JButton("Super Admin");
        JButton branchManagerButton = new JButton("Branch Manager");
        JButton dataEntryOperatorButton = new JButton("Data Entry Operator");
        JButton cashierButton = new JButton("Cashier");

        add(superAdminButton);
        add(branchManagerButton);
        add(dataEntryOperatorButton);
        add(cashierButton);

        superAdminButton.addActionListener(new RoleActionListener("Super Admin"));
        branchManagerButton.addActionListener(new RoleActionListener("Branch Manager"));
        dataEntryOperatorButton.addActionListener(new RoleActionListener("Data Entry Operator"));
        cashierButton.addActionListener(new RoleActionListener("Cashier"));

        setVisible(true);
    }

    private class RoleActionListener implements ActionListener {
        private String role;

        public RoleActionListener(String role) {
            this.role = role;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(SelectRole.this, "Selected role: " + role);
            dispose();

            switch (role) {
                case "Super Admin":
                    new AdminLogin();
                    break;
//                case "Branch Manager":                   
//                    break;
//                case "Data Entry Operator":   
//                    break;
//                case "Cashier": 
//                    break;
            }
        }
    }
}
