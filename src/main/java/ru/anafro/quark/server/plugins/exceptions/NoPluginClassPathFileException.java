package ru.anafro.quark.server.plugins.exceptions;

import ru.anafro.quark.server.plugins.PluginManager;

import java.nio.file.Path;

public class NoPluginClassPathFileException extends PluginException {
    public NoPluginClassPathFileException(Path pluginPath) {
        super("The plugin %s cannot be loaded, because there's no file %s in it. Please, create this file inside your plugin project and put the plugin class path into it. For example, org.yummy.MyYummyPlugin".formatted(
                pluginPath.toAbsolutePath(),
                PluginManager.PLUGIN_CLASS_PATH_FILE_NAME
        ));
    }
}
