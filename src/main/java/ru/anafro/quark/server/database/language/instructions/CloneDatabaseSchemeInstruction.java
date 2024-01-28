package ru.anafro.quark.server.database.language.instructions;

import ru.anafro.quark.server.database.data.Database;
import ru.anafro.quark.server.database.data.Table;
import ru.anafro.quark.server.database.exceptions.QueryException;
import ru.anafro.quark.server.database.language.Instruction;
import ru.anafro.quark.server.database.language.InstructionArguments;
import ru.anafro.quark.server.database.language.InstructionResultRecorder;

import static ru.anafro.quark.server.database.language.InstructionParameter.general;
import static ru.anafro.quark.server.database.language.InstructionParameter.required;

/**
 * This class represents the clone database scheme instruction of Quark QL.
 * <br><br>
 * <p>
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.instructions().get("clone database scheme"); }
 * to get an instance of this class.
 *
 * <br><br>
 * <p>
 * You can check out the syntax of this instruction by running
 * <pre>
 * {@code
 * Quark.instructions().get("clone database scheme").getSyntax();
 * }
 * </pre>
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public class CloneDatabaseSchemeInstruction extends Instruction {

    /**
     * Creates a new instance of the clone database scheme instruction
     * representing object.
     * <br><br>
     * <p>
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.instructions().get("clone database scheme");
     * to get an instance of this class.
     * <br><br>
     * <p>
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("clone database scheme").getSyntax();
     * }
     * </pre>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public CloneDatabaseSchemeInstruction() { // TODO: register
        super("clone database scheme",

                "Clones the database, but clears the tables",

                "database.scheme",

                general("prototype"),

                required("destination")
        );
    }

    /**
     * Runs the action of this instruction with passed in arguments.
     * <br><br>
     * <p>
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("clone database scheme").getSyntax();
     * }
     * </pre>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    @Override
    protected void performAction(InstructionArguments arguments, InstructionResultRecorder result) {
        var prototype = arguments.getDatabase("prototype");
        var destinationName = arguments.getString("destination");

        if (Database.exists(destinationName)) {
            throw new QueryException(STR."Destination database '\{destinationName}' already exists.");
        }

        prototype.copy(destinationName).allTables().forEach(Table::clear);

        result.ok(STR."Database '\{prototype.getName()}' successfully cloned to '\{destinationName}', and all the tables were cleared in the destination.");

    }
}
