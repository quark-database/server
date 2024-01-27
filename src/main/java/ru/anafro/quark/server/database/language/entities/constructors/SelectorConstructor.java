package ru.anafro.quark.server.database.language.entities.constructors;

import ru.anafro.quark.server.database.data.ExpressionTableRecordSelector;
import ru.anafro.quark.server.database.language.entities.Entity;
import ru.anafro.quark.server.database.language.entities.EntityConstructor;
import ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorArguments;
import ru.anafro.quark.server.database.language.entities.SelectorEntity;

import static ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorParameter.required;
import static ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorReturnDescription.returns;

/**
 * This class represents the selector constructor of Quark QL.
 * <br><br>
 * <p>
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.constructors().get("selector"); }
 * to get an instance of this class.
 *
 * <br><br>
 * <p>
 * You can check out the syntax of this constructor by running
 * <pre>
 * {@code
 * Quark.constructors().get("selector").getSyntax();
 * }
 * </pre>
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public class SelectorConstructor extends EntityConstructor {

    /**
     * Creates a new instance of the selector constructor
     * representing object.
     * <br><br>
     * <p>
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.constructors().get("selector");
     * to get an instance of this class.
     * <br><br>
     * <p>
     * You can check out the syntax of this constructor by running
     * <pre>
     * {@code
     * Quark.constructors().get("selector").getSyntax();
     * }
     * </pre>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public SelectorConstructor() {
        super(
                "selector",

                returns("the selector", "selector"),

                required("selector lambda", "str")
        );
    }

    /**
     * Invokes the @selector constructor programmatically with arguments.
     * <br><br>
     * <p>
     * You can check out the syntax of this constructor by running
     * <pre>
     * {@code
     * Quark.constructors().get("selector").getSyntax();
     * }
     * </pre>
     *
     * @param arguments the arguments this constructor needs to be invoked with.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new SelectorEntity(new ExpressionTableRecordSelector(arguments.getString("selector lambda")));
    }
}
