package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.data.Database;
import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.exceptions.QueryException;
import ru.anafro.quark.server.databases.ql.*;
import ru.anafro.quark.server.files.Databases;
import ru.anafro.quark.server.networking.Server;
import ru.anafro.quark.server.utils.files.FileUtils;

import java.io.IOException;

/**
 * This class represents the clone database scheme instruction of Quark QL.
 * <br><br>
 *
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.instructions().get("clone database scheme"); }
 * to get an instance of this class.
 *
 * <br><br>
 *
 * You can check out the syntax of this instruction by running
 * <pre>
 * {@code
 * Quark.instructions().get("clone database scheme").getSyntax();
 * }
 * </pre>
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class CloneDatabaseSchemeInstruction extends Instruction {

    /**
     * Creates a new instance of the clone database scheme instruction
     * representing object.
     * <br><br>
     *
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.instructions().get("clone database scheme");
     * to get an instance of this class.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("clone database scheme").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public CloneDatabaseSchemeInstruction() { // TODO: register
        super("clone database scheme",

                "Clones the database, but clears the tables",

                "database.scheme",

                InstructionParameter.general("prototype"),

                InstructionParameter.required("destination")
        );
    }

    /**
     * Runs the action of this instruction with passed in arguments.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("clone database scheme").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        try {
            var prototypeName = arguments.getString("prototype");
            var destinationName = arguments.getString("destination");

            if (!Database.exists(prototypeName)) {
                throw new QueryException("Prototype database '%s' does not exist.".formatted(prototypeName));
            }

            if (Database.exists(destinationName)) {
                throw new QueryException("Destination database '%s' already exist. To clone to here, delete it first.");
            }

            var prototype = Database.byName(prototypeName);
            FileUtils.copyDirectory(prototype.getFolder().getPath(), Databases.get(destinationName));

            var destination = Database.byName(destinationName);

            destination.allTables().forEach(Table::clear);

            result.status(QueryExecutionStatus.OK, "Database '%s' successfully cloned to '%s', and all the tables was cleared in the destination.".formatted(
                    prototypeName,
                    destinationName
            ));
        } catch(IOException exception) {
            throw new QueryException("Unable to clone database, because of %s: %s.".formatted(
                    exception.getClass().getSimpleName(),
                    exception.getMessage()
            ));
        }
    }
}
