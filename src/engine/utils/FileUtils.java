package engine.utils;

import main.Main;

import java.io.*;

public class FileUtils {
    public static String loadAsString(String path) {
        StringBuilder result = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(path).getAbsolutePath()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Couldn't find the file at " + path);
        }

        return result.toString();
    }
}
