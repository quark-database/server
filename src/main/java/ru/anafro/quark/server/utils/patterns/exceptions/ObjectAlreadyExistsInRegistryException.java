package ru.anafro.quark.server.utils.patterns.exceptions;

import ru.anafro.quark.server.utils.exceptions.UtilityException;
import ru.anafro.quark.server.utils.patterns.NamedObjectsList;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

/**
 * {@link ObjectAlreadyExistsInRegistryException} is only used inside the {@link NamedObjectsList}
 * to indicate if any object inside the registry has the same name as adding one.
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @see UtilityException
 * @see NamedObjectsList
 * @since Quark 1.1
 */
public class ObjectAlreadyExistsInRegistryException extends UtilityException {

    /**
     * Creates a new exception instance.
     *
     * @param objectName  a name of object that both an adding object and any of existing object
     *                    has called with.
     * @param objectClass a registry type.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see UtilityException
     * @see NamedObjectsList
     * @since Quark 1.1
     */
    public ObjectAlreadyExistsInRegistryException(String objectName, Class<?> objectClass) {
        super("An object with name %s already exists in the registry of %s objects".formatted(quoted(objectName), quoted(objectClass.getSimpleName())));
    }
}
