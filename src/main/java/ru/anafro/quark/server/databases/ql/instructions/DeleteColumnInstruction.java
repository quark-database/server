package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.data.CompoundedTableName;
import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.data.exceptions.TableNotFoundException;
import ru.anafro.quark.server.databases.data.structures.RecordCollectionResolver;
import ru.anafro.quark.server.databases.exceptions.QueryException;
import ru.anafro.quark.server.databases.ql.Instruction;
import ru.anafro.quark.server.databases.ql.InstructionArguments;
import ru.anafro.quark.server.databases.ql.InstructionParameter;
import ru.anafro.quark.server.databases.ql.InstructionResultRecorder;
import ru.anafro.quark.server.networking.Server;

/**
 * This class represents the delete column instruction of Quark QL.
 * <br><br>
 *
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.instructions().get("delete column"); }
 * to get an instance of this class.
 *
 * <br><br>
 *
 * You can check out the syntax of this instruction by running
 * <pre>
 * {@code
 * Quark.instructions().get("delete column").getSyntax();
 * }
 * </pre>
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class DeleteColumnInstruction extends Instruction {

    /**
     * Creates a new instance of the delete column instruction
     * representing object.
     * <br><br>
     *
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.instructions().get("delete column");
     * to get an instance of this class.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("delete column").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public DeleteColumnInstruction() {
        super("delete column",

                "Deletes a column in a table",

                "column.delete",

                InstructionParameter.general("name"),

                InstructionParameter.required("table")
        );
    }

    /**
     * Runs the action of this instruction with passed in arguments.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("delete column").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        var columnName = arguments.getString("name");
        var tableName = arguments.getString("table");

        if(!Table.exists(tableName)) {
            throw new TableNotFoundException(new CompoundedTableName(tableName));
        }

        var table = Table.byName(tableName);

        if(table.getHeader().missingColumn(columnName)) {
            throw new QueryException("Table '%s' does not contain column '%s'.".formatted(
                    tableName,
                    columnName
            ));
        }

        var records = table.loadRecords(new RecordCollectionResolver(RecordCollectionResolver.RecordCollectionResolverCase.JUST_SELECT_EVERYTHING));

        records.forEach(record -> record.removeField(columnName));

        table.getHeader().getColumns().removeIf(columnDescription -> columnDescription.getName().equals(columnName));
        table.getHeader().getModifiers().removeIf(columnModifierEntity -> columnModifierEntity.getColumnName().equals(columnName));

        table.getHeader().save();
        table.getRecords().save(records);
    }
}
