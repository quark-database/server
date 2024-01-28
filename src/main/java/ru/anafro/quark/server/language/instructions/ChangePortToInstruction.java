package ru.anafro.quark.server.language.instructions;

import ru.anafro.quark.server.database.exceptions.QuerySyntaxException;
import ru.anafro.quark.server.language.Instruction;
import ru.anafro.quark.server.language.InstructionArguments;
import ru.anafro.quark.server.language.InstructionResultRecorder;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.networking.Ports;

import static ru.anafro.quark.server.language.InstructionParameter.general;

/**
 * This class represents the change port to instruction of Quark QL.
 * <br><br>
 * <p>
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.instructions().get("change port to"); }
 * to get an instance of this class.
 *
 * <br><br>
 * <p>
 * You can check out the syntax of this instruction by running
 * <pre>
 * {@code
 * Quark.instructions().get("change port to").getSyntax();
 * }
 * </pre>
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public class ChangePortToInstruction extends Instruction {

    /**
     * Creates a new instance of the change port to instruction
     * representing object.
     * <br><br>
     * <p>
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.instructions().get("change port to");
     * to get an instance of this class.
     * <br><br>
     * <p>
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("change port to").getSyntax();
     * }
     * </pre>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public ChangePortToInstruction() {
        super("change port to",

                "Changes the server port",

                "server.port.change",

                general("port", "int")
        );
    }

    /**
     * Runs the action of this instruction with passed in arguments.
     * <br><br>
     * <p>
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("change port to").getSyntax();
     * }
     * </pre>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    @Override
    protected void performAction(InstructionArguments arguments, InstructionResultRecorder result) {
        int newPort = arguments.getInt("port");

        if (Ports.isUsable(newPort)) {
            throw new QuerySyntaxException(STR."Port should be between \{Ports.FIRST} and \{Ports.LAST}, not \{newPort}");
        }

        var configuration = Quark.configuration();

        configuration.setPort(newPort);
        configuration.save();

        Quark.reload();
    }
}
