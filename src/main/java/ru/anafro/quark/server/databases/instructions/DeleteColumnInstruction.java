package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.networking.Server;

public class DeleteColumnInstruction extends Instruction {
    public DeleteColumnInstruction(String name, String permission, InstructionParameter generalArgument, InstructionParameter... arguments) {
        super("delete column", "column.delete",

                InstructionParameter.required("name"),

                InstructionParameter.required("table")
        );
    }

    @Override
    public void action(Server server, InstructionResultRecorder result) {

    }
}
