package ru.anafro.quark.server.files;

import ru.anafro.quark.server.plugins.exceptions.PluginException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public final class Plugins {
    public static final String FOLDER = "Plugins/";

    private Plugins() {
        //
    }

    public static String get(String pluginName) {
        return FOLDER + pluginName;
    }

    public static void forEachPluginFile(Consumer<Path> pluginFileConsumer) {
        try (Stream<Path> paths = Files.walk(Paths.get(FOLDER))) {
            paths.filter(Files::isRegularFile).filter(path -> path.getFileName().toString().toLowerCase().endsWith(".jar")).forEach(pluginFileConsumer);
        } catch(IOException exception) {
            throw new PluginException("Plugins from the folder %s cannot be loaded, because of %s: %s".formatted(
                    quoted(FOLDER),
                    exception.getClass().getSimpleName(),
                    exception.getMessage()
            ));
        }
    }
}
