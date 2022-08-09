package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionArguments;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.networking.Server;

public class DeleteFromInstruction extends Instruction {
    public DeleteFromInstruction() {
        super("delete from", "data.delete",

                InstructionParameter.general("table"),

                InstructionParameter.required("if", InstructionParameter.Types.CONDITION)
        );
    }

    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {

    }
}
