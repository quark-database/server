package ru.anafro.quark.server.plugins;

import ru.anafro.quark.server.files.PluginDirectory;
import ru.anafro.quark.server.logging.Logger;
import ru.anafro.quark.server.plugins.events.Event;
import ru.anafro.quark.server.utils.arrays.Arrays;
import ru.anafro.quark.server.utils.patterns.NamedObjectsList;
import ru.anafro.quark.server.utils.reflection.Reflection;

public final class PluginManager extends NamedObjectsList<Plugin> implements Iterable<Plugin> {
    public static final String PLUGIN_CLASS_PATH_FILE_NAME = "Plugin Class Path.txt";
    public static final Class<?>[] PLUGIN_EVENT_LISTENER_SIGNATURE = Arrays.<Class<?>>array(Event.class);
    private final PluginDirectory directory = PluginDirectory.getInstance();
    private final Logger logger = new Logger(this.getClass());
    private boolean arePluginsLoaded = false;

    public void load() {
        assert !arePluginsLoaded : "Plugin manager cannot load plugins twice.";

        directory.forEach(file -> {
            try {
                logger.info(STR."Loading the plugin \{file.getName()}...");

                var classLoader = Reflection.createURLClassLoader(getClass(), file);
                var classPath = Reflection.readResource(classLoader, PLUGIN_CLASS_PATH_FILE_NAME).strip();
                var pluginClass = Reflection.loadClass(Plugin.class, classLoader, classPath);
                var plugin = Reflection.newInstance(pluginClass);

                var pluginName = plugin.getName();
                var pluginAuthor = plugin.getAuthor();

                this.add(plugin);

                logger.info(STR."Plugin '\{pluginName}' by \{pluginAuthor} is loaded.");
            } catch (Exception exception) {
                logger.error(exception);
            }
        });

        arePluginsLoaded = true;
    }

    public void enableAll() {
        forEach(Plugin::onEnable);
    }

    public void fireEvent(Event event) {
        this.forEach(plugin -> {
            var pluginMethods = plugin.getClass().getDeclaredMethods();

            for (var method : pluginMethods) {
                if (!Reflection.hasAnnotation(method, EventHandler.class)) {
                    continue;
                }

                if (Reflection.doesntHaveParameters(method, PLUGIN_EVENT_LISTENER_SIGNATURE)) {
                    var methodSignature = Reflection.getSignature(method);
                    var pluginName = plugin.getName();

                    logger.error(STR."The plugin '\{pluginName}' has a wrong event handler signature:");
                    logger.error(methodSignature);
                    continue;
                }

                Reflection.invoke(plugin, method, event);
            }
        });
    }

    @Override
    protected String getNameOf(Plugin plugin) {
        return plugin.getName();
    }
}
