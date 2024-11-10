/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.project;

/**
 *
 * @author fatimabintetariq
 */
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class Project extends JFrame {
    
    JProgressBar bar;

    Project() {
        setTitle("Loading");
        setBounds(200, 150, 400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        bar = new JProgressBar(0, 100);
        bar.setStringPainted(true);
        bar.setBackground(Color.black);
        bar.setForeground(Color.red);
        
        setLayout(new FlowLayout());
        add(bar);
        setVisible(true);
        
        fillProgressBar();
    }
    
    private void fillProgressBar() {
        for (int i = 0; i <= 100; i++) {
            bar.setValue(i);
            
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(rootPane, ex.getMessage());
            }
        }
        
        bar.setString("Done...");
        
        JOptionPane.showMessageDialog(this, "Loading complete!");
        dispose();
        // You can open a new window here if needed.
    }

    public static void main(String[] args) {
        new Project();
    }
}
