package com.example.pro;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class LoginPagePro extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JComboBox<String> themeComboBox;
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/resume_ats_pro";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Akshat@123"; // CHANGE THIS TO YOUR PASSWORD
    
    public LoginPagePro() {
        setupUI();
        initializeDatabase();
        createLoginForm();
    }
    
    private void setupUI() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        setTitle(" Resume ATS Pro - Login");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(45, 45, 48));
    }
    
    private void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "username VARCHAR(50) UNIQUE NOT NULL, " +
                    "password VARCHAR(255) NOT NULL, " +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
            stmt.execute(createTableSQL);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Database connection failed!\nError: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void createLoginForm() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(45, 45, 48));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel titleLabel = new JLabel(" Resume ATS Pro", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        mainPanel.add(titleLabel, gbc);

        // Subtitle
        gbc.gridy = 1;
        JLabel subtitleLabel = new JLabel("Advanced Resume Parser & ATS Scorer", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.LIGHT_GRAY);
        mainPanel.add(subtitleLabel, gbc);

        // Username
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        JLabel userLabel = new JLabel(" Username:");
        userLabel.setForeground(Color.WHITE);
        mainPanel.add(userLabel, gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        mainPanel.add(usernameField, gbc);

        // Password
        gbc.gridy = 3;
        gbc.gridx = 0;
        JLabel passLabel = new JLabel(" Password:");
        passLabel.setForeground(Color.WHITE);
        mainPanel.add(passLabel, gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        mainPanel.add(passwordField, gbc);

        // Theme Selector
        gbc.gridy = 4;
        gbc.gridx = 0;
        JLabel themeLabel = new JLabel(" Theme:");
        themeLabel.setForeground(Color.WHITE);
        mainPanel.add(themeLabel, gbc);

        gbc.gridx = 1;
        themeComboBox = new JComboBox<>(new String[]{"Dark", "Light", "Blue"});
        mainPanel.add(themeComboBox, gbc);

        // Buttons
        gbc.gridy = 5;
        gbc.gridx = 0;
        loginButton = new JButton(" Login");
        loginButton.setBackground(new Color(0, 122, 204));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.addActionListener(this::handleLogin);
        mainPanel.add(loginButton, gbc);

        gbc.gridx = 1;
        registerButton = new JButton(" Register");
        registerButton.setBackground(new Color(76, 175, 80));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        registerButton.addActionListener(this::handleRegister);
        mainPanel.add(registerButton, gbc);

        // Add the main panel to the frame
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void handleLogin(ActionEvent e) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            showMessage("Please enter both username and password", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT password FROM users WHERE username = ?")) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                if (password.equals(storedPassword)) {
                    showMessage("Login successful! Welcome to Resume ATS Pro 🎉", "Success", JOptionPane.INFORMATION_MESSAGE);
                    openResumeATSPro(username);
                    dispose();
                } else {
                    showMessage("Invalid password!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                showMessage("User not found! Please register first.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (SQLException ex) {
            showMessage("Login failed: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleRegister(ActionEvent e) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            showMessage("Please enter both username and password", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (username.length() < 3) {
            showMessage("Username must be at least 3 characters long", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (password.length() < 4) {
            showMessage("Password must be at least 4 characters long", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)")) {
            
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            
            showMessage("Registration successful! \nYou can now login with your credentials.", "Success", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (SQLIntegrityConstraintViolationException ex) {
            showMessage("Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            showMessage("Registration failed: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
    
    private void openResumeATSPro(String username) {
        SwingUtilities.invokeLater(() -> {
            ResumeATSPro app = new ResumeATSPro(username);
            app.setVisible(true);
        });
    }
    
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "MySQL JDBC Driver not found!");
            return;
        }
        
        SwingUtilities.invokeLater(() -> {
            LoginPagePro loginPage = new LoginPagePro();
            loginPage.setVisible(true);
        });
    }
}