package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.networking.Server;

public class ReloadServerInstruction extends Instruction {
    public ReloadServerInstruction(String name, String permission, InstructionParameter generalArgument, InstructionParameter... arguments) {
        super("reload server", "server.reload", null);
    }

    @Override
    public void action(Server server, InstructionResultRecorder result) {

    }
}
