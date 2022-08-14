package ru.anafro.quark.server.plugins.exceptions;

import java.nio.file.Path;

public class PluginLoadingException extends PluginException {
    public PluginLoadingException(Path path, Exception causedBy) {
        super("Loading of the plugin %s is failed, because of %s occurred: %s".formatted(
                path.toAbsolutePath().toString(),
                causedBy.getClass().getSimpleName(),
                causedBy.getMessage()
        ));
        initCause(causedBy);
    }
}
