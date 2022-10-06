package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorParameter.required;
import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorReturnDescription.returns;

/**
 * This class represents the sort constructor of Quark QL.
 * <br><br>
 *
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.constructors().get("sort"); }
 * to get an instance of this class.
 *
 * <br><br>
 *
 * You can check out the syntax of this constructor by running
 * <pre>
 * {@code
 * Quark.constructors().get("sort").getSyntax();
 * }
 * </pre>
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class SortConstructor extends EntityConstructor {

    /**
     * Creates a new instance of the sort constructor
     * representing object.
     * <br><br>
     *
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.constructors().get("sort");
     * to get an instance of this class.
     * <br><br>
     *
     * You can check out the syntax of this constructor by running
     * <pre>
     * {@code
     * Quark.constructors().get("sort").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public SortConstructor() {
        super(
                "sort",

                returns("the sorted list", "list"),

                required("list to sort", "list of ?")
        );
    }

    /**
     * Invokes the @sort constructor programmatically with arguments.
     * <br><br>
     *
     * You can check out the syntax of this constructor by running
     * <pre>
     * {@code
     * Quark.constructors().get("sort").getSyntax();
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
        return new ListEntity(arguments.<ListEntity>get("list to sort").getTypeOfValuesInside(), arguments.<ListEntity>get("list to sort").getValue().stream().sorted().toList());
    }
}
