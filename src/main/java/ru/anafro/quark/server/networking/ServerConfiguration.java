package ru.anafro.quark.server.networking;

public class ServerConfiguration {
    private String name;
    private String token;
    private int port;

    public String getName() {
        return name;
    }

    public int getPort() {
        return port;
    }

    public String getToken() {
        return token;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
