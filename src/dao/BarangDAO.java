
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;
import model.Barang;

public class BarangDAO {
    private Connection conn;
    
    public BarangDAO() {
        conn = DatabaseConnection.getConnection();
    }
    
    public void add(Barang barang) throws SQLException {
        String sql = "INSERT INTO barang (nama, kategori, jumlah, kondisi, lokasi) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, barang.getNama());
            pstmt.setString(2, barang.getKategori());
            pstmt.setInt(3, barang.getJumlah());
            pstmt.setString(4, barang.getKondisi());
            pstmt.setString(5, barang.getLokasi());
            pstmt.executeUpdate();
        }
    }
    
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM barang WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
    
    public List<Barang> search(String keyword) throws SQLException {
        List<Barang> results = new ArrayList<>();
        String sql = "SELECT * FROM barang WHERE nama LIKE ? OR kategori LIKE ? OR lokasi LIKE ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String term = "%" + keyword + "%";
            pstmt.setString(1, term);
            pstmt.setString(2, term);
            pstmt.setString(3, term);
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                results.add(new Barang(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getString("kategori"),
                    rs.getInt("jumlah"),
                    rs.getString("kondisi"),
                    rs.getString("lokasi")
                ));
            }
        }
        return results;
    }
    
    public List<Barang> getAll() throws SQLException {
        List<Barang> results = new ArrayList<>();
        String sql = "SELECT * FROM barang";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                results.add(new Barang(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getString("kategori"),
                    rs.getInt("jumlah"),
                    rs.getString("kondisi"),
                    rs.getString("lokasi")
                ));
            }
        }
        return results;
    }
}