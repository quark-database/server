package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.exceptions.QuerySyntaxException;
import ru.anafro.quark.server.databases.ql.Instruction;
import ru.anafro.quark.server.databases.ql.InstructionArguments;
import ru.anafro.quark.server.databases.ql.InstructionParameter;
import ru.anafro.quark.server.databases.ql.InstructionResultRecorder;
import ru.anafro.quark.server.databases.ql.entities.IntegerEntity;
import ru.anafro.quark.server.networking.Ports;
import ru.anafro.quark.server.networking.Server;
import ru.anafro.quark.server.networking.exceptions.PortIsUnavailableException;

/**
 * This class represents the change port to instruction of Quark QL.
 * <br><br>
 *
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.instructions().get("change port to"); }
 * to get an instance of this class.
 *
 * <br><br>
 *
 * You can check out the syntax of this instruction by running
 * <pre>
 * {@code
 * Quark.instructions().get("change port to").getSyntax();
 * }
 * </pre>
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class ChangePortToInstruction extends Instruction {

    /**
     * Creates a new instance of the change port to instruction
     * representing object.
     * <br><br>
     *
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.instructions().get("change port to");
     * to get an instance of this class.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("change port to").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public ChangePortToInstruction() {
        super("change port to", "server.port.change",

                InstructionParameter.general("port", "int")
        );
    }

    /**
     * Runs the action of this instruction with passed in arguments.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("change port to").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        int newPort = arguments.<IntegerEntity>get("port").getValue();

        if(Ports.isInvalid(newPort)) {
            throw new QuerySyntaxException("Port should be between %d and %d, not %d".formatted(Ports.FIRST, Ports.LAST, newPort));
        }

        if(Ports.isUnavailable(newPort)) {
            throw new PortIsUnavailableException(newPort);
        }

        server.getConfiguration().setPort(newPort);
        server.getConfiguration().save();

        Quark.warning("The port has been changed. To start the server on the new port, please, reload the server manually (stop it by typing 'exit' and run it again).");
//        server.reload();
    }
}
