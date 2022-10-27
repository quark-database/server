package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.data.ColumnDescription;
import ru.anafro.quark.server.databases.data.CompoundedTableName;
import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.data.exceptions.TableNotFoundException;
import ru.anafro.quark.server.databases.exceptions.QueryException;
import ru.anafro.quark.server.databases.ql.*;
import ru.anafro.quark.server.networking.Server;

import java.util.Collections;

/**
 * This class represents the swap columns in instruction of Quark QL.
 * <br><br>
 *
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.instructions().get("swap columns in"); }
 * to get an instance of this class.
 *
 * <br><br>
 *
 * You can check out the syntax of this instruction by running
 * <pre>
 * {@code
 * Quark.instructions().get("swap columns in").getSyntax();
 * }
 * </pre>
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class SwapColumnsInstruction extends Instruction {

    /**
     * Creates a new instance of the swap columns in instruction
     * representing object.
     * <br><br>
     *
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.instructions().get("swap columns in");
     * to get an instance of this class.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("swap columns in").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public SwapColumnsInstruction() {
        super(
                "swap columns in",

                "Swaps two columns in a table",

                "columns.swap",

                InstructionParameter.general("table"),

                InstructionParameter.required("first"),
                InstructionParameter.required("second")
        );
    }

    /**
     * Runs the action of this instruction with passed in arguments.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("swap columns in").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        var tableName = arguments.getString("table");
        var firstColumnName = arguments.getString("first");
        var secondColumnName = arguments.getString("second");

        if(!Table.exists(tableName)) {
            throw new TableNotFoundException(new CompoundedTableName(tableName));
        }

        var table = Table.byName(tableName);

        if(table.getHeader().missingColumn(firstColumnName)) {
            throw new QueryException("There's no column named %s.".formatted(firstColumnName));
        }

        if(table.getHeader().missingColumn(secondColumnName)) {
            throw new QueryException("There's no column named %s.".formatted(secondColumnName));
        }

        var newOrder = table.getHeader().getColumns().stream().map(ColumnDescription::getName).toList();
        int firstIndex = newOrder.indexOf(firstColumnName);
        int secondIndex = newOrder.indexOf(secondColumnName);

        Collections.swap(newOrder, firstIndex, secondIndex);

        result.status(QueryExecutionStatus.OK, "Successfully swapped two columns in the table.");
    }
}
