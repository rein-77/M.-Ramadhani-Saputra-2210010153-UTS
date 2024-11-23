/**
 * Kelas utilitas untuk mengelola konversi data ke dan dari format JSON.
 * Menyediakan fungsi untuk ekspor dan impor data barang menggunakan library Gson.
 * 
 * Fitur utama:
 * - Konversi List<Barang> ke JSON dan sebaliknya
 * - Ekspor data barang ke file JSON
 * - Impor data barang dari file JSON
 * 
 */
package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Barang;
import java.io.*;
import java.lang.reflect.Type;
import java.util.List;
import java.util.ArrayList;

/**
 * Utilitas untuk konversi data ke format JSON dan sebaliknya.
 * Mendukung ekspor dan impor data barang ke file.
 */
public class JsonUtil {

    // Inisialisasi Gson untuk konversi JSON
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Type LIST_TYPE = new TypeToken<List<Barang>>() {
    }.getType();

    /**
     * Mengkonversi dan menyimpan daftar barang ke file JSON.
     * @param barangList daftar barang yang akan diekspor
     * @param filePath lokasi file tujuan
     */
    public static void exportToJson(List<Barang> barangList, String filePath) throws IOException {
        String json = gson.toJson(barangList, LIST_TYPE);
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(json);
        }
    }

    /**
     * Membaca dan mengkonversi file JSON menjadi daftar barang.
     * @param filePath lokasi file JSON sumber
     * @return daftar barang dari file JSON
     */
    public static List<Barang> importFromJson(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            List<Barang> barangList = gson.fromJson(reader, LIST_TYPE);
            return barangList != null ? barangList : new ArrayList<>();
        }
    }
}
