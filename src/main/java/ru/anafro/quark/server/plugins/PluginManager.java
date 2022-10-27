package ru.anafro.quark.server.plugins;

import ru.anafro.quark.server.files.Plugins;
import ru.anafro.quark.server.logging.Logger;
import ru.anafro.quark.server.plugins.events.Event;
import ru.anafro.quark.server.plugins.exceptions.*;
import ru.anafro.quark.server.utils.patterns.NamedObjectsRegistry;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class PluginManager extends NamedObjectsRegistry<Plugin> implements Iterable<Plugin> {
    private final Logger logger = new Logger(this.getClass());
    private boolean isLoaded = false;
    public static final String PLUGIN_CLASS_PATH_FILE_NAME = "Plugin Class Path.txt";

    public void loadPlugins() {
        if(isLoaded) {
            throw new PluginLoaderUsedTwiceException();
        }

        Plugins.forEachPluginFile(path -> {
            logger.info("Loading the plugin %s...".formatted(path.getFileName()));

            try {
                URLClassLoader child = new URLClassLoader(
                        new URL[]{ path.toUri().toURL() },
                        this.getClass().getClassLoader()
                );

                InputStream stream = child.getResourceAsStream(PLUGIN_CLASS_PATH_FILE_NAME);

                if(stream == null) {
                    throw new NoPluginClassPathFileException(path);
                }

                BufferedInputStream bufferedInputStream = new BufferedInputStream(stream);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                for (int result = bufferedInputStream.read(); result != -1; result = bufferedInputStream.read()) {
                    byteArrayOutputStream.write((byte) result);
                }

                Class<?> classToLoad = Class.forName(byteArrayOutputStream.toString(StandardCharsets.UTF_8).strip(), true, child);

                Plugin plugin = (Plugin) classToLoad.getDeclaredConstructor().newInstance();

                add(plugin);
                logger.info("Plugin %s by %s is successfully loaded!".formatted(quoted(plugin.getName()), plugin.getAuthor()));
            } catch(Exception exception) {
                logger.error("Loading of the plugin %s is failed, because of %s occurred: %s".formatted(
                        path.toAbsolutePath().toString(),
                        exception.getClass().getSimpleName(),
                        exception.getMessage()
                ));

                exception.printStackTrace();
            }
        });

        isLoaded = true;
    }

    public void fireEvent(Event event) {
        forEach(plugin -> {
            for (Method method : plugin.getClass().getDeclaredMethods()) {
                logger.debug("Checking if the method " + method.getName() + " is an event handler");
                if(method.isAnnotationPresent(EventHandler.class)) {
                    if(method.getParameterCount() != 1) {
                        throw new BadPluginEventHandlerParameterLengthException(plugin, method);
                    }

                    if(!Event.class.isAssignableFrom(method.getParameterTypes()[0])) {
                        throw new BadPluginEventHandlerParameterTypeException(plugin, method);
                    }

                    try {
                        if(method.getParameterTypes()[0].isAssignableFrom(event.getClass())) {
                            method.invoke(plugin, method.getParameterTypes()[0].cast(event));
                        }
                    } catch (IllegalAccessException | InvocationTargetException exception) {
                        throw new PluginReflectionException(exception, event, plugin, method);
                    } catch(Exception exception) {
                        throw new PluginEventException(exception, event, plugin, method);
                    }
                } else {
                    logger.debug("It's not marked with @" + EventHandler.class.getSimpleName());
                }
            }
        });
    }

    @Override
    protected String getNameOf(Plugin plugin) {
        return plugin.getName();
    }
}
