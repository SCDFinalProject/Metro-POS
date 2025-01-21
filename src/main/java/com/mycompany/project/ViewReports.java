/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.project;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author fatimabintetariq
 */

public class ViewReports extends JFrame {

    private static final String url = "jdbc:mysql://localhost:3306/projectDb";
    private static final String user = "root";
    private static final String password = "12345678";

    public ViewReports() {
        setTitle("Reports and Analysis");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Sales Report", createSalesPanel());
        tabbedPane.addTab("Remaining Stock", createStockPanel());
        tabbedPane.addTab("Profit Report", createProfitPanel());

        add(tabbedPane);
        setVisible(true);
    }

    private JPanel createSalesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel branchCodeLabel = new JLabel("Branch Code:");
        JTextField branchCodeField = new JTextField();
        JLabel fromDateLabel = new JLabel("From Date (YYYY-MM-DD):");
        JTextField fromDateField = new JTextField();
        JLabel toDateLabel = new JLabel("To Date (YYYY-MM-DD):");
        JTextField toDateField = new JTextField();

        JButton generateButton = new JButton("Generate Sales Report");
        JTable reportTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(reportTable);

        inputPanel.add(branchCodeLabel);
        inputPanel.add(branchCodeField);
        inputPanel.add(fromDateLabel);
        inputPanel.add(fromDateField);
        inputPanel.add(toDateLabel);
        inputPanel.add(toDateField);
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);
        panel.add(generateButton, BorderLayout.SOUTH);

        generateButton.addActionListener(e -> {
            String branchCode = branchCodeField.getText().trim();
            String fromDate = fromDateField.getText().trim();
            String toDate = toDateField.getText().trim();

            if (branchCode.isEmpty() || fromDate.isEmpty() || toDate.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            displaySalesReport(branchCode, fromDate, toDate, reportTable);
        });

        return panel;
    }

    private JPanel createStockPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTable stockTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(stockTable);

        JButton loadStockButton = new JButton("Load Remaining Stock");
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(loadStockButton, BorderLayout.SOUTH);

        loadStockButton.addActionListener(e -> remainingStock(stockTable));
        return panel;
    }

    private JPanel createProfitPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTable profitTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(profitTable);

        JButton loadProfitButton = new JButton("Load Profit Report");
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(loadProfitButton, BorderLayout.SOUTH);

        loadProfitButton.addActionListener(e -> displayProfit(profitTable));
        return panel;
    }

    private void displaySalesReport(String branchCode, String fromDate, String toDate, JTable reportTable) {
        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Sale ID", "Product ID", "Quantity", "Total Cost", "Sale Date"}, 0);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT saleId, productId, quantity, totalCost, saleDate FROM Sale WHERE branchCode = ? AND saleDate BETWEEN ? AND ?";

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, branchCode);
                stmt.setString(2, fromDate);
                stmt.setString(3, toDate);

                ResultSet resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    int saleId = resultSet.getInt("saleId");
                    int productId = resultSet.getInt("productId");
                    int quantity = resultSet.getInt("quantity");
                    double totalCost = resultSet.getDouble("totalCost");
                    String saleDate = resultSet.getString("saleDate");

                    tableModel.addRow(new Object[]{saleId, productId, quantity, totalCost, saleDate});
                    dataset.addValue(quantity, "Quantity Sold", "Product ID: " + productId);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        reportTable.setModel(tableModel);
        JFreeChart barChart = ChartFactory.createBarChart(
            "Sales Report",
            "Product ID",
            "Quantity Sold",
            dataset,
            PlotOrientation.VERTICAL,
            true, true, false
        );
        ChartPanel chartPanel = new ChartPanel(barChart);
        JFrame chartFrame = new JFrame("Sales Report Chart");        
        chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chartFrame.setSize(500, 500);
        CategoryPlot plot = (CategoryPlot) barChart.getPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.ORANGE);
        chartFrame.add(chartPanel);
        chartFrame.setVisible(true);
    }

    private void remainingStock(JTable stockTable) {
        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Product ID", "Product Name", "Remaining Stock"}, 0);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT productId, productName, quantity FROM Product";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                ResultSet resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    int productId = resultSet.getInt("productId");
                    String productName = resultSet.getString("productName");
                    int stockQuantity = resultSet.getInt("quantity");

                    tableModel.addRow(new Object[]{productId, productName, stockQuantity});
                    dataset.addValue(stockQuantity, "Remaining Stock", productName);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        stockTable.setModel(tableModel);

        JFreeChart barChart = ChartFactory.createBarChart(
            "Remaining Stock",
            "Product Name",
            "Stock Quantity",
            dataset,
            PlotOrientation.VERTICAL,
            true, true, false
        );
        ChartPanel chartPanel = new ChartPanel(barChart);
        JFrame chartFrame = new JFrame("Remaining Stock Chart");
        chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chartFrame.setSize(500, 500);
        CategoryPlot plot = (CategoryPlot) barChart.getPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.ORANGE);
        chartFrame.add(chartPanel);
        chartFrame.setVisible(true);
    }

    private void displayProfit(JTable profitTable) {
        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Product ID", "Product Name", "Profit Amount"}, 0);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT p.productId, p.productName, SUM((p.salePrice - p.originalPrice) * s.quantity) AS profit " +
                           "FROM Product p " +
                           "JOIN Sale s ON p.productId = s.productId " +
                           "GROUP BY p.productId, p.productName";

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                ResultSet resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    int productId = resultSet.getInt("productId");
                    String productName = resultSet.getString("productName");
                    double profit = resultSet.getDouble("profit");

                    tableModel.addRow(new Object[]{productId, productName, profit});
                    dataset.addValue(profit, "Profit Amount", productName);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        profitTable.setModel(tableModel);

        JFreeChart barChart = ChartFactory.createBarChart(
            "Profit Report",
            "Product Name",
            "Profit Amount",
            dataset,
            PlotOrientation.VERTICAL,
            true, true, false
        );
        ChartPanel chartPanel = new ChartPanel(barChart);
        JFrame chartFrame = new JFrame("Profit Report Chart");
        chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chartFrame.setSize(500, 500);
        CategoryPlot plot = (CategoryPlot) barChart.getPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.ORANGE);
        chartFrame.add(chartPanel);
        chartFrame.setVisible(true);
    }
}
