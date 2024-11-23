package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Barang;
import java.io.*;
import java.lang.reflect.Type;
import java.util.List;
import java.util.ArrayList;

public class JsonUtil {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Type LIST_TYPE = new TypeToken<List<Barang>>() {
    }.getType();

    public static void exportToJson(List<Barang> barangList, String filePath) throws IOException {
        String json = gson.toJson(barangList, LIST_TYPE);
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(json);
        }
    }

    public static List<Barang> importFromJson(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            List<Barang> barangList = gson.fromJson(reader, LIST_TYPE);
            return barangList != null ? barangList : new ArrayList<>();
        }
    }
}
