package EMP;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.Font;
import java.awt.event.*;
import java.awt.Color;

public class AdminDashboard extends JFrame implements ActionListener {

    private JButton buttonAddEmp, buttonViewEmp, buttonRemoveEmp, buttonUpdateEmp, buttonLogout;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AdminDashboard window = new AdminDashboard();
                window.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AdminDashboard() {
        setTitle("Employee Management - Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(300, 100, 709, 491);
        getContentPane().setLayout(null);

        // 🟢 Add Employee Button
        buttonAddEmp = new JButton("Add Employee");
        buttonAddEmp.setFont(new Font("Tahoma", Font.BOLD, 13));
        buttonAddEmp.setBounds(53, 111, 172, 33);
        buttonAddEmp.addActionListener(this);
        getContentPane().add(buttonAddEmp);

        // 🟣 View Employees Button
        buttonViewEmp = new JButton("View Employees");
        buttonViewEmp.setFont(new Font("Tahoma", Font.BOLD, 13));
        buttonViewEmp.setBounds(53, 167, 172, 33);
        buttonViewEmp.addActionListener(this);
        getContentPane().add(buttonViewEmp);

        // 🔴 Remove Employee Button
        buttonRemoveEmp = new JButton("Remove Employee");
        buttonRemoveEmp.setFont(new Font("Tahoma", Font.BOLD, 13));
        buttonRemoveEmp.setBounds(53, 225, 172, 33);
        buttonRemoveEmp.addActionListener(this);
        getContentPane().add(buttonRemoveEmp);

        // 🟠 Update Employee Button
        buttonUpdateEmp = new JButton("Update Employee");
        buttonUpdateEmp.setFont(new Font("Tahoma", Font.BOLD, 13));
        buttonUpdateEmp.setBounds(53, 286, 172, 33);
        buttonUpdateEmp.addActionListener(this);
        getContentPane().add(buttonUpdateEmp);

        // 🔵 Logout Button
        buttonLogout = new JButton("Logout");
        buttonLogout.setFont(new Font("Tahoma", Font.BOLD, 13));
        buttonLogout.setBackground(new Color(255, 69, 0));
        buttonLogout.setForeground(Color.WHITE);
        buttonLogout.setBounds(560, 20, 100, 30);
        getContentPane().add(buttonLogout);

        // Logout Button Action
        buttonLogout.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout",
                    JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                // 👇 Login window open karo yahan
                new login().setVisible(true);
                dispose(); // Current window close
            }
        });

        // Optional Background Image
        JLabel backgroundLabel = new JLabel("");
        backgroundLabel.setIcon(new ImageIcon("C:\\Users\\DEll\\Downloads\\just2.jpg"));
        backgroundLabel.setBounds(0, 0, 683, 452);
        getContentPane().add(backgroundLabel);

        setLocationRelativeTo(null); // Center window
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        dispose(); // Close current Home window

        if (source == buttonAddEmp) {
             new AddEmp().setVisible(true);
        } else if (source == buttonViewEmp) {
             new ViewEmp().setVisible(true);
        } else if (source == buttonRemoveEmp) {
             new RemoveEmp().setVisible(true);
        } else if (source == buttonUpdateEmp) {
            new UpdateEmp().setVisible(true);
        }
    }
}
