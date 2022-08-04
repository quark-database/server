package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.networking.Server;

public class ListDatabasesInstruction extends Instruction {
    public ListDatabasesInstruction(String name, String permission, InstructionParameter generalArgument, InstructionParameter... arguments) {
        super("list databases", "databases.list", null);
    }

    @Override
    public void action(Server server, InstructionResultRecorder result) {

    }
}
