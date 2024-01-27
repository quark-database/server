package ru.anafro.quark.server.database.language.entities.constructors;

import ru.anafro.quark.server.database.language.entities.Entity;
import ru.anafro.quark.server.database.language.entities.EntityConstructor;
import ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorArguments;
import ru.anafro.quark.server.database.language.entities.IntegerEntity;
import ru.anafro.quark.server.database.language.entities.constructors.exceptions.BadInstructionEntityConstructorArgumentTypeException;
import ru.anafro.quark.server.utils.strings.Converter;
import ru.anafro.quark.server.utils.validation.Validators;

import static ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorParameter.optional;
import static ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorParameter.required;
import static ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorReturnDescription.returns;

/**
 * This class represents the "to int" constructor of Quark QL.
 * <br><br>
 * <p>
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.constructors().get("to int"); }
 * to get an instance of this class.
 *
 * <br><br>
 * <p>
 * You can check out the syntax of this constructor by running
 * <pre>
 * {@code
 * Quark.constructors().get("to int").getSyntax();
 * }
 * </pre>
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public class ToIntConstructor extends EntityConstructor {

    /**
     * Creates a new instance of the "to int" constructor
     * representing object.
     * <br><br>
     * <p>
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.constructors().get("to int");
     * to get an instance of this class.
     * <br><br>
     * <p>
     * You can check out the syntax of this constructor by running
     * <pre>
     * {@code
     * Quark.constructors().get("to int").getSyntax();
     * }
     * </pre>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public ToIntConstructor() {
        super(
                "to int",

                returns("the integer", "int"),

                required("string to convert to integer", "str"),
                optional("default integer value if conversation fails", "int")
        );
    }

    /**
     * Invokes the @to int constructor programmatically with arguments.
     * <br><br>
     * <p>
     * You can check out the syntax of this constructor by running
     * <pre>
     * {@code
     * Quark.constructors().get("to int").getSyntax();
     * }
     * </pre>
     *
     * @param arguments the arguments this constructor needs to be invoked with.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        var string = arguments.getString("string to convert to integer");

        if (Validators.validate(string, Validators.INTEGER_STRING)) {
            return new IntegerEntity(Converter.toInteger(string));
        }

        return Entity.wrap(arguments.tryGetInt("default integer value if conversation fails").orElseThrow(() -> {
            return new BadInstructionEntityConstructorArgumentTypeException(this, "string to convert to integer");
        }));
    }
}
