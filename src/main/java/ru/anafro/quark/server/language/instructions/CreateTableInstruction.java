package ru.anafro.quark.server.language.instructions;

import ru.anafro.quark.server.database.data.Table;
import ru.anafro.quark.server.language.Instruction;
import ru.anafro.quark.server.language.InstructionArguments;
import ru.anafro.quark.server.language.InstructionResultRecorder;
import ru.anafro.quark.server.language.entities.ColumnEntity;
import ru.anafro.quark.server.language.entities.RecordEntity;
import ru.anafro.quark.server.utils.collections.Lists;

import static ru.anafro.quark.server.language.InstructionParameter.*;

/**
 * This class represents the "create table" instruction of Quark QL.
 * <br><br>
 * <p>
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.instructions().get("create table"); }
 * to get an instance of this class.
 *
 * <br><br>
 * <p>
 * You can check out the syntax of this instruction by running
 * <pre>
 * {@code
 * Quark.instructions().get("create table").getSyntax();
 * }
 * </pre>
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public class CreateTableInstruction extends Instruction {

    /**
     * Creates a new instance of the "create table" instruction
     * representing object.
     * <br><br>
     * <p>
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.instructions().get("create table");
     * to get an instance of this class.
     * <br><br>
     * <p>
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("create table").getSyntax();
     * }
     * </pre>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public CreateTableInstruction() {
        super("create table",

                "Creates a table",

                "table.create",

                general("table"),

                required("columns", "list of column"),
                optional("records", "list of record")
        );
    }

    /**
     * Runs the action of this instruction with passed in arguments.
     * <br><br>
     * <p>
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("create table").getSyntax();
     * }
     * </pre>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    @Override
    protected void performAction(InstructionArguments arguments, InstructionResultRecorder result) {
        var tableName = arguments.getTableName("table");
        var columns = arguments.getList(ColumnEntity.class, "columns").stream().map(ColumnEntity::getColumnDescription).toList();
        var records = arguments.tryGetList(RecordEntity.class, "records").orElseGet(Lists::empty);

        Table.create(tableName, columns, records);
        result.ok("A table has been created.");
    }
}
