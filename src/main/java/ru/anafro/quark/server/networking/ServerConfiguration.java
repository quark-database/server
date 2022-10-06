package ru.anafro.quark.server.networking;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import ru.anafro.quark.server.databases.data.files.Savable;
import ru.anafro.quark.server.networking.exceptions.ServerConfigurationCannotBeSavedException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServerConfiguration implements Savable {
    private String path = null;
    private String name;
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
            var options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            options.setPrettyFlow(true);

            var yaml = new Yaml(options);

            Map<String, String> mappedConfiguration = new HashMap<>();
            yaml.dump(mappedConfiguration, new FileWriter(path));
        } catch (IOException exception) {
            throw new ServerConfigurationCannotBeSavedException(exception);
        }
    }
}
