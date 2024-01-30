package ru.anafro.quark.server.language.instructions;

import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.language.Instruction;
import ru.anafro.quark.server.language.InstructionArguments;
import ru.anafro.quark.server.language.InstructionResultRecorder;

/**
 * This class represents the stop server instruction of Quark QL.
 * <br><br>
 * <p>
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.instructions().get("stop server"); }
 * to get an instance of this class.
 *
 * <br><br>
 * <p>
 * You can check out the syntax of this instruction by running
 * <pre>
 * {@code
 * Quark.instructions().get("stop server").getSyntax();
 * }
 * </pre>
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public class StopServerInstruction extends Instruction {

    /**
     * Creates a new instance of the stop server instruction
     * representing object.
     * <br><br>
     * <p>
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.instructions().get("stop server");
     * to get an instance of this class.
     * <br><br>
     * <p>
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("stop server").getSyntax();
     * }
     * </pre>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public StopServerInstruction() {
        super(
                "stop server",

                "Stop the server",

                "server.stop"
        );
    }

    /**
     * Runs the action of this instruction with passed in arguments.
     * <br><br>
     * <p>
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("stop server").getSyntax();
     * }
     * </pre>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    @Override
    protected void performAction(InstructionArguments arguments, InstructionResultRecorder result) {
        Quark.info(STR."The server is stopped by querying '\{this.getName()}'.");
        Quark.exit();
    }
}
