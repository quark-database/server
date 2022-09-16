package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.data.TableRecordSelector;
import ru.anafro.quark.server.databases.ql.*;
import ru.anafro.quark.server.exceptions.QuarkException;
import ru.anafro.quark.server.networking.Server;
import ru.anafro.quark.server.utils.exceptions.Exceptions;

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
        super("select from", "data.select",

                InstructionParameter.general("table name"),

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
        var selector = arguments.has("selector") ? arguments.get("selector").valueAs(TableRecordSelector.class) : TableRecordSelector.SELECT_ALL;

        try {
            var table = Table.byName(arguments.getString("table name"));
            var selectedRecords = table.select(selector, skip, limit);

            result.header(table.createTableViewHeader());

            for(var selectedRecord : selectedRecords) {
                result.appendRow(selectedRecord.toTableViewRow());
            }

            result.status(QueryExecutionStatus.OK, "%d rows successfully selected.".formatted(selectedRecords.size()));
        } catch(QuarkException exception) {
            result.status(QueryExecutionStatus.SERVER_ERROR, "?" + Exceptions.getTraceAsString(exception));
        }
    }
}
