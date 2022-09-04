package ru.anafro.quark.server.utils.exceptions;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class Exceptions {
    private Exceptions() {
        throw new CallingUtilityConstructorException(getClass());
    }

    public static String getTraceAsString(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);

        return stringWriter.toString();
    }
}
