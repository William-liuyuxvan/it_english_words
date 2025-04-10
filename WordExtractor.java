import java.io.*;
import java.nio.file.*;

/**
 * @ClassName main
 * @Description TODO
 * @Author eeekuu
 * @Date 2025/4/10 16:48
 */
public class WordExtractor {

    public static void main(String[] args) {
        String inputFilePath = "{your words.txt file path}"; // "/words.txt"
        String outputFilePath = "{target path}"; // "/subwords.txt"

        try {
            extractSubWords(inputFilePath, outputFilePath);
        } catch (Exception e) {
            System.err.println("Error processing files: " + e.getMessage());
        }
    }

    // 读写文件
    private static void extractSubWords(String inputFilePath, String outputFilePath) throws IOException {
        Path inputPath = Paths.get(inputFilePath);
        Path outputPath = Paths.get(outputFilePath);

        try (
                BufferedReader br = new BufferedReader(new FileReader(inputPath.toFile()));
                BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath.toFile()))
                    ) {

            br.lines().forEach(str -> {
                if (!str.isEmpty() && Character.isDigit(str.charAt(0))) {
                    String subStr = extractSubString(str);
                    if (subStr != null) {
                        System.out.println(str);
                        System.out.println(subStr);

                        try {
                            bw.write(subStr);
                            bw.newLine();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        }
    }

    // 分割单词
    private static String extractSubString(String str) {
        int spaceIndex = str.indexOf(" ");
        int colonIndex = str.indexOf(":");
        if (spaceIndex != -1 && colonIndex != -1 && spaceIndex < colonIndex) {
            return str.substring(spaceIndex + 1, colonIndex);
        }
        return null;
    }
}
