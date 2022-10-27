package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.data.CompoundedTableName;
import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.data.exceptions.TableNotFoundException;
import ru.anafro.quark.server.databases.ql.*;
import ru.anafro.quark.server.exceptions.QuarkException;
import ru.anafro.quark.server.networking.Server;

/**
 * This class represents the rename column instruction of Quark QL.
 * <br><br>
 *
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.instructions().get("rename column"); }
 * to get an instance of this class.
 *
 * <br><br>
 *
 * You can check out the syntax of this instruction by running
 * <pre>
 * {@code
 * Quark.instructions().get("rename column").getSyntax();
 * }
 * </pre>
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class RenameColumnInInstruction extends Instruction {

    /**
     * Creates a new instance of the rename column instruction
     * representing object.
     * <br><br>
     *
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.instructions().get("rename column");
     * to get an instance of this class.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("rename column").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public RenameColumnInInstruction() {
        super("rename column in",

                "Renames a column in a table",

                "column.rename",

                InstructionParameter.general("name"),

                InstructionParameter.required("old"),
                InstructionParameter.required("new")
        );
    }

    /**
     * Runs the action of this instruction with passed in arguments.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("rename column").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        var oldName = arguments.getString("old");
        var newName = arguments.getString("new");
        var tableName = arguments.getString("name");

        if(!Table.exists(tableName)) {
            throw new TableNotFoundException(new CompoundedTableName(tableName));
        }

        var table = Table.byName(tableName);

        if(table.getHeader().missingColumn(oldName)) {
            throw new QuarkException("Cannot rename the column '%s' to '%s', because it does not exist.".formatted(
                    oldName,
                    newName
            ));
        }

        if(table.getHeader().hasColumn(newName)) {
            throw new QuarkException("Cannot rename the column '%s' to '%s', because column with such name already exists.".formatted(
                    oldName,
                    newName
            ));
        }

        table.getHeader().save();

        result.status(QueryExecutionStatus.OK, "A column has been successfully renamed.");
    }
}
