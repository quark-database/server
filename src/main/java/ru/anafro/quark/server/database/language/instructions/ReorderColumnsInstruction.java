package ru.anafro.quark.server.database.language.instructions;

import ru.anafro.quark.server.database.data.ColumnDescription;
import ru.anafro.quark.server.database.data.Table;
import ru.anafro.quark.server.database.data.TableName;
import ru.anafro.quark.server.database.data.exceptions.TableNotFoundException;
import ru.anafro.quark.server.database.data.structures.RecordCollectionResolver;
import ru.anafro.quark.server.database.exceptions.QueryException;
import ru.anafro.quark.server.database.language.Instruction;
import ru.anafro.quark.server.database.language.InstructionArguments;
import ru.anafro.quark.server.database.language.InstructionResultRecorder;
import ru.anafro.quark.server.database.language.entities.StringEntity;
import ru.anafro.quark.server.utils.collections.Lists;

import static ru.anafro.quark.server.database.language.InstructionParameter.general;
import static ru.anafro.quark.server.database.language.InstructionParameter.required;

/**
 * This class represents the reorder columns in instruction of Quark QL.
 * <br><br>
 * <p>
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.instructions().get("reorder columns in"); }
 * to get an instance of this class.
 *
 * <br><br>
 * <p>
 * You can check out the syntax of this instruction by running
 * <pre>
 * {@code
 * Quark.instructions().get("reorder columns in").getSyntax();
 * }
 * </pre>
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public class ReorderColumnsInstruction extends Instruction {

    /**
     * Creates a new instance of the reorder columns in instruction
     * representing object.
     * <br><br>
     * <p>
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.instructions().get("reorder columns in");
     * to get an instance of this class.
     * <br><br>
     * <p>
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("reorder columns in").getSyntax();
     * }
     * </pre>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public ReorderColumnsInstruction() {
        super(
                "reorder columns in",

                "Changes the column order",

                "column.reorder",

                general("table"),

                required("order", "list of str")
        );
    }

    /**
     * Runs the action of this instruction with passed in arguments.
     * <br><br>
     * <p>
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("reorder columns in").getSyntax();
     * }
     * </pre>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    @Override
    protected void performAction(InstructionArguments arguments, InstructionResultRecorder result) {
        var tableName = arguments.getString("table");
        var order = arguments.getList("order").stream().map(entity -> ((StringEntity) entity).getString()).toList();

        if (!Table.exists(tableName)) {
            throw new TableNotFoundException(new TableName(tableName));
        }

        var table = Table.byName(tableName);

        if (order.stream().anyMatch(columnName -> !table.getHeader().hasColumn(columnName)) || table.getHeader().getColumns().stream().anyMatch(columnDescription -> !order.contains(columnDescription.name()))) {
            throw new QueryException("A new order cannot be applied, because it has extra columns or missed some existing ones (your order: %s, existing order: %s).".formatted(
                    Lists.join(order),
                    Lists.join(table.getHeader().getColumns(), ColumnDescription::name)
            ));
        }

        var records = table.loadRecords(new RecordCollectionResolver(RecordCollectionResolver.RecordCollectionResolverCase.JUST_SELECT_EVERYTHING));

        for (var record : records) {
            record.reorderFields(order);
        }

        result.ok("All the columns has been reordered successfully.");
    }
}
