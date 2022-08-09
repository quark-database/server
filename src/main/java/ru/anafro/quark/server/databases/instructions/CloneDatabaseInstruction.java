package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.InstructionArguments;
import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.networking.Server;

public class CloneDatabaseInstruction extends Instruction {
    public CloneDatabaseInstruction() {
        super("clone database",
                "database.clone",

                InstructionParameter.general("prototype"),

                InstructionParameter.required("destination")
        );
    }

    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {

    }
}
