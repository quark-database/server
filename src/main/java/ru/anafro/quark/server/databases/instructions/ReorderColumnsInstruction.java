package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionArguments;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.networking.Server;

public class ReorderColumnsInstruction extends Instruction {
    public ReorderColumnsInstruction() {
        super("reorder columns in", "column.reorder",

                InstructionParameter.general("table"),

                InstructionParameter.required("order", "array")
        );
    }

    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {

    }
}
