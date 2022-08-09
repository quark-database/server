package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.InstructionArguments;
import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.networking.Server;

public class ClearDatabaseInstruction extends Instruction {
    public ClearDatabaseInstruction() {
        super("clear database",
                "database.clear",

                InstructionParameter.general("database")
        );
    }

    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {

    }
}
