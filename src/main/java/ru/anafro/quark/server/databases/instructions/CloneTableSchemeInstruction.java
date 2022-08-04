package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.networking.Server;

public class CloneTableSchemeInstruction extends Instruction {
    public CloneTableSchemeInstruction() {
        super("clone table scheme",
                "table.scheme",

                InstructionParameter.required("prototype"),

                InstructionParameter.required("destination")
        );
    }

    @Override
    public void action(Server server, InstructionResultRecorder result) {

    }
}
