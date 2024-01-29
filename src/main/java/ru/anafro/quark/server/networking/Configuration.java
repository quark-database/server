package ru.anafro.quark.server.networking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import ru.anafro.quark.server.console.Console;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.utils.files.JsonFile;

import static ru.anafro.quark.server.utils.time.TimeSpan.seconds;

public class Configuration {
    @SerializedName("stringHashingFunction")
    private final String stringHashingFunction = "default";
    @SerializedName("integerHashingFunction")
    private final String integerHashingFunction = "default";
    @Expose
    private transient JsonFile file;
    @SerializedName("name")
    private String name = "Unnamed Quark Server";
    @SerializedName("port")
    private int port = 10000;

    public static Configuration load(String path) {
        var file = new JsonFile(path);

        try {
            var configuration = file.tryRead(Configuration.class).orElseGet(Configuration::new);
            configuration.setFile(file);

            return configuration;
        } catch (Exception exception) {
            Quark.error(STR."""
                The \{file.getName()} contains a JSON error:
                <red>\{exception.getMessage()}</>

                """);

            Console.sleep("The server will start with default configuration...", seconds(15));

            var configuration = new Configuration();
            configuration.setFile(file);

            return configuration;
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
        file.write(this, Configuration.class);
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

    private void setFile(JsonFile file) {
        this.file = file;
    }
}
