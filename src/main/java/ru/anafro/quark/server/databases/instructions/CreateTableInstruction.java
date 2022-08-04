package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.networking.Server;

public class CreateTableInstruction extends Instruction {
    public CreateTableInstruction(String name, String permission, InstructionParameter generalArgument, InstructionParameter... arguments) {
        super("create table", "table.create",
                InstructionParameter.required("name"),

                InstructionParameter.required("columns", "array"),
                InstructionParameter.optional("database")
        );
    }

    @Override
    public void action(Server server, InstructionResultRecorder result) {

    }
}
