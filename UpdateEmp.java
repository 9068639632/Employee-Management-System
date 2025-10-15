package EMP;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateEmp extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField empIdField, nameField, fatherField, addressField, designationField, emailField,
            mobField, higherField, dobField, aadharField, salaryField;

    private static final String URL = "jdbc:mysql://localhost:3306/empdb";
    private static final String USER = "root";
    private static final String PASSWORD = "Samrin@123";

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UpdateEmp frame = new UpdateEmp();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public UpdateEmp() {
        setTitle("Update Employee");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel lblTitle = new JLabel("Update Employee Details");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblTitle.setBounds(160, 10, 300, 30);
        contentPane.add(lblTitle);

        JLabel lblEmpId = new JLabel("Enter Employee ID:");
        lblEmpId.setBounds(50, 60, 150, 25);
        contentPane.add(lblEmpId);

        empIdField = new JTextField();
        empIdField.setBounds(200, 60, 150, 25);
        contentPane.add(empIdField);

        JButton btnLoad = new JButton("Load Data");
        btnLoad.setBounds(380, 60, 120, 25);
        btnLoad.setBackground(new Color(0, 102, 204));
        btnLoad.setForeground(Color.WHITE);
        contentPane.add(btnLoad);

        // Fields
        nameField = createLabelAndField("Name:", 100);
        fatherField = createLabelAndField("Father Name:", 140);
        addressField = createLabelAndField("Address:", 180);
        designationField = createLabelAndField("Designation:", 220);
        emailField = createLabelAndField("Email:", 260);
        mobField = createLabelAndField("Mobile No:", 300);
        higherField = createLabelAndField("Higher Education:", 340);
        dobField = createLabelAndField("DOB (YYYY-MM-DD):", 380);
        aadharField = createLabelAndField("Aadhar No:", 420);
        salaryField = createLabelAndField("Salary:", 460);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setBounds(200, 510, 120, 30);
        btnUpdate.setBackground(new Color(0, 153, 76));
        btnUpdate.setForeground(Color.WHITE);
        contentPane.add(btnUpdate);

        JButton btnBack = new JButton("Back");
        btnBack.setBounds(350, 510, 120, 30);
        btnBack.setBackground(Color.BLACK);
        btnBack.setForeground(Color.WHITE);
        contentPane.add(btnBack);

        // Actions
        btnLoad.addActionListener(e -> loadEmployee());
        btnUpdate.addActionListener(e -> updateEmployee());
        btnBack.addActionListener(e -> {
            new AdminDashboard().setVisible(true);
            dispose();
        });
    }

    private JTextField createLabelAndField(String labelText, int yPos) {
        JLabel label = new JLabel(labelText);
        label.setBounds(50, yPos, 150, 25);
        contentPane.add(label);

        JTextField field = new JTextField();
        field.setBounds(200, yPos, 200, 25);
        contentPane.add(field);

        return field;
    }

    // ✅ Load Employee Data by emp_id
    private void loadEmployee() {
        String empIdText = empIdField.getText().trim();
        if (empIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Employee ID!");
            return;
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            int empId = Integer.parseInt(empIdText);

            String query = "SELECT * FROM employee1 WHERE emp_id = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, empId);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                nameField.setText(rs.getString("name"));
                fatherField.setText(rs.getString("Father_name"));
                addressField.setText(rs.getString("Address"));
                designationField.setText(rs.getString("Designation"));
                emailField.setText(rs.getString("email"));
                mobField.setText(rs.getString("mob_no"));
                higherField.setText(rs.getString("Higher"));
                dobField.setText(rs.getString("DOB"));
                aadharField.setText(rs.getString("Aadhar_no"));
                salaryField.setText(rs.getString("Salary"));
            } else {
                JOptionPane.showMessageDialog(this, "Employee not found!");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Employee ID! Please enter a number.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
        }
    }

    // ✅ Update Employee Data
    private void updateEmployee() {
        String empIdText = empIdField.getText().trim();
        if (empIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Employee ID!");
            return;
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            int empId = Integer.parseInt(empIdText);

            String query = "UPDATE employee1 SET name=?, Father_name=?, Address=?, Designation=?, email=?, mob_no=?, Higher=?, DOB=?, Aadhar_no=?, Salary=? WHERE emp_id=?";
            PreparedStatement pst = conn.prepareStatement(query);

            pst.setString(1, nameField.getText());
            pst.setString(2, fatherField.getText());
            pst.setString(3, addressField.getText());
            pst.setString(4, designationField.getText());
            pst.setString(5, emailField.getText());
            pst.setString(6, mobField.getText());
            pst.setString(7, higherField.getText());
            pst.setString(8, dobField.getText());
            pst.setString(9, aadharField.getText());
            pst.setString(10, salaryField.getText());
            pst.setInt(11, empId);

            int updated = pst.executeUpdate();
            if (updated > 0) {
                JOptionPane.showMessageDialog(this, "Employee updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Update failed! Employee not found.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Employee ID!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
        }
    }
}
