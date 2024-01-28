package ru.anafro.quark.server.database.language.instructions;

import ru.anafro.quark.server.database.data.Database;
import ru.anafro.quark.server.database.data.Table;
import ru.anafro.quark.server.database.data.TableRecord;
import ru.anafro.quark.server.database.data.structures.RecordCollectionResolver;
import ru.anafro.quark.server.database.language.Instruction;
import ru.anafro.quark.server.database.language.InstructionArguments;
import ru.anafro.quark.server.database.language.InstructionResultRecorder;
import ru.anafro.quark.server.database.language.entities.ListEntity;
import ru.anafro.quark.server.database.language.entities.StringEntity;
import ru.anafro.quark.server.facade.Quark;

import static ru.anafro.quark.server.database.language.InstructionParameter.general;
import static ru.anafro.quark.server.database.language.InstructionParameter.required;

/**
 * This class represents the "create token" instruction of Quark QL.
 * <br><br>
 * <p>
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.instructions().get("create token"); }
 * to get an instance of this class.
 *
 * <br><br>
 * <p>
 * You can check out the syntax of this instruction by running
 * <pre>
 * {@code
 * Quark.instructions().get("create token").getSyntax();
 * }
 * </pre>
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public class CreateTokenInstruction extends Instruction {

    /**
     * Creates a new instance of the "create token" instruction
     * representing object.
     * <br><br>
     * <p>
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.instructions().get("create token");
     * to get an instance of this class.
     * <br><br>
     * <p>
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("create token").getSyntax();
     * }
     * </pre>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public CreateTokenInstruction() {
        super("create token",

                "Creates an access token",

                "token.create",

                general("token"),

                required("permissions", "list of str"));
    }

    /**
     * Runs the action of this instruction with passed in arguments.
     * <br><br>
     * <p>
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("create token").getSyntax();
     * }
     * </pre>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    @Override
    protected void performAction(InstructionArguments arguments, InstructionResultRecorder result) {
        if (!Database.exists("Quark")) {
            Quark.logger().warning("Quark database is missing. Trying to create a new one...");
            Database.create("Quark");
        }

        if (!Table.exists("Quark.Tokens")) {
            Quark.logger().warning("Quark.Tokens table is missing. Trying to create a new one...");
            Quark.query("""
                            create table "Quark.Tokens": columns = @list(@str("token"), @str("permission"));
                    """);
        }

        var table = Table.byName("Quark.Tokens");
        var records = table.loadRecords(new RecordCollectionResolver(RecordCollectionResolver.RecordCollectionResolverCase.JUST_SELECT_EVERYTHING));
        var token = arguments.getString("token");
        var permissions = arguments.getList("permissions");

        for (var permission : permissions) {
            records.add(new TableRecord(table, new ListEntity("any", new StringEntity(token), new StringEntity(permission.valueAs(String.class)))));
        }

        table.getRecords().save(records);
        result.ok("A new token was registered!");
    }
}
