package view;

import app.DBConnection;
import controller.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RegisterScreen extends JFrame {

    private LoginController loginController = new LoginController();
    MealPlanController mealPlanController = new MealPlanController();
    DietPreferenceController dietController = new DietPreferenceController();

    public RegisterScreen() {
        setTitle("Create Account - FloodPanda");
        setSize(800, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(new Color(248,248,255));
        add(wrapper);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        content.setOpaque(false);

        ImageIcon logoIcon = new ImageIcon("resources/floodpanda.png");
        Image logoImg = logoIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(logoImg);

        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("FloodPanda");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(new Color(220, 31, 127));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false);
        topPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(logoLabel);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(title);
        topPanel.add(Box.createVerticalStrut(20));


        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8); // padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JTextField fullNameField = new JTextField();
        JTextField contactField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();
        JTextField unitField = new JTextField();
        JComboBox<LocationItem> locationDrop = new JComboBox<>();
        loadLocations(locationDrop);
        JComboBox<MealPlanItem> mealPlanDrop = new JComboBox<>();
        loadMealPlans(mealPlanDrop);
        JList<DietPreferenceItem> dietList = new JList<>();
        dietList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        loadDietPreferences(dietList);
        JScrollPane dietScroll = new JScrollPane(dietList);
        dietScroll.setPreferredSize(new Dimension(200, 100));

        JCheckBox showPassword = new JCheckBox("Show Password");

        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(fullNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Contact Number:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(contactField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(new JLabel("Confirm Password:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(confirmPasswordField, gbc);


        gbc.gridx = 1; gbc.gridy = 4;
        mainPanel.add(showPassword, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        mainPanel.add(new JLabel("Unit / House No.:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(unitField, gbc);


        gbc.gridx = 0; gbc.gridy = 6;
        mainPanel.add(new JLabel("Street / Area:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(locationDrop, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        mainPanel.add(new JLabel("Meal Plan:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(mealPlanDrop, gbc);

        gbc.gridx = 0; gbc.gridy = 8;
        mainPanel.add(new JLabel("Diet Preference"), gbc);
        gbc.gridx = 1;
        mainPanel.add(dietScroll, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);

        mainPanel.setBackground(new Color(248,248,255));
        mainPanel.setOpaque(true);


        JButton registerBtn = new JButton("Create Account");
        JButton exitBtn = new JButton("Exit");

        buttonPanel.add(registerBtn);
        buttonPanel.add(exitBtn);

        content.add(topPanel);
        content.add(mainPanel);
        content.add(Box.createVerticalStrut(20));
        content.add(buttonPanel);
        wrapper.add(content, new GridBagConstraints());


        showPassword.addActionListener(e -> {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
                confirmPasswordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
                confirmPasswordField.setEchoChar('*');
            }
        });


        registerBtn.addActionListener(e -> {
            String name = fullNameField.getText().trim();
            String contact = contactField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String confirmPassword = new String(confirmPasswordField.getPassword()).trim();
            String unit = unitField.getText().trim();

            if (name.isEmpty() || contact.isEmpty() || password.isEmpty() ||
                    confirmPassword.isEmpty() || unit.isEmpty() || locationDrop.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                return;
            }

            if (!contact.matches("^09\\d{9}$")) {
                JOptionPane.showMessageDialog(this, "Contact number must start with 09 and be 11 digits.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.");
                return;
            }

            int locationId = ((LocationItem) locationDrop.getSelectedItem()).id;
            int mealPlanId = ((MealPlanItem) mealPlanDrop.getSelectedItem()).id;

            List<Integer> dietPrefIds = new ArrayList<>();
            for (DietPreferenceItem item : dietList.getSelectedValuesList()) {
                dietPrefIds.add(item.id);
            }

            if (dietPrefIds.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select at least one diet preference.");
                return;
            }


            boolean success = loginController.register(name, contact, password, unit, locationId, mealPlanId, dietPrefIds);

            if (success) {
                JOptionPane.showMessageDialog(this, "Account successfully created!");
                dispose();
                new LoginScreen();
            } else {
                if (loginController.getClientDAO().isContactExists(contact)) {
                    JOptionPane.showMessageDialog(this, "This contact number is already registered.");
                } else {
                    JOptionPane.showMessageDialog(this, "Registration failed.");
                }
            }
        });


        exitBtn.addActionListener(e -> {
            dispose();
            new FloodPandaWelcome();
        });

        setVisible(true);
    }

    private void loadLocations(JComboBox<LocationItem> comboBox) {
        try {
            var conn = DBConnection.getConnection();
            var stmt = conn.prepareStatement("SELECT location_id, street_address, city FROM location");
            var rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("location_id");
                String text = rs.getString("street_address").trim() + ", " + rs.getString("city").trim();
                comboBox.addItem(new LocationItem(id, text));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadMealPlans(JComboBox<MealPlanItem> comboBox) {
        try {
            var conn = DBConnection.getConnection();
            var stmt = conn.prepareStatement("SELECT plan_id, plan_name FROM meal_plan");
            var rs = stmt.executeQuery();

            while (rs.next()) {
                comboBox.addItem(new MealPlanItem(rs.getInt("plan_id"), rs.getString("plan_name")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDietPreferences(JList<DietPreferenceItem> list) {
        DefaultListModel<DietPreferenceItem> model = new DefaultListModel<>();
        try {
            var conn = DBConnection.getConnection();
            var stmt = conn.prepareStatement("SELECT diet_preference_id, diet_name FROM diet_preference");
            var rs = stmt.executeQuery();

            while (rs.next()) {
                model.addElement(new DietPreferenceItem(rs.getInt("diet_preference_id"), rs.getString("diet_name")));
            }
            list.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    class LocationItem {
        int id;
        String text;
        public LocationItem(int id, String text) { this.id = id; this.text = text; }
        public String toString() { return text; }
    }

    class MealPlanItem {
        int id;
        String name;
        public MealPlanItem(int id, String name) { this.id = id; this.name = name; }
        public String toString() { return name; }
    }

    class DietPreferenceItem {
        int id;
        String name;
        public DietPreferenceItem(int id, String name) { this.id = id; this.name = name; }
        public String toString() { return name; }
    }

}

