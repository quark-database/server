package ru.anafro.quark.server.api;

import ru.anafro.quark.server.console.CommandLoop;
import ru.anafro.quark.server.multithreading.AsyncServicePool;
import ru.anafro.quark.server.networking.Server;
import ru.anafro.quark.server.plugins.PluginManager;

public final class Quark {
    public static final String NAME = "Quark";
    private final static Server server = new Server();
    private final static PluginManager pluginManager = new PluginManager();
    private final static CommandLoop commands = new CommandLoop(server);
    private final static AsyncServicePool pool = new AsyncServicePool(server, commands);

    private Quark() {
        //
    }

    public static void init() {
        pluginManager.loadPlugins();
        pool.run();
    }

    public static Server server() {
        return server;
    }

    public static PluginManager plugins() {
        return pluginManager;
    }

    public static CommandLoop commands() {
        return commands;
    }
}
