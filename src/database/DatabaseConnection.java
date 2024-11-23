/**
 * Kelas pengelola koneksi database SQLite untuk aplikasi inventaris barang.
 * Menggunakan pola Singleton untuk memastikan hanya ada satu koneksi database yang aktif.
 * Database akan disimpan dalam file inventory.db di folder db pada direktori proyek.
 * 
 * Fitur utama:
 * - Membuat koneksi tunggal ke database SQLite
 * - Membuat folder db jika belum ada
 * - Membuat tabel barang secara otomatis jika belum ada
 * - Menyediakan akses ke koneksi database melalui getConnection()
 *
 * Struktur tabel barang:
 * - id: Primary key, auto increment
 * - nama: Nama barang (TEXT)
 * - kategori: Kategori barang (TEXT) 
 * - jumlah: Jumlah stok barang (INTEGER)
 * - kondisi: Kondisi barang (TEXT)
 * - lokasi: Lokasi penyimpanan barang (TEXT)
 *
 */
package database;

import java.sql.*;
import java.io.File;

/**
 * Kelas pengelola koneksi database SQLite.
 * Menggunakan pola Singleton untuk memastikan hanya ada satu koneksi database.
 */
public class DatabaseConnection {

    private static Connection connection = null;

    private DatabaseConnection() {
    } // Private constructor to prevent instantiation

    /**
     * Mendapatkan koneksi database yang aktif.
     * Jika koneksi belum ada, akan dibuat koneksi baru.
     * @return objek Connection untuk operasi database
     */
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

    /**
     * Membuat tabel barang jika belum ada di database.
     * Tabel ini menyimpan semua data inventaris barang.
     */
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
