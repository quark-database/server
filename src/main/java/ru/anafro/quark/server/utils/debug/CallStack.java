package ru.anafro.quark.server.utils.debug;

import ru.anafro.quark.server.utils.exceptions.CallingUtilityConstructorException;
import ru.anafro.quark.server.utils.integers.Integers;

public final class CallStack {
    private CallStack() {
        throw new CallingUtilityConstructorException(getClass());
    }

    public static String ofDepth(int depth) {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        var caller = elements[Integers.limit(depth, 0, elements.length - 1)];

        return caller.getClassName().substring(caller.getClassName().lastIndexOf('.') + 1) + "." + caller.getMethodName() + "(), line " + caller.getLineNumber();
    }
}
