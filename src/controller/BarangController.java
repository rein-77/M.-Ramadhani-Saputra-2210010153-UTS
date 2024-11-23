
package controller;

import dao.BarangDAO;
import model.Barang;
import java.sql.SQLException;
import java.util.List;

public class BarangController {
    private final BarangDAO barangDAO;
    
    public BarangController() {
        this.barangDAO = new BarangDAO();
    }
    
    public void addBarang(String nama, String kategori, int jumlah, String kondisi, String lokasi) throws SQLException {
        Barang barang = new Barang(0, nama, kategori, jumlah, kondisi, lokasi);
        barangDAO.add(barang);
    }
    
    public void updateBarang(int id, String nama, String kategori, int jumlah, String kondisi, String lokasi) throws SQLException {
        Barang barang = new Barang(id, nama, kategori, jumlah, kondisi, lokasi);
        barangDAO.update(barang);
    }
    
    public void deleteBarang(int id) throws SQLException {
        barangDAO.delete(id);
    }
    
    public List<Barang> searchBarang(String keyword) throws SQLException {
        return barangDAO.search(keyword);
    }
    
    public List<Barang> getAllBarang() throws SQLException {
        return barangDAO.getAll();
    }
}