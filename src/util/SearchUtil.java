
package util;

public class SearchUtil {
    public static String getSearchInstructions() {
        return "Petunjuk Pencarian:\n" +
               "- Masukkan nama barang, kategori, atau lokasi\n" +
               "- Pencarian tidak bersifat case-insensitive\n" +
               "- Minimal 3 karakter untuk hasil yang lebih akurat\n" +
               "- Contoh: 'laptop', 'elektronik', 'ruang a'";
    }
    
    public static boolean isValidSearchTerm(String term) {
        return term != null && !term.trim().isEmpty();
    }
}