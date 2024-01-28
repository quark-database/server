package ru.anafro.quark.server.language.instructions;

import ru.anafro.quark.server.language.Instruction;
import ru.anafro.quark.server.language.InstructionArguments;
import ru.anafro.quark.server.language.InstructionResultRecorder;
import ru.anafro.quark.server.facade.Quark;

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
    protected void performAction(InstructionArguments arguments, InstructionResultRecorder result) {
        Quark.query("""
                        clear table "Quark.Scheduled Commands";
                """);
        result.ok("All the scheduled commands have been removed.");
    }
}
