/**
 * Kelas akses data untuk operasi CRUD barang.
 * Menangani semua interaksi dengan database untuk entitas Barang.
 */
/**
 * Kelas DAO (Data Access Object) untuk mengelola operasi database pada entitas Barang.
 * Kelas ini menangani semua interaksi dengan database untuk tabel barang, termasuk
 * operasi CRUD (Create, Read, Update, Delete) dan pencarian.
 * 
 * Fitur utama:
 * - Validasi data barang sebelum operasi database
 * - Pengecekan duplikasi barang
 * - Operasi CRUD untuk data barang
 * - Pencarian barang berdasarkan keyword (case-insensitive)
 * 
 * Penggunaan:
 * BarangDAO dao = new BarangDAO();
 * dao.add(barang);     // Menambah barang baru
 * dao.update(barang);  // Memperbarui data barang
 * dao.delete(id);      // Menghapus barang
 * dao.getAll();        // Mendapatkan semua data barang
 * dao.search(keyword); // Mencari barang berdasarkan keyword
 * 
 * @see Barang
 * @see DatabaseConnection
 */

package dao;

import model.Barang;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;


public class BarangDAO {

    private final Connection conn;

    public BarangDAO() {
        conn = DatabaseConnection.getConnection();
    }

    /**
     * Memvalidasi data barang sebelum operasi database.
     * @throws SQLException jika validasi gagal
     */
    private void validateBarang(Barang barang) throws SQLException {
        if (barang.getNama() == null || barang.getNama().trim().isEmpty()) {
            throw new SQLException("Nama barang tidak boleh kosong");
        }
        if (barang.getJumlah() < 0) {
            throw new SQLException("Jumlah barang tidak boleh negatif");
        }
        if (barang.getKondisi() == null || barang.getKondisi().trim().isEmpty()) {
            throw new SQLException("Kondisi barang harus dipilih");
        }
    }

    /**
     * Memeriksa keberadaan barang berdasarkan nama, kategori, dan lokasi.
     * Pemeriksaan bersifat case-insensitive.
     * @param barang objek barang yang akan diperiksa
     * @return true jika ditemukan barang yang sama
     */
    public boolean isBarangExists(Barang barang) throws SQLException {
        String sql = "SELECT COUNT(*) FROM barang WHERE "
                + "LOWER(nama) = LOWER(?) AND "
                + "LOWER(kategori) = LOWER(?) AND "
                + "LOWER(lokasi) = LOWER(?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, barang.getNama().trim());
            pstmt.setString(2, barang.getKategori().trim());
            pstmt.setString(3, barang.getLokasi().trim());

            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    /**
     * Menambah barang baru ke database
     */
    public void add(Barang barang) throws SQLException {
        validateBarang(barang);
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
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>();
        }

        List<Barang> results = new ArrayList<>();
        // Mengubah SQL untuk menggunakan fungsi LOWER() agar pencarian tidak peka huruf besar/kecil
        String sql = "SELECT * FROM barang WHERE "
                + "LOWER(nama) LIKE LOWER(?) OR "
                + "LOWER(kategori) LIKE LOWER(?) OR "
                + "LOWER(lokasi) LIKE LOWER(?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String term = "%" + keyword.toLowerCase() + "%";
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
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
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

    public void update(Barang barang) throws SQLException {
        validateBarang(barang);
        String sql = "UPDATE barang SET nama=?, kategori=?, jumlah=?, kondisi=?, lokasi=? WHERE id=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, barang.getNama());
            pstmt.setString(2, barang.getKategori());
            pstmt.setInt(3, barang.getJumlah());
            pstmt.setString(4, barang.getKondisi());
            pstmt.setString(5, barang.getLokasi());
            pstmt.setInt(6, barang.getId());
            pstmt.executeUpdate();
        }
    }

}
