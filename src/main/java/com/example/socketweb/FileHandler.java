package com.example.socketweb;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileHandler {
    private String baseDirectory;

    public FileHandler(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    public void downloadFile(String fileName, OutputStream outputStream) throws IOException {
        Path filePath = Path.of(baseDirectory, fileName);
        File file = filePath.toFile();

        if (file.exists() && file.isFile()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                Files.copy(filePath, outputStream);
            }
        }
    }
}
