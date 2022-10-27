package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.ql.*;
import ru.anafro.quark.server.databases.ql.entities.ChangerEntity;
import ru.anafro.quark.server.databases.ql.entities.SelectorEntity;
import ru.anafro.quark.server.databases.data.CompoundedTableName;
import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.data.exceptions.ColumnNotFoundException;
import ru.anafro.quark.server.databases.data.exceptions.TableNotFoundException;
import ru.anafro.quark.server.databases.data.structures.RecordCollectionResolver;
import ru.anafro.quark.server.networking.Server;
import ru.anafro.quark.server.utils.integers.Counter;

/**
 * This class represents the change in instruction of Quark QL.
 * <br><br>
 *
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.instructions().get("change in"); }
 * to get an instance of this class.
 *
 * <br><br>
 *
 * You can check out the syntax of this instruction by running
 * <pre>
 * {@code
 * Quark.instructions().get("change in").getSyntax();
 * }
 * </pre>
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class ChangeInInstruction extends Instruction {

    /**
     * Creates a new instance of the change in instruction
     * representing object.
     * <br><br>
     *
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.instructions().get("change in");
     * to get an instance of this class.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("change in").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public ChangeInInstruction() {
        super("change in",

                "Changes records match the condition",

                "table.change",

                InstructionParameter.general("table"),

                InstructionParameter.required("selector", "selector"),
                InstructionParameter.required("changer", "changer")
        );
    }

    /**
     * Runs the action of this instruction with passed in arguments.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("change in").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        var selector = arguments.<SelectorEntity>get("selector").getSelector();
        var changer = arguments.<ChangerEntity>get("changer").getChanger();
        var tableName = arguments.getString("table");

        if(!Table.exists(tableName)) {
            throw new TableNotFoundException(new CompoundedTableName(tableName));
        }

        var table = Table.byName(tableName);

        if(table.getHeader().missingColumn(changer.column())) {
            throw new ColumnNotFoundException(table, changer.column());
        }

        var records = table.loadRecords(new RecordCollectionResolver(RecordCollectionResolver.RecordCollectionResolverCase.JUST_SELECT_EVERYTHING));
        var selectedRecords = new Counter();

        for(var record : records) {
            if(selector.shouldBeSelected(record)) {
                changer.change(record);
                selectedRecords.count();
            }
        }

        table.getRecords().save(records);
        result.status(QueryExecutionStatus.OK, "%d records has been changed".formatted(selectedRecords.getCount()));
    }
}
