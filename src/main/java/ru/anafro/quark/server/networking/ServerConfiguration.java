package ru.anafro.quark.server.networking;

public class ServerConfiguration {
    private String name;
    private String token;
    private int port;

    /**
     * This function returns the name of the server
     * 
     * @return The name of the server.
     */
    public String getName() {
        return name;
    }

    /**
     * This function returns the port number of the server
     * 
     * @return The port number.
     */
    public int getPort() {
        return port;
    }

    /**
     * This function returns the token of the server
     * 
     * @return The token is being returned.
     */
    public String getToken() {
        return token;
    }

    /**
     * This function sets the name of the server to the name passed in as a parameter
     * 
     * @param name The new server name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This function sets the port number of the server
     * 
     * @param port The port number to listen on.
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * This function sets the token to the token passed in
     * 
     * @param token The new token.
     */
    public void setToken(String token) {
        this.token = token;
    }
}
