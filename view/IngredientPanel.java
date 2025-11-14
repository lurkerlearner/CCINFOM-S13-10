package view;

import controller.*;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.*;

public class IngredientPanel extends JPanel
{
    private IngredientController controller;

    private JTabbedPane tabbedPane;
    private JPanel addPanel;
    private JPanel viewPanel;
    private JPanel searchPanel;

    private JTextField batch_no;
    private JTextField ingredient_name;
    private JComboBox<String> category;
    private JComboBox<String> storage_type;
    private JComboBox<String> measurement_unit;
    private JTextField stock_quantity;  
    private JTextField expiry_date;
    private JTextField supplier_id;
    private JButton addButton;

    private JTable ingredientTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton;
    private JButton detailsButton;

    private JComboBox<String> searchTypeComboBox;
    private JTextField searchField;
    private JTable searchResultTable;
    private DefaultTableModel searchTableModel;

    public IngredientPanel(IngredientController ingredientController)
    {
        this.controller = ingredientController;

        setLayout(new BorderLayout());

        initComponents();
    }

    // Initialize all GUI components
    private void initComponents() 
    {
        // Create the tabbed pane
        tabbedPane = new JTabbedPane();
        
        // Create and add panels for each tab
        createAddPanel();
        createViewPanel();
        createSearchPanel();
        
        tabbedPane.addTab("Add Delivery", addPanel);
        tabbedPane.addTab("View All", viewPanel);
        tabbedPane.addTab("Search", searchPanel);
        
        add(tabbedPane, BorderLayout.CENTER);
    }

    private void createAddPanel()
    {
        addPanel = new JPanel();
        addPanel.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        String[] categories = Arrays.stream(Category.values())
                                    .map(Category::getDbValue)
                                    .toArray(String[]::new);
        
        String[] storageTypes = Arrays.stream(Storage_type.values())
                                    .map(Storage_type::getDbValue)
                                    .toArray(String[]::new);
        
        String[] measurementUnits = Arrays.stream(Measurement_unit.values())
                                    .map(Measurement_unit::getDbValue)
                                    .toArray(String[]::new);

        // String[] restockStatuses if needed
        // size of textfield na columns is the number of m that can fit in a text field
        batch_no = new JTextField(5);
        ingredient_name = new JTextField(20);
        stock_quantity = new JTextField(8);
        category = new JComboBox<>(categories);
        storage_type = new JComboBox<>(storageTypes);
        measurement_unit = new JComboBox<>(measurementUnits);
        expiry_date = new JTextField(10);
        supplier_id = new JTextField(5);

        // x and y refer to row and column respectively
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Batch No:"), gbc);
        gbc.gridx = 1;
        formPanel.add(batch_no, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Ingredient Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(ingredient_name, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        formPanel.add(category, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Storage Type:"), gbc);
        gbc.gridx = 1;
        formPanel.add(storage_type, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Measurement Unit:"), gbc);
        gbc.gridx = 1;
        formPanel.add(measurement_unit, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Stock Quantity:"), gbc);
        gbc.gridx = 1;
        formPanel.add(stock_quantity, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(new JLabel("Expiry Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        formPanel.add(expiry_date, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        formPanel.add(new JLabel("Supplier ID:"), gbc);
        gbc.gridx = 1;
        formPanel.add(supplier_id, gbc);

        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        addButton = new JButton("Add Ingredient");
        addButton.addActionListener(e -> addIngredient());
        
        buttonPanel.add(addButton);
        
        // Add panels to the main panel
        addPanel.add(new JScrollPane(formPanel), BorderLayout.CENTER);
        addPanel.add(buttonPanel, BorderLayout.SOUTH);
    }    

    private void addIngredient() {
            
    }
    
}
