package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.ql.Instruction;
import ru.anafro.quark.server.databases.ql.InstructionArguments;
import ru.anafro.quark.server.databases.ql.InstructionResultRecorder;
import ru.anafro.quark.server.databases.ql.QueryExecutionStatus;
import ru.anafro.quark.server.networking.Server;

public class ClearScheduledCommandsInstruction extends Instruction {
    /**
     * Creates a new instruction object. You should not use it anywhere
     * but in the registering ({@code Quark.instructions().add(new YourInstruction()}).
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public ClearScheduledCommandsInstruction() {
        super(
                "clear scheduled commands",
                "Clears all the scheduled commands.",
                "server.schedule.command.clear"
        );
    }

    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        Quark.runInstruction("""
                clear table "Quark.Scheduled Commands";
        """);
        result.status(QueryExecutionStatus.OK, "All the scheduled commands have been removed.");
    }
}
