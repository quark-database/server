package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.networking.Server;

public class UngrandTokenInstruction extends Instruction {
    public UngrandTokenInstruction(String name, String permission, InstructionParameter generalArgument, InstructionParameter... arguments) {
        super("ungrand token", "token.ungrand",

                InstructionParameter.required("token"),

                InstructionParameter.required("remove")
        );
    }

    @Override
    public void action(Server server, InstructionResultRecorder result) {

    }
}
