package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.data.CompoundedTableName;
import ru.anafro.quark.server.databases.data.ExpressionTableRecordSelector;
import ru.anafro.quark.server.databases.data.RecordIterationLimiter;
import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.data.exceptions.TableNotFoundException;
import ru.anafro.quark.server.databases.exceptions.QueryException;
import ru.anafro.quark.server.databases.ql.*;
import ru.anafro.quark.server.exceptions.QuarkException;
import ru.anafro.quark.server.networking.Server;

/**
 * This class represents the select from instruction of Quark QL.
 * <br><br>
 *
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.instructions().get("select from"); }
 * to get an instance of this class.
 *
 * <br><br>
 *
 * You can check out the syntax of this instruction by running
 * <pre>
 * {@code
 * Quark.instructions().get("select from").getSyntax();
 * }
 * </pre>
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class SelectFromInstruction extends Instruction {

    /**
     * Creates a new instance of the select from instruction
     * representing object.
     * <br><br>
     *
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.instructions().get("select from");
     * to get an instance of this class.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("select from").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public SelectFromInstruction() {
        super("select from",

                "Selects records from a table",

                "data.select",

                InstructionParameter.general("table"),

                InstructionParameter.optional("selector", "selector"),
                InstructionParameter.optional("skip", "int"),
                InstructionParameter.optional("limit", "int")
        );
    }

    /**
     * Runs the action of this instruction with passed in arguments.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("select from").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        var skip = arguments.has("skip") ? arguments.getInteger("skip") : 0;
        var limit = arguments.has("limit") ? arguments.getInteger("limit") : Integer.MAX_VALUE;
        var selector = arguments.has("selector") ? arguments.get("selector").valueAs(ExpressionTableRecordSelector.class) : ExpressionTableRecordSelector.SELECT_ALL;
        var tableName = arguments.getString("table");

        try {
            if(!Table.exists(tableName)) {
                throw new TableNotFoundException(new CompoundedTableName(tableName));
            }

            var table = Table.byName(arguments.getString("table"));
            var selectedRecords = table.select(selector, new RecordIterationLimiter(skip, limit));

            result.header(table.createTableViewHeader());

            for(var selectedRecord : selectedRecords) {
                result.appendRow(selectedRecord.toTableViewRow());
            }

            result.status(QueryExecutionStatus.OK, "%d rows successfully selected.".formatted(selectedRecords.count()));
        } catch(QuarkException exception) {
            throw new QueryException("Cannot select from the table, because of: " + exception);
        }
    }
}
