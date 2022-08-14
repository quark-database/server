package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.ql.InstructionArguments;
import ru.anafro.quark.server.databases.ql.InstructionResultRecorder;
import ru.anafro.quark.server.databases.ql.Instruction;
import ru.anafro.quark.server.databases.ql.InstructionParameter;
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
