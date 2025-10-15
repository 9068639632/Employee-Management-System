package EMP;

import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.io.FileOutputStream;
import java.sql.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class UserProfile extends JFrame {

    private String userEmail;
    private JLabel lblName, lblEmail, lblRole, lblDesignation, lblAddress, lblMobile;
    private JButton btnBack, btnLoad, btnSavePDF;

    private static final String URL = "jdbc:mysql://localhost:3306/empdb";
    private static final String USER = "root";
    private static final String PASSWORD = "Samrin@123";

    public UserProfile(String email) {
        this.userEmail = email;

        setTitle("Employee Profile");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(245, 250, 255));
        getContentPane().setLayout(null);

        JLabel title = new JLabel("My Profile", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(150, 20, 200, 30);
        getContentPane().add(title);

        lblName = createLabel("Name:", 80);
        lblEmail = createLabel("Email:", 115);
        lblRole = createLabel("Role:", 150);
        lblDesignation = createLabel("Designation:", 185);
        lblAddress = createLabel("Address:", 220);
        lblMobile = createLabel("Mobile:", 255);

        btnBack = new JButton("Back");
        btnBack.setBounds(107, 321, 120, 40);
        btnBack.setBackground(new Color(0, 153, 76));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFont(new Font("Arial", Font.BOLD, 14));
        getContentPane().add(btnBack);
/*
        btnLoad = new JButton("Load Data");
        btnLoad.setBounds(190, 320, 120, 40);
        btnLoad.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnLoad.setBackground(new Color(30, 144, 255));
        btnLoad.setForeground(Color.WHITE);
        getContentPane().add(btnLoad);
        */

        btnSavePDF = new JButton("Save as PDF");
        btnSavePDF.setBounds(274, 320, 120, 40);
        btnSavePDF.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnSavePDF.setBackground(new Color(128, 0, 128));
        btnSavePDF.setForeground(Color.WHITE);
        getContentPane().add(btnSavePDF);

        loadProfile();
       // btnLoad.addActionListener(e -> loadProfile());
        btnBack.addActionListener(e -> { new UserDashboard(userEmail).setVisible(true); dispose(); });
        btnSavePDF.addActionListener(e -> saveProfileAsPDF());
    }

    private JLabel createLabel(String text, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Arial", Font.BOLD, 16));
        lbl.setBounds(70, y, 350, 25);
        getContentPane().add(lbl);
        return lbl;
    }

    private void loadProfile() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT * FROM employee1 WHERE email = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, userEmail);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                lblName.setText("Name: " + rs.getString("name"));
                lblEmail.setText("Email: " + rs.getString("email"));
                lblRole.setText("Role: " + rs.getString("role"));
                lblDesignation.setText("Designation: " + (rs.getString("Designation") != null ? rs.getString("Designation") : "N/A"));
                lblAddress.setText("Address: " + (rs.getString("Address") != null ? rs.getString("Address") : "N/A"));
                lblMobile.setText("Mobile: " + (rs.getString("mob_no") != null ? rs.getString("mob_no") : "N/A"));
            } else {
                JOptionPane.showMessageDialog(this, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading profile: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveProfileAsPDF() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Profile as PDF");
        fileChooser.setSelectedFile(new java.io.File("EmployeeProfile.pdf"));
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();
            try {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(fileToSave));
                document.open();

                com.itextpdf.text.Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
                com.itextpdf.text.Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 14);

                document.add(new Paragraph("Employee Profile\n\n", titleFont));
                document.add(new Paragraph(lblName.getText(), normalFont));
                document.add(new Paragraph(lblEmail.getText(), normalFont));
                document.add(new Paragraph(lblRole.getText(), normalFont));
                document.add(new Paragraph(lblDesignation.getText(), normalFont));
                document.add(new Paragraph(lblAddress.getText(), normalFont));
                document.add(new Paragraph(lblMobile.getText(), normalFont));

                document.close();
                JOptionPane.showMessageDialog(this, "Profile saved at:\n" + fileToSave.getAbsolutePath());

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error saving PDF: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserProfile("user@gmail.com").setVisible(true));
    }
}
