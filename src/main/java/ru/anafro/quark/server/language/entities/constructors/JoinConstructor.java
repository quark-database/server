package ru.anafro.quark.server.language.entities.constructors;

import ru.anafro.quark.server.language.entities.Entity;
import ru.anafro.quark.server.language.entities.EntityConstructor;
import ru.anafro.quark.server.language.entities.InstructionEntityConstructorArguments;
import ru.anafro.quark.server.language.entities.ListEntity;
import ru.anafro.quark.server.language.entities.constructors.exceptions.BadInstructionEntityConstructorArgumentTypeException;

import static ru.anafro.quark.server.language.entities.InstructionEntityConstructorParameter.required;
import static ru.anafro.quark.server.language.entities.InstructionEntityConstructorParameter.varargs;
import static ru.anafro.quark.server.language.entities.InstructionEntityConstructorReturnDescription.returns;

/**
 * This class represents the join constructor of Quark QL.
 * <br><br>
 * <p>
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.constructors().get("join"); }
 * to get an instance of this class.
 *
 * <br><br>
 * <p>
 * You can check out the syntax of this constructor by running
 * <pre>
 * {@code
 * Quark.constructors().get("join").getSyntax();
 * }
 * </pre>
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public class JoinConstructor extends EntityConstructor {

    /**
     * Creates a new instance of the join constructor
     * representing object.
     * <br><br>
     * <p>
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.constructors().get("join");
     * to get an instance of this class.
     * <br><br>
     * <p>
     * You can check out the syntax of this constructor by running
     * <pre>
     * {@code
     * Quark.constructors().get("join").getSyntax();
     * }
     * </pre>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public JoinConstructor() {
        super(
                "join",

                returns("a joined list", "list"),

                required("first list", "list of ?"),
                varargs("more lists", "list of ?")
        );
    }

    /**
     * Invokes the @join constructor programmatically with arguments.
     * <br><br>
     * <p>
     * You can check out the syntax of this constructor by running
     * <pre>
     * {@code
     * Quark.constructors().get("join").getSyntax();
     * }
     * </pre>
     *
     * @param arguments the arguments this constructor needs to be invoked with.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        var firstList = arguments.getList("first list");

        for (var listToJoin : arguments.getList(ListEntity.class, "more lists")) {
            for (var element : listToJoin) {
                if (firstList.elementsDoesntHaveType(listToJoin.getTypeName())) {
                    throw new BadInstructionEntityConstructorArgumentTypeException(this, "more lists");
                }

                firstList.add(element);
            }
        }

        return firstList;
    }
}
