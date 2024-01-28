package ru.anafro.quark.server.database.language.instructions;

import ru.anafro.quark.server.database.data.parser.RecordCharacterEscapeService;
import ru.anafro.quark.server.database.language.Instruction;
import ru.anafro.quark.server.database.language.InstructionArguments;
import ru.anafro.quark.server.database.language.InstructionResultRecorder;
import ru.anafro.quark.server.database.language.entities.StringEntity;
import ru.anafro.quark.server.facade.Quark;

import static ru.anafro.quark.server.database.language.InstructionParameter.general;
import static ru.anafro.quark.server.database.language.InstructionParameter.required;

/**
 * This class represents the redefine permissions for token instruction of Quark QL.
 * <br><br>
 * <p>
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.instructions().get("redefine permissions for token"); }
 * to get an instance of this class.
 *
 * <br><br>
 * <p>
 * You can check out the syntax of this instruction by running
 * <pre>
 * {@code
 * Quark.instructions().get("redefine permissions for token").getSyntax();
 * }
 * </pre>
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public class RedefinePermissionsForTokenInstruction extends Instruction {

    /**
     * Creates a new instance of the redefine permissions for token instruction
     * representing object.
     * <br><br>
     * <p>
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.instructions().get("redefine permissions for token");
     * to get an instance of this class.
     * <br><br>
     * <p>
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("redefine permissions for token").getSyntax();
     * }
     * </pre>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public RedefinePermissionsForTokenInstruction() {
        super(
                "redefine permissions for token",

                "Defines a new set of permissions for a token",

                "token.redefine",

                general("token"),

                required("permissions", "list of str")
        );
    }

    /**
     * Runs the action of this instruction with passed in arguments.
     * <br><br>
     * <p>
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("redefine permissions for token").getSyntax();
     * }
     * </pre>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    @Override
    protected void performAction(InstructionArguments arguments, InstructionResultRecorder result) {
        var token = arguments.getString("token");
        var permissions = arguments.getList("permissions").stream().map(permission -> permission.valueAs(String.class)).toList();

        Quark.query("""
                        delete from "Quark.Tokens": selector = @selector("@equals(:token, \\"%s\\")");
                """.formatted(new RecordCharacterEscapeService().wrapEscapableCharacters(token)));

        for (var permission : permissions) {
            Quark.query("""
                            insert into "Quark.Tokens": record = @record(%s, %s);
                    """.formatted(
                    new StringEntity(token).toInstructionForm(),
                    new StringEntity(permission).toInstructionForm()
            ));
        }

        result.ok("Token permissions has been redefined.");
    }
}
