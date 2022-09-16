package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

/**
 * This class represents the count constructor of Quark QL.
 * <br><br>
 *
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.constructors().get("count"); }
 * to get an instance of this class.
 *
 * <br><br>
 *
 * You can check out the syntax of this constructor by running
 * <pre>
 * {@code
 * Quark.constructors().get("count").getSyntax();
 * }
 * </pre>
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class CountConstructor extends EntityConstructor {

    /**
     * Creates a new instance of the count constructor
     * representing object.
     * <br><br>
     *
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.constructors().get("count");
     * to get an instance of this class.
     * <br><br>
     *
     * You can check out the syntax of this constructor by running
     * <pre>
     * {@code
     * Quark.constructors().get("count").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public CountConstructor() {
        super("count", InstructionEntityConstructorParameter.required("list to count elements", "list of ?"));
    }

    /**
     * Invokes the @count constructor programmatically with arguments.
     * <br><br>
     *
     * You can check out the syntax of this constructor by running
     * <pre>
     * {@code
     * Quark.constructors().get("count").getSyntax();
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
        return new IntegerEntity(arguments.<ListEntity>get("list to count elements").size());
    }
}
