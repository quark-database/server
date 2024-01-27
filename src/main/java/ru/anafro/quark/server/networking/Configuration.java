package ru.anafro.quark.server.networking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import ru.anafro.quark.server.networking.exceptions.ServerConfigurationCannotBeSavedException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Configuration {
    @SerializedName(value = "strhash", alternate = {"string-hashing-function", "stringHashingFunction"})
    private final String stringHashingFunction = "default";
    @SerializedName(value = "inthash", alternate = {"integer-hashing-function", "integerHashingFunction"})
    private final String integerHashingFunction = "default";
    @Expose
    private final transient String path = null;
    @SerializedName(value = "name", alternate = {"server-name", "serverName"})
    private String name = "Unnamed Quark Server";
    @SerializedName(value = "port")
    private int port = 10000;

    public static Configuration load(String path) {
        try {
            var gson = new Gson();
            return gson.fromJson(new FileReader(path), Configuration.class);
        } catch (IOException exception) {
            return new Configuration();
        }
    }

    /**
     * This function returns the name of the server
     *
     * @return The name of the server.
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void save() {
        if (path == null) {
            return;
        }

        try {
            var gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(this, new FileWriter(path));
        } catch (IOException exception) {
            throw new ServerConfigurationCannotBeSavedException(exception);
        }
    }

    public String getStringHashingFunction() {
        return stringHashingFunction;
    }

    public String getIntegerHashingFunction() {
        return integerHashingFunction;
    }

    public int getPort() {
        return port;
    }

    /**
     * This function sets the port number of the server
     *
     * @param port The port number to listen on.
     */
    public void setPort(int port) {
        this.port = port;
    }
}
