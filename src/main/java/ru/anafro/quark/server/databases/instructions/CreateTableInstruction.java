package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionArguments;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.networking.Server;

public class CreateTableInstruction extends Instruction {
    public CreateTableInstruction() {
        super("create table", "table.create",
                InstructionParameter.general("name"),

                InstructionParameter.required("columns", "array")
        );
    }

    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {

    }
}
