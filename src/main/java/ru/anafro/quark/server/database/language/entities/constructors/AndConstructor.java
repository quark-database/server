package ru.anafro.quark.server.database.language.entities.constructors;

import ru.anafro.quark.server.database.language.entities.BooleanEntity;
import ru.anafro.quark.server.database.language.entities.Entity;
import ru.anafro.quark.server.database.language.entities.EntityConstructor;
import ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorArguments;

import static ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorParameter.varargs;
import static ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorReturnDescription.returns;

/**
 * This class represents the "and" constructor of Quark QL.
 * <br><br>
 * <p>
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.constructors().get("and"); }
 * to get an instance of this class.
 *
 * <br><br>
 * <p>
 * You can check out the syntax of this constructor by running
 * <pre>
 * {@code
 * Quark.constructors().get("and").getSyntax();
 * }
 * </pre>
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public class AndConstructor extends EntityConstructor {

    /**
     * Creates a new instance of the "and" constructor
     * representing object.
     * <br><br>
     * <p>
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.constructors().get("and");
     * to get an instance of this class.
     * <br><br>
     * <p>
     * You can check out the syntax of this constructor by running
     * <pre>
     * {@code
     * Quark.constructors().get("and").getSyntax();
     * }
     * </pre>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public AndConstructor() {
        super(
                "and",

                returns("result of and", "boolean"),

                varargs("booleans", "boolean")
        );
    }

    /**
     * Invokes the @and constructor programmatically with arguments.
     * <br><br>
     * <p>
     * You can check out the syntax of this constructor by running
     * <pre>
     * {@code
     * Quark.constructors().get("and").getSyntax();
     * }
     * </pre>
     *
     * @param arguments the arguments this constructor needs to be invoked with.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        boolean result = true;

        for (var untypedBoolean : arguments.getList(BooleanEntity.class, "booleans")) {
            result = result && untypedBoolean.getValue();
        }

        return new BooleanEntity(result);
    }
}
