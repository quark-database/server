package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.ql.InstructionArguments;
import ru.anafro.quark.server.databases.ql.InstructionResultRecorder;
import ru.anafro.quark.server.databases.ql.Instruction;
import ru.anafro.quark.server.databases.ql.InstructionParameter;
import ru.anafro.quark.server.networking.Server;

public class ChangeInInstruction extends Instruction {
    public ChangeInInstruction() {
        super("change in",
                "table.change",

                InstructionParameter.general("table"),

                InstructionParameter.required("if", InstructionParameter.Types.CONDITION),
                InstructionParameter.required("perform", InstructionParameter.Types.CHANGER)
        );
    }

    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {

    }
}
