package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.data.parser.RecordCharacterEscapeService;
import ru.anafro.quark.server.databases.ql.*;
import ru.anafro.quark.server.databases.ql.entities.StringEntity;
import ru.anafro.quark.server.networking.Server;

/**
 * This class represents the redefine permissions for token instruction of Quark QL.
 * <br><br>
 *
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.instructions().get("redefine permissions for token"); }
 * to get an instance of this class.
 *
 * <br><br>
 *
 * You can check out the syntax of this instruction by running
 * <pre>
 * {@code
 * Quark.instructions().get("redefine permissions for token").getSyntax();
 * }
 * </pre>
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class RedefinePermissionsForTokenInstruction extends Instruction {

    /**
     * Creates a new instance of the redefine permissions for token instruction
     * representing object.
     * <br><br>
     *
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.instructions().get("redefine permissions for token");
     * to get an instance of this class.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("redefine permissions for token").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public RedefinePermissionsForTokenInstruction() {
        super(
                "redefine permissions for token",

                "Defines a new set of permissions for a token",

                "token.redefine",

                InstructionParameter.general("token"),

                InstructionParameter.required("permissions", "list of str")
        );
    }

    /**
     * Runs the action of this instruction with passed in arguments.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("redefine permissions for token").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        var token = arguments.getString("token");
        var permissions = arguments.getList("permissions").stream().map(permission -> permission.valueAs(String.class)).toList();

        Quark.runInstruction("""
                delete from "Quark.Tokens": selector = @selector("@equals(:token, \\"%s\\")");
        """.formatted(new RecordCharacterEscapeService().wrapEscapableCharacters(token)));

        for(var permission : permissions) {
            Quark.runInstruction("""
                    insert into "Quark.Tokens": record = @record(%s, %s);
            """.formatted(
                    new StringEntity(token).toInstructionForm(),
                    new StringEntity(permission).toInstructionForm()
            ));
        }

        result.status(QueryExecutionStatus.OK, "Token permissions has been redefined.");
    }
}
