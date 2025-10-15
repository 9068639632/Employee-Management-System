package EMP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UserDashboard extends JFrame {

    private String userEmail;
    private JLabel welcomeLabel;
    private JButton btnProfile, btnUpdate, btnLogout;

    private static final String URL = "jdbc:mysql://localhost:3306/empdb";
    private static final String USER = "root";
    private static final String PASSWORD = "Samrin@123";

    public UserDashboard(String email) {
        this.userEmail = email;

        setTitle("User Dashboard");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(245, 250, 255));

        // Title
        JLabel title = new JLabel("User Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(120, 20, 250, 30);
        add(title);

        // Welcome message
        welcomeLabel = new JLabel("", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        welcomeLabel.setBounds(80, 70, 330, 25);
        add(welcomeLabel);

        // Buttons
        btnProfile = new JButton("My Profile");
        btnProfile.setBounds(170, 130, 150, 35);
        btnProfile.setBackground(new Color(30, 144, 255));
        btnProfile.setForeground(Color.WHITE);
        btnProfile.setFont(new Font("Arial", Font.BOLD, 14));
        add(btnProfile);

        btnUpdate = new JButton("Update Info");
        btnUpdate.setBounds(170, 190, 150, 35);
        btnUpdate.setBackground(new Color(50, 205, 50));
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setFont(new Font("Arial", Font.BOLD, 14));
        add(btnUpdate);

        btnLogout = new JButton("Logout");
        btnLogout.setBounds(170, 250, 150, 35);
        btnLogout.setBackground(new Color(255, 69, 0));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFont(new Font("Arial", Font.BOLD, 14));
        add(btnLogout);

        // Load user name
        loadUserName();

        // Button actions
        btnProfile.addActionListener(e -> openProfile());
        btnUpdate.addActionListener(e -> openUpdate());
        btnLogout.addActionListener(e -> logout());
    }

    // Load user name from DB
    private void loadUserName() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT name FROM user WHERE email = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, userEmail);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                welcomeLabel.setText("Welcome, " + name + " 👋");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    // Open profile
    private void openProfile() {
        new UserProfile(userEmail).setVisible(true);
        dispose();
    }

    // Open update page
    private void openUpdate() {
        new UserUpdate(userEmail).setVisible(true);
        dispose();
    }

    // Logout
    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            new login().setVisible(true);
            dispose();
        }
    }

    public static void main(String[] args) {
        new UserDashboard("user@gmail.com").setVisible(true);
    }
}
