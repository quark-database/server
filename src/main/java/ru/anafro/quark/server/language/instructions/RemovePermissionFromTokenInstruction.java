package ru.anafro.quark.server.language.instructions;

import ru.anafro.quark.server.language.Instruction;
import ru.anafro.quark.server.language.InstructionArguments;
import ru.anafro.quark.server.language.InstructionResultRecorder;
import ru.anafro.quark.server.language.entities.StringEntity;
import ru.anafro.quark.server.facade.Quark;

import static ru.anafro.quark.server.language.InstructionParameter.general;
import static ru.anafro.quark.server.language.InstructionParameter.required;

/**
 * This class represents the remove permission from token instruction of Quark QL.
 * <br><br>
 * <p>
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.instructions().get("remove permission from token"); }
 * to get an instance of this class.
 *
 * <br><br>
 * <p>
 * You can check out the syntax of this instruction by running
 * <pre>
 * {@code
 * Quark.instructions().get("remove permission from token").getSyntax();
 * }
 * </pre>
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public class RemovePermissionFromTokenInstruction extends Instruction {

    /**
     * Creates a new instance of the remove permission from token instruction
     * representing object.
     * <br><br>
     * <p>
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.instructions().get("remove permission from token");
     * to get an instance of this class.
     * <br><br>
     * <p>
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("remove permission from token").getSyntax();
     * }
     * </pre>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public RemovePermissionFromTokenInstruction() {
        super("remove permission from token",

                "Removes a permission from a token",

                "token.remove permission",

                general("token"),

                required("permission")
        );
    }

    /**
     * Runs the action of this instruction with passed in arguments.
     * <br><br>
     * <p>
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("remove permission from token").getSyntax();
     * }
     * </pre>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    @Override
    protected void performAction(InstructionArguments arguments, InstructionResultRecorder result) {
        var token = arguments.getString("token");
        var permission = arguments.getString("permission");

        Quark.query("""
                        insert into "Quark.Tokens": record = @record(%s, %s);
                """.formatted(
                new StringEntity(token).toInstructionForm(),
                new StringEntity(permission).toInstructionForm()
        ));

        result.ok("A permission has been successfully removed from the token.");
    }
}
