package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.ql.Instruction;
import ru.anafro.quark.server.databases.ql.InstructionArguments;
import ru.anafro.quark.server.databases.ql.InstructionParameter;
import ru.anafro.quark.server.databases.ql.InstructionResultRecorder;
import ru.anafro.quark.server.networking.Server;

public class SwapColumnsInstruction extends Instruction {
    public SwapColumnsInstruction() {
        super("swap columns in", "columns.swap",

                InstructionParameter.general("table"),

                InstructionParameter.required("first"),
                InstructionParameter.required("second")
        );
    }

    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {

    }
}
