package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.ql.*;
import ru.anafro.quark.server.databases.ql.entities.LongEntity;
import ru.anafro.quark.server.databases.ql.entities.StringEntity;
import ru.anafro.quark.server.networking.Server;

/**
 * This class represents the schedule command instruction of Quark QL.
 * <br><br>
 *
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.instructions().get("schedule command"); }
 * to get an instance of this class.
 *
 * <br><br>
 *
 * You can check out the syntax of this instruction by running
 * <pre>
 * {@code
 * Quark.instructions().get("schedule command").getSyntax();
 * }
 * </pre>
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class ScheduleCommandInstruction extends Instruction {

    /**
     * Creates a new instance of the schedule command instruction
     * representing object.
     * <br><br>
     *
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.instructions().get("schedule command");
     * to get an instance of this class.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("schedule command").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public ScheduleCommandInstruction() {
        super(
                "schedule command",

                "Schedules a command",

                "server.schedule.command",

                InstructionParameter.general("command"),

                InstructionParameter.required("period", "long")
        );
    }

    /**
     * Runs the action of this instruction with passed in arguments.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("schedule command").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        var command = arguments.getString("command");
        var period = arguments.getLong("period");

        Quark.runInstruction("""
                insert into "Quark.Scheduled Commands": record = @record(%s, %s);
        """.formatted(
                new StringEntity(command).toInstructionForm(),
                new LongEntity(period).toInstructionForm()
        ));

        result.status(QueryExecutionStatus.OK, "A new command is scheduled. Rerun the server.");
    }
}
