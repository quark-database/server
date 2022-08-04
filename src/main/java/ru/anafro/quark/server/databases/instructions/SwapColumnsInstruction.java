package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.networking.Server;

public class SwapColumnsInstruction extends Instruction {
    public SwapColumnsInstruction(String name, String permission, InstructionParameter generalArgument, InstructionParameter... arguments) {
        super("swap columns in", "columns.swap",

                InstructionParameter.required("table"),

                InstructionParameter.required("first"),
                InstructionParameter.required("second")
        );
    }

    @Override
    public void action(Server server, InstructionResultRecorder result) {

    }
}
