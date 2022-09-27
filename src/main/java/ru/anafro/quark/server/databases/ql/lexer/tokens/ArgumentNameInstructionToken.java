package ru.anafro.quark.server.databases.ql.lexer.tokens;

import ru.anafro.quark.server.utils.validation.Validators;

/**
 * Represents an argument name on the query parsing and lexing
 * processes.
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class ArgumentNameInstructionToken extends InstructionToken {
    /**
     * Represents the starting prefix of the argument name
     * inside the constructors. Will be supported
     * in the next versions of Quark.
     *
     * <pre>
     * {@code
     * select from "Shop.Users": selector = @selector(%expression = "@equals(@random(0, 2), 1)");
     * }
     * </pre>
     *
     * @since Quark 1.1
     */
    public static final String ARGUMENT_NAME_MARKER = "%";

    /**
     * Creates a new token.
     *
     * @param   value the initial value of the token.
     * @since   Quark 1.1
     * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public ArgumentNameInstructionToken(String value) {
        super("argument name", value);
    }

    /**
     * Returns the representation of the token as a string.
     *
     * @since   Quark 1.1
     * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    @Override
    public String getRepresentation() {
        return ARGUMENT_NAME_MARKER + getValue();
    }

    /**
     * Checks whether the value is valid to be stored
     * inside this token or not.
     *
     * @since   Quark 1.1
     * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    @Override
    public boolean isValueValid(String value) {
        return Validators.validate(value, Validators.LOWER_ALPHA);
    }
}
