package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionArguments;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.networking.Server;

public class CreateTokenInstruction extends Instruction {
    public CreateTokenInstruction() {
        super("create token", "token.create",

                InstructionParameter.general("token"),

                InstructionParameter.required("permissions", "array"));
    }

    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {

    }
}
