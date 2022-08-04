package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.networking.Server;

public class ClearTableInstruction extends Instruction {
    public ClearTableInstruction() {
        super("clear table",
                "table.clear",

                InstructionParameter.required("name")
        );
    }

    @Override
    public void action(Server server, InstructionResultRecorder result) {

    }
}
