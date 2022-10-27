package ru.anafro.quark.server.networking;

import com.google.gson.Gson;
import ru.anafro.quark.server.networking.exceptions.ServerConfigurationCannotBeLoadedException;

import java.io.FileReader;
import java.io.IOException;

public class ServerConfigurationLoader {
    /**
     * It loads a JSON file and returns a ServerConfiguration object
     * 
     * @param path The path to the configuration file.
     * @return A ServerConfiguration object.
     */
    public ServerConfiguration load(String path) {
        try {
            var gson = new Gson();
            return gson.fromJson(new FileReader(path), ServerConfiguration.class);
        } catch(IOException exception) {
            throw new ServerConfigurationCannotBeLoadedException(path, exception);
        }
    }
}
