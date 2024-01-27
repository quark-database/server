package ru.anafro.quark.server.database.language.entities.constructors.exceptions;

import ru.anafro.quark.server.database.exceptions.DatabaseException;
import ru.anafro.quark.server.database.language.entities.EntityConstructor;

/**
 * This exception is thrown when constructor got
 * a value with a bad format or type.
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public class BadInstructionEntityConstructorArgumentTypeException extends DatabaseException { // This should be viewed in code editor, change to InstructionSyntaxException

    /**
     * Creates a new exception instance.
     *
     * @param constructor   a constructor got a malformed argument.
     * @param parameterName a parameter name that received that malformed entity.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public BadInstructionEntityConstructorArgumentTypeException(EntityConstructor constructor, String parameterName) {
        super("%s's value passed to constructor %s is wrong, because it should have type %s. Check out this constructor syntax: %s".formatted(parameterName, constructor.getName(), constructor.getParameters().get(parameterName).type(), constructor.getSyntax()));
    }
}
