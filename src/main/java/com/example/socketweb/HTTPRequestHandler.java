package com.example.socketweb;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HTTPRequestHandler {
    private String baseDirectory;
    private Map<String, String> mimeTypes;

    public HTTPRequestHandler(String baseDirectory) {
        this.baseDirectory = baseDirectory;
        this.mimeTypes = new HashMap<>();
        initializeMimeTypes();
    }

    public String handleRequest(String request) {
        String[] requestLines = request.split("\\r\\n");
        String[] requestParts = requestLines[0].split("\\s+");
        String method = requestParts[0];
        String url = requestParts[1];

        if (method.equals("GET")) {
            return handleGETRequest(url);
        }

        return "HTTP/1.1 400 Bad Request\r\nContent-Length: 0\r\n\r\n";
    }

    private String handleGETRequest(String url) {
        String filePath = baseDirectory + url;

        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            String mimeType = getMimeType(file.getName());
            try {
                byte[] fileBytes = readFileBytes(file);
                String responseHeaders = "HTTP/1.1 200 OK\r\nContent-Length: " + file.length() + "\r\nContent-Type: " + mimeType + "\r\n\r\n";
                return responseHeaders + new String(fileBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "HTTP/1.1 404 Not Found\r\nContent-Length: 0\r\n\r\n";
    }

    private byte[] readFileBytes(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[(int) file.length()];
            fis.read(buffer);
            return buffer;
        }
    }

    private String getMimeType(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            String extension = fileName.substring(dotIndex + 1).toLowerCase();
            return mimeTypes.getOrDefault(extension, "application/octet-stream");
        }
        return "application/octet-stream";
    }

    private void initializeMimeTypes() {
        mimeTypes.put("html", "text/html");
        mimeTypes.put("css", "text/css");
        mimeTypes.put("js", "text/javascript");
        mimeTypes.put("json", "application/json");
        mimeTypes.put("png", "image/png");
        mimeTypes.put("jpg", "image/jpeg");
        mimeTypes.put("jpeg", "image/jpeg");
        mimeTypes.put("gif", "image/gif");
        mimeTypes.put("pdf", "application/pdf");
        mimeTypes.put("txt", "text/plain");
    }
}
