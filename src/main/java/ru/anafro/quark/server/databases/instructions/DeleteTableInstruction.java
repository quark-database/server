package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.networking.Server;

public class DeleteTableInstruction extends Instruction {
    public DeleteTableInstruction(String name, String permission, InstructionParameter generalArgument, InstructionParameter... arguments) {
        super("delete table", "table.delete", null);
    }

    @Override
    public void action(Server server, InstructionResultRecorder result) {

    }
}
