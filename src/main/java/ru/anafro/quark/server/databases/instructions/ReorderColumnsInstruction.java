package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.networking.Server;

public class ReorderColumnsInstruction extends Instruction {
    public ReorderColumnsInstruction(String name, String permission, InstructionParameter generalArgument, InstructionParameter... arguments) {
        super("reorder columns in", "column.reorder",

                InstructionParameter.required("table"),

                InstructionParameter.required("order", "array")
        );
    }

    @Override
    public void action(Server server, InstructionResultRecorder result) {

    }
}
