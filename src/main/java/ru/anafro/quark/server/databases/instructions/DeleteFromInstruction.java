package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.networking.Server;

public class DeleteFromInstruction extends Instruction {
    public DeleteFromInstruction(String name, String permission, InstructionParameter generalArgument, InstructionParameter... arguments) {
        super("delete from", "data.delete",

                InstructionParameter.required("table"),

                InstructionParameter.required("if", InstructionParameter.Types.CONDITION)
        );
    }

    @Override
    public void action(Server server, InstructionResultRecorder result) {

    }
}
