package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.ql.*;
import ru.anafro.quark.server.networking.Server;

/**
 * This class represents the grand token instruction of Quark QL.
 * <br><br>
 *
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.instructions().get("grand token"); }
 * to get an instance of this class.
 *
 * <br><br>
 *
 * You can check out the syntax of this instruction by running
 * <pre>
 * {@code
 * Quark.instructions().get("grand token").getSyntax();
 * }
 * </pre>
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class GrantTokenInstruction extends Instruction {

    /**
     * Creates a new instance of the grand token instruction
     * representing object.
     * <br><br>
     *
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.instructions().get("grand token");
     * to get an instance of this class.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("grand token").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public GrantTokenInstruction() {
        super("grant token",

                "Grants a token a permission",

                "token.grand",

                InstructionParameter.general("token"),

                InstructionParameter.required("permission")
        );
    }

    /**
     * Runs the action of this instruction with passed in arguments.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("grand token").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        var token = arguments.getString("token");
        var permission = arguments.getString("permission");

        Quark.runInstruction("""
                insert into "Quark.Tokens": record = @record(:token, :permission);
        """.replace(":token", token).replace(":permission", permission));

        result.status(QueryExecutionStatus.OK, "");
    }
}
