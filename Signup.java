package EMP;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Signup extends JFrame {

    private JTextField nameField, emailField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;
    private JButton signupButton, clearButton;

    private static final String URL = "jdbc:mysql://localhost:3306/empdb";
    private static final String USER = "root";
    private static final String PASSWORD = "Samrin@123";

    public Signup() {
        setTitle("Signup Form (Admin/User)");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        getContentPane().setBackground(new Color(240, 248, 255));

        JLabel titleLabel = new JLabel("Signup Form", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBounds(100, 10, 250, 40);
        getContentPane().add(titleLabel);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(60, 70, 100, 25);
        getContentPane().add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(160, 70, 200, 25);
        getContentPane().add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(60, 110, 100, 25);
        getContentPane().add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(160, 110, 200, 25);
        getContentPane().add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(60, 150, 100, 25);
        getContentPane().add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(160, 150, 200, 25);
        getContentPane().add(passwordField);

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(60, 190, 100, 25);
        getContentPane().add(roleLabel);

        String[] roles = {"Admin", "User"};
        roleBox = new JComboBox<>(roles);
        roleBox.setBounds(160, 190, 200, 25);
        getContentPane().add(roleBox);

        signupButton = new JButton("Signup");
        signupButton.setBounds(230, 250, 100, 30);
        signupButton.setBackground(new Color(30, 144, 255));
        signupButton.setForeground(Color.WHITE);
        getContentPane().add(signupButton);

        clearButton = new JButton("Clear");
        clearButton.setBounds(110, 250, 100, 30);
        clearButton.setBackground(new Color(255, 69, 0));
        clearButton.setForeground(Color.WHITE);
        getContentPane().add(clearButton);

        // ✅ Button actions
        signupButton.addActionListener(e -> signupUser());
        clearButton.addActionListener(e -> clearFields());
    }

    private void signupUser() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = String.valueOf(passwordField.getPassword()).trim();
        String role = (String) roleBox.getSelectedItem();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // Insert only signup fields; other fields null
            String query = "INSERT INTO employee1 (name, email, password, role) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, password);
            pst.setString(4, role);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Signup successful as " + role + "!");
                clearFields();

                new login().setVisible(true);
                dispose();
            }

        } catch (SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(this, "Email already exists!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        roleBox.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Signup().setVisible(true));
    }
}
