package ru.anafro.quark.server.plugins.events;

import ru.anafro.quark.server.plugins.Plugin;

public class PluginCrashed extends Event {
    private final Plugin plugin;
    private final Throwable reason;

    public PluginCrashed(Plugin plugin, Throwable reason) {
        this.plugin = plugin;
        this.reason = reason;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public Throwable getReason() {
        return reason;
    }
}
