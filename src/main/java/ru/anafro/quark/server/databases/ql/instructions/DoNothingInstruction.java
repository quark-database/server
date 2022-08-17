package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.ql.Instruction;
import ru.anafro.quark.server.databases.ql.InstructionArguments;
import ru.anafro.quark.server.databases.ql.InstructionParameter;
import ru.anafro.quark.server.databases.ql.InstructionResultRecorder;
import ru.anafro.quark.server.networking.Server;

public class DoNothingInstruction extends Instruction {
    public DoNothingInstruction() {
        super("do nothing", "server.do-nothing", InstructionParameter.general("general parameter", "?"));
    }

    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        // See? There is nothing! Because it's 'do nothing' instruction!
    }
}
