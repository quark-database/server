package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionArguments;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.networking.Server;

public class RenameColumnInstruction extends Instruction {
    public RenameColumnInstruction() {
        super("rename column",
                "column.rename",

                InstructionParameter.general("name"),

                InstructionParameter.required("old"),
                InstructionParameter.required("new")
        );
    }

    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {

    }
}
