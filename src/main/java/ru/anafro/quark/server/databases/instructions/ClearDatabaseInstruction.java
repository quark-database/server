package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.networking.Server;

public class ClearDatabaseInstruction extends Instruction {
    public ClearDatabaseInstruction() {
        super("clear database",
                "database.clear",

                InstructionParameter.required("database")
        );
    }

    @Override
    public void action(Server server, InstructionResultRecorder result) {

    }
}
