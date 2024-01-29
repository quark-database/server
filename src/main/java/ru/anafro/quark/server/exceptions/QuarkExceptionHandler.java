package ru.anafro.quark.server.exceptions;

import ru.anafro.quark.server.console.Console;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.utils.exceptions.Exceptions;
import ru.anafro.quark.server.utils.runtime.ExitCodes;
import ru.anafro.quark.server.utils.strings.Strings;

public class QuarkExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread thread, Throwable error) {
        var exceptionName = Exceptions.getName(error);
        var exceptionMessage = Strings.breakWords(error.getMessage(), 40);
        var exceptionStackTrace = Exceptions.getPrettyTrace(error);

        Console.clear();
        Console.print(STR."""
                ---------------------------------------------------------------------
                |                                                                   |
                |   W E   A R E   S O R R Y   :(                                    |
                |                                                                   |
                ---------------------------------------------------------------------


                Unfortunately, the error occurred
                that makes server work impossible.

                This is a bug. Please, report:
                <italic><purple>\{Quark.EMAIL}</></>


                TECHNICAL INFORMATION:

                The exception name:
                <italic><gray>\{exceptionName}</></>

                The exception message:
                <italic><gray>\{exceptionMessage}</></>

                The stack trace:
                \{exceptionStackTrace}
                """);


        System.exit(Console.promptBoolean("Reload?") ? ExitCodes.RELOAD : ExitCodes.ERROR);
    }
}
