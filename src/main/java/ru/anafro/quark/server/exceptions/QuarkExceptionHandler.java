package ru.anafro.quark.server.exceptions;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.logging.Logger;
import ru.anafro.quark.server.plugins.events.ServerCrashed;

public class QuarkExceptionHandler implements Thread.UncaughtExceptionHandler {
    private final Logger logger = Quark.logger();

    @Override
    public void uncaughtException(Thread thread, Throwable error) {
        Quark.fire(new ServerCrashed(Quark.server(), error));

        logger.error("Server has crashed because of an exception, here is why: " + error.getMessage());
        logger.error("The thread called " + thread.getName() + " has thrown an error, which was not expected by us");
        logger.error("Reading a stacktrace may help you to figure out the reason better:");

        error.printStackTrace();

        logger.error("Because of this error, we have to stop the Quark Server.");
        logger.error("\tIf you think that it's a bug (99% it is our fault), please, report it on the project's GitHub: github.com/anafro/quark");
        logger.error("\tIf you can't solve this problem by your own, please, feel free to write me on email: contact@anafro.ru (but please, add 'Quark Help' words to the message's theme, thanks)");

        System.exit(-1);
    }
}
