package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.networking.Server;

public class StopServerInstruction extends Instruction {
    public StopServerInstruction() {
        super("stop server", "server.stop", null);
    }

    @Override
    public void action(Server server, InstructionResultRecorder result) {
        server.stop();
    }
}
