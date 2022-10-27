package ru.anafro.quark.server.plugins.events;

import ru.anafro.quark.server.databases.ql.Instruction;
import ru.anafro.quark.server.databases.ql.InstructionArguments;

public class BeforeInstructionExecuted extends Event {
    private final Instruction instruction;
    private final InstructionArguments arguments;

    public BeforeInstructionExecuted(Instruction instruction, InstructionArguments arguments) {
        this.instruction = instruction;
        this.arguments = arguments;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public InstructionArguments getArguments() {
        return arguments;
    }
}
