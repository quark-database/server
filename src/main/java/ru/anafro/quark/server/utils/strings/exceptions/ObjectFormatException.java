package ru.anafro.quark.server.utils.strings.exceptions;

import ru.anafro.quark.server.exceptions.QuarkException;
import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class ObjectFormatException extends QuarkException {
    public ObjectFormatException(String string, Class<?> clazz) {
        super("String " + quoted(string) + " is being tried to be converted to type " + quoted(clazz.getSimpleName()) + ", but it is not in a good format.");
    }
}
