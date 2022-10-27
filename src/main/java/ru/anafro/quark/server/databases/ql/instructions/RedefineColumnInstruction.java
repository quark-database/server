package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.data.CompoundedTableName;
import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.data.exceptions.TableNotFoundException;
import ru.anafro.quark.server.databases.exceptions.QueryException;
import ru.anafro.quark.server.databases.ql.*;
import ru.anafro.quark.server.databases.ql.entities.ColumnEntity;
import ru.anafro.quark.server.networking.Server;

/**
 * This class represents the redefine column instruction of Quark QL.
 * <br><br>
 *
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.instructions().get("redefine column"); }
 * to get an instance of this class.
 *
 * <br><br>
 *
 * You can check out the syntax of this instruction by running
 * <pre>
 * {@code
 * Quark.instructions().get("redefine column").getSyntax();
 * }
 * </pre>
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class RedefineColumnInstruction extends Instruction {

    /**
     * Creates a new instance of the redefine column instruction
     * representing object.
     * <br><br>
     *
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.instructions().get("redefine column");
     * to get an instance of this class.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("redefine column").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public RedefineColumnInstruction() {
        super("redefine column in",

                "Changes the definition of a column",

                "column.redefine",

                InstructionParameter.general("name"),

                InstructionParameter.required("definition", "column")
        );
    }

    /**
     * Runs the action of this instruction with passed in arguments.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("redefine column").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        var tableName = arguments.getString("name");
        var columnDescription = arguments.<ColumnEntity>get("definition").getColumnDescription();

        if(!Table.exists(tableName)) {
            throw new TableNotFoundException(new CompoundedTableName(tableName));
        }

        var table = Table.byName(tableName);

        if(table.getHeader().missingColumn(columnDescription.getName())) {
            throw new QueryException("Cannot redefine a column %s, because it does not exist. Did you make a typo? Or wanted to add a column instead?");
        }

        table.getHeader().redefineColumn(columnDescription);

        result.status(QueryExecutionStatus.OK, "A column has been successfully redefined.");
    }
}
