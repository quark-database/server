package ru.anafro.quark.server.networking;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import ru.anafro.quark.server.databases.data.files.Savable;
import ru.anafro.quark.server.networking.exceptions.ServerConfigurationCannotBeSavedException;

import java.io.FileWriter;
import java.io.IOException;

public class ServerConfiguration implements Savable {
    @Expose
    private transient String path = null;

    @SerializedName(value = "name", alternate = {"server-name", "serverName"})
    private String name;

    @SerializedName(value = "port")
    private int port;

    @SerializedName(value = "strhash", alternate = {"string-hashing-function", "stringHashingFunction"})
    private String stringHashingFunction;

    @SerializedName(value = "inthash", alternate = {"integer-hashing-function", "integerHashingFunction"})
    private String integerHashingFunction;

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

    public void setPath(String path) {
        this.path = path;
    }

    public void save() {
        try {
            var gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(this, new FileWriter(path));
        } catch (IOException exception) {
            throw new ServerConfigurationCannotBeSavedException(exception);
        }
    }

    public String getPath() {
        return path;
    }

    public String getStringHashingFunction() {
        return stringHashingFunction;
    }

    public String getIntegerHashingFunction() {
        return integerHashingFunction;
    }

    public void setStringHashingFunction(String stringHashingFunction) {
        this.stringHashingFunction = stringHashingFunction;
    }

    public void setIntegerHashingFunction(String integerHashingFunction) {
        this.integerHashingFunction = integerHashingFunction;
    }
}
