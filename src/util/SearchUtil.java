/**
 * Utility class untuk membantu operasi pencarian dalam aplikasi.
 * Class ini menyediakan metode-metode statis untuk validasi dan petunjuk pencarian.
 */

/**
 * Memvalidasi term pencarian berdasarkan kriteria berikut:
 * - Tidak boleh null
 * - Tidak boleh string kosong setelah di-trim
 * - Minimal memiliki 3 karakter setelah di-trim
 *
 * @param term String yang akan divalidasi sebagai term pencarian
 * @return boolean true jika term valid, false jika tidak memenuhi kriteria
 */

package util;

public class SearchUtil {

    public static String getSearchInstructions() {
        return "Petunjuk Pencarian:\n"
                + "- Masukkan nama barang, kategori, atau lokasi\n"
                + "- Pencarian tidak bersifat case-sensitive\n"
                + "- Minimal 3 karakter untuk hasil yang lebih akurat\n"
                + "- Contoh: 'laptop', 'elektronik', 'ruang a'";
    }

    public static boolean isValidSearchTerm(String term) {
        return term != null && !term.trim().isEmpty() && term.trim().length() >= 3;
    }
}
