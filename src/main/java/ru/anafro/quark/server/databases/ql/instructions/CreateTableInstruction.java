package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.data.ColumnDescription;
import ru.anafro.quark.server.databases.data.CompoundedTableName;
import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.data.TableRecord;
import ru.anafro.quark.server.databases.data.structures.LinearRecordCollection;
import ru.anafro.quark.server.databases.ql.*;
import ru.anafro.quark.server.databases.ql.entities.ColumnModifierEntity;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.ListEntity;
import ru.anafro.quark.server.databases.ql.entities.RecordEntity;
import ru.anafro.quark.server.exceptions.QuarkException;
import ru.anafro.quark.server.networking.Server;
import ru.anafro.quark.server.utils.containers.Lists;

import java.util.ArrayList;

/**
 * This class represents the create table instruction of Quark QL.
 * <br><br>
 *
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.instructions().get("create table"); }
 * to get an instance of this class.
 *
 * <br><br>
 *
 * You can check out the syntax of this instruction by running
 * <pre>
 * {@code
 * Quark.instructions().get("create table").getSyntax();
 * }
 * </pre>
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class CreateTableInstruction extends Instruction {

    /**
     * Creates a new instance of the create table instruction
     * representing object.
     * <br><br>
     *
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.instructions().get("create table");
     * to get an instance of this class.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("create table").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public CreateTableInstruction() {
        super("create table",

                "Creates a table",

                "table.create",

                InstructionParameter.general("name"),

                InstructionParameter.required("columns", "list of column"),
                InstructionParameter.optional("modifiers", "list of modifier"),
                InstructionParameter.optional("records", "list of record")
        );
    }

    /**
     * Runs the action of this instruction with passed in arguments.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("create table").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        var tableName = arguments.getString("name");
        var columns = arguments.getList("columns");
        var modifiers = arguments.has("modifiers") ? arguments.getList("modifiers") : Lists.<Entity>empty();
        var records = arguments.has("records") ? arguments.getList("records") : Lists.<Entity>empty();

        if(Table.exists(tableName)) {
            throw new QuarkException("Table '%s' already exists.".formatted(tableName));
        }

        var table = Table.create(
                new CompoundedTableName(tableName),
                Lists.empty(),
                (ArrayList<ColumnModifierEntity>) (ArrayList<?>) modifiers
        );

        for(var column : columns) {
            table.getHeader().addColumn(column.valueAs(ColumnDescription.class));
        }

        var recordCollection = new LinearRecordCollection();
        records.forEach(record -> recordCollection.add(new TableRecord(table, new ListEntity("any", ((RecordEntity) record).getValues()))));

        table.getHeader().save();
        table.getRecords().save(recordCollection);

        result.status(QueryExecutionStatus.OK, "A table has been created.");
    }
}
