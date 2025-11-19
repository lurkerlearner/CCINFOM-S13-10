package view;

import javax.swing.*;
import java.awt.*;

public class FloodPandaWelcome extends JFrame {

    public FloodPandaWelcome() {

        setTitle("FloodPanda");
        setSize(600, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(true);
        wrapper.setBackground(new Color(248,248,255));
        add(wrapper);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
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


        //buttons
        JButton login = new JButton("Login");
        JButton register = new JButton("Create New Account");
        JButton admin = new JButton("Admin Module");
        JButton exit = new JButton("Exit");

        JButton[] buttons = { login, register, admin, exit };
        Dimension buttonSize = new Dimension(400, 65);

        for (JButton b : buttons) {
            b.setPreferredSize(buttonSize);
            b.setMaximumSize(buttonSize);
            b.setMinimumSize(buttonSize);

            b.setFont(new Font("Arial", Font.BOLD, 16));
            b.setBackground(new Color(255, 214, 221));
            b.setOpaque(true);
            b.setContentAreaFilled(true);
            b.setFocusPainted(true);
            b.setBorder(BorderFactory.createLineBorder(Color.PINK, 2));
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(login);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(register);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(admin);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(exit);

        topPanel.setBackground(new Color(248,248,255));
        buttonPanel.setBackground(new Color(248,248,255));

        content.add(topPanel);
        content.add(Box.createVerticalStrut(30));
        content.add(buttonPanel);

        wrapper.add(content, new GridBagConstraints());

        login.addActionListener(e -> {
            new LoginScreen().setVisible(true);
            dispose();
        });

        register.addActionListener(e -> {
            new RegisterScreen().setVisible(true);
            dispose();
        });

        admin.addActionListener(e -> {
            new AdminLoginScreen().setVisible(true);
            dispose();
        });

        exit.addActionListener(e -> System.exit(0));

        setVisible(true);
    }
}
