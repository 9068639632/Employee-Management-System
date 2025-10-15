package EMP;

import java.sql.*;

public class conn {
    Connection c;
    Statement s;

    public conn() {
        try {
            // 1. Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. Establish the connection
            c = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/empdb", 
                    "root", 
                    "Samrin@123"
            );

            // 3. Create the statement
            s = c.createStatement();
            System.out.println("✅ Connection established successfully!");

        } catch (ClassNotFoundException e) {
            System.out.println("❌ JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Database connection error!");
            e.printStackTrace();
        }
    }

    // Optional test method
    public static void main(String[] args) {
        new conn();
    }
}
