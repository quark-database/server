package ru.anafro.quark.server.language.constructors;

import ru.anafro.quark.server.language.entities.BooleanEntity;
import ru.anafro.quark.server.language.entities.Entity;
import ru.anafro.quark.server.language.entities.EntityConstructor;
import ru.anafro.quark.server.language.entities.InstructionEntityConstructorArguments;

import static ru.anafro.quark.server.language.entities.InstructionEntityConstructorParameter.required;
import static ru.anafro.quark.server.language.entities.InstructionEntityConstructorReturnDescription.returns;

/**
 * This class represents the matches constructor of Quark QL.
 * <br><br>
 * <p>
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.constructors().get("matches"); }
 * to get an instance of this class.
 *
 * <br><br>
 * <p>
 * You can check out the syntax of this constructor by running
 * <pre>
 * {@code
 * Quark.constructors().get("matches").getSyntax();
 * }
 * </pre>
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public class MatchesConstructor extends EntityConstructor {

    /**
     * Creates a new instance of the matches constructor
     * representing object.
     * <br><br>
     * <p>
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.constructors().get("matches");
     * to get an instance of this class.
     * <br><br>
     * <p>
     * You can check out the syntax of this constructor by running
     * <pre>
     * {@code
     * Quark.constructors().get("matches").getSyntax();
     * }
     * </pre>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public MatchesConstructor() {
        super(
                "matches",

                returns("is the string matches regex", "boolean"),

                required("string to check", "str"),
                required("regex lambda", "str")
        );
    }

    /**
     * Invokes the @matches constructor programmatically with arguments.
     * <br><br>
     * <p>
     * You can check out the syntax of this constructor by running
     * <pre>
     * {@code
     * Quark.constructors().get("matches").getSyntax();
     * }
     * </pre>
     *
     * @param arguments the arguments this constructor needs to be invoked with.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new BooleanEntity(arguments.getString("string to check").matches(arguments.getString("regex lambda")));
    }
}
