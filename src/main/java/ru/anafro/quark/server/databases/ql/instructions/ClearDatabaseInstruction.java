package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.ql.InstructionArguments;
import ru.anafro.quark.server.databases.ql.InstructionResultRecorder;
import ru.anafro.quark.server.databases.ql.Instruction;
import ru.anafro.quark.server.databases.ql.InstructionParameter;
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
