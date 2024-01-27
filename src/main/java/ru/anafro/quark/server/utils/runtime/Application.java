package ru.anafro.quark.server.utils.runtime;

import ru.anafro.quark.server.Main;
import ru.anafro.quark.server.utils.exceptions.UtilityException;
import ru.anafro.quark.server.utils.files.File;

import java.net.URISyntaxException;
import java.nio.file.Paths;

public final class Application {
    private Application() {
    }

    public static File getJarFile() {
        try {
            return new File(Paths.get(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()));
        } catch (URISyntaxException e) {
            throw new UtilityException(e.getMessage());
        }
    }
}
