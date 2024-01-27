package ru.anafro.quark.server.database.language.instructions;

import ru.anafro.quark.server.database.data.RecordIterationLimiter;
import ru.anafro.quark.server.database.language.Instruction;
import ru.anafro.quark.server.database.language.InstructionArguments;
import ru.anafro.quark.server.database.language.InstructionParameter;
import ru.anafro.quark.server.database.language.InstructionResultRecorder;

/**
 * This class represents the delete from instruction of Quark QL.
 * <br><br>
 * <p>
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.instructions().get("delete from"); }
 * to get an instance of this class.
 *
 * <br><br>
 * <p>
 * You can check out the syntax of this instruction by running
 * <pre>
 * {@code
 * Quark.instructions().get("delete from").getSyntax();
 * }
 * </pre>
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public class DeleteFromInstruction extends Instruction {

    /**
     * Creates a new instance of the delete from instruction
     * representing object.
     * <br><br>
     * <p>
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.instructions().get("delete from");
     * to get an instance of this class.
     * <br><br>
     * <p>
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("delete from").getSyntax();
     * }
     * </pre>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public DeleteFromInstruction() {
        super("delete from",

                "Deletes records meet the condition",

                "json.delete",

                InstructionParameter.general("table"),

                InstructionParameter.required("selector", "selector"),
                InstructionParameter.optional("skip", "int"),
                InstructionParameter.optional("limit", "int")
        );
    }

    /**
     * Runs the action of this instruction with passed in arguments.
     * <br><br>
     * <p>
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("delete from").getSyntax();
     * }
     * </pre>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    @Override
    protected void performAction(InstructionArguments arguments, InstructionResultRecorder result) {
        var table = arguments.getTable();
        var selector = arguments.getSelector();
        var skip = arguments.tryGetInt("skip").orElse(0);
        var limit = arguments.tryGetInt("limit").orElse(Integer.MAX_VALUE);
        var records = table.selectAll();

        records.remove(selector, new RecordIterationLimiter(skip, limit));
        table.store(records);

        result.ok("Deletion has been performed.");
    }
}
