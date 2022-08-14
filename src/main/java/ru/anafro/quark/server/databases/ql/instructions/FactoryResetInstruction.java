package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.ql.Instruction;
import ru.anafro.quark.server.databases.ql.InstructionArguments;
import ru.anafro.quark.server.databases.ql.InstructionResultRecorder;
import ru.anafro.quark.server.networking.Server;

public class FactoryResetInstruction extends Instruction {
    public FactoryResetInstruction() {
        super("factory reset", "!unsafe.factory-reset");
    }

    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        //
    }
}
