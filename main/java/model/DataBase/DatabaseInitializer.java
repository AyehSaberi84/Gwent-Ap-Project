package model.DataBase;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseInitializer {
    public static void main(String[] args) {
        createUsersTable();
    }

    public static void createUsersTable() {
        // Establish database connection
        try (Connection connection = DatabaseConnector.connect()) {
            if (connection != null) {
                // SQL statement for creating a new table
                String sql = """
             CREATE TABLE IF NOT EXISTS users (
                 id INTEGER PRIMARY KEY AUTOINCREMENT,
                 username TEXT NOT NULL UNIQUE,
                 password TEXT NOT NULL,
                 nickname TEXT,
                 email TEXT,
                 answer TEXT
             );""";


                // Create table
                try (Statement statement = connection.createStatement()) {
                    statement.execute(sql);
                } catch (SQLException e) {
                    System.out.println("Error creating table: " + e.getMessage());
                }
            } else {
                System.out.println("Failed to make connection to database.");
            }
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
    }

}
