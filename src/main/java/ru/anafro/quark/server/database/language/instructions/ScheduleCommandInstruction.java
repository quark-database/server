package ru.anafro.quark.server.database.language.instructions;

import ru.anafro.quark.server.database.language.*;
import ru.anafro.quark.server.database.language.entities.LongEntity;
import ru.anafro.quark.server.database.language.entities.StringEntity;
import ru.anafro.quark.server.facade.Quark;

/**
 * This class represents the schedule command instruction of Quark QL.
 * <br><br>
 * <p>
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.instructions().get("schedule command"); }
 * to get an instance of this class.
 *
 * <br><br>
 * <p>
 * You can check out the syntax of this instruction by running
 * <pre>
 * {@code
 * Quark.instructions().get("schedule command").getSyntax();
 * }
 * </pre>
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public class ScheduleCommandInstruction extends Instruction {

    /**
     * Creates a new instance of the schedule command instruction
     * representing object.
     * <br><br>
     * <p>
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.instructions().get("schedule command");
     * to get an instance of this class.
     * <br><br>
     * <p>
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("schedule command").getSyntax();
     * }
     * </pre>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
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
     * <p>
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("schedule command").getSyntax();
     * }
     * </pre>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    @Override
    protected void performAction(InstructionArguments arguments, InstructionResultRecorder result) {
        var command = arguments.getString("command");
        var period = arguments.getLong("period");

        Quark.query("""
                        insert into "Quark.Scheduled Commands": record = @record(%s, %s);
                """.formatted(
                new StringEntity(command).toInstructionForm(),
                new LongEntity(period).toInstructionForm()
        ));

        result.ok("A new command is scheduled. Rerun the server.");
    }
}
