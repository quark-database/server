package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.data.Database;
import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.data.TableRecord;
import ru.anafro.quark.server.databases.data.structures.RecordCollectionResolver;
import ru.anafro.quark.server.databases.ql.*;
import ru.anafro.quark.server.databases.ql.entities.ListEntity;
import ru.anafro.quark.server.databases.ql.entities.StringEntity;
import ru.anafro.quark.server.networking.Server;

/**
 * This class represents the create token instruction of Quark QL.
 * <br><br>
 *
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.instructions().get("create token"); }
 * to get an instance of this class.
 *
 * <br><br>
 *
 * You can check out the syntax of this instruction by running
 * <pre>
 * {@code
 * Quark.instructions().get("create token").getSyntax();
 * }
 * </pre>
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class CreateTokenInstruction extends Instruction {

    /**
     * Creates a new instance of the create token instruction
     * representing object.
     * <br><br>
     *
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.instructions().get("create token");
     * to get an instance of this class.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("create token").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public CreateTokenInstruction() {
        super("create token",

                "Creates an access token",

                "token.create",

                InstructionParameter.general("token"),

                InstructionParameter.required("permissions", "list of str"));
    }

    /**
     * Runs the action of this instruction with passed in arguments.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("create token").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        if(!Database.exists("Quark")) {
            Quark.logger().warning("Quark database is missing. Trying to create a new one...");
            Database.create("Quark");
        }

        if(!Table.exists("Quark.Tokens")) {
            Quark.logger().warning("Quark.Tokens table is missing. Trying to create a new one...");
            Quark.runInstruction("""
                    create table "Quark.Tokens": columns = @list(@str("token"), @str("permission"));
            """);
        }

        var table = Table.byName("Quark.Tokens");
        var records = table.loadRecords(new RecordCollectionResolver(RecordCollectionResolver.RecordCollectionResolverCase.JUST_SELECT_EVERYTHING));
        var token = arguments.getString("token");
        var permissions = arguments.getList("permissions");

        for(var permission : permissions) {
            records.add(new TableRecord(table, new ListEntity("any", new StringEntity(token), new StringEntity(permission.valueAs(String.class)))));
        }

        table.getRecords().save(records);
        result.status(QueryExecutionStatus.OK, "A new token was registered!");
    }
}
