package ru.anafro.quark.server.files;

import ru.anafro.quark.server.utils.files.Directory;

import static ru.anafro.quark.server.utils.files.filters.ExtensionInclusionFileFilter.withExtension;

public final class PluginDirectory extends Directory {
    private static final PluginDirectory instance = new PluginDirectory();

    private PluginDirectory() {
        super("Plugins", withExtension("jar"));
    }

    public static PluginDirectory getInstance() {
        return instance;
    }
}
