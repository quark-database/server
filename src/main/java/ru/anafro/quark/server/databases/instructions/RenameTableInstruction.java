package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.networking.Server;

public class RenameTableInstruction extends Instruction {
    public RenameTableInstruction(String name, String permission, InstructionParameter generalArgument, InstructionParameter... arguments) {
        super("rename table", "table.rename",

                null,

                InstructionParameter.required("old"),
                InstructionParameter.required("new")
        );
    }

    @Override
    public void action(Server server, InstructionResultRecorder result) {

    }
}
