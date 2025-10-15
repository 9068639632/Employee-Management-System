package EMP;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class login extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton, signupButton;

    private static final String URL = "jdbc:mysql://localhost:3306/empdb";
    private static final String USER = "root";
    private static final String PASSWORD = "Samrin@123";

    public login() {
        setTitle("Login Page");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        getContentPane().setBackground(new Color(245, 245, 245));

        JLabel titleLabel = new JLabel("Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBounds(120, 20, 150, 30);
        getContentPane().add(titleLabel);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 80, 100, 25);
        getContentPane().add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(150, 80, 180, 25);
        getContentPane().add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 120, 100, 25);
        getContentPane().add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 120, 180, 25);
        getContentPane().add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(211, 179, 100, 30);
        loginButton.setBackground(new Color(30, 144, 255));
        loginButton.setForeground(Color.WHITE);
        getContentPane().add(loginButton);

        signupButton = new JButton("Signup");
        signupButton.setBounds(64, 179, 100, 30);
        signupButton.setBackground(new Color(60, 179, 113));
        signupButton.setForeground(Color.WHITE);
        getContentPane().add(signupButton);

        // Button actions
        loginButton.addActionListener(e -> loginUser());
        signupButton.addActionListener(e -> openSignup());
    }

    private void loginUser() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both email and password", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT * FROM employee1 WHERE email = ? AND password = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, email);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                JOptionPane.showMessageDialog(this, "Login Successful as " + role);

                if (role.equalsIgnoreCase("Admin")) {
                    new AdminDashboard().setVisible(true);
                } else {
                    new UserDashboard(email).setVisible(true);
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Email or Password", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void openSignup() {
        new Signup().setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new login().setVisible(true));
    }
}
