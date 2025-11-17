package view;

import controller.*;
import DAO.*;
import model.*;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ClientDietPreferencePanel extends JPanel {

    private ClientDietPreferenceController controller;

    private JTabbedPane tabbedPane;
    private JPanel addPanel;
    private JPanel viewPanel;
    private JPanel searchPanel;
    private JPanel editPanel;

    private JTextField dietPrefField, clientIdField;

    private JTable cdTable;
    private DefaultTableModel cdTableModel;
    private JButton refreshButton;

    private JButton searchButton;
    private JComboBox<String> searchTypeDropdown;
    private JTextField searchField;
    private JTable searchResultTable;
    private DefaultTableModel searchTableModel;

    private JButton mainMenuBtn, addBtn, searchBtn;

    public ClientDietPreferencePanel(ClientDietPreferenceController controller){
        this.controller = controller;

        setLayout(new BorderLayout());

        initComponents();
    }

    private void initComponents()
    {
        // Create the tabbed pane
        tabbedPane = new JTabbedPane();

        // Create and add panels for each tab
        createAddPanel();
        createViewPanel();
        createSearchPanel();
        createEditPanel();

        tabbedPane.addTab("Add Record", addPanel);
        tabbedPane.addTab("View All", viewPanel);
        tabbedPane.addTab("Search", searchPanel);
        tabbedPane.addTab("Edit", editPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }

    public void createAddPanel(){
        addPanel = new JPanel(new BorderLayout());

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8,8,8,8);

        dietPrefField = new JTextField(20);
        clientIdField = new JTextField(20);

        int row = 0;
        gbc.gridx=0; gbc.gridy=row; form.add(new JLabel("Diet Preference ID:"), gbc);
        gbc.gridx=1; form.add(dietPrefField, gbc);

        row++;
        gbc.gridx=0; gbc.gridy=row; form.add(new JLabel("Client ID:"), gbc);
        gbc.gridx=1; form.add(clientIdField, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        mainMenuBtn = new JButton("Return to Main Menu");
        mainMenuBtn.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(this).dispose();
            new AdminMainMenu().setVisible(true);
        });

        addBtn = new JButton("Add");
        addBtn.addActionListener(e -> addMapping());

        btnPanel.add(mainMenuBtn);
        btnPanel.add(addBtn);

        addPanel.add(form, BorderLayout.CENTER);
        addPanel.add(btnPanel, BorderLayout.SOUTH);
    }

    private void addMapping() {
        try {
            int dietPrefID = Integer.parseInt(dietPrefField.getText().trim());
            int clientID = Integer.parseInt(clientIdField.getText().trim());

            boolean ok = controller.add(dietPrefID, clientID);

            if (ok) {
                JOptionPane.showMessageDialog(this, "Mapping added successfully!");
                dietPrefField.setText("");
                clientIdField.setText("");
                refreshViewTable();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Insert failed.\nEither the Client ID or Diet Preference ID does not exist OR this mapping already exists.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + e.getMessage());
        }
    }


    public void createViewPanel(){
        viewPanel = new JPanel(new BorderLayout());

        cdTableModel = new DefaultTableModel(
                new String[]{"Diet Pref ID", "Client ID"}, 0
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        cdTable = new JTable(cdTableModel);
        cdTable.getTableHeader().setReorderingAllowed(false);

        refreshViewTable();

        viewPanel.add(new JScrollPane(cdTable), BorderLayout.CENTER);
    }

    private void refreshViewTable() {
        cdTableModel.setRowCount(0);

        List<ClientDietPreference> list = controller.getAll();

        for (ClientDietPreference c : list) {
            cdTableModel.addRow(new Object[]{
                    c.getDietPreferenceID(),
                    c.getClientID()
            });
        }
    }

    public void createSearchPanel(){
        searchPanel = new JPanel(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));

        searchTypeDropdown = new JComboBox<>(new String[]{"By Diet Preference ID", "By Client ID"});
        searchField = new JTextField(20);
        searchBtn = new JButton("Search");
        searchBtn.addActionListener(e -> searchCdp());

        top.add(searchTypeDropdown);
        top.add(searchField);
        top.add(searchBtn);

        searchTableModel = new DefaultTableModel(
                new String[]{"Diet Pref ID", "Client ID"}, 0
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        searchResultTable = new JTable(searchTableModel);
        searchResultTable.getTableHeader().setReorderingAllowed(false);

        searchPanel.add(top, BorderLayout.NORTH);
        searchPanel.add(new JScrollPane(searchResultTable), BorderLayout.CENTER);
    }

    public void createEditPanel(){
        editPanel = new JPanel(new BorderLayout());
        editPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //todo:IMPLEMENT EDIT
    }

    public void searchCdp(){
        searchTableModel.setRowCount(0);

        try {
            int id = Integer.parseInt(searchField.getText().trim());

            List<ClientDietPreference> results;

            if (searchTypeDropdown.getSelectedIndex() == 0) {
                results = controller.searchByDietPref(id);
            } else {
                results = controller.searchByClient(id);
            }

            for (ClientDietPreference c : results) {
                searchTableModel.addRow(new Object[]{
                        c.getDietPreferenceID(),
                        c.getClientID()
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input.");
        }
    }






}
