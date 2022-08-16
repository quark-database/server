package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.utils.containers.UniqueList;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class ReloadCommand extends Command {
    public ReloadCommand() {
        super(new UniqueList<>("reload", "restart", "r"), "Reloads the server", "Stops the Quark Server and starts it again. All the plugins will also be reloaded.");
    }

    @Override
    public void action(CommandArguments arguments) {  // TODO: Rewrite
        try {
            String javaBinaryFolder = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
            File currentJarFile = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());

            if(!currentJarFile.getName().endsWith(".jar")) {
                error("The server cannot be reloaded, because it's launching file does not have the .jar extension somehow.");
            }

            final ProcessBuilder processBuilder = new ProcessBuilder(List.of(javaBinaryFolder, "-jar", currentJarFile.getPath()));

            Quark.server().stop();
            processBuilder.start();
            System.exit(0);
        } catch (URISyntaxException | IOException e) {
            error("Server cannot be reloaded, because of %s: %s".formatted(e.getClass().getSimpleName(), e.getMessage()));
        }
    }
}
