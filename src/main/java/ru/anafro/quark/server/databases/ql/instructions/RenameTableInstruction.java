package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.exceptions.QueryException;
import ru.anafro.quark.server.databases.ql.*;
import ru.anafro.quark.server.files.Databases;
import ru.anafro.quark.server.networking.Server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This class represents the rename table instruction of Quark QL.
 * <br><br>
 *
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.instructions().get("rename table"); }
 * to get an instance of this class.
 *
 * <br><br>
 *
 * You can check out the syntax of this instruction by running
 * <pre>
 * {@code
 * Quark.instructions().get("rename table").getSyntax();
 * }
 * </pre>
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class RenameTableInstruction extends Instruction {

    /**
     * Creates a new instance of the rename table instruction
     * representing object.
     * <br><br>
     *
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.instructions().get("rename table");
     * to get an instance of this class.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("rename table").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public RenameTableInstruction() {
        super("rename table", "table.rename",
                InstructionParameter.required("old"),
                InstructionParameter.required("new")
        );
    }

    /**
     * Runs the action of this instruction with passed in arguments.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("rename table").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        try {
            var oldName = arguments.getString("old");
            var newName = arguments.getString("new");

            if(!Table.exists(oldName)) {
                throw new QueryException("Table '%s' does not exist. You can't rename it.");
            }

            if(Table.exists(newName)) {
                throw new QueryException("Table '%s' already exists. You can't rename table with this name.");
            }

            Files.move(Path.of(Databases.get(oldName)), Path.of(Databases.get(newName)));
        } catch (IOException exception) {
            throw new QueryException("Table cannot be renamed, because: " + exception);
        }

        result.status(QueryExecutionStatus.OK, "Database has been successfully renamed.");
    }
}
