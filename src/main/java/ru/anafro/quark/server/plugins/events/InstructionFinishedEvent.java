package ru.anafro.quark.server.plugins.events;

import ru.anafro.quark.server.database.language.Instruction;
import ru.anafro.quark.server.database.language.InstructionArguments;
import ru.anafro.quark.server.database.language.InstructionResult;

public class InstructionFinishedEvent extends Event {
    private final Instruction instruction;
    private final InstructionArguments arguments;
    private final InstructionResult result;

    public InstructionFinishedEvent(Instruction instruction, InstructionArguments arguments, InstructionResult result) {
        this.instruction = instruction;
        this.arguments = arguments;
        this.result = result;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public InstructionArguments getArguments() {
        return arguments;
    }

    public InstructionResult getResult() {
        return result;
    }
}
