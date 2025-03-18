package com.example.data_compression.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHandler {
    public String readTextFile(String path) {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8)) {
            StringBuilder contentBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append("\n"); // Add line separator
            }
            String content = contentBuilder.toString();
            System.out.println(content);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error";
        }

    }
}
