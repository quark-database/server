package ru.anafro.quark.server.utils.patterns.exceptions;

import ru.anafro.quark.server.utils.exceptions.UtilityException;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class ObjectAlreadyExistsInRegistryException extends UtilityException {
    public ObjectAlreadyExistsInRegistryException(String objectName, Class<?> objectClass) {
        super("An object with name %s already exists in the registry of %s objects".formatted(quoted(objectName), quoted(objectClass.getSimpleName())));
    }
}
