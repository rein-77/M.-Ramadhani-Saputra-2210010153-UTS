/**
 * Kelas Barang merepresentasikan item dalam sistem inventaris.
 * Kelas ini menyimpan informasi detail tentang setiap barang, termasuk identifikasi unik,
 * karakteristik fisik, dan lokasi penyimpanan.
 */

package model;

/**
 * Model untuk menyimpan data barang inventaris.
 * Kelas ini berfungsi sebagai struktur data utama untuk menyimpan informasi barang.
 */
public class Barang {
    // Properti-properti barang
    private int id;                 // ID unik untuk identifikasi barang di database
    private String nama;            // Nama atau judul barang
    private String kategori;        // Kategori/jenis barang (mis: Elektronik, Furnitur)
    private int jumlah;            // Jumlah unit barang yang tersedia
    private String kondisi;         // Status kondisi barang (Baru/Bekas/Rusak)
    private String lokasi;          // Tempat penyimpanan barang

    /**
     * Konstruktor untuk membuat objek barang baru
     */
    public Barang(int id, String nama, String kategori, int jumlah, String kondisi, String lokasi) {
        this.id = id;
        this.nama = nama;
        this.kategori = kategori;
        this.jumlah = jumlah;
        this.kondisi = kondisi;
        this.lokasi = lokasi;
    }

    // Getter dan Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public String getKondisi() {
        return kondisi;
    }

    public void setKondisi(String kondisi) {
        this.kondisi = kondisi;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }
}
