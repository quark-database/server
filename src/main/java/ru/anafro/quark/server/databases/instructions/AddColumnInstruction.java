package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.InstructionArguments;
import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.networking.Server;

public class AddColumnInstruction extends Instruction {
    public AddColumnInstruction() {
        super("add column",
            "column.add",
            InstructionParameter.general("name"),

            InstructionParameter.required("table"),
            InstructionParameter.required("definition", InstructionParameter.Types.COLUMN)
        );
    }

    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        // TODO
    }
}
