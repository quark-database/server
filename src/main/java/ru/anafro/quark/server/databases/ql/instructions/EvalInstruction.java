package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.ql.*;
import ru.anafro.quark.server.databases.views.TableViewHeader;
import ru.anafro.quark.server.databases.views.TableViewRow;
import ru.anafro.quark.server.networking.Server;

/**
 * This class represents the eval instruction of Quark QL.
 * <br><br>
 *
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.instructions().get("eval"); }
 * to get an instance of this class.
 *
 * <br><br>
 *
 * You can check out the syntax of this instruction by running
 * <pre>
 * {@code
 * Quark.instructions().get("eval").getSyntax();
 * }
 * </pre>
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class EvalInstruction extends Instruction {

    /**
     * Creates a new instance of the eval instruction
     * representing object.
     * <br><br>
     *
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.instructions().get("eval");
     * to get an instance of this class.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("eval").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public EvalInstruction() {
        super(
                "eval",

                "Evaluates the Quark QL entity.",

                "!unsafe.eval",

                InstructionParameter.general("entity", "?")
        );
    }

    /**
     * Runs the action of this instruction with passed in arguments.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("eval").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        var entity = arguments.get("entity");

        result.header(new TableViewHeader("value", "type", "exact type"));
        result.appendRow(new TableViewRow(entity.toInstructionForm(), entity.getTypeName(), entity.getExactTypeName()));
        result.status(QueryExecutionStatus.OK, "Expression is evaluated.");
    }
}
