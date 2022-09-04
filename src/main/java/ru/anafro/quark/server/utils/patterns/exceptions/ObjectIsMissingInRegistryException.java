package ru.anafro.quark.server.utils.patterns.exceptions;

import ru.anafro.quark.server.utils.exceptions.UtilityException;

public class ObjectIsMissingInRegistryException extends UtilityException {
    public ObjectIsMissingInRegistryException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
