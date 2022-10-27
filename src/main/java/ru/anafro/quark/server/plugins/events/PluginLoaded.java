package ru.anafro.quark.server.plugins.events;

import ru.anafro.quark.server.plugins.Plugin;

public class PluginLoaded extends Event {
    private final Plugin plugin;

    public PluginLoaded(Plugin plugin) {
        this.plugin = plugin;
    }

    public Plugin getPlugin() {
        return plugin;
    }
}
