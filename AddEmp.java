package EMP;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

@SuppressWarnings("serial")
public class AddEmp extends JFrame {

    private JTextField txtName, txtFather, txtAddress, txtEmail, txtMobile, txtDOB, txtAadhar, txtSalary;
    private JComboBox<String> comboDesignation, comboEducation, comboRole;
    private JPasswordField txtPassword;

    public AddEmp() {
        setTitle("Add Employee");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(300, 80, 800, 520);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        JLabel lblTitle = new JLabel("Add Employee Details", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setBounds(220, 20, 340, 40);
        lblTitle.setForeground(new Color(0, 51, 102));
        getContentPane().add(lblTitle);

        JLabel lblName = new JLabel("Employee Name:");
        lblName.setBounds(80, 90, 120, 25);
        getContentPane().add(lblName);
        txtName = new JTextField();
        txtName.setBounds(210, 90, 160, 25);
        getContentPane().add(txtName);

        JLabel lblFather = new JLabel("Father's Name:");
        lblFather.setBounds(80, 130, 120, 25);
        getContentPane().add(lblFather);
        txtFather = new JTextField();
        txtFather.setBounds(210, 130, 160, 25);
        getContentPane().add(txtFather);

        JLabel lblAddress = new JLabel("Address:");
        lblAddress.setBounds(80, 170, 120, 25);
        getContentPane().add(lblAddress);
        txtAddress = new JTextField();
        txtAddress.setBounds(210, 170, 160, 25);
        getContentPane().add(txtAddress);

        JLabel lblDesignation = new JLabel("Designation:");
        lblDesignation.setBounds(80, 210, 120, 25);
        getContentPane().add(lblDesignation);
        comboDesignation = new JComboBox<>(new String[]{"Manager", "Developer", "Designer", "Tester", "HR"});
        comboDesignation.setBounds(210, 210, 160, 25);
        getContentPane().add(comboDesignation);

        JLabel lblRole = new JLabel("Role:");
        lblRole.setBounds(80, 250, 120, 25);
        getContentPane().add(lblRole);
        comboRole = new JComboBox<>(new String[]{"Admin", "User"});
        comboRole.setBounds(210, 250, 160, 25);
        getContentPane().add(comboRole);

        JLabel lblEmail = new JLabel("Email ID:");
        lblEmail.setBounds(420, 90, 100, 25);
        getContentPane().add(lblEmail);
        txtEmail = new JTextField();
        txtEmail.setBounds(530, 90, 160, 25);
        getContentPane().add(txtEmail);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(420, 130, 100, 25);
        getContentPane().add(lblPassword);
        txtPassword = new JPasswordField();
        txtPassword.setBounds(530, 130, 160, 25);
        getContentPane().add(txtPassword);

        JLabel lblMobile = new JLabel("Mobile No:");
        lblMobile.setBounds(420, 170, 100, 25);
        getContentPane().add(lblMobile);
        txtMobile = new JTextField();
        txtMobile.setBounds(530, 170, 160, 25);
        getContentPane().add(txtMobile);

        JLabel lblEdu = new JLabel("Higher Edu:");
        lblEdu.setBounds(420, 210, 100, 25);
        getContentPane().add(lblEdu);
        comboEducation = new JComboBox<>(new String[]{"BBA", "BSC", "MA", "BTECH", "MTECH", "BCA", "Diploma"});
        comboEducation.setBounds(530, 210, 160, 25);
        getContentPane().add(comboEducation);

        JLabel lblDOB = new JLabel("DOB [yyyy-mm-dd]");
        lblDOB.setBounds(420, 250, 120, 25);
        getContentPane().add(lblDOB);
        txtDOB = new JTextField();
        txtDOB.setBounds(530, 250, 160, 25);
        getContentPane().add(txtDOB);

        JLabel lblAadhar = new JLabel("Aadhar No:");
        lblAadhar.setBounds(420, 290, 100, 25);
        getContentPane().add(lblAadhar);
        txtAadhar = new JTextField();
        txtAadhar.setBounds(530, 290, 160, 25);
        getContentPane().add(txtAadhar);

        JLabel lblSalary = new JLabel("Salary:");
        lblSalary.setBounds(420, 330, 100, 25);
        getContentPane().add(lblSalary);
        txtSalary = new JTextField();
        txtSalary.setBounds(530, 330, 160, 25);
        getContentPane().add(txtSalary);

        JButton btnSave = new JButton("Save");
        btnSave.setBounds(530, 390, 120, 35);
        getContentPane().add(btnSave);

        JButton btnCancel = new JButton("Clear");
        btnCancel.setBounds(342, 390, 120, 35);
        getContentPane().add(btnCancel);

        JButton btnBack = new JButton("Back");
        btnBack.setBounds(152, 390, 120, 35);
        getContentPane().add(btnBack);

        btnSave.addActionListener(e -> saveEmployee());
        btnCancel.addActionListener(e -> clearFields());
        btnBack.addActionListener(e -> {
            new AdminDashboard().setVisible(true);
            dispose();
        });
    }

    private void saveEmployee() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/empdb", "root", "Samrin@123")) {

            String query = "INSERT INTO employee1 (name, email, password, role, Father_name, Address, Designation, mob_no, Higher, DOB, Aadhar_no, Salary) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);

            pst.setString(1, txtName.getText());
            pst.setString(2, txtEmail.getText());
            pst.setString(3, new String(txtPassword.getPassword()));
            pst.setString(4, (String) comboRole.getSelectedItem());
            pst.setString(5, txtFather.getText());
            pst.setString(6, txtAddress.getText());
            pst.setString(7, (String) comboDesignation.getSelectedItem());
            pst.setString(8, txtMobile.getText());
            pst.setString(9, (String) comboEducation.getSelectedItem());
            pst.setString(10, txtDOB.getText());
            pst.setString(11, txtAadhar.getText());
            pst.setString(12, txtSalary.getText());

            int rows = pst.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "✅ Employee added successfully!");
                clearFields();
            }

        } catch (SQLException e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Database Error: " + e1.getMessage());
        }
    }

    private void clearFields() {
        txtName.setText("");
        txtFather.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
        txtMobile.setText("");
        txtDOB.setText("");
        txtAadhar.setText("");
        txtSalary.setText("");
        comboDesignation.setSelectedIndex(0);
        comboEducation.setSelectedIndex(0);
        comboRole.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddEmp().setVisible(true));
    }
}
