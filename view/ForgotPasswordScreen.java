package view;

import DAO.*;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class ForgotPasswordScreen extends JFrame {

    private final LocationDAO locationDAO = new LocationDAO();
    private final ClientDAO clientDAO = new ClientDAO();
    private final JTextField contactField;
    private final JButton generateCodeBtn;

    private final JTextField codeField;
    private final JButton verifyCodeBtn;

    private final JPasswordField newPasswordField;
    private final JPasswordField verifyNewPasswordField;
    private final JButton savePasswordBtn;
    private final JCheckBox showPasswordCheck;

    private String generatedCode;
    private Client currentClient;
    private JButton exitBtn;

    public ForgotPasswordScreen(){
        setTitle("FloodPanda - Forgot Password");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));


        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(Color.WHITE);

        JLabel logo = new JLabel("FloodPanda", SwingConstants.RIGHT);
        logo.setFont(new Font("Arial", Font.BOLD, 24));
        logo.setForeground(new Color(220, 31, 127));
        topPanel.add(logo, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        //CONTACT
        contactField = new JTextField(20);
        generateCodeBtn = new JButton("Generate Code");

        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(new JLabel("Enter Contact Number:"), gbc);
        gbc.gridx = 1;
        centerPanel.add(contactField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        centerPanel.add(generateCodeBtn, gbc);

        //otp
        codeField = new JTextField(6);
        verifyCodeBtn = new JButton("Verify Code");
        codeField.setEnabled(false);
        verifyCodeBtn.setEnabled(false);

        gbc.gridx = 0;
        gbc.gridy = 2;
        centerPanel.add(new JLabel("Enter Verification Code:"), gbc);
        gbc.gridx = 1;
        centerPanel.add(codeField, gbc);
        codeField.setEnabled(false);

        gbc.gridx = 1;
        gbc.gridy = 3;
        centerPanel.add(verifyCodeBtn, gbc);
        verifyCodeBtn.setEnabled(false);

        //new pass
        newPasswordField = new JPasswordField(20);
        verifyNewPasswordField = new JPasswordField(20);
        newPasswordField.setEnabled(false);
        verifyNewPasswordField.setEnabled(false);

        gbc.gridx = 0;
        gbc.gridy = 4;
        centerPanel.add(new JLabel("New Password:"), gbc);
        gbc.gridx = 1;
        centerPanel.add(newPasswordField, gbc);
        newPasswordField.setEnabled(false);

        gbc.gridx = 0;
        gbc.gridy = 5;
        centerPanel.add(new JLabel("Verify New Password:"), gbc);
        gbc.gridx = 1;
        centerPanel.add(verifyNewPasswordField, gbc);
        newPasswordField.setEnabled(false);

        //show pass
        showPasswordCheck = new JCheckBox("Show Password");
        gbc.gridx = 1;
        gbc.gridy = 6;
        centerPanel.add(showPasswordCheck, gbc);

        //save pass
        savePasswordBtn = new JButton("Save New Password");
        savePasswordBtn.setEnabled(false);

        gbc.gridx = 1;
        gbc.gridy = 7;
        centerPanel.add(savePasswordBtn, gbc);


        generateCodeBtn.addActionListener(this::generateCode);
        verifyCodeBtn.addActionListener(this::verifyCode);
        savePasswordBtn.addActionListener(this::saveNewPassword);
        showPasswordCheck.addActionListener(e -> {
            boolean show = showPasswordCheck.isSelected();
            newPasswordField.setEchoChar(show ? (char) 0 : '•');
            verifyNewPasswordField.setEchoChar(show ? (char) 0 : '•');
        });

        setVisible(true);
        add(centerPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        exitBtn = new JButton("Exit");
        exitBtn.addActionListener(e -> {
            dispose();
            Location clientLocation = locationDAO.getLocationById(currentClient.getLocationID());
            new ClientMainMenu(currentClient, clientLocation).setVisible(true);
        });
    }

    private void generateCode(ActionEvent e) {
        String contact = contactField.getText().trim();
        if (contact.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your contact number.");
            return;
        }

        currentClient = clientDAO.getClientByContact(contact);
        if (currentClient == null) {
            JOptionPane.showMessageDialog(this, "No client found with this contact.");
            return;
        }

        generatedCode = String.format("%06d", new Random().nextInt(999999));
        JOptionPane.showMessageDialog(this, "Your verification code is: " + generatedCode);

        codeField.setEnabled(true);
        verifyCodeBtn.setEnabled(true);
    }

    private void verifyCode(ActionEvent e) {
        String inputCode = codeField.getText().trim();
        if (generatedCode == null) {
            JOptionPane.showMessageDialog(this, "Please generate a code first.");
            return;
        }

        if (generatedCode.equals(inputCode)) {
            JOptionPane.showMessageDialog(this, "Code verified! You can now set a new password.");
            newPasswordField.setEnabled(true);
            verifyNewPasswordField.setEnabled(true);
            savePasswordBtn.setEnabled(true);
            generateCodeBtn.setEnabled(false);
            codeField.setEnabled(false);
            verifyCodeBtn.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect code. Try again.");
        }
    }

    private void saveNewPassword(ActionEvent e) {
        String newPass = new String(newPasswordField.getPassword()).trim();
        String verifyPass = new String(verifyNewPasswordField.getPassword()).trim();

        if (newPass.isEmpty() || verifyPass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Password fields cannot be empty.");
            return;
        }

        if (!newPass.equals(verifyPass)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match. Please try again.");
            return;
        }

        boolean updated = clientDAO.updateClientPassword(currentClient.getClientID(), newPass);
        if (updated) {
            JOptionPane.showMessageDialog(this, "Password updated successfully! Kindly login again.");
            dispose();
            new LoginScreen().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update password.");
        }
    }
}
