package ru.anafro.quark.server.database.language.entities.constructors;

import ru.anafro.quark.server.database.language.entities.*;

import static ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorReturnDescription.returns;

/**
 * This class represents the list constructor of Quark QL.
 * <br><br>
 * <p>
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.constructors().get("list"); }
 * to get an instance of this class.
 *
 * <br><br>
 * <p>
 * You can check out the syntax of this constructor by running
 * <pre>
 * {@code
 * Quark.constructors().get("list").getSyntax();
 * }
 * </pre>
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public class ListConstructor extends EntityConstructor {

    /**
     * Creates a new instance of the list constructor
     * representing object.
     * <br><br>
     * <p>
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.constructors().get("list");
     * to get an instance of this class.
     * <br><br>
     * <p>
     * You can check out the syntax of this constructor by running
     * <pre>
     * {@code
     * Quark.constructors().get("list").getSyntax();
     * }
     * </pre>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public ListConstructor() {
        super(
                "list",

                returns("a new list of values", "list"),

                InstructionEntityConstructorParameter.varargs("values", "?")
        );
    }

    /**
     * Invokes the @list constructor programmatically with arguments.
     * <br><br>
     * <p>
     * You can check out the syntax of this constructor by running
     * <pre>
     * {@code
     * Quark.constructors().get("list").getSyntax();
     * }
     * </pre>
     *
     * @param arguments the arguments this constructor needs to be invoked with.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        var wildcardList = arguments.getList("values");

        if (wildcardList.isEmpty()) {
            return wildcardList;
        }

        var typedList = new ListEntity(wildcardList.getFirst().getExactTypeName());

        for (var element : wildcardList) {
            typedList.add(element);
        }

        return typedList;
    }
}
