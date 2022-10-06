package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

import java.util.Random;

import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorParameter.required;
import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorReturnDescription.returns;

/**
 * This class represents the random between constructor of Quark QL.
 * <br><br>
 *
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.constructors().get("random between"); }
 * to get an instance of this class.
 *
 * <br><br>
 *
 * You can check out the syntax of this constructor by running
 * <pre>
 * {@code
 * Quark.constructors().get("random between").getSyntax();
 * }
 * </pre>
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class RandomBetweenConstructor extends EntityConstructor {
    private static final Random random = new Random();

    /**
     * Creates a new instance of the random between constructor
     * representing object.
     * <br><br>
     *
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.constructors().get("random between");
     * to get an instance of this class.
     * <br><br>
     *
     * You can check out the syntax of this constructor by running
     * <pre>
     * {@code
     * Quark.constructors().get("random between").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public RandomBetweenConstructor() {
        super(
                "random between",

                returns("the random value", "int"),

                required("inclusive min", "int"),
                required("exclusive max", "int")
        );
    }

    /**
     * Invokes the @random between constructor programmatically with arguments.
     * <br><br>
     *
     * You can check out the syntax of this constructor by running
     * <pre>
     * {@code
     * Quark.constructors().get("random between").getSyntax();
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
        IntegerEntity min = arguments.get("inclusive min");
        IntegerEntity max = arguments.get("exclusive max");

        return new IntegerEntity(random.nextInt(min.getValue(), max.getValue()));
    }
}
