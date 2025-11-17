package view;

import controller.*;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.*;

public class SalesReportPanel extends JPanel {
    private DeliveryController controller;

    // UI Components
    private JTabbedPane tabbedPane;
    private JPanel viewPanel;

    // Components for viewing sales report
    private JTable salesReportTable;
    private DefaultTableModel tableModel;
    private JButton generateButton;

    // Components for inputting parameters
    private JComboBox<String> reportTypeComboBox;
    private JTextField inputYear;
    private JTextField inputMonth;
    private JLabel monthLabel;

    // Button to go back to main menu
    private JButton mainMenuButton;

    public SalesReportPanel(DeliveryController deliveryController)
    {
        this.controller = deliveryController;

        setLayout(new BorderLayout());

        initComponents();
    }

    // Initialize all GUI components
    private void initComponents() 
    {
        // Create the tabbed pane
        tabbedPane = new JTabbedPane();
        
        // Create view panel
        createViewPanel();
        
        tabbedPane.addTab("Generate Sales Report", viewPanel);
        
        add(tabbedPane, BorderLayout.CENTER);
    }

    // Create the panel for viewing all contents of the sales report
    private void createViewPanel()
    {
        viewPanel = new JPanel();
        viewPanel.setLayout(new BorderLayout());
        viewPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel for inputting parameters
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Enter Report Parameters"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Report Type selection
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Report Type:"), gbc);

        gbc.gridx = 1;
        reportTypeComboBox = new JComboBox<>(
            new String[]{"Monthly Report", "Annual Report"});
        reportTypeComboBox.addActionListener(e -> updateInputFieldsVisibility());
        inputPanel.add(reportTypeComboBox, gbc);

        // Year input
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Year:"), gbc);
        gbc.gridx = 1;
        inputYear = new JTextField(10);
        inputPanel.add(inputYear, gbc);

        // Month input
        gbc.gridx = 0; gbc.gridy = 2;
        monthLabel = new JLabel("Month (1-12):");
        inputPanel.add(monthLabel, gbc);

        gbc.gridx = 1;
        inputMonth = new JTextField(5);
        inputPanel.add(inputMonth, gbc);

        // Set up table columns based on report type
        String[] columnNames = {
            "Year", "Month", "Sales Made", "Gross Income", "Net Profit"
        };

        // create a view-only table
        tableModel = new DefaultTableModel(columnNames, 0) 
        {
            @Override
            public boolean isCellEditable(int row, int column) 
            {
                return false;
            }
        };

        salesReportTable = new JTable(tableModel);
        salesReportTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        salesReportTable.getTableHeader().setReorderingAllowed(false);

        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        mainMenuButton = new JButton("Return to Main Menu");
        mainMenuButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(this).dispose();
            new AdminMainMenu().setVisible(true);
        });
        
        generateButton = new JButton("Generate Report");
        generateButton.addActionListener(e -> generateSalesReport());

        buttonPanel.add(mainMenuButton);
        buttonPanel.add(generateButton);
        
        // Add components to view panel
        viewPanel.add(inputPanel, BorderLayout.NORTH);
        viewPanel.add(new JScrollPane(salesReportTable), BorderLayout.CENTER);
        viewPanel.add(buttonPanel, BorderLayout.SOUTH);

        // show different input fields based on selected report type
        updateInputFieldsVisibility();
    }

    // show different input fields based on selected report type
    private void updateInputFieldsVisibility() 
    {
        String selectedType = (String) reportTypeComboBox.getSelectedItem();
        boolean isMonthly = "Monthly Report".equals(selectedType);
        
        // Show/hide month field based on report type
        monthLabel.setVisible(isMonthly);
        inputMonth.setVisible(isMonthly);
        
        // Update table columns based on report type
        updateTableColumns(isMonthly);
    }

    // Update table columns based on report type
    private void updateTableColumns(boolean includeMonth) 
    {
        tableModel.setRowCount(0); // Clear existing data
        
        if (includeMonth) 
        {
            // Monthly report columns
            tableModel.setColumnIdentifiers(new String[]{
                "Year", "Month", "Sales Made", "Gross Income", "Net Profit"
            });
        } 
        else 
        {
            // Annual report columns (no month column)
            tableModel.setColumnIdentifiers(new String[]{
                "Year", "Sales Made", "Gross Income", "Net Profit"
            });
        }
    }

    // Generate and display the sales report based on user input
    private void generateSalesReport() 
    {
        tableModel.setRowCount(0); // Clear table

        try 
        {
            String reportType = (String) reportTypeComboBox.getSelectedItem();
            int year = Integer.parseInt(inputYear.getText().trim());

            // Validate year (reasonable range)
            if (year < 1900 || year > 2100) 
            {
                JOptionPane.showMessageDialog(this, 
                    "Please enter a valid year.", 
                    "Invalid Input", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Generate reports based sa report type
            ArrayList<SalesReport> salesRecords;

            if ("Monthly Report".equals(reportType)) 
            {
                // Generate monthly report
                int month = Integer.parseInt(inputMonth.getText().trim());

                // Validate month range
                if (month < 1 || month > 12) 
                {
                    JOptionPane.showMessageDialog(this, 
                        "Month must be between 1 and 12.", 
                        "Invalid Input", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                salesRecords = controller.generateSalesReportByMonthYear(year, month);
                
                if (salesRecords.isEmpty()) 
                {
                    JOptionPane.showMessageDialog(this, 
                        "No sales data found for " + getMonthName(month) + " " + year, 
                        "No Data", 
                        JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                // Populate table with monthly report data
                for (SalesReport s : salesRecords) 
                {
                    Object[] row = new Object[]
                    {
                        s.getYear(),
                        getMonthName(s.getMonth()),
                        s.getSales_made(),
                        String.format("%.2f", s.getGross_income()),
                        String.format("%.2f", s.getNet_profit())
                    };
                    tableModel.addRow(row);
                }
            } 
            else 
            {
                // Generate annual report
                salesRecords = controller.generateSalesReportByYear(year);
                
                if (salesRecords.isEmpty()) 
                {
                    JOptionPane.showMessageDialog(this, 
                        "No sales data found for year " + year, 
                        "No Data", 
                        JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                // Populate table with annual report data (no month column)
                for (SalesReport s : salesRecords) 
                {
                    Object[] row = new Object[]
                    {
                        s.getYear(),
                        s.getSales_made(),
                        String.format("%.2f", s.getGross_income()),
                        String.format("%.2f", s.getNet_profit())
                    };
                    tableModel.addRow(row);
                }
            }

            JOptionPane.showMessageDialog(this, 
                "Sales report generated successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);

        } 
        catch (NumberFormatException e) 
        {
            JOptionPane.showMessageDialog(this, 
                "Please enter valid numbers for year and month.", 
                "Invalid Input", 
                JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(this, 
                "An error occurred while generating the report: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Helper method to convert month number to month name
    private String getMonthName(int month) 
    {
        String[] monthNames = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        };
        
        if (month >= 1 && month <= 12) 
        {
            return monthNames[month - 1];
        }
        return String.valueOf(month);
    }
}