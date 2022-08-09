package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.InstructionArguments;
import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionParameter;
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
