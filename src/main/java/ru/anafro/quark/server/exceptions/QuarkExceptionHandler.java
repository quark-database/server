package ru.anafro.quark.server.exceptions;

import ru.anafro.quark.server.console.Console;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.utils.exceptions.Exceptions;
import ru.anafro.quark.server.utils.runtime.ExitCodes;
import ru.anafro.quark.server.utils.types.classes.Classes;

public class QuarkExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread thread, Throwable error) {
        var exceptionName = Classes.getHumanReadableClassName(error);
        var exceptionMessage = error.getMessage();
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
                <purple>\{Quark.EMAIL}</>


                TECHNICAL INFORMATION:

                The exception name:
                <cyan>\{exceptionName}</>

                The exception message:
                <cyan>\{exceptionMessage}</>

                The stack trace:
                <cyan>\{exceptionStackTrace}</>
                """);


        System.exit(Console.promptBoolean("Reload?") ? ExitCodes.RELOAD : ExitCodes.ERROR);
    }
}
