package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.networking.Server;

public class AddColumnInstruction extends Instruction {
    public AddColumnInstruction() {
        super("add column",
            "column.add",
            InstructionParameter.required("name"),

            InstructionParameter.required("table"),
            InstructionParameter.required("definition", InstructionParameter.Types.COLUMN)
        );
    }

    @Override
    public void action(Server server, InstructionResultRecorder result) {
        // TODO
    }
}
