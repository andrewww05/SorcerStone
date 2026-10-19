import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Main {

    private static final Path FILE = Path.of("src/txt/harry.txt");
    private static final int TOP = 30;

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();

        String text = Files.readString(FILE, StandardCharsets.ISO_8859_1);
        String[] words = splitToWords(text);
        Map<String, Integer> freq = countWords(words);

        printTop(freq, TOP);

        System.out.println("------");
        System.out.println(System.currentTimeMillis() - start + " ms");
    }

    private static String[] splitToWords(String text) {
        String clean = text.replaceAll("[^A-Za-z ]", " ").toLowerCase(Locale.ROOT).trim();
        return clean.split(" +");
    }

    private static Map<String, Integer> countWords(String[] words) {
        Map<String, Integer> freq = new HashMap<>();
        for (String word : words) {
            freq.merge(word, 1, Integer::sum);
        }
        return freq;
    }

    private static void printTop(Map<String, Integer> freq, int limit) {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(freq.entrySet());
        entries.sort(Map.Entry.<String, Integer>comparingByValue().reversed()
                .thenComparing(Map.Entry.comparingByKey()));

        for (int i = 0; i < Math.min(limit, entries.size()); i++) {
            Map.Entry<String, Integer> e = entries.get(i);
            System.out.println(e.getKey() + " " + e.getValue());
        }
    }
}
