package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.networking.Server;

public class CreateDatabaseInstruction extends Instruction {
    public CreateDatabaseInstruction(String name, String permission, InstructionParameter generalArgument, InstructionParameter... arguments) {
        super("create database", "database.create",

                InstructionParameter.required("name")
        );
    }

    @Override
    public void action(Server server, InstructionResultRecorder result) {

    }
}
