package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.networking.Server;

public class ListTablesInstruction extends Instruction {
    public ListTablesInstruction(String name, String permission, InstructionParameter generalArgument, InstructionParameter... arguments) {
        super("list tables in", "table.list",

                InstructionParameter.required("database")
        );
    }

    @Override
    public void action(Server server, InstructionResultRecorder result) {

    }
}
