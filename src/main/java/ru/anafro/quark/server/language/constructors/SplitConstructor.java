package ru.anafro.quark.server.language.constructors;

import ru.anafro.quark.server.language.entities.*;

import java.util.stream.Stream;

import static ru.anafro.quark.server.language.entities.InstructionEntityConstructorParameter.required;
import static ru.anafro.quark.server.language.entities.InstructionEntityConstructorReturnDescription.returns;

/**
 * This class represents the split constructor of Quark QL.
 * <br><br>
 * <p>
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.constructors().get("split"); }
 * to get an instance of this class.
 *
 * <br><br>
 * <p>
 * You can check out the syntax of this constructor by running
 * <pre>
 * {@code
 * Quark.constructors().get("split").getSyntax();
 * }
 * </pre>
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public class SplitConstructor extends EntityConstructor {

    /**
     * Creates a new instance of the split constructor
     * representing object.
     * <br><br>
     * <p>
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.constructors().get("split");
     * to get an instance of this class.
     * <br><br>
     * <p>
     * You can check out the syntax of this constructor by running
     * <pre>
     * {@code
     * Quark.constructors().get("split").getSyntax();
     * }
     * </pre>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public SplitConstructor() {
        super(
                "split",

                returns("the string parts", "list"),

                required("string to split", "str"),
                required("delimiter", "str")
        );
    }

    /**
     * Invokes the @split constructor programmatically with arguments.
     * <br><br>
     * <p>
     * You can check out the syntax of this constructor by running
     * <pre>
     * {@code
     * Quark.constructors().get("split").getSyntax();
     * }
     * </pre>
     *
     * @param arguments the arguments this constructor needs to be invoked with.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        var string = arguments.getString("string to split");
        var delimiter = arguments.getString("delimiter");

        return new ListEntity("str", Stream.of(string.split(delimiter)).map(StringEntity::new).toList());
    }
}
