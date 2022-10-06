package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.data.CompoundedTableName;
import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.data.exceptions.DatabaseFileException;
import ru.anafro.quark.server.databases.data.exceptions.TableNotFoundException;
import ru.anafro.quark.server.databases.exceptions.QueryException;
import ru.anafro.quark.server.databases.ql.*;
import ru.anafro.quark.server.files.Databases;
import ru.anafro.quark.server.networking.Server;
import ru.anafro.quark.server.utils.files.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * This class represents the clone table instruction of Quark QL.
 * <br><br>
 *
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.instructions().get("clone table"); }
 * to get an instance of this class.
 *
 * <br><br>
 *
 * You can check out the syntax of this instruction by running
 * <pre>
 * {@code
 * Quark.instructions().get("clone table").getSyntax();
 * }
 * </pre>
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class CloneTableInstruction extends Instruction {

    /**
     * Creates a new instance of the clone table instruction
     * representing object.
     * <br><br>
     *
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.instructions().get("clone table");
     * to get an instance of this class.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("clone table").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public CloneTableInstruction() {
        super("clone table",
                "table.clone",

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
     * Quark.instructions().get("clone table").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        var prototypeName = arguments.getString("prototype");
        var destinationName = new CompoundedTableName(arguments.getString("destination"));

        if(!Table.exists(prototypeName)) {
            throw new TableNotFoundException(new CompoundedTableName(prototypeName));
        }

        if(Table.exists(destinationName.toCompoundedString())) {
            throw new QueryException("Table '%s' can't be a destination of cloning, it already exists. Delete it first.");
        }

        var prototype = Table.byName(prototypeName);
        var prototypeFolder = new File(Path.of(prototype.getDatabase().getFolder().getPath(), prototype.getName()).toUri());

        try {
            FileUtils.copyDirectory(prototypeFolder.toPath().toString(), Path.of(Databases.get(destinationName.getDatabaseName()), destinationName.getTableName()).toString());
        } catch (IOException exception) {
            throw new DatabaseFileException("Cannot clone table '%s' to '%s', because of %s: %s.".formatted(
                    prototypeName,
                    destinationName.toCompoundedString(),
                    exception.getClass().getSimpleName(),
                    exception.getMessage()
            ));
        }

        result.status(QueryExecutionStatus.OK, "Table '%s' successfully cloned to '%s'.".formatted(
                prototypeName,
                destinationName.toCompoundedString()
        ));
    }
}
