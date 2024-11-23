package controller;

import dao.BarangDAO;
import model.Barang;
import util.JsonUtil;
import java.sql.SQLException;
import java.util.List;
import java.io.IOException;
import java.io.File;

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

    public void exportToJson(String fileName) throws SQLException, IOException {
        List<Barang> allBarang = barangDAO.getAll();
        String dbPath = System.getProperty("user.dir") + File.separator + "db";
        String fullPath = dbPath + File.separator + fileName;
        JsonUtil.exportToJson(allBarang, fullPath);
    }

    public void importFromJson(String fileName) throws SQLException, IOException {
        String dbPath = System.getProperty("user.dir") + File.separator + "db";
        String fullPath = dbPath + File.separator + fileName;
        List<Barang> barangList = JsonUtil.importFromJson(fullPath);

        int imported = 0;
        int skipped = 0;

        for (Barang barang : barangList) {
            if (!barangDAO.isBarangExists(barang)) {
                barangDAO.add(barang);
                imported++;
            } else {
                skipped++;
            }
        }

        if (skipped > 0) {
            throw new SQLException("Impor selesai: " + imported + " data ditambahkan, "
                    + skipped + " data dilewati karena duplikat");
        }
    }
}
