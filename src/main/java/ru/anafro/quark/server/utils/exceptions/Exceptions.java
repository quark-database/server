package ru.anafro.quark.server.utils.exceptions;

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

    public static String getPrettyTrace(Throwable throwable) {
        var trace = new TextBuffer();

        for (var line : throwable.getStackTrace()) {
            if (Classes.isQuarkPackageName(line.getClassName())) {
                trace.appendLine(STR."<blue>\{Classes.cutPackageName(line.getClassName())}</><white>.<salad>\{line.getMethodName()}<white>(), line \{line.getLineNumber()}</>");
            } else {
                trace.appendLine(STR."<gray><italic>\{line.getClassName()}.\{line.getMethodName()}(), line \{line.getLineNumber()}</></>");
            }
        }

        return trace.getContent();
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
}
