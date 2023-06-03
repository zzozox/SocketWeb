package com.example.socketweb;

import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer {
    private ServerSocket serverSocket;
    private ExecutorService threadPool;

    public WebServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            threadPool = Executors.newFixedThreadPool(10); // 使用大小为 10 的线程池
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        System.out.println("WebServer started on port " + serverSocket.getLocalPort());

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                threadPool.execute(new RequestHandler(clientSocket));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        try {
            serverSocket.close();
            threadPool.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class RequestHandler implements Runnable {
        private Socket clientSocket;

        public RequestHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (
                    BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    OutputStreamWriter writer = new OutputStreamWriter(clientSocket.getOutputStream())
            ) {
                // 读取 HTTP 请求
                StringBuilder request = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null && !line.isEmpty()) {
                    request.append(line).append("\r\n");
                }

                // 处理请求
                String response = handleRequest(request.toString());

                // 发送 HTTP 响应
                writer.write(response);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private String handleRequest(String request) {
            // TODO: 处理请求逻辑，根据请求内容生成响应
            return "HTTP/1.1 200 OK\r\nContent-Length: 11\r\n\r\nHello World";
        }
    }


public static void main(String[] args) {
        WebServer server = new WebServer(8080);
        server.start();
    }
}
