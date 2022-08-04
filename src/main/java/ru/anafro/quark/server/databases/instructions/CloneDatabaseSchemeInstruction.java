package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.networking.Server;

public class CloneDatabaseSchemeInstruction extends Instruction {
    public CloneDatabaseSchemeInstruction() {
        super("clone database scheme",
                "database.scheme",

                InstructionParameter.required("prototype"),

                InstructionParameter.required("destination")
        );
    }

    @Override
    public void action(Server server, InstructionResultRecorder result) {

    }
}
