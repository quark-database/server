package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.ql.InstructionArguments;
import ru.anafro.quark.server.databases.ql.InstructionResultRecorder;
import ru.anafro.quark.server.databases.ql.Instruction;
import ru.anafro.quark.server.databases.ql.InstructionParameter;
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