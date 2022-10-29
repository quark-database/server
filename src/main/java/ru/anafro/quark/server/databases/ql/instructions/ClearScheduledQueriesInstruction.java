package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.ql.Instruction;
import ru.anafro.quark.server.databases.ql.InstructionArguments;
import ru.anafro.quark.server.databases.ql.InstructionResultRecorder;
import ru.anafro.quark.server.databases.ql.QueryExecutionStatus;
import ru.anafro.quark.server.networking.Server;

public class ClearScheduledQueriesInstruction extends Instruction {
    /**
     * Creates a new instruction object. You should not use it anywhere
     * but in the registering ({@code Quark.instructions().add(new YourInstruction()}).
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public ClearScheduledQueriesInstruction() {
        super(
                "clear scheduled queries",
                "Clears all the scheduled queries.",
                "server.schedule.query.clear"
        );
    }

    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        Quark.runInstruction("""
                clear table "Quark.Scheduled Queries";
        """);
        result.status(QueryExecutionStatus.OK, "All the scheduled queries have been removed.");
    }
}
