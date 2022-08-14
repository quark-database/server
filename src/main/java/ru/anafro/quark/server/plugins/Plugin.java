package ru.anafro.quark.server.plugins;

import ru.anafro.quark.server.logging.Logger;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class Plugin {
    protected final String name;
    protected final String author;
    protected final Logger logger;

    public Plugin(String pluginName, String pluginAuthor) {
        this.name = pluginName;
        this.author = pluginAuthor;
        this.logger = new Logger("Plugin %s".formatted(quoted(name)));
    }

    public void onEnable() {
        //
    }

    public String getName() {
        return name;
    }

    public Logger getLogger() {
        return logger;
    }

    public String getAuthor() {
        return author;
    }
}
