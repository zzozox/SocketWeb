package com.example.socketweb;

public class ServerConfig {
    private int serverPort;
    private String databaseConnection;

    public ServerConfig(int serverPort, String databaseConnection) {
        this.serverPort = serverPort;
        this.databaseConnection = databaseConnection;
    }

    public int getServerPort() {
        return serverPort;
    }

    public String getDatabaseConnection() {
        return databaseConnection;
    }
}
