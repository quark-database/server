package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.ql.Instruction;
import ru.anafro.quark.server.databases.ql.InstructionArguments;
import ru.anafro.quark.server.databases.ql.InstructionParameter;
import ru.anafro.quark.server.databases.ql.InstructionResultRecorder;
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
