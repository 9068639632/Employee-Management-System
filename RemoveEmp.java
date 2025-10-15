package EMP;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class RemoveEmp extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField empIdField;

    private static final String URL = "jdbc:mysql://localhost:3306/empdb";
    private static final String USER = "root";
    private static final String PASSWORD = "Samrin@123";

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                RemoveEmp frame = new RemoveEmp();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public RemoveEmp() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel lblTitle = new JLabel("Remove Employee");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblTitle.setBounds(150, 10, 250, 30);
        contentPane.add(lblTitle);

        JLabel lblEmpId = new JLabel("Enter Employee ID:");
        lblEmpId.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblEmpId.setBounds(50, 70, 150, 25);
        contentPane.add(lblEmpId);

        empIdField = new JTextField();
        empIdField.setBounds(200, 70, 150, 25);
        contentPane.add(empIdField);
        empIdField.setColumns(10);

        JButton btnRemove = new JButton("Remove");
        btnRemove.setForeground(Color.WHITE);
        btnRemove.setBackground(Color.RED);
        btnRemove.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnRemove.setBounds(150, 130, 100, 30);
        contentPane.add(btnRemove);

        JButton btnBack = new JButton("Back");
        btnBack.setForeground(Color.WHITE);
        btnBack.setBackground(Color.BLACK);
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnBack.setBounds(270, 130, 100, 30);
        contentPane.add(btnBack);

        // Actions
        btnRemove.addActionListener(e -> removeEmployee());
        btnBack.addActionListener(e -> {
            new AdminDashboard().setVisible(true);
            dispose();
        });

        setPreferredSize(new Dimension(500, 250));
        pack();
        setLocationRelativeTo(null);
    }

    private void removeEmployee() {
        String empIdText = empIdField.getText().trim();
        if (empIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Employee ID!");
            return;
        }

        try {
            int empId = Integer.parseInt(empIdText);

            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                // Check if employee exists
                String checkQuery = "SELECT * FROM employee1 WHERE emp_id = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
                checkStmt.setInt(1, empId);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    // Confirm deletion
                    int confirm = JOptionPane.showConfirmDialog(this,
                            "Are you sure you want to delete Employee ID " + empId + "?",
                            "Confirm Delete", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        String deleteQuery = "DELETE FROM employee1 WHERE emp_id = ?";
                        PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
                        deleteStmt.setInt(1, empId);
                        int deleted = deleteStmt.executeUpdate();

                        if (deleted > 0) {
                            JOptionPane.showMessageDialog(this, "Employee with ID " + empId + " removed successfully!");
                            empIdField.setText("");
                        } else {
                            JOptionPane.showMessageDialog(this, "Failed to remove employee. Try again.");
                        }
                        deleteStmt.close();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Employee ID not found!");
                }

                rs.close();
                checkStmt.close();
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric Employee ID!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
        }
    }
}
