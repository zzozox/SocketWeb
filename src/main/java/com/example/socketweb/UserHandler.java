package com.example.socketweb;

import java.util.HashMap;
import java.util.Map;

public class UserHandler {
    private Map<String, String> users;

    public UserHandler() {
        this.users = new HashMap<>();
    }

    public boolean registerUser(String username, String password) {
        if (!users.containsKey(username)) {
            users.put(username, password);
            return true;
        }
        return false;
    }

    public boolean loginUser(String username, String password) {
        String storedPassword = users.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }

    public void logoutUser(String username) {
        // Perform any logout-related operations, if necessary
    }
}
