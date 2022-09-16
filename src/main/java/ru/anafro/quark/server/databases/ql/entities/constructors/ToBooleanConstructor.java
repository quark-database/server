package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;
import ru.anafro.quark.server.databases.ql.entities.constructors.exceptions.BadInstructionEntityConstructorArgumentTypeException;
import ru.anafro.quark.server.utils.arrays.Arrays;

/**
 * This class represents the to boolean constructor of Quark QL.
 * <br><br>
 *
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.constructors().get("to boolean"); }
 * to get an instance of this class.
 *
 * <br><br>
 *
 * You can check out the syntax of this constructor by running
 * <pre>
 * {@code
 * Quark.constructors().get("to boolean").getSyntax();
 * }
 * </pre>
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class ToBooleanConstructor extends EntityConstructor {
    private static final String[] TRUTHY_STRING_VALUES = { "yes", "true", "+", "1", "y" };
    private static final String[] FALSY_STRING_VALUES = { "no", "false", "-", "0", "n" };

    /**
     * Creates a new instance of the to boolean constructor
     * representing object.
     * <br><br>
     *
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.constructors().get("to boolean");
     * to get an instance of this class.
     * <br><br>
     *
     * You can check out the syntax of this constructor by running
     * <pre>
     * {@code
     * Quark.constructors().get("to boolean").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public ToBooleanConstructor() {
        super("to boolean", InstructionEntityConstructorParameter.required("string to convert to boolean", "str"), InstructionEntityConstructorParameter.optional("default boolean value if conversation fails", "boolean"));
    }

    /**
     * Invokes the @to boolean constructor programmatically with arguments.
     * <br><br>
     *
     * You can check out the syntax of this constructor by running
     * <pre>
     * {@code
     * Quark.constructors().get("to boolean").getSyntax();
     * }
     * </pre>
     *
     * @param arguments the arguments this constructor needs to be invoked with.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        var string = arguments.<StringEntity>get("string to convert to boolean").getString();

        if(Arrays.contains(TRUTHY_STRING_VALUES, string)) {
            return new BooleanEntity(true);
        }

        if(Arrays.contains(FALSY_STRING_VALUES, string)) {
            return new BooleanEntity(false);
        }

        if(arguments.has("default boolean value if conversation fails")) {
            return arguments.get("default boolean value if conversation fails");
        }

        throw new BadInstructionEntityConstructorArgumentTypeException(this, "string to convert to boolean");
    }
}
