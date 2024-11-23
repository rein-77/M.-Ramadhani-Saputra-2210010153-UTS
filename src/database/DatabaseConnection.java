package database;

import java.sql.*;
import java.io.File;

public class DatabaseConnection {
    private static Connection connection = null;
    
    private DatabaseConnection() {} // Private constructor to prevent instantiation
    
    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Get the project's root directory
                String userDir = System.getProperty("user.dir");
                // Create db directory if it doesn't exist
                File dbDir = new File(userDir, "db");
                if (!dbDir.exists()) {
                    dbDir.mkdir();
                }
                // Create full path to database file
                String dbPath = new File(dbDir, "inventory.db").getAbsolutePath();
                // Connect to database
                String url = "jdbc:sqlite:" + dbPath;
                connection = DriverManager.getConnection(url);
                createTableIfNotExists();
            } catch (SQLException e) {
                System.err.println("Database connection error: " + e.getMessage());
            }
        }
        return connection;
    }
    
    private static void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS barang ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "nama TEXT NOT NULL,"
            + "kategori TEXT NOT NULL,"
            + "jumlah INTEGER NOT NULL,"
            + "kondisi TEXT NOT NULL,"
            + "lokasi TEXT NOT NULL"
            + ")";
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }
}