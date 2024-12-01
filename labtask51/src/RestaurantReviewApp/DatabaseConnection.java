/**
 *
 * @author IO
 */

package RestaurantReviewApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Update the schema name to 'newreview'
    private static final String URL = "jdbc:mysql://localhost:3306/newreview";  // New schema name is 'newreview'
    private static final String USER = "root";  // Username is 'root'
    private static final String PASSWORD = "venti";  // Password is 'venti'

    // Static method to establish a connection
    public static Connection connect() throws SQLException {
        try {
            // Ensure the JDBC driver is loaded (optional with modern JDKs)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found!", e);
        }

        // Return the database connection
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Optional utility to close a connection
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Failed to close the connection: " + e.getMessage());
            }
        }
    }
}
