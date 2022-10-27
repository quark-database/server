package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.data.Database;
import ru.anafro.quark.server.databases.exceptions.QueryException;
import ru.anafro.quark.server.databases.ql.*;
import ru.anafro.quark.server.networking.Server;

/**
 * This class represents the create database instruction of Quark QL.
 * <br><br>
 *
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.instructions().get("create database"); }
 * to get an instance of this class.
 *
 * <br><br>
 *
 * You can check out the syntax of this instruction by running
 * <pre>
 * {@code
 * Quark.instructions().get("create database").getSyntax();
 * }
 * </pre>
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class CreateDatabaseInstruction extends Instruction {

    /**
     * Creates a new instance of the create database instruction
     * representing object.
     * <br><br>
     *
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.instructions().get("create database");
     * to get an instance of this class.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("create database").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public CreateDatabaseInstruction() {
        super("create database",

                "Creates a database",

                "database.create",

                InstructionParameter.general("name")
        );
    }

    /**
     * Runs the action of this instruction with passed in arguments.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("create database").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        var databaseName = arguments.getString("name");

        if(Database.exists(databaseName)) {
            throw new QueryException("Database '%s' already exists.".formatted(databaseName));
        }

        Database.create(databaseName);

        result.status(QueryExecutionStatus.OK, "Database '%s' successfully created.".formatted(databaseName));
    }
}
