package ru.anafro.quark.server.database.language.instructions;

import ru.anafro.quark.server.database.data.ExpressionTableRecordSelector;
import ru.anafro.quark.server.database.data.RecordIterationLimiter;
import ru.anafro.quark.server.database.language.Instruction;
import ru.anafro.quark.server.database.language.InstructionArguments;
import ru.anafro.quark.server.database.language.InstructionParameter;
import ru.anafro.quark.server.database.language.InstructionResultRecorder;

/**
 * This class represents the select from instruction of Quark QL.
 * <br><br>
 * <p>
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.instructions().get("select from"); }
 * to get an instance of this class.
 *
 * <br><br>
 * <p>
 * You can check out the syntax of this instruction by running
 * <pre>
 * {@code
 * Quark.instructions().get("select from").getSyntax();
 * }
 * </pre>
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public class SelectFromInstruction extends Instruction {

    /**
     * Creates a new instance of the select from instruction
     * representing object.
     * <br><br>
     * <p>
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.instructions().get("select from");
     * to get an instance of this class.
     * <br><br>
     * <p>
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("select from").getSyntax();
     * }
     * </pre>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public SelectFromInstruction() {
        super("select from",

                "Selects records from a table",

                "json.select",

                InstructionParameter.general("table"),

                InstructionParameter.optional("selector", "selector"),
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
     * Quark.instructions().get("select from").getSyntax();
     * }
     * </pre>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    @Override
    protected void performAction(InstructionArguments arguments, InstructionResultRecorder result) {
        var skip = arguments.tryGetInt("skip").orElse(0);
        var limit = arguments.tryGetInt("limit").orElse(Integer.MAX_VALUE);
        var selector = arguments.tryGetSelector("selector").orElse(ExpressionTableRecordSelector.SELECT_ALL);
        var table = arguments.getTable();
        var records = table.select(selector, new RecordIterationLimiter(skip, limit));

        result.header(table.createViewHeader());

        for (var record : records) {
            result.row(record);
        }

        result.ok(STR."\{records.count()} rows successfully selected.");
    }
}
