package ru.anafro.quark.server.language.instructions;

import ru.anafro.quark.server.language.Instruction;
import ru.anafro.quark.server.language.InstructionArguments;
import ru.anafro.quark.server.language.InstructionResultRecorder;

import static ru.anafro.quark.server.database.data.Table.systemTable;

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
    protected void performAction(InstructionArguments arguments, InstructionResultRecorder result) {
        systemTable("Scheduled Queries").clear();
        result.ok("All the scheduled queries have been removed.");
    }
}
