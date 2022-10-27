package ru.anafro.quark.server.plugins.events;

import ru.anafro.quark.server.databases.ql.Instruction;
import ru.anafro.quark.server.databases.ql.InstructionArguments;
import ru.anafro.quark.server.databases.ql.InstructionResult;

public class InstructionFinished extends Event {
    private final Instruction instruction;
    private final InstructionArguments arguments;
    private final InstructionResult result;

    public InstructionFinished(Instruction instruction, InstructionArguments arguments, InstructionResult result) {
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
