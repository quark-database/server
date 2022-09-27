package ru.anafro.quark.server.databases.ql.entities.constructors.exceptions;

import ru.anafro.quark.server.databases.exceptions.DatabaseException;
import ru.anafro.quark.server.databases.ql.entities.EntityConstructor;

/**
 * This exception is thrown when constructor got
 * a value with a bad format or type.
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class BadInstructionEntityConstructorArgumentTypeException extends DatabaseException { // This should be viewed in code editor, change to InstructionSyntaxException

    /**
     * Creates a new exception instance.
     *
     * @param   constructor a constructor got a malformed argument.
     * @param   parameterName a parameter name that received that malformed entity.
     * @since   Quark 1.1
     * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public BadInstructionEntityConstructorArgumentTypeException(EntityConstructor constructor, String parameterName) {
        super("%s's value passed to constructor %s is wrong, because it should have type %s. Check out this constructor syntax: %s".formatted(parameterName, constructor.getName(), constructor.getParameters().getParameter(parameterName).type(), constructor.getSyntax()));
    }
}
