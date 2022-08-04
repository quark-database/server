package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.networking.Server;

public class CreateTokenInstruction extends Instruction {
    public CreateTokenInstruction(String name, String permission, InstructionParameter generalArgument, InstructionParameter... arguments) {
        super("create token", "token.create",

                InstructionParameter.required("token"),

                InstructionParameter.required("permissions", "array"));
    }

    @Override
    public void action(Server server, InstructionResultRecorder result) {

    }
}
