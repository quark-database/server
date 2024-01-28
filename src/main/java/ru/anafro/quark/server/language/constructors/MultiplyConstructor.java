package ru.anafro.quark.server.language.constructors;

import ru.anafro.quark.server.language.entities.Entity;
import ru.anafro.quark.server.language.entities.EntityConstructor;
import ru.anafro.quark.server.language.entities.FloatEntity;
import ru.anafro.quark.server.language.entities.InstructionEntityConstructorArguments;

import static ru.anafro.quark.server.language.entities.InstructionEntityConstructorParameter.varargs;
import static ru.anafro.quark.server.language.entities.InstructionEntityConstructorReturnDescription.returns;

/**
 * This class represents the multiply constructor of Quark QL.
 * <br><br>
 * <p>
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.constructors().get("multiply"); }
 * to get an instance of this class.
 *
 * <br><br>
 * <p>
 * You can check out the syntax of this constructor by running
 * <pre>
 * {@code
 * Quark.constructors().get("multiply").getSyntax();
 * }
 * </pre>
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public class MultiplyConstructor extends EntityConstructor {

    /**
     * Creates a new instance of the multiply constructor
     * representing object.
     * <br><br>
     * <p>
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.constructors().get("multiply");
     * to get an instance of this class.
     * <br><br>
     * <p>
     * You can check out the syntax of this constructor by running
     * <pre>
     * {@code
     * Quark.constructors().get("multiply").getSyntax();
     * }
     * </pre>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public MultiplyConstructor() {
        super(
                "multiply",

                returns("the product", "float"),

                varargs("numbers to multiply", "float")
        );
    }

    /**
     * Invokes the @multiply constructor programmatically with arguments.
     * <br><br>
     * <p>
     * You can check out the syntax of this constructor by running
     * <pre>
     * {@code
     * Quark.constructors().get("multiply").getSyntax();
     * }
     * </pre>
     *
     * @param arguments the arguments this constructor needs to be invoked with.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        var numbers = arguments.getList(FloatEntity.class, "numbers to multiply");
        var result = 1.0F;

        for (var number : numbers) {
            result *= number.getValue();
        }

        return new FloatEntity(result);
    }
}
