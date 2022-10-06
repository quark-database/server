package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.data.CompoundedTableName;
import ru.anafro.quark.server.databases.data.RecordIterationLimiter;
import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.data.exceptions.TableNotFoundException;
import ru.anafro.quark.server.databases.data.structures.RecordCollectionResolver;
import ru.anafro.quark.server.databases.ql.*;
import ru.anafro.quark.server.databases.ql.entities.SelectorEntity;
import ru.anafro.quark.server.networking.Server;

/**
 * This class represents the delete from instruction of Quark QL.
 * <br><br>
 *
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.instructions().get("delete from"); }
 * to get an instance of this class.
 *
 * <br><br>
 *
 * You can check out the syntax of this instruction by running
 * <pre>
 * {@code
 * Quark.instructions().get("delete from").getSyntax();
 * }
 * </pre>
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class DeleteFromInstruction extends Instruction {

    /**
     * Creates a new instance of the delete from instruction
     * representing object.
     * <br><br>
     *
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.instructions().get("delete from");
     * to get an instance of this class.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("delete from").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public DeleteFromInstruction() {
        super("delete from", "data.delete",

                InstructionParameter.general("table"),

                InstructionParameter.required("selector", "selector"),
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
     * Quark.instructions().get("delete from").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        var tableName = arguments.getString("table");
        var selector = arguments.<SelectorEntity>get("selector");

        if(!Table.exists(tableName)) {
            throw new TableNotFoundException(new CompoundedTableName(tableName));
        }

        var table = Table.byName(tableName);
        var records = table.loadRecords(new RecordCollectionResolver(RecordCollectionResolver.RecordCollectionResolverCase.SELECTOR_IS_TOO_COMPLEX));

        records.remove(selector.getSelector(), new RecordIterationLimiter(
                arguments.has("skip") ? arguments.getInteger("skip") : 0,
                arguments.has("limit") ? arguments.getInteger("limit") : Integer.MAX_VALUE
        ));

        table.getRecords().save(records);

        result.status(QueryExecutionStatus.OK, "Deletion of some records in table '%s' has been performed.".formatted(tableName));
    }
}
