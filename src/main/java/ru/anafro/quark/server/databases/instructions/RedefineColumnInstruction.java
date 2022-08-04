package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.networking.Server;

public class RedefineColumnInstruction extends Instruction {
    public RedefineColumnInstruction(String name, String permission, InstructionParameter generalArgument, InstructionParameter... arguments) {
        super("redefine column", "column.redefine",

                InstructionParameter.required("name"),

                InstructionParameter.required("definition", "column")
        );
    }

    @Override
    public void action(Server server, InstructionResultRecorder result) {

    }
}
