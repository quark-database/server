package ru.anafro.quark.server.utils.exceptions;

import ru.anafro.quark.server.exceptions.QuarkException;
import ru.anafro.quark.server.utils.debug.CallStack;

public class NotImplementedException extends QuarkException {
    public NotImplementedException() {
        super("Method %s is not implemented yet.".formatted(CallStack.ofDepth(3)));
    }
}
