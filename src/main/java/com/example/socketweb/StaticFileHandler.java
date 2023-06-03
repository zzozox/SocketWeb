package com.example.socketweb;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class StaticFileHandler {
    private String baseDirectory;

    public StaticFileHandler(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    public byte[] readFile(String fileName) throws IOException {
        Path filePath = Path.of(baseDirectory, fileName);
        return Files.readAllBytes(filePath);
    }

    public void writeFile(String fileName, byte[] data) throws IOException {
        Path filePath = Path.of(baseDirectory, fileName);
        Files.write(filePath, data, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public boolean fileExists(String fileName) {
        Path filePath = Path.of(baseDirectory, fileName);
        return Files.exists(filePath) && Files.isRegularFile(filePath);
    }
}
