package EMP;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class ViewEmp extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;

    private static final String URL = "jdbc:mysql://localhost:3306/empdb";
    private static final String USER = "root";
    private static final String PASSWORD = "Samrin@123";

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ViewEmp frame = new ViewEmp();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ViewEmp() {
        setTitle("Employee Details");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 500);
        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel lblTitle = new JLabel("Employee Details", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblTitle.setBounds(300, 10, 400, 30);
        contentPane.add(lblTitle);

        // Table
        table = new JTable();
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 50, 960, 350);
        contentPane.add(scrollPane);

        // Back Button
        JButton btnBack = new JButton("Back");
        btnBack.setForeground(Color.WHITE);
        btnBack.setBackground(Color.BLACK);
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnBack.setBounds(420, 410, 120, 30);
        contentPane.add(btnBack);

        btnBack.addActionListener(e -> {
            new AdminDashboard().setVisible(true); // Replace with your dashboard class
            dispose();
        });

        loadData();
    }

    private void loadData() {
        // Columns exactly matching employee1 table
        String[] columns = {"Emp ID", "Name", "Email", "Password", "Role", "Father Name", "Address", "Designation", "Mob No", "Higher Edu", "DOB", "Aadhar No", "Salary"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);
        table.setModel(model);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT * FROM employee1";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Object[] row = new Object[13];
                row[0] = rs.getInt("emp_id");
                row[1] = rs.getString("name");
                row[2] = rs.getString("email");
                row[3] = rs.getString("password");
                row[4] = rs.getString("role");
                row[5] = rs.getString("Father_name");
                row[6] = rs.getString("Address");
                row[7] = rs.getString("Designation");
                row[8] = rs.getString("mob_no");
                row[9] = rs.getString("Higher");
                row[10] = rs.getDate("DOB");
                row[11] = rs.getString("Aadhar_no");
                row[12] = rs.getString("Salary");
                model.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
        }
    }
}
