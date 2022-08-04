package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.networking.Server;

public class SelectFromInstruction extends Instruction {
    public SelectFromInstruction(String name, String permission, InstructionParameter generalArgument, InstructionParameter... arguments) {
        super("select from", "data.select",

                InstructionParameter.required("table"),

                InstructionParameter.required("condition")
        );
    }

    @Override
    public void action(Server server, InstructionResultRecorder result) {

    }
}
