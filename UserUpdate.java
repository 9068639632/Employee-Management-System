package EMP;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UserUpdate extends JFrame {

    private JTextField txtName, txtMobile, txtAddress;
    private JPasswordField txtPassword;
    private JButton btnUpdate, btnBack;
    private String userEmail;  // Logged-in user ka email
    private static final String URL = "jdbc:mysql://localhost:3306/empdb";
    private static final String USER = "root";
    private static final String PASSWORD = "Samrin@123";

    public UserUpdate(String email) {
        this.userEmail = email;

        setTitle("Update My Profile");
        setSize(420, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        getContentPane().setBackground(new Color(240, 248, 255));

        JLabel lblTitle = new JLabel("Update Profile", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setBounds(100, 20, 200, 30);
        getContentPane().add(lblTitle);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(60, 80, 100, 25);
        getContentPane().add(lblName);

        txtName = new JTextField();
        txtName.setBounds(160, 80, 180, 25);
        getContentPane().add(txtName);

        JLabel lblMobile = new JLabel("Mobile:");
        lblMobile.setBounds(60, 120, 100, 25);
        getContentPane().add(lblMobile);

        txtMobile = new JTextField();
        txtMobile.setBounds(160, 120, 180, 25);
        getContentPane().add(txtMobile);

        JLabel lblAddress = new JLabel("Address:");
        lblAddress.setBounds(60, 160, 100, 25);
        getContentPane().add(lblAddress);

        txtAddress = new JTextField();
        txtAddress.setBounds(160, 160, 180, 25);
        getContentPane().add(txtAddress);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(60, 200, 100, 25);
        getContentPane().add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(160, 200, 180, 25);
        getContentPane().add(txtPassword);

        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(226, 263, 100, 35);
        btnUpdate.setBackground(new Color(30, 144, 255));
        btnUpdate.setForeground(Color.WHITE);
        getContentPane().add(btnUpdate);

        btnBack = new JButton("Back");
        btnBack.setBounds(88, 263, 100, 35);
        btnBack.setBackground(new Color(255, 69, 0));
        btnBack.setForeground(Color.WHITE);
        getContentPane().add(btnBack);

        // ✅ Load current user data
        loadUserData();

        // ✅ Button actions
        btnUpdate.addActionListener(e -> updateUser());
        btnBack.addActionListener(e -> {
            new UserDashboard(userEmail).setVisible(true);
            dispose();
        });
    }

    // ✅ Load user details from employee1 table
    private void loadUserData() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT name, mob_no, Address, password FROM employee1 WHERE email = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, userEmail);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                txtName.setText(rs.getString("name"));
                txtMobile.setText(rs.getString("mob_no"));
                txtAddress.setText(rs.getString("Address"));
                txtPassword.setText(rs.getString("password"));
            } else {
                JOptionPane.showMessageDialog(this, "User not found!");
            }

            rs.close();
            pst.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ✅ Update user details in employee1 table
    private void updateUser() {
        String name = txtName.getText().trim();
        String mob_no = txtMobile.getText().trim();
        String address = txtAddress.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (name.isEmpty() || mob_no.isEmpty() || address.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "UPDATE employee1 SET name=?, mob_no=?, Address=?, password=? WHERE email=?";
            PreparedStatement pst = conn.prepareStatement(query);

            pst.setString(1, name);
            pst.setString(2, mob_no);
            pst.setString(3, address);
            pst.setString(4, password);
            pst.setString(5, userEmail);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Profile updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Update failed. User not found!");
            }

            pst.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error updating data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserUpdate("user@example.com").setVisible(true));
    }
}
