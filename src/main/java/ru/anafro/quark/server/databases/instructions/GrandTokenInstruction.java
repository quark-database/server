package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.networking.Server;

public class GrandTokenInstruction extends Instruction {
    public GrandTokenInstruction(String name, String permission, InstructionParameter generalArgument, InstructionParameter... arguments) {
        super("grand token", "token.grand",

                InstructionParameter.required("token"),

                InstructionParameter.required("permission")
        );
    }

    @Override
    public void action(Server server, InstructionResultRecorder result) {

    }
}
