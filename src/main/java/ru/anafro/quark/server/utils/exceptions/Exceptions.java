package ru.anafro.quark.server.utils.exceptions;

import ru.anafro.quark.server.utils.strings.Strings;
import ru.anafro.quark.server.utils.strings.TextBuffer;
import ru.anafro.quark.server.utils.types.classes.Classes;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class Exceptions {
    private Exceptions() {
        throw new UtilityClassInstantiationException(getClass());
    }

    public static String getTrace(Throwable throwable) {
        var stringWriter = new StringWriter();
        var printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);

        return stringWriter.toString();
    }

    public static String makePrettyTrace(StackTraceElement[] stackTrace) {
        var trace = new TextBuffer();

        for (var line : stackTrace) {
            if (Classes.isQuarkPackageName(line.getClassName())) {
                trace.appendLine(STR."<blue>\{Classes.cutPackageName(line.getClassName())}</><white>.<salad>\{line.getMethodName()}<white>(), line \{line.getLineNumber()}</>");
            } else {
                trace.appendLine(STR."<gray><italic>\{line.getClassName()}.\{line.getMethodName()}(), line \{line.getLineNumber()}</></>");
            }
        }

        return trace.getContent();
    }

    public static String getPrettyTrace(Throwable throwable) {
        return makePrettyTrace(throwable.getStackTrace());
    }

    public static String format(Throwable throwable) {
        var name = throwable.getClass().getName();
        var message = throwable.getMessage();
        var trace = getPrettyTrace(throwable);

        return STR."""
                \{name}: \{message}

                \{trace}
                """;
    }

    public static String getName(Throwable throwable) {
        var className = throwable.getClass().getSimpleName();
        var exceptionName = Strings.removeTrailing(className, "Exception");

        return Classes.toHumanReadableName(exceptionName);
    }
}
