package ru.anafro.quark.server.networking;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.error.YAMLException;
import ru.anafro.quark.server.networking.exceptions.BadServerConfigurationFormatException;
import ru.anafro.quark.server.networking.exceptions.ServerConfigurationCannotBeLoadedException;

import java.io.FileInputStream;
import java.io.IOException;

public class ServerConfigurationLoader {
    /**
     * It loads a YAML file and returns a ServerConfiguration object
     * 
     * @param path The path to the configuration file.
     * @return A ServerConfiguration object.
     */
    public ServerConfiguration load(String path) {
        Yaml yaml = new Yaml(new Constructor(ServerConfiguration.class));

        try(FileInputStream configurationFileInputStream = new FileInputStream(path)) {
            return yaml.load(configurationFileInputStream);
        } catch(IOException exception) {
            throw new ServerConfigurationCannotBeLoadedException(path, exception);
        } catch(YAMLException exception) {
            throw new BadServerConfigurationFormatException(path, exception);
        }
    }
}
