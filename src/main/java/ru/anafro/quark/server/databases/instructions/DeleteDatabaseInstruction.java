package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.networking.Server;

public class DeleteDatabaseInstruction extends Instruction {
    public DeleteDatabaseInstruction(String name, String permission, InstructionParameter generalArgument, InstructionParameter... arguments) {
        super("delete database", "database.delete",

                InstructionParameter.required("name")
        );
    }

    @Override
    public void action(Server server, InstructionResultRecorder result) {

    }
}
