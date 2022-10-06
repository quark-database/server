package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.data.ColumnDescription;
import ru.anafro.quark.server.databases.data.CompoundedTableName;
import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.data.exceptions.TableNotFoundException;
import ru.anafro.quark.server.databases.data.structures.RecordCollectionResolver;
import ru.anafro.quark.server.databases.exceptions.QueryException;
import ru.anafro.quark.server.databases.ql.*;
import ru.anafro.quark.server.databases.ql.entities.StringEntity;
import ru.anafro.quark.server.networking.Server;
import ru.anafro.quark.server.utils.containers.Lists;

/**
 * This class represents the reorder columns in instruction of Quark QL.
 * <br><br>
 *
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.instructions().get("reorder columns in"); }
 * to get an instance of this class.
 *
 * <br><br>
 *
 * You can check out the syntax of this instruction by running
 * <pre>
 * {@code
 * Quark.instructions().get("reorder columns in").getSyntax();
 * }
 * </pre>
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class ReorderColumnsInstruction extends Instruction {

    /**
     * Creates a new instance of the reorder columns in instruction
     * representing object.
     * <br><br>
     *
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.instructions().get("reorder columns in");
     * to get an instance of this class.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("reorder columns in").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public ReorderColumnsInstruction() {
        super("reorder columns in", "column.reorder",

                InstructionParameter.general("table"),

                InstructionParameter.required("order", "list of str")
        );
    }

    /**
     * Runs the action of this instruction with passed in arguments.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("reorder columns in").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        var tableName = arguments.getString("table");
        var order = arguments.getList("order").stream().map(entity -> ((StringEntity) entity).getString()).toList();

        if(!Table.exists(tableName)) {
            throw new TableNotFoundException(new CompoundedTableName(tableName));
        }

        var table = Table.byName(tableName);

        if(order.stream().anyMatch(columnName -> !table.getHeader().hasColumn(columnName)) || table.getHeader().getColumns().stream().anyMatch(columnDescription -> !order.contains(columnDescription.getName()))) {
            throw new QueryException("A new order cannot be applied, because it has extra columns or missed some existing ones (your order: %s, existing order: %s).".formatted(
                    Lists.join(order),
                    Lists.joinPresentations(table.getHeader().getColumns(), ColumnDescription::getName)
            ));
        }

        var records = table.loadRecords(new RecordCollectionResolver(RecordCollectionResolver.RecordCollectionResolverCase.JUST_SELECT_EVERYTHING));

        for(var record : records) {
            record.reorderFields(order);
        }

        result.status(QueryExecutionStatus.OK, "All the columns has been reordered successfully.");
    }
}
