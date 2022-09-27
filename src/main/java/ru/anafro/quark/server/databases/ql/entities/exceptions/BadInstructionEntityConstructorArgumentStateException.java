package ru.anafro.quark.server.databases.ql.entities.exceptions;

import ru.anafro.quark.server.databases.exceptions.DatabaseException;

/**
 * This exception is thrown when {@link ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArgument}
 * is being tried to invoke {@code eval()}, but it's badly formed (like it has value computed, but it also has
 * a constructor to evaluate).
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @deprecated In-time entity evaluation mechanism is deprecated.
 *             Constructors return entities without themselves inside.
 */
@Deprecated
public class BadInstructionEntityConstructorArgumentStateException extends DatabaseException {

    /**
     * Creates a new exception instance.
     *
     * @since   Quark 1.1
     * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public BadInstructionEntityConstructorArgumentStateException() {
        super("A constructor's argument is being tried to compute, but it is not in a right state: entity should be null, but constructor should not.");
    }
}
