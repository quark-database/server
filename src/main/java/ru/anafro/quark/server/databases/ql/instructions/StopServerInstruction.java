package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.ql.InstructionArguments;
import ru.anafro.quark.server.databases.ql.InstructionResultRecorder;
import ru.anafro.quark.server.databases.ql.Instruction;
import ru.anafro.quark.server.networking.Server;

public class StopServerInstruction extends Instruction {
    public StopServerInstruction() {
        super("stop server", "server.stop");
    }

    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        server.stop();
    }
}
