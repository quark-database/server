package ru.anafro.quark.server.utils.types.exceptions;

import ru.anafro.quark.server.exceptions.QuarkException;

/**
 * This is a super class for all exceptions specific for {@link ru.anafro.quark.server.utils.types}
 * package. Please, do not throw {@link TypeException} without creating a new class for it.
 *
 * @since  Quark 1.1
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class TypeException extends QuarkException {

    /**
     * Creates an exception instance. Please, do not throw {@link TypeException}
     * without creating a new class for it.
     *
     * @param message an exception message
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public TypeException(String message) {
        super(message);
    }
}
