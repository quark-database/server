package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;
import ru.anafro.quark.server.databases.ql.entities.constructors.exceptions.BadInstructionEntityConstructorArgumentTypeException;
import ru.anafro.quark.server.utils.strings.Converter;
import ru.anafro.quark.server.utils.validation.Validators;

import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorParameter.optional;
import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorParameter.required;
import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorReturnDescription.returns;

/**
 * This class represents the to float constructor of Quark QL.
 * <br><br>
 *
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.constructors().get("to float"); }
 * to get an instance of this class.
 *
 * <br><br>
 *
 * You can check out the syntax of this constructor by running
 * <pre>
 * {@code
 * Quark.constructors().get("to float").getSyntax();
 * }
 * </pre>
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class ToFloatConstructor extends EntityConstructor {

    /**
     * Creates a new instance of the to float constructor
     * representing object.
     * <br><br>
     *
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.constructors().get("to float");
     * to get an instance of this class.
     * <br><br>
     *
     * You can check out the syntax of this constructor by running
     * <pre>
     * {@code
     * Quark.constructors().get("to float").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public ToFloatConstructor() {
        super(
                "to float",

                returns("the float", "float"),

                required("string to convert to float", "str"),
                optional("default float value if conversation fails", "float")
        );
    }

    /**
     * Invokes the @to float constructor programmatically with arguments.
     * <br><br>
     *
     * You can check out the syntax of this constructor by running
     * <pre>
     * {@code
     * Quark.constructors().get("to float").getSyntax();
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
        var string = arguments.<StringEntity>get("string to convert to float").getString();

        if(Validators.validate(string, Validators.FLOAT_STRING)) {
            return new FloatEntity(Converter.toFloat(string));
        }

        if(arguments.has("default float value if conversation fails")) {
            return arguments.get("default float value if conversation fails");
        }

        throw new BadInstructionEntityConstructorArgumentTypeException(this, "string to convert to float");
    }
}
