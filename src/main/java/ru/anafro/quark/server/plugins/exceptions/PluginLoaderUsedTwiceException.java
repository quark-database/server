package ru.anafro.quark.server.plugins.exceptions;

public class PluginLoaderUsedTwiceException extends PluginException {
    public PluginLoaderUsedTwiceException() {
        super("The plugin loader cannot be used twice. Remove an additional usage of loader.loadPlugins()");
    }
}
