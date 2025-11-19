package view;

import javax.swing.*;
import java.awt.*;

public class AdminLoginScreen extends JFrame {

    //HARDCODED NA SIYA HERE
    private final String ADMIN_USERNAME = "admin";
    private final String ADMIN_PASSWORD = "admin123";

    public AdminLoginScreen() {
        setTitle("FloodPanda - Admin Login");
        setSize(600, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Use the same wrapper layout as client login
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(true);
        wrapper.setBackground(new Color(248,248,255)); // Same background color
        add(wrapper);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);

        //==TOP PANEL WITH LOGO AND TITLE (matching client login style)
        ImageIcon logoIcon = new ImageIcon("resources/floodpanda.png");
        Image logoImg = logoIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(logoImg);

        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("FloodPanda - Admin");
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

        //==FORM PANEL (matching client login style)
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JCheckBox showPassword = new JCheckBox("Show Password");

        Dimension fieldSize = new Dimension(300, 30);
        usernameField.setPreferredSize(fieldSize);
        passwordField.setPreferredSize(fieldSize);

        //==USERNAME
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        formPanel.add(usernameField, gbc);

        //==PASSWORD
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        formPanel.add(passwordField, gbc);

        //==SHOW PASSWORD
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0;
        formPanel.add(showPassword, gbc);

        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.setBackground(new Color(248,248,255)); // Same background

        //==SOUTH PANEL (matching client login style)
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton loginBtn = new JButton("Login");
        JButton exitBtn = new JButton("Exit");

        Dimension btnSize = new Dimension(120, 40);
        loginBtn.setPreferredSize(btnSize);
        exitBtn.setPreferredSize(btnSize);

        southPanel.setBackground(new Color(248,248,255)); // Same background

        southPanel.add(loginBtn);
        southPanel.add(exitBtn);

        //==ASSEMBLE ALL COMPONENTS
        content.add(topPanel);
        content.add(Box.createVerticalStrut(20));
        content.add(formPanel);
        content.add(Box.createVerticalStrut(20));
        content.add(southPanel);
        wrapper.add(content, new GridBagConstraints());

        //==ACTION LISTENERS
        showPassword.addActionListener(e -> {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        });

        loginBtn.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
                JOptionPane.showMessageDialog(this, "Welcome, Admin!");
                dispose();
                new AdminMainMenu().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid admin credentials.");
            }
        });

        exitBtn.addActionListener(e -> {
            new FloodPandaWelcome().setVisible(true);
            dispose();
        });

        setVisible(true);
    }
}
